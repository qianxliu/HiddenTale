package hidden.edu.activity

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import hidden.edu.R
import hidden.edu.activity.MainTabActivity.Companion.mainFile
import hidden.edu.activity.MainTabActivity.Companion.mediaPlayer
import qian.xin.library.base.BaseActivity
import qian.xin.library.interfaces.Presenter
import qian.xin.library.util.DataKeeper
import java.io.File
import java.lang.Integer.parseInt


class PdfViewActivity : BaseActivity() {
    private var name: String? = null
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.pdf_view_activity)
        intent = getIntent()
        name = intent.getStringExtra(Presenter.INTENT_TITLE)
        id = intent.getStringExtra(Presenter.INTENT_ID)
        initData()
        FileDownloader.setup(context)
        initPdf()
        initEvent()
        initView()
    }

    override fun initView() {}
    override fun initData() {}

    override fun initEvent() {
        val string: String
        val file: File
        var flag = 1
        if (id!!.toInt() != 1) {
            if (id!!.toInt() < 9) flag = 2 else if (id!!.toInt() < 11) flag = 3 else if (id!!.toInt() < 16) flag = 4 else if (id!!.toInt() < 18) flag = 5 else if (id!!.toInt() < 22) flag = 6
        }
        string = "mklkajg$flag"
        file = File(DataKeeper.tempPath + string)
        if (!file.exists()) {
            var url: String? = null
            //!! !null 非空
            when (flag) {
                1 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/qin.aac"
                2 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/tang.aac"
                3 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/zhou.aac"
                4 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/xian.aac"
                5 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/wei.mp3"
                6 -> url = "https://git.nwu.edu.cn/2018104171/pdf/raw/master/dang.aac"
            }
            FileDownloader.getImpl().create(url)
                    .setPath(file.path, false)
                    .setListener(object : FileDownloadListener() {
                        override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun connected(task: BaseDownloadTask, etag: String, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}
                        override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun blockComplete(task: BaseDownloadTask) {}
                        override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {}
                        override fun completed(task: BaseDownloadTask) {
                            //Must add for init
                            mediaPlayer = MediaPlayer()
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                            mediaPlayer.setDataSource(file.absolutePath)
                            mediaPlayer.isLooping = true // Set looping
                            mediaPlayer.prepare() // might take long! (for buffering, etc)
                            mediaPlayer.start()
                        }

                        override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun error(task: BaseDownloadTask, e: Throwable) {}
                        override fun warn(task: BaseDownloadTask) {}
                    }).start()
        } else {
            //Must add for init
            mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(file.absolutePath)
            mediaPlayer.isLooping = true // Set looping
            mediaPlayer.prepare() // might take long! (for buffering, etc)
            mediaPlayer.start()
        }
    }


    private fun initPdf() {
        val file: File
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

        var i = parseInt(id)
        i--

        val n = "klfskjkf$i"
        file = File(DataKeeper.tempPath + n)

        if (!file.exists()) {
            FileDownloader.getImpl().create(name)
                    .setPath(file.absolutePath, false)
                    .setListener(object : FileDownloadListener() {
                        override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun connected(task: BaseDownloadTask, etag: String, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}
                        override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun blockComplete(task: BaseDownloadTask) {}
                        override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {}
                        override fun completed(task: BaseDownloadTask) {
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

                        override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                        override fun error(task: BaseDownloadTask, e: Throwable) {}
                        override fun warn(task: BaseDownloadTask) {}
                    }).start()
        } else {
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
    }

    override fun onDestroy() {
        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mainFile.exists()) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(MainTabActivity.mainFile.absolutePath)
            mediaPlayer.prepare() // might take long! (for buffering, etc)
            mediaPlayer.isLooping = true // Set looping
            mediaPlayer.start()
        }
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