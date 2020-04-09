package com.unicorn.vehicle.app.helper

import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.vehicle.app.di.Holder
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.StringQuery
import com.unicorn.vehicle.ui.base.BaseAct
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.Call
import java.io.File

object UpdateHelper {

    fun checkUpdate(activity: BaseAct) {
        val mask = DialogHelper.showMask(activity)
        val api = Holder.appComponent.simpleApi()
        api.checkAppVersion(StringQuery(key = AppUtils.getAppVersionName()))
            .observeOnMain(activity)
            .subscribeBy(
                onSuccess = {
                    mask.dismiss()
                    if (it.data.needUpdate)
                        download(activity, apkUrl = it.data.url)
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    private fun download(activity: BaseAct, apkUrl: String) {
        val mask = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        OkHttpUtils
            .get()
            .url(apkUrl)
            .build()
            .execute(object : FileCallBack(
                Holder.appComponent.context().cacheDir.path,
                "Vehicle.apk"
            ) {
                override fun onResponse(response: File, id: Int) {
                    mask.dismiss()
                    AppUtils.installApp(response)
                }

                override fun inProgress(progress: Float, total: Long, id: Int) {
                    val p = (100 * progress).toInt()
                    mask.setProgress(p)
                }

                override fun onError(call: Call?, e: Exception?, id: Int) {
                    mask.dismiss()
                }
            })
    }

}