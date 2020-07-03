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
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import hidden.edu.R
import hidden.edu.fragment.MapFragment
import hidden.edu.fragment.MyRecyclerFragment
import hidden.edu.fragment.SettingFragment
import pub.devrel.easypermissions.EasyPermissions
import zuo.biao.library.base.BaseBottomTabActivity
import zuo.biao.library.interfaces.OnBottomDragListener
import zuo.biao.library.manager.SystemBarTintManager
import zuo.biao.library.ui.BottomMenuWindow
import zuo.biao.library.util.DataKeeper
import zuo.biao.library.util.DownloadUtil
import java.io.File

/*
 * 应用主页
 *
 * @author Lemon
 * @use MainTabActivity.createIntent(...)
 */
class MainTabActivity : BaseBottomTabActivity(), OnBottomDragListener, View.OnClickListener {
    private var currentPos = 0

    /*
    private View settingSetting;
    private View settingAbout;
    private View settingLogout;
     */
    //private View bottomWindowLayout;
    /*
    private View llAboutZBLibraryMainActivity;
    private View llAboutShare;
    private View llAboutComment;
    private View llAboutDeveloper;
    private View llAboutWeibo;
    private View llAboutContactUs;
    */
    private var navigationView: NavigationView? = null
    private var channelID = "3"
    private var channelName = "channel_name"
    private val mSmallIconId = R.drawable.ic_launcher
    private val mLargeIconId = R.drawable.ic_launcher
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private fun permission() {
        val PERMISSION_STORAGE_MSG = "请授予权限，否则影响部分使用功能"
        val perms: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            arrayOf(
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.MODIFY_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_PHONE_STATE)
        } else {
            arrayOf(
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.MODIFY_PHONE_STATE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_PHONE_STATE)
        }
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, PERMISSION_STORAGE_MSG, 10001, *perms)
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

    @SuppressLint("ResourceType")
    override fun initView() { // 必须调用
        super.initView()
        exitAnim = R.anim.bottom_push_out
        navigationView = findViewById(R.id.nav_view)
    }

    private fun setTintColor(position: Int) {
        var position = position
        if (position < 0) {
            position = 0
        } else if (position >= TOPBAR_COLOR_RESIDS.size) {
            position = TOPBAR_COLOR_RESIDS.size - 1
        }


        //navigationView.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);

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
        tintManager.isStatusBarTintEnabled = true
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
    override fun initEvent() { // 必须调用
        super.initEvent()
        permission()
        try {
            notification()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Thread(Runnable {
            if (!mainFile.exists()) {
                mainFile = DownloadUtil.downLoadFile(context, "/temp/" + "mainFile", "https://git.nwu.edu.cn/2018104171/pdf/raw/master/main.mp3")
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer.setDataSource(mainFile.absolutePath)
            mediaPlayer.prepare() // might take long! (for buffering, etc)
            mediaPlayer.isLooping = true // Set looping
            mediaPlayer.start()
        })


        //findView(R.id.drawer).setOnItemClickListener(this);
        navigationView!!.setNavigationItemSelectedListener { item: MenuItem ->
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
                        .putExtra(BottomMenuWindow.INTENT_TITLE, "选择颜色"), REQUEST_TO_BOTTOM_MENU, false)
            }
            false
        }
    }

    // This snippet hides the system bars.
    /*
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.

    private void showSystemUI() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
*/
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
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                showShortToast("再按一次退出")
                firstTime = secondTime
            } else { //完全退出
                moveTaskToBack(false) //应用退到后台
                System.exit(0)
            }
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onClick(v: View) { //直接调用不会显示v被点击效果
        /*
        if (v.getId() == R.id.btn_location) {
            toActivity(NaviActivity.createIntent(context), true);
        }

         */
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

    fun notification() {
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
        val TOPBAR_COLOR_NAMES = arrayOf("红色", "蓝色", "绿色", "黄色", "木纹", "书幅", "羊皮卷")
        val TOPBAR_COLOR_RESIDS = intArrayOf(R.color.red, R.color.blue, R.color.green, R.color.act_yellow, R.drawable.texture1, R.drawable.texture3, R.drawable.texture2)
        var mediaPlayer = MediaPlayer()
        var mainFile = File(DataKeeper.tempPath + "mainFile")

        /*
     * 启动这个Activity的Intent
     */
        fun createIntent(context: Context?): Intent {
            return Intent(context, MainTabActivity::class.java)
        }

        private const val REQUEST_TO_BOTTOM_MENU = 31
    }
}