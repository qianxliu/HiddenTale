package hidden.edu.activity

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import com.github.barteksc.pdfviewer.BuildConfig
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import hidden.edu.R
import qian.xin.library.base.BaseActivity
import qian.xin.library.interfaces.Presenter
import qian.xin.library.util.DataKeeper
import qian.xin.library.util.DownloadUtil
import java.io.File

class PdfViewActivity : BaseActivity() {
    private var name: String? = null
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainTabActivity.mediaPlayer.release()
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.pdf_view_activity)
        intent = getIntent()
        name = intent.getStringExtra(Presenter.INTENT_TITLE)
        id = intent.getStringExtra(Presenter.INTENT_ID)
        initPdf()
        initData()
        initEvent()
        initView()
    }

    override fun initView() {}
    override fun initData() {}
    override fun initEvent() {
        val s: String
        var f: File
        var flag = 1
        if (id!!.toInt() != 1) {
            if (id!!.toInt() < 9) flag = 2 else if (id!!.toInt() < 11) flag = 3 else if (id!!.toInt() < 16) flag = 4 else if (id!!.toInt() < 18) flag = 5 else if (id!!.toInt() < 22) flag = 6
        }
        s = "mklkajg$flag"
        f = File(DataKeeper.tempPath + s)
        if (!f.exists()) {
            var url: String? = null
            //!! !null 非空
            if (id!!.toInt() == 1) {
                url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/qin.aac" // your URL here
            } else {
                if (id!!.toInt() < 9) url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/tang.aac" else if (id!!.toInt() < 11) url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/zhou.aac" else if (id!!.toInt() < 16) url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/xian.aac" else if (id!!.toInt() < 18) url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/wei.mp3" else if (id!!.toInt() < 22) url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/dang.aac"
            }
            if (BuildConfig.DEBUG && url == null) {
                error("Assertion failed")
            }
            f = DownloadUtil.downLoadFile("/temp/$s", url)
        }
        try {
            MainTabActivity.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            MainTabActivity.mediaPlayer.setDataSource(f.absolutePath)
            MainTabActivity.mediaPlayer.isLooping = true // Set looping
            MainTabActivity.mediaPlayer.prepare() // might take long! (for buffering, etc)
            MainTabActivity.mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
            showShortToast("未联网！！！")
        }
    }

    private fun initPdf() {
        var file: File
        /*
        Intent intent = new Intent(PdfViewActivity.this, MusicService.class);
        intent.putExtra("path", file.getAbsolutePath());
        MusicService.enqueueWork(context, intent);
         */

        val pdfView = findViewById<PDFView>(R.id.pdfView)
        //File pdfFile = new File(getCacheDir() + "/testthreepdf/" + "testing.pdf");
        //Uri path = Uri.fromFile(pdfFile);
        //pdfView.fromUri(path).load();
        pdfView.visibility = View.VISIBLE
        val n = "klfskjkf$id"
        file = File(DataKeeper.tempPath + n)
        if (!file.exists()) {
            file = DownloadUtil.downLoadFile("/temp/$n", name)
        }
        pdfView.fromFile(file)
                .pageFitPolicy(FitPolicy.WIDTH)
                .enableSwipe(true) //pdf文档翻页是否是水平翻页，默认是左右滑动翻页
                .swipeHorizontal(false) //
                .enableDoubletap(true) //设置默认显示第0页
                .defaultPage(0) //允许在当前页面上绘制一些内容，通常在屏幕中间可见。
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
                .scrollHandle(null) // 改善低分辨率屏幕上的渲染
                .enableAntialiasing(true) // 页面间的间距。定义间距颜色，设置背景视图
                .spacing(0) // add dynamic spacing to fit each page on its own on the screen
                .autoSpacing(false) // mode to fit pages in the view
                .pageFitPolicy(FitPolicy.WIDTH) // fit each page to the view, else smaller pages are scaled relative to largest page.
                .fitEachPage(true)
                .load()
    }

    override fun onDestroy() {
        MainTabActivity.mediaPlayer.release()
        MainTabActivity.mediaPlayer = MediaPlayer()
        (Runnable {
            if (!MainTabActivity.mainFile.exists()) {
                MainTabActivity.mainFile = DownloadUtil.downLoadFile("/temp/" + "mainFile", "https://git.nwu.edu.cn/2018104171/pdf/raw/master/main.mp3")
            }
            try {
                MainTabActivity.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                MainTabActivity.mediaPlayer.setDataSource(MainTabActivity.mainFile.absolutePath)
                MainTabActivity.mediaPlayer.isLooping = true // Set looping
                MainTabActivity.mediaPlayer.prepare()
                MainTabActivity.mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
                showShortToast("未联网！！！")
            }
        }).run()

        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun createIntent(context: BaseActivity, name: String, no: String): Intent {
            val intent = Intent(context, PdfViewActivity::class.java)
            intent.putExtra(Presenter.INTENT_TITLE, name)
            intent.putExtra(Presenter.INTENT_ID, no)
            return intent
        }
    }
}