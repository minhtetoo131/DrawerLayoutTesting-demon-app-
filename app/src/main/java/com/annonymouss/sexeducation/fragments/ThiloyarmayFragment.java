package com.annonymouss.sexeducation.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.annonymouss.sexeducation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThiloyarmayFragment extends BaseFragment {
    @BindView(R.id.webview_thiloyarmay)
    WebView wvThiloyarmay;
    private ProgressDialog mProgressDialog;

    public ThiloyarmayFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("loading page");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_thiloyarmay, container, false);
        ButterKnife.bind(this, fragmentView);
        wvThiloyarmay.getSettings().setJavaScriptEnabled(true);
        wvThiloyarmay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://www.thiloyarmay.org")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressDialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressDialog.hide();
                super.onPageFinished(view, url);
            }
        });
        wvThiloyarmay.loadUrl("http://www.thiloyarmay.org");
        return fragmentView;
    }

    public boolean canGoBack() {
        return wvThiloyarmay.canGoBack();
    }

    public void goBack() {
        wvThiloyarmay.goBack();
    }
}
