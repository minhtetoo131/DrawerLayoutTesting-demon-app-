package com.annonymouss.sexeducation.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.annonymouss.sexeducation.R;
import com.annonymouss.sexeducation.events.ErrorEvents;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    EditText tv;

    CallbackManager callbackManager;
    LoginButton loginButton;
    private FirebaseAuth fbAuth;

    private ProgressDialog mProgressDialog;

    public static Intent newIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this, this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Logging into facebook");

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                exchangeAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Snackbar.make(loginButton,"You cancel Authentication!",Snackbar.LENGTH_INDEFINITE).show();
                mProgressDialog.hide();
            }

            @Override
            public void onError(FacebookException exception) {
                Snackbar.make(loginButton,"Facebook Login error!",Snackbar.LENGTH_INDEFINITE).show();
                mProgressDialog.hide();
            }
        });

        fbAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void exchangeAccessToken(AccessToken token) {
        AuthCredential credential =
                FacebookAuthProvider.getCredential(token.getToken());
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(loginButton,"Authentication fail!",Snackbar.LENGTH_INDEFINITE).show();
                        } else {
                            startActivity(MainActivity.newIntent(getApplicationContext()));
                            finish();
                        }
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void internetOffEvent(ErrorEvents.InternetOffEvent internetOffEvent) {
        Snackbar.make(toolbar, internetOffEvent.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }

    @OnClick(R.id.login_button)
    public void onTapLogin(View view){
        mProgressDialog.show();
    }
}