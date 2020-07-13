package qian.xin.library.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


import qian.xin.library.R;
import qian.xin.library.base.BaseActivity;
import qian.xin.library.interfaces.OnBottomDragListener;
import qian.xin.library.util.Log;
import qian.xin.library.util.StringUtil;

/*
 * 通用网页Activity
 *
 * @author Lemon
 * @use toActivity(WebViewActivity.createIntent ( ...));
 */
public class WebViewActivity extends BaseActivity implements OnBottomDragListener, OnClickListener {
    public static final String TAG = "WebViewActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String INTENT_URL = "INTENT_URL";

    /*获取启动这个Activity的Intent
     * @param title
     * @param url
     */
    public static Intent createIntent(AppCompatActivity context, String title, String url) {
        return new Intent(context, WebViewActivity.class).
                putExtra(WebViewActivity.INTENT_TITLE, title).
                putExtra(WebViewActivity.INTENT_URL, url);
    }

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity, this);//传this是为了全局滑动返回

        url = StringUtil.getCorrectUrl(getIntent().getStringExtra(INTENT_URL));
        if (!StringUtil.isNotEmpty(url, true)) {
            Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
            enterAnim = exitAnim = R.anim.null_anim;
            finish();
            return;
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ProgressBar pbWebView;
    private WebView wvWebView;

    @Override
    public void initView() {
        //autoSetTitle();

        pbWebView = findView(R.id.pbWebView);
        wvWebView = findView(R.id.wvWebView);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void initData() {

        WebSettings webSettings = wvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wvWebView.requestFocus();

        // 设置setWebChromeClient对象
        wvWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                assert tvBaseTitle != null;
                tvBaseTitle.setText(StringUtil.getTrimedString(title));
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbWebView.setProgress(newProgress);
            }
        });

        wvWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wvWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                assert tvBaseTitle != null;
                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getUrl()));
                pbWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                assert tvBaseTitle != null;
                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getTitle()));
                pbWebView.setVisibility(View.GONE);
            }
        });

        wvWebView.loadUrl(url);
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {

        assert tvBaseTitle != null;
        tvBaseTitle.setOnClickListener(this);

    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            if (wvWebView.canGoForward()) {
                wvWebView.goForward();
            }
            return;
        }
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvBaseTitle) {
            toActivity(EditTextInfoWindow.createIntent(context
                    , EditTextInfoWindow.TYPE_WEBSITE
                    , StringUtil.getTrimedString(tvBaseTitle)
                    , wvWebView.getUrl()),
                    REQUEST_TO_EDIT_TEXT_WINDOW, false);
        }
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onBackPressed() {
        if (wvWebView.canGoBack()) {
            wvWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onPause() {
        super.onPause();
        wvWebView.onPause();
    }

    @Override
    protected void onResume() {
        wvWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (wvWebView != null) {
            wvWebView.destroy();
            wvWebView = null;
        }
        super.onDestroy();
    }

    protected static final int REQUEST_TO_EDIT_TEXT_WINDOW = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_TO_EDIT_TEXT_WINDOW) {
            if (data != null) {
                wvWebView.loadUrl(StringUtil.getCorrectUrl(data.getStringExtra(EditTextInfoWindow.RESULT_VALUE)));
            }
        }
    }

    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}