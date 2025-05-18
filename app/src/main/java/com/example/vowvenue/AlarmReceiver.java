package com.example.vowvenue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.sendNotification(context, "Emlékeztető", "Ne felejtsd el a helyszíneidet!", 2);
    }
}

