package com.annonymouss.sexeducation.activities;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.annonymouss.sexeducation.recievers.NetworkChangeReceiver;

import org.greenrobot.eventbus.EventBus;

import io.fabric.sdk.android.Fabric;

/**
 * Created by aung on 12/2/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    NetworkChangeReceiver mInternetReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mInternetReceiver = new NetworkChangeReceiver();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mInternetReceiver,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mInternetReceiver);
        EventBus.getDefault().unregister(this);
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


}
