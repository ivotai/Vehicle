package com.unicorn.vehicle.ui

import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.ToastUtils
import com.unicorn.vehicle.R
import com.unicorn.vehicle.app.*
import com.unicorn.vehicle.app.helper.DialogHelper
import com.unicorn.vehicle.data.model.LoggedUser
import com.unicorn.vehicle.data.model.param.UserLoginParam
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.act_login.*

class LoginAct : BaseAct() {

    override fun initViews() {
        fun restoreUserInfo() = with(AppInfo) {
            etLoginStr.setText(LoginStr)
            etUserPwd.setText(UserPwd)
        }
        restoreUserInfo()
    }

    override fun bindIntent() {
        rtvLogin.safeClicks().subscribe { login() }
    }

    private fun login() {
        fun saveUserInfo() = with(AppInfo) {
            LoginStr = etLoginStr.trimText()
            UserPwd = etUserPwd.trimText()
        }

        val mask = DialogHelper.showMask(this)
        val userLoginParam = UserLoginParam(
            loginStr = etLoginStr.trimText(),
            userPwd = EncryptUtils.encryptMD5ToString(etUserPwd.trimText())
        )
        api.login(userLoginParam = userLoginParam)
            .observeOnMain(this)
            .subscribeBy(
                onSuccess = { response ->
                    mask.dismiss()
                    if (response.failed) return@subscribeBy
                    if (response.data.role != 1) {
                        ToastUtils.showShort("无法登录，您没有审批权限")
                        return@subscribeBy
                    }
                    loggedUser = response.data
                    isLogin = true
                    saveUserInfo()
                    toActAndFinish(MainAct::class.java)
                    setAliasAndTags(response.data)
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    private fun setAliasAndTags(loggedUser: LoggedUser) {
        JPushInterface.setAliasAndTags(
            this, loggedUser.uid, setOf(loggedUser.role.toString())
        ) { _, _, _ -> }
    }

    override val layoutId = R.layout.act_login

}
