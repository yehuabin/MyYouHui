package com.yhb.myyouhui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
            actionBar.setTitle("");

            webView = (WebView) findViewById(R.id.webView);



            webView.setWebViewClient(new WebViewClient()  );
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);

            webView.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5MzYzNzg4MQ==&mid=203002877&idx=1&sn=411517ea5fc4a745cb344f52015ad786&mpshare=1&scene=1&srcid=1205whF77IVhnuXSKOxdgHaB&key=c0a5de018bf99cfb00568a31454a1eaa4529c10bc5d2001f0b0647f00bd3932113396bea3394c8480a767e766f5a518fc097e6b53bb59fd771aa0af351311fccd7c8379501c23fecd127852fb0c12a4a&ascene=1&uin=Mjk0MjgxNDU2MA%3D%3D&devicetype=Windows-QQBrowser&version=61030006&pass_ticket=UmuAS2Ol3%2FR4lolG7F4gNtLFQnGm0i426exyNu6B4GxhdoQbfIs8dGkGz80eB5XM");
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

