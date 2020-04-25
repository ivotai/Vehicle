package com.unicorn.vehicle.app.helper

import android.app.Activity
import com.blankj.utilcode.util.AppUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.unicorn.vehicle.app.di.Holder
import com.unicorn.vehicle.app.observeOnMain
import com.unicorn.vehicle.data.model.param.StringQuery
import com.unicorn.vehicle.ui.base.BaseAct
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import rxhttp.wrapper.param.RxHttp
import java.io.File

object UpdateHelper {

    fun checkUpdate(activity: BaseAct) {
        val mask = DialogHelper.showMask(activity)
        val api = Holder.appComponent.simpleApi()
        api.checkAppVersion(StringQuery(key = AppUtils.getAppVersionName()))
            .observeOnMain(activity)
            .subscribeBy(
                onSuccess = { response ->
                    mask.dismiss()
                    if (response.failed) return@subscribeBy
                    if (response.data.needUpdate)
                        download(activity = activity, apkUrl = response.data.url)
                },
                onError = {
                    mask.dismiss()
                }
            )
    }

    private fun download(activity: Activity, apkUrl: String) {
        val progressMask = KProgressHUD.create(activity)
            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
            .setCancellable(true)
            .setDimAmount(0.5f)
            .setMaxProgress(100)
            .show()
        val destPath = "${Holder.appComponent.context().cacheDir.path}/vehicle.apk"
//        val testUrl = "http://renjiawen1988.vicp.cc:33333/smartkeybox/api/file/LatestAppDownload"
        RxHttp.get(apkUrl)
            .asDownload(
                destPath,
                Consumer { progressMask.setProgress(it.progress) },
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(
                onNext = {
                    progressMask.dismiss()
                    AppUtils.installApp(File(it))
                },
                onError = {
                    progressMask.dismiss()
                }
            )

//        OkHttpUtils
//            .get()
//            .url(apkUrl)
//            .build()
//            .execute(object : FileCallBack(
//                Holder.appComponent.context().cacheDir.path,
//                "Vehicle.apk"
//            ) {
//                override fun onResponse(response: File, id: Int) {
//                    progressMask.dismiss()
//                    AppUtils.installApp(response)
//                }
//
//                override fun inProgress(progress: Float, total: Long, id: Int) {
//                    val p = (100 * progress).toInt()
//                    progressMask.setProgress(p)
//                }
//
//                override fun onError(call: Call?, e: Exception?, id: Int) {
//                    progressMask.dismiss()
//                }
//            })
    }

}