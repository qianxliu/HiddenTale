package hidden.edu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import hidden.edu.R;
import hidden.edu.service.MusicService;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.DownloadUtil;

public class PdfViewActivity extends BaseActivity implements OnLoadCompleteListener {


    public static Intent createIntent(AppCompatActivity context, String name, String no) {
        Intent intent = new Intent(context, PdfViewActivity.class);
        intent.putExtra(PdfViewActivity.INTENT_TITLE, name);
        intent.putExtra(PdfViewActivity.INTENT_ID, no);
        return intent;
    }

    private String name;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.pdf_view_activity);

        intent = getIntent();
        name = intent.getStringExtra(INTENT_TITLE);
        id = intent.getStringExtra(INTENT_ID);

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


    @Override
    public void initEvent() {


        String n;
        File file;


        PDFView pdfView = findViewById(R.id.pdfView);
        //File pdfFile = new File(getCacheDir() + "/testthreepdf/" + "testing.pdf");
        //Uri path = Uri.fromFile(pdfFile);
        //pdfView.fromUri(path).load();
        pdfView.setVisibility(View.VISIBLE);

        n = "klfskjkf" + id;
        file = new File(DataKeeper.tempPath + n);
        if (!file.exists()) {
            file = DownloadUtil.downLoadFile(this, "/temp/" + n, name);
        }

        pdfView.fromFile(file)
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
                .onLoad(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        String n;
        File file;
        int flag = 1;
        if (Integer.parseInt(id) != 1) {
            if (Integer.parseInt(id) < 9)
                flag = 2;
            else if (Integer.parseInt(id) < 11)
                flag = 3;
            else if (Integer.parseInt(id) < 16)
                flag = 4;
            else if (Integer.parseInt(id) < 18)
                flag = 5;
            else if (Integer.parseInt(id) < 22)
                flag = 6;
        }

        n = "mklkajg" + flag;
        file = new File(DataKeeper.tempPath + n);
        if (!file.exists()) {
            String url = null;
            if (Integer.parseInt(id) == 1) {
                url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/qin.aac"; // your URL here
            } else {
                if (Integer.parseInt(id) < 9)
                    url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/tang.aac";
                else if (Integer.parseInt(id) < 11)
                    url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/zhou.aac";
                else if (Integer.parseInt(id) < 16)
                    url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/xian.aac";
                else if (Integer.parseInt(id) < 18)
                    url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/wei.mp3";
                else if (Integer.parseInt(id) < 22)
                    url = "http://git.nwu.edu.cn/2018104171/pdf/raw/master/dang.aac";
            }
            assert url != null;
            file = DownloadUtil.downLoadFile(this, "/temp/" + n, url);
        }

        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(MusicService.FILE_PATH, Uri.parse(file.getAbsolutePath()));
        startService(intent);
    }
}


