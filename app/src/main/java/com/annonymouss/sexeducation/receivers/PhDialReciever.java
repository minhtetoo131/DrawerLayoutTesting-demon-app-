package com.annonymouss.sexeducation.receivers;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.annonymouss.sexeducation.activities.MainActivity;

public class PhDialReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber =  intent.getExtras().getString(android.content.Intent.EXTRA_PHONE_NUMBER);

            if (phoneNumber.equals("*8888#")) {
                PackageManager p = context.getPackageManager();
                ComponentName componentName = new ComponentName(context,MainActivity.class);
                p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }

        }
    }
}
