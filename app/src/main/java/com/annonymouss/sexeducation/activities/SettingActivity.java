package com.annonymouss.sexeducation.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.events.ErrorEvents;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.switch_dialing_only)Switch mSwitchDial;

    public static Intent newIntent(Context context){
        return new Intent(context,SettingActivity.class);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this,this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hide app from launcher");
        builder.setMessage("app ကို *8888# နွိပ္ချင္းဖျင့္သာဖြင့္နိုင္ေတာ့မည္ဖျစ္သည္");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PackageManager p = getPackageManager();
                ComponentName componentName = new ComponentName(getApplicationContext(), MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                getSharedPreferences("Hide app",MODE_PRIVATE).edit().putBoolean("hide",true).commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PackageManager p = getPackageManager();
                ComponentName componentName = new ComponentName(getApplicationContext(),MainActivity.class);
                p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                getSharedPreferences("Hide app",MODE_PRIVATE).edit().putBoolean("hide",false).commit();
                mSwitchDial.setChecked(false);
            }
        });

        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);


        mSwitchDial.setChecked(getSharedPreferences("Hide app",MODE_PRIVATE).getBoolean("hide",false));
        mSwitchDial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    dialog.show();
                }else{
                    PackageManager p = getPackageManager();
                    ComponentName componentName = new ComponentName(getApplicationContext(),MainActivity.class);
                    p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    getSharedPreferences("Hide app",MODE_PRIVATE).edit().putBoolean("hide",false).commit();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void internetOffEvent(ErrorEvents.InternetOffEvent internetOffEvent) {
        Snackbar.make(toolbar, internetOffEvent.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences("Hide app",MODE_PRIVATE).getBoolean("hide",false)){
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        if (getSharedPreferences("Hide app",MODE_PRIVATE).getBoolean("hide",false)){
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
        super.onDestroy();
    }
}
