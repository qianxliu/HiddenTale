package hidden.edu.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

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

    @Override
    public void initEvent() {
        PDFView pdfView = findViewById(R.id.pdfView);
        //File pdfFile = new File(getCacheDir() + "/testthreepdf/" + "testing.pdf");
        //Uri path = Uri.fromFile(pdfFile);
        //pdfView.fromUri(path).load();

        pdfView.fromAsset(name)
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
}
