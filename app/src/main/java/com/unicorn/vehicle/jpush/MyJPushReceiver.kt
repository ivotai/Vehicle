package com.unicorn.vehicle.jpush

import android.content.Context
import android.content.Intent
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.unicorn.vehicle.app.Globals
import com.unicorn.vehicle.ui.CarRequisitionFra
import com.unicorn.vehicle.ui.LoginAct

class MyJPushReceiver : JPushMessageReceiver() {
//    override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance()
//            .onTagOperatorResult(context, jPushMessage)
//        super.onTagOperatorResult(context, jPushMessage)
//    }
//
//    override fun onCheckTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance()
//            .onCheckTagOperatorResult(context, jPushMessage)
//        super.onCheckTagOperatorResult(context, jPushMessage)
//    }
//
//    override fun onAliasOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance()
//            .onAliasOperatorResult(context, jPushMessage)
//        super.onAliasOperatorResult(context, jPushMessage)
//    }
//
//    override fun onMobileNumberOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
//        TagAliasOperatorHelper.getInstance()
//            .onMobileNumberOperatorResult(context, jPushMessage)
//        super.onMobileNumberOperatorResult(context, jPushMessage)
//    }

    override fun onNotifyMessageOpened(context: Context, notificationMessage: NotificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage)

//        val extra = notificationMessage.notificationExtras
//        val jsonObject = JSONObject(extra)
//        val carRequisitionId = jsonObject.getString(Key.CarRequisitionId)

        val intent1 = Intent(
            context,
            if (Globals.isLogin) CarRequisitionFra::class.java else LoginAct::class.java
        )
//        intent1.putExtra(Key.CarRequisitionId, carRequisitionId)
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent1);
    }

    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, p1)
    }

    override fun onMessage(p0: Context?, p1: CustomMessage?) {
        super.onMessage(p0, p1)
    }
}