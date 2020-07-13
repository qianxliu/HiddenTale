package hidden.edu.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import hidden.edu.R
import hidden.edu.fragment.MapFragment
import hidden.edu.fragment.MyRecyclerFragment
import hidden.edu.fragment.SettingFragment
import kotlinx.android.synthetic.main.main_tab_activity.*
import pub.devrel.easypermissions.EasyPermissions
import qian.xin.library.base.BaseBottomTabActivity
import qian.xin.library.interfaces.OnBottomDragListener
import qian.xin.library.interfaces.Presenter.Companion.INTENT_TITLE
import qian.xin.library.manager.SystemBarTintManager
import qian.xin.library.ui.BottomMenuWindow
import qian.xin.library.util.CommonUtil
import qian.xin.library.util.DataKeeper
import java.io.File
import kotlin.system.exitProcess

/*
 * 应用主页
 * @use MainTabActivity.createIntent(...)
 */
class MainTabActivity : BaseBottomTabActivity(), OnBottomDragListener, View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private var currentPos = 0

    private var channelID = "3"
    private var channelName = "channel_name"
    private val mSmallIconId = R.drawable.ic_launcher
    private val mLargeIconId = R.drawable.ic_launcher
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        /*
        val perms: Array<String> = arrayOf(
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE)
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(this.activity, "请授予必要权限！", 10005, *perms)
        }

         */
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private fun permission() {
        val MSG = "请授予权限，否则影响部分使用功能"
        val perms: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            arrayOf(
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE
            )
        } else {
            arrayOf(
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE
            )
        }
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, MSG, 10001, *perms)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_tab_activity, this)


        //功能归类分区方法，必须调用<<<<<<<<<<
        initView()
        initData()
        initEvent()
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //private MapFragment mapFragment = new MapFragment();
    private val mapFragment = MapFragment()


    //var web : RelativeLayout = findView(R.id.web_layout)
    @SuppressLint("ResourceType")
    override fun initView() { // 必须调用
        super.initView()
        exitAnim = R.anim.bottom_push_out
        //navigationHeader = findViewById(R.id.nav_header)
    }

    private fun setTintColor(position: Int) {
        var position = position
        if (position < 0) {
            position = 0
        } else if (position >= TOPBAR_COLOR_RESIDS.size) {
            position = TOPBAR_COLOR_RESIDS.size - 1
        }


        nav_view.setBackgroundResource(TOPBAR_COLOR_RESIDS[position])
        //weblayout.setBackgroundResource(TOPBAR_COLOR_RESIDS[position])
        //navigationHeader?.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        /*
        settingAbout.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        settingLogout.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        settingSetting.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);

         */
        //bottomWindowLayout.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        /*
        llAboutZBLibraryMainActivity.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        llAboutComment.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        llAboutContactUs.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        llAboutDeveloper.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        llAboutShare.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        llAboutWeibo.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);
        */
        val tintManager = SystemBarTintManager(this)

        tintManager.setStatusBarTintResource(TOPBAR_COLOR_RESIDS[position]) //状态背景色，可传drawable资源
    }

    override fun getFragmentContainerResId(): Int {
        return R.id.flMainTabFragmentContainer
    }

    override fun getFragment(position: Int): Fragment {
        return when (position) {
            1 -> MyRecyclerFragment.createInstance(MyRecyclerFragment.RANGE_RECOMMEND)
            2 -> SettingFragment.createInstance()
            else -> {
                mapFragment
            }
        }
    }

    override fun getTabClickIds(): IntArray {
        return IntArray(4)
    }

    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @SuppressLint("ShowToast")
    override fun initEvent() { // 必须调用
        super.initEvent()
        permission()
        try {
            notification()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //findView(R.id.drawer).setOnItemClickListener(this);
        nav_view.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_explorer -> {
                    currentPos = 0
                    selectFragment(currentPos)
                }
                R.id.nav_find -> {
                    currentPos = 1
                    selectFragment(currentPos)
                }
                R.id.nav_setting -> {
                    currentPos = 2
                    selectFragment(currentPos)
                }
                R.id.nav_skin -> toActivity(BottomMenuWindow.createIntent(context, TOPBAR_COLOR_NAMES)
                        .putExtra(INTENT_TITLE, "选择颜色"), REQUEST_TO_BOTTOM_MENU, false)
            }
            false
        }

        if (!CommonUtil.isNetWorkConnected(this)) {
            Toast.makeText(this, "未联网！请联网加载音频和Pdf资源……", Toast.LENGTH_LONG)
        } else {
            //Must add!
            FileDownloader.setup(this)
            for ((index, value) in MyRecyclerFragment.URLS.withIndex()) {
                if (!File(DataKeeper.tempPath + "klfskjkf$index").exists()) {
                    FileDownloader.getImpl().create(value)
                            .setPath(DataKeeper.tempPath + "klfskjkf$index", false)
                            .start()
                }
            }

            if (mainFile.exists()) {
                //Must add for init
                try {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer.setAudioAttributes(
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build())


                    mediaPlayer.setDataSource(mainFile.absolutePath)
                    mediaPlayer.isLooping = true // Set looping
                    mediaPlayer.prepare() // might take long! (for buffering, etc)
                    mediaPlayer.start()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            } else {
                FileDownloader.getImpl().create("https://git.nwu.edu.cn/2018104171/pdf/raw/master/main.mp3")
                        .setPath(mainFile.absolutePath, false)
                        .setListener(object : FileDownloadListener() {
                            override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                            override fun connected(task: BaseDownloadTask, etag: String, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}
                            override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                            override fun blockComplete(task: BaseDownloadTask) {}
                            override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {}
                            override fun completed(task: BaseDownloadTask) {
                                //Must add for init
                                mediaPlayer = MediaPlayer()
                                mediaPlayer.setAudioAttributes(
                                        AudioAttributes.Builder()
                                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                                .build())
                                mediaPlayer.setDataSource(mainFile.absolutePath)
                                mediaPlayer.isLooping = true // Set looping
                                mediaPlayer.prepare() // might take long! (for buffering, etc)
                                mediaPlayer.start()
                            }

                            override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                            override fun error(task: BaseDownloadTask, e: Throwable) {}
                            override fun warn(task: BaseDownloadTask) {}
                        }).start()
            }
        }

    }

    override fun onDragBottom(rightToLeft: Boolean) {
        if (rightToLeft) {
            when (currentPos) {
                0 -> currentPos = 2
                1, 2 -> currentPos--
            }
            selectFragment(currentPos)
        }
        //finish();
    }

    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private var firstTime: Long = 0 //第一次返回按钮计时

    @SuppressLint("ShowToast")
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                CommonUtil.showShortToast(this, "再按一次退出")
                firstTime = secondTime
            } else { //完全退出
                moveTaskToBack(false) //应用退到后台
                exitProcess(0)
            }
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onClick(v: View) { //直接调用不会显示v被点击效果
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_TO_BOTTOM_MENU) {
            if (data != null) {
                val position = data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID, -1)
                if (position >= 0) {
                    setTintColor(position)
                }
            }
        }
    }

    private fun notification() {
        try {
            mapFragment.caculate()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            val manager = (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            manager.createNotificationChannel(channel)
            val builder = Notification.Builder(this, channelID)
            builder.setTicker("System Ticker O")
                    .setContentTitle("当前最近的宝藏为")
                    .setContentText(MapFragment.temp.toInt().toString() + "米")
                    .setSmallIcon(mSmallIconId)
                    .setLargeIcon(largeIcon)
                    .setChannelId(channelID)
                    .setAutoCancel(false)
                    .setOngoing(true)
            manager.notify(0, builder.build())
        } else {
            val builder = Notification.Builder(this)
            builder.setTicker("System Ticker")
                    .setContentTitle("当前最近的宝藏为")
                    .setContentText(MapFragment.temp.toString() + "米")
                    .setSmallIcon(mSmallIconId)
                    .setLargeIcon(largeIcon)
                    .setAutoCancel(false)
                    .setOngoing(true)
            notificationManager.notify(0, builder.build())
        }
    }

    private val largeIcon: Bitmap
        get() = BitmapFactory.decodeResource(resources, mLargeIconId)

    private val notificationManager: NotificationManager
        get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        val TOPBAR_COLOR_NAMES = arrayOf("纯白", "蓝色", "黄色", "木纹", "书幅", "羊皮卷")
        val TOPBAR_COLOR_RESIDS = intArrayOf(R.color.white, R.color.blue, R.color.act_yellow, R.drawable.texture1, R.drawable.texture3, R.drawable.texture2)
        lateinit var mediaPlayer: MediaPlayer
        var mainFile = File(DataKeeper.tempPath + "mainFile")

        /*
     * 启动这个Activity的Intent
     */
        fun createIntent(activity: AppCompatActivity): Intent {
            return Intent(activity, MainTabActivity::class.java)
        }

        private const val REQUEST_TO_BOTTOM_MENU = 31
    }
}