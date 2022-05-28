/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maxx.oschinanews.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.maxx.oschinanews.R;
import com.maxx.oschinanews.api.RetrofitClient;
import com.maxx.oschinanews.model.Token;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Simple activity that shows the requested HTML page. This utility is
 * purposefully very limited in what it supports, including no network or
 * JavaScript.
 */
public class HtmlViewer extends Activity {
    private static final String TAG = "HTMLViewer";

    private WebView mWebView;
    private View mLoading;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer);

        mWebView = findViewById(R.id.webview);
        mLoading = findViewById(R.id.loading);

        mWebView.setWebChromeClient(new ChromeClient());
        //处理网页导航
        mWebView.setWebViewClient(new ViewClient());

        //setInitialScale:初始缩放等级 参数为25表示为25%，最小缩放等级，100代表不缩放
        mWebView.setInitialScale(100);

        //TODO 1:add addJavascriptInterface
        mWebView.addJavascriptInterface(new WebAppInterface(), "handler");

        //将 JavaScript 代码绑定到 Android 代码:这会为在 WebView 中运行的 JavaScript 创建名为 Android 的接口
        //mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        /*public class WebAppInterface {
            Context mContext;

            *//** Instantiate the interface and set the context *//*
            WebAppInterface(Context c) {
                mContext = c;
            }

            *//** Show a toast from the web page *//*
            @JavascriptInterface
            public void showToast(String toast) {
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            }
        }*/

        WebSettings s = mWebView.getSettings();
        s.setUseWideViewPort(true);
        s.setSupportZoom(true);
        s.setBuiltInZoomControls(true);
        s.setDisplayZoomControls(false);

        //Deprecated Usages
        //s.setSavePassword(false);
        //s.setSaveFormData(false);

        // solved net::ERR_CACHE_MISS
        //s.setBlockNetworkLoads(true);
        s.setAllowFileAccess(true);

        //开启AppCache支持:WebSettings的setAppCacheEnabled和setAppCachePath都必须要调用才行
        s.setAppCacheEnabled(true);
        // 把内部私有缓存目录'/data/data/包名/cache/'作为WebView的AppCache的存储路径
        String cachePath = getApplicationContext().getCacheDir().getPath();
        s.setAppCachePath(cachePath);

        // 如果您正在开发专为 Android 应用中的 WebView 设计的 Web 应用，则可以使用
        // setUserAgentString定义自定义用户代理字符串，然后在网页中查询自定义用户代理，以验证请求网页的客户端实际上是您的 Android 应用
        //s.setUserAgentString("");

        //s.setAllowContentAccess(true);

        // default:WebSettings.LOAD_DEFAULT
        // LOAD_DEFAULT优先从缓存获取未过期的资源加载，否则从网络加载资源
        // LOAD_CACHE_ELSE_NETWORK优先从缓存获取资源加载，即使缓存已过期，否则从网络加载资源
        s.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Javascript is purposely disabled, so that nothing can be
        // automatically run.
        s.setJavaScriptEnabled(true);
        s.setDefaultTextEncodingName("utf-8");

        mIntent = getIntent();
        setBackButton();
        loadUrl();
    }

    // TODO 2:add JavascriptInterface
    class WebAppInterface {

        @JavascriptInterface
        public void show(String data) { // 这里的data就webview加载的内容，即使页面跳转页都可以获取到，这样就可以做自己的处理了
            //new AlertDialog.Builder(HtmlViewer.this).setMessage(data).create().show();
            Log.d("Maxx", "data=" + data);
        }
    }

    private void loadUrl() {
        if (mIntent.hasExtra(Intent.EXTRA_TITLE)) {
            setTitle(mIntent.getStringExtra(Intent.EXTRA_TITLE));
        }
        Log.d("Maxx", "loadUrl=" + String.valueOf(mIntent.getData()));
        mWebView.loadUrl(String.valueOf(mIntent.getData()));
    }

    private void setBackButton() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void toBackPage(View view) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    public void toForwardPage(View view) {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!getIntent().hasExtra(Intent.EXTRA_TITLE)) {
                HtmlViewer.this.setTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    private class ViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            //TODO 3:add javascript to use JavascriptInterface
            //mWebView.loadUrl("javascript:window.handler.show(document.getElementsByTagName('pre')[0].innerHTML);");
            mLoading.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if ("www.oschina.net".equals(request.getUrl().getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }

            // 获取OSChina Token
            // http://192.168.0.106/?code=iap9LL&state=
            String originUrl = request.getUrl().toString();
            if (originUrl.startsWith("http://192.168.0.106")) {
                String getCode = originUrl.substring(originUrl.indexOf("code=")).substring(5, 11);
                Log.d("Maxx", "getCode=" + getCode);
                //String BASE_TOKEN_URL = "https://www.oschina.net/action/openapi/token?client_id=GCqPUbrIaoe7nUScRlet&client_secret=YFJDhiYsnyI6qW8nzdAHYIPocZsPEff1&grant_type=authorization_code&redirect_uri=http://192.168.0.106/&dataType=json&code=";
                //mWebView.loadUrl(BASE_TOKEN_URL+getCode);

                RetrofitClient.getInstance()
                        .getApi()
                        .getToken(Constants.OSCHINA_CLIENT_ID, Constants.OSCHINA_CLIENT_SECRET, "authorization_code", Constants.OSCHINA_REDIRECT_URL, getCode)
                        .enqueue(new Callback<Token>() {
                            @Override
                            public void onResponse(Call<Token> call, Response<Token> response) {
                                Log.d("Maxx", response.body().toString());
                                NetworkUtil.setToken(HtmlViewer.this, response.body().access_token);
                                Toast.makeText(HtmlViewer.this, "Get Token Success!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Token> call, Throwable t) {

                            }
                        });
                return false;
            }

            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            /*boolean isOpenWebViewer = request.getUrl().toString().startsWith("https://www.oschina.net");
            if (isOpenWebViewer) {
                mWebView.loadUrl(request.getUrl().toString());
                return true;
            }*/

            String url = request.getUrl().toString();
            Intent intent;
            // Perform generic parsing of the URI to turn it into an Intent.
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException ex) {
                Log.w(TAG, "Bad URI " + url + ": " + ex.getMessage());
                Toast.makeText(HtmlViewer.this,
                        R.string.cannot_open_link, Toast.LENGTH_SHORT).show();
                return true;
            }
            // Sanitize the Intent, ensuring web pages can not bypass browser
            // security (only access to BROWSABLE activities).
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            Intent selector = intent.getSelector();
            if (selector != null) {
                selector.addCategory(Intent.CATEGORY_BROWSABLE);
                selector.setComponent(null);
            }
            // Pass the package name as application ID so that the intent from the
            // same application can be opened in the same tab.
            intent.putExtra(Browser.EXTRA_APPLICATION_ID,
                    view.getContext().getPackageName());
            try {
                view.getContext().startActivity(intent);
            } catch (ActivityNotFoundException | SecurityException ex) {
                Log.w(TAG, "No application can handle " + url);
                Toast.makeText(HtmlViewer.this,
                        R.string.cannot_open_link, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          WebResourceRequest request) {
            final Uri uri = request.getUrl();
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())
                    && uri.getPath().endsWith(".gz")) {
                Log.d(TAG, "Trying to decompress " + uri + " on the fly");
                try {
                    final InputStream in = new GZIPInputStream(
                            getContentResolver().openInputStream(uri));
                    final WebResourceResponse resp = new WebResourceResponse(
                            getIntent().getType(), "utf-8", in);
                    resp.setStatusCodeAndReasonPhrase(200, "OK");
                    return resp;
                } catch (IOException e) {
                    Log.w(TAG, "Failed to decompress; falling back", e);
                }
            }
            return null;
        }
    }
}