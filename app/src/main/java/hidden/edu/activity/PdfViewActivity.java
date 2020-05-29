package hidden.edu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import hidden.edu.BuildConfig;
import hidden.edu.R;
import zuo.biao.library.base.BaseActivity;

public class PdfViewActivity extends BaseActivity {


    public static Intent createIntent(AppCompatActivity context, String name) {
        return new Intent(context, PdfViewActivity.class).
                putExtra(PdfViewActivity.INTENT_TITLE, name);
    }

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view_activity);

        intent = getIntent();
        name = intent.getStringExtra(INTENT_TITLE);

        initData();

        initEvent();

        initView();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void initEvent() {
        PDFView pdfView = findViewById(R.id.pdfView);
        //File pdfFile = new File(getCacheDir() + "/testthreepdf/" + "testing.pdf");
        //Uri path = Uri.fromFile(pdfFile);
        //pdfView.fromUri(path).load();
        pdfView.setVisibility(View.VISIBLE);

        InputStream in = new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        };
        int response;
        URL url = null;
        URLConnection conn = null;
        try {
            url = new URL(name);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert url != null;
            conn = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!(conn instanceof HttpURLConnection))
            try {
                throw new IOException("Not an HTTP connection");
            } catch (IOException e) {
                e.printStackTrace();
            }


        try {
            if (BuildConfig.DEBUG && !(conn instanceof HttpURLConnection)) {
                throw new AssertionError("Assertion failed");
            }
            HttpURLConnection httpConn = null;
            if (conn instanceof HttpURLConnection) {
                httpConn = (HttpURLConnection) conn;
            }
            Objects.requireNonNull(httpConn).setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            try {
                throw new IOException("Error connecting");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream finalIn = in;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                pdfView.fromStream(finalIn)
                        .pageFitPolicy(FitPolicy.WIDTH)
                        .enableSwipe(true)
                        //pdf文档翻页是否是水平翻页，默认是左右滑动翻页
                        .swipeHorizontal(false)
                        //
                        .enableDoubletap(true)
                        //设置默认显示第0页
                        .defaultPage(0)
                        //允许在当前页面上绘制一些内容，通常在屏幕中间可见。
//             .onDraw(onDrawListener)
//                // 允许在每一页上单独绘制一个页面。只调用可见页面
//                .onDrawAll(onDrawListener)
                        //设置加载监听
                        //设置页面滑动监听
//                .onPageScroll(onPageScrollListener)
//                .onError(onErrorListener)
                        // 首次提交文档后调用。
//                .onRender(onRenderListener)
                        // 渲染风格（就像注释，颜色或表单）
                        .enableAnnotationRendering(true)
                        .password(null)
                        .scrollHandle(null)
                        // 改善低分辨率屏幕上的渲染
                        .enableAntialiasing(true)
                        // 页面间的间距。定义间距颜色，设置背景视图
                        .spacing(0)
                        // add dynamic spacing to fit each page on its own on the screen
                        .autoSpacing(false)
                        // mode to fit pages in the view
                        .pageFitPolicy(FitPolicy.WIDTH)
                        // fit each page to the view, else smaller pages are scaled relative to largest page.
                        .fitEachPage(true)
                        .load();
            }
        }.execute();
    }
}
