/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package hidden.edu.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import hidden.edu.R;
import hidden.edu.fragment.MyRecyclerFragment;
import hidden.edu.fragment.SettingFragment;
import pub.devrel.easypermissions.EasyPermissions;
import zuo.biao.library.base.BaseBottomTabActivity;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.manager.SystemBarTintManager;
import zuo.biao.library.ui.BottomMenuWindow;

import static hidden.edu.fragment.MapFragment.temp;


/*
 * 应用主页
 *
 * @author Lemon
 * @use MainTabActivity.createIntent(...)
 */
public class MainTabActivity extends BaseBottomTabActivity implements OnBottomDragListener, View.OnClickListener {

    public static final String[] TOPBAR_COLOR_NAMES = {"红色", "蓝色", "绿色", "黄色", "木纹", "书幅", "羊皮卷"};
    public static final int[] TOPBAR_COLOR_RESIDS = {R.color.red, R.color.blue, R.color.green, R.color.act_yellow, R.drawable.texture1, R.drawable.texture3, R.drawable.texture2};
    private int currentPos;

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
    NavigationView navigationView;


    public String channelID = "3";
    public String channelName = "channel_name";
    public final int mSmallIconId = R.drawable.ic_launcher;
    public final int mLargeIconId = R.drawable.ic_launcher;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public void permission() {
        String PERMISSION_STORAGE_MSG = "请授予权限，否则影响部分使用功能";
        String[] perms;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            perms = new String[]{
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
                    Manifest.permission.READ_PHONE_STATE,
            };
        } else {
            perms = new String[]{
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
                    Manifest.permission.READ_PHONE_STATE,
            };
        }
        if (!EasyPermissions.hasPermissions(this, perms)) {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, PERMISSION_STORAGE_MSG, 10001, perms);
        }
    }

    /*
     * 启动这个Activity的Intent
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MainTabActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_tab_activity, this);


        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //private MapFragment mapFragment = new MapFragment();
    private hidden.edu.fragment.MapFragment mapFragment = new hidden.edu.fragment.MapFragment();

    @SuppressLint("ResourceType")
    @Override
    public void initView() {// 必须调用
        super.initView();

        exitAnim = R.anim.bottom_push_out;


        navigationView = findViewById(R.id.nav_view);
        //navigationView.setBackgroundResource(R.drawable.texture3);





         /*
        settingAbout = View.inflate(context, R.id.llSettingAbout, null);
        settingAbout.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);

        settingSetting = View.inflate(context, R.id.llSettingSetting, null);
        settingSetting.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);

        settingLogout = View.inflate(context, R.id.llSettingLogout, null);
        settingLogout.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
**/

        /*
        llAboutZBLibraryMainActivity = findViewById(R.id.llAboutZBLibraryMainActivity);
        llAboutComment = findViewById(R.id.llAboutComment);
        llAboutContactUs = findViewById(R.id.llAboutContactUs);
        llAboutDeveloper = findViewById(R.id.llAboutDeveloper);
        llAboutShare = findViewById(R.id.llAboutShare);
        llAboutWeibo = findViewById(R.id.llAboutWeibo);

        try {
            llAboutZBLibraryMainActivity.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
            llAboutComment.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
            llAboutContactUs.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
            llAboutDeveloper.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
            llAboutShare.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
            llAboutWeibo.setBackgroundResource(TOPBAR_COLOR_RESIDS[TOPBAR_COLOR_RESIDS.length - 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

         */
    }


    private void setTintColor(int position) {
        if (position < 0) {
            position = 0;
        } else if (position >= TOPBAR_COLOR_RESIDS.length) {
            position = TOPBAR_COLOR_RESIDS.length - 1;
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

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(TOPBAR_COLOR_RESIDS[position]);//状态背景色，可传drawable资源
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.flMainTabFragmentContainer;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 1:
                return MyRecyclerFragment.createInstance(MyRecyclerFragment.RANGE_RECOMMEND);
            case 2:
                return SettingFragment.createInstance();
            default: {
                return mapFragment;
            }
            //return UserListFragment.createInstance(UserListFragment.RANGE_ALL);
        }
    }


    // UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {// 必须调用
        super.initData();
    }

    @Override
    protected int[] getTabClickIds() {
        return new int[4];
    }


    // Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {// 必须调用
        super.initEvent();

        permission();

        try {
            notification();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //findView(R.id.drawer).setOnItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_explorer:
                    currentPos = 0;
                    selectFragment(currentPos);
                    break;
                case R.id.nav_find:
                    currentPos = 1;
                    selectFragment(currentPos);
                    break;
                case R.id.nav_setting:
                    currentPos = 2;
                    selectFragment(currentPos);
                    break;
                case R.id.nav_skin:
                    toActivity(BottomMenuWindow.createIntent(context, TOPBAR_COLOR_NAMES)
                            .putExtra(BottomMenuWindow.INTENT_TITLE, "选择颜色"), REQUEST_TO_BOTTOM_MENU, false);
                    break;
            }


            /*
            if (id == R.id.nav_skin)
                toActivity(BottomMenuWindow.createIntent(context, TOPBAR_COLOR_NAMES)
                        .putExtra(BottomMenuWindow.INTENT_TITLE, "选择颜色"), REQUEST_TO_BOTTOM_MENU, false);
            */
            return false;
        });
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


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            switch (currentPos) {
                case 0:
                    currentPos = 2;
                    break;
                case 1:
                case 2:
                    currentPos--;
                    break;
            }
            selectFragment(currentPos);
        }
        //finish();
    }


    //双击手机返回键退出<<<<<<<<<<<<<<<<<<<<<
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                showShortToast("再按一次退出");
                firstTime = secondTime;
            } else {//完全退出
                moveTaskToBack(false);//应用退到后台
                System.exit(0);
            }
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {//直接调用不会显示v被点击效果
        /*
        if (v.getId() == R.id.btn_location) {
            toActivity(NaviActivity.createIntent(context), true);
        }

         */
    }

    private static final int REQUEST_TO_BOTTOM_MENU = 31;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_TO_BOTTOM_MENU) {
            if (data != null) {
                int position = data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID, -1);
                if (position >= 0) {
                    setTintColor(position);
                }
            }
        }
    }

    public void notification() {
        try {
            mapFragment.caculate();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            assert manager != null;
            manager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, channelID);
            builder.setTicker("System Ticker O")
                    .setContentTitle("当前最近的宝藏为")
                    .setContentText(((int) temp) + "米")
                    .setSmallIcon(mSmallIconId)
                    .setLargeIcon(getLargeIcon())
                    .setChannelId(channelID)
                    .setAutoCancel(false)
                    .setOngoing(true);

            manager.notify(0, builder.build());
        } else {

            Notification.Builder builder = new Notification.Builder(this);
            builder.setTicker("System Ticker")
                    .setContentTitle("当前最近的宝藏为")
                    .setContentText(temp + "米")
                    .setSmallIcon(mSmallIconId)
                    .setLargeIcon(getLargeIcon())
                    .setAutoCancel(false)
                    .setOngoing(true);
            getNotificationManager().notify(0, builder.build());
        }
    }

    private Bitmap getLargeIcon() {
        return BitmapFactory.decodeResource(getResources(), mLargeIconId);
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

}