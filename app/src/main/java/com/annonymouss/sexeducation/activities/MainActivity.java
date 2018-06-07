package com.annonymouss.sexeducation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.facebook.login.LoginManager;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.events.ErrorEvents;
import com.annonymouss.sexeducation.fragments.AnatomyFragment;

import io.fabric.sdk.android.Fabric;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    private View headerLayout;
    private Button btnLogout;

    private FirebaseUser currentLoginUser;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(LoginActivity.newIntent(getApplicationContext()));
            finish();
        }

        currentLoginUser = FirebaseAuth.getInstance().getCurrentUser();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        headerLayout = navigationView.getHeaderView(0);
        btnLogout = headerLayout.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);


        if (currentLoginUser != null && currentLoginUser.getPhotoUrl() != null) {

            ImageView ivUserProfile = headerLayout.findViewById(R.id.iv_login_user_bg);

            Glide.with(getApplicationContext())
                    .load(currentLoginUser.getPhotoUrl())
                    .into(ivUserProfile);
        }
        navigationView.setNavigationItemSelectedListener(this);
//        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        loadFragment(new AnatomyFragment());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void internetOffEvent(ErrorEvents.InternetOffEvent internetOffEvent) {
        Snackbar.make(toolbar, internetOffEvent.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(LoginActivity.newIntent(getApplicationContext()));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_anatomy:
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "anatomy" + "clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_physiology:
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "menu_physiology" + "clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_dr_tint_swe:
                drawerLayout.closeDrawer(GravityCompat.START);
                Toast.makeText(getApplicationContext(), "menu_dr_tint_swe" + "clicked", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
//                .addToBackStack(null)
                .commit();
    }
}
