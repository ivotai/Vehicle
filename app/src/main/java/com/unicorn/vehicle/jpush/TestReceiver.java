package com.unicorn.vehicle.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent = new Intent(context, PushService.class);
        context.startService(pushintent);
    }
}