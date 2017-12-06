package com.yhb.myyouhui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yhb.myyouhui.model.CookieModel;
import com.yhb.myyouhui.provider.BmobDataProvider;

public class BroswerActivity extends BaseActivity {
        private static final String TAG = "BroswerActivity";
        WebView webView;

        @Override
        protected int getLayoutId() {
            return R.layout.activity_broswer;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("MY优惠使用帮助");

            webView = (WebView) findViewById(R.id.webView);



            webView.setWebViewClient(new WebViewClient()  );
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);

            BmobDataProvider.loadHelp(new BmobDataProvider.LoadCookieCallBack() {
                @Override
                public void execute(CookieModel cookieModel) {
                    if (cookieModel.getRemark()!=null&&cookieModel.getRemark().length()>0)
                    {
                        webView.loadUrl(cookieModel.getRemark());
                    }
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

