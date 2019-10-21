package com.unicorn.vehicle.jpush

import android.content.Context
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver

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

    override fun onNotifyMessageOpened(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageOpened(p0, p1)
    }

    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        super.onNotifyMessageArrived(p0, p1)
    }

    override fun onMessage(p0: Context?, p1: CustomMessage?) {
        super.onMessage(p0, p1)
    }
}