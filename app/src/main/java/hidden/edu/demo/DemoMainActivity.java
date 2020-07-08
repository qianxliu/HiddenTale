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

package hidden.edu.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hidden.edu.R;
import hidden.edu.activity.ScanActivity;
import qian.xin.library.base.BaseActivity;
import qian.xin.library.interfaces.OnBottomDragListener;
import qian.xin.library.manager.SystemBarTintManager;
import qian.xin.library.ui.AlertDialog;
import qian.xin.library.ui.AlertDialog.OnDialogButtonClickListener;
import qian.xin.library.ui.BottomMenuWindow;
import qian.xin.library.ui.CutPictureActivity;
import qian.xin.library.ui.DatePickerWindow;
import qian.xin.library.ui.EditTextInfoActivity;
import qian.xin.library.ui.EditTextInfoWindow;
import qian.xin.library.ui.ItemDialog;
import qian.xin.library.ui.ItemDialog.OnDialogItemClickListener;
import qian.xin.library.ui.PlacePickerWindow;
import qian.xin.library.ui.SelectPictureActivity;
import qian.xin.library.ui.ServerSettingActivity;
import qian.xin.library.ui.TimePickerWindow;
import qian.xin.library.ui.TopMenuWindow;
import qian.xin.library.ui.WebViewActivity;
import qian.xin.library.util.CommonUtil;
import qian.xin.library.util.DataKeeper;
import qian.xin.library.util.SettingUtil;
import qian.xin.library.util.StringUtil;
import qian.xin.library.util.TimeUtil;

/**
 * demo主页
 *
 * @author Lemon
 */
public class DemoMainActivity extends BaseActivity implements OnClickListener, OnBottomDragListener
        , OnDialogButtonClickListener, OnDialogItemClickListener, OnTouchListener {
    private static final String TAG = "DemoMainActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     */
    public static Intent createIntent(AppCompatActivity context) {
        return new Intent(context, DemoMainActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_main_activity, this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public static final String[] TOPBAR_COLOR_NAMES = {"灰色", "蓝色", "黄色"};
    private static final int[] TOPBAR_COLOR_RESIDS = {R.color.gray, R.color.blue, R.color.yellow};

    private View rlDemoMainTopbar;

    private ImageView ivDemoMainHead;
    private TextView tvDemoMainHeadName;

    private ScrollView svDemoMain;

    @Override
    public void initView() {//必须调用
        exitAnim = R.anim.bottom_push_out;

        rlDemoMainTopbar = findView(R.id.rlDemoMainTopbar);

        ivDemoMainHead = findView(R.id.ivDemoMainHead, this);
        tvDemoMainHeadName = findView(R.id.tvDemoMainHeadName, this);

        svDemoMain = findView(R.id.svDemoMain);
    }

    /**
     * 设置沉浸状态栏和导航栏颜色
     */
    private void setTintColor(int position) {
        if (position < 0) {
            position = 0;
        } else if (position >= TOPBAR_COLOR_RESIDS.length) {
            position = TOPBAR_COLOR_RESIDS.length - 1;
        }

        rlDemoMainTopbar.setBackgroundResource(TOPBAR_COLOR_RESIDS[position]);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(TOPBAR_COLOR_RESIDS[position]);//状态背景色，可传drawable资源
    }

    /**
     * 显示列表选择弹窗
     */
    private void showItemDialog() {
        new ItemDialog(context, TOPBAR_COLOR_NAMES, "选择颜色", DIALOG_SET_TOPBAR, this).show();
    }

    /**
     * 显示顶部菜单
     */
    private void showTopMenu() {
        toActivity(TopMenuWindow.createIntent(context, new String[]{"更改导航栏颜色", "更改图片"}), REQUEST_TO_TOP_MENU, false);
    }


    private String picturePath;

    /**
     * 选择图片
     */
    private void selectPicture() {
        toActivity(SelectPictureActivity.createIntent(context), REQUEST_TO_SELECT_PICTURE, false);
    }

    /**
     * 裁剪图片
     */
    private void cutPicture(String path) {
        if (StringUtil.isFilePath(path)) {
            this.picturePath = path;

            toActivity(CutPictureActivity.createIntent(context, path
                    , DataKeeper.imagePath, "photo" + System.currentTimeMillis(), 200)
                    , REQUEST_TO_CUT_PICTURE);
        } else {
            Log.e(TAG, "cutPicture  StringUtil.isFilePath(path) == false >> CommonUtil.showShortToast(找不到图片);return;");
            CommonUtil.showShortToast(getActivity(),"找不到图片");
        }
    }

    /**
     * 显示图片
     */
    private void setPicture(String path) {
        if (StringUtil.isFilePath(path)) {
            this.picturePath = path;

            svDemoMain.smoothScrollTo(0, 0);
            Glide.with(context).load(path).into(ivDemoMainHead);
        } else {
            Log.e(TAG, "setPicture  StringUtil.isFilePath(path) == false >> CommonUtil.showShortToast(找不到图片);return;");
            CommonUtil.showShortToast(getActivity(),"找不到图片");
        }
    }

    /**
     * 编辑图片名称
     */
    private void editName(boolean toWindow) {
        if (toWindow) {
            intent = EditTextInfoWindow.createIntent(context, EditTextInfoWindow.TYPE_NICK
                    , "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName), getPackageName());
        } else {
            intent = EditTextInfoActivity.createIntent(context, EditTextInfoActivity.TYPE_NICK
                    , "照片名称", StringUtil.getTrimedString(tvDemoMainHeadName));
        }

        toActivity(intent, REQUEST_TO_EDIT_TEXT_INFO, !toWindow);
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须调用

    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

        findView(R.id.llDemoMainItemDialog).setOnClickListener(this);
        findView(R.id.llDemoMainAlertDialog).setOnClickListener(this);


        findView(R.id.llDemoMainScanActivity).setOnClickListener(this);
        findView(R.id.llDemoMainSelectPictureActivity).setOnClickListener(this);
        findView(R.id.llDemoMainCutPictureActivity).setOnClickListener(this);
        findView(R.id.llDemoMainWebViewActivity).setOnClickListener(this);
        findView(R.id.llDemoMainEditTextInfoActivity).setOnClickListener(this);
        findView(R.id.llDemoMainServerSettingActivity).setOnTouchListener(this);

        findView(R.id.llDemoMainDemoActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoListActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoRecyclerActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoHttpListActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoHttpRecyclerActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoFragmentActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoTabActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoSQLActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoTimeRefresherActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoBroadcastReceiverActivity).setOnClickListener(this);
        findView(R.id.llDemoMainDemoBottomWindow).setOnClickListener(this);
        findView(R.id.llDemoMainDemoThreadPoolActivity).setOnClickListener(this);


        findView(R.id.llDemoMainTopMenuWindow).setOnClickListener(this);
        findView(R.id.llDemoMainBottomMenuWindow).setOnClickListener(this);
        findView(R.id.llDemoMainEditTextInfoWindow).setOnClickListener(this);
        findView(R.id.llDemoMainPlacePickerWindow).setOnClickListener(this);
        findView(R.id.llDemoMainDatePickerWindow).setOnClickListener(this);
        findView(R.id.llDemoMainTimePickerWindow).setOnClickListener(this);

    }


    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (isPositive) {
            rlDemoMainTopbar.setBackgroundResource(R.color.red);
        }
    }


    private static final int DIALOG_SET_TOPBAR = 1;

    @Override
    public void onDialogItemClick(int requestCode, int position, String item) {
        if (position < 0) {
            position = 0;
        }
        if (requestCode == DIALOG_SET_TOPBAR) {
            setTintColor(position);
        }
    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            showTopMenu();
            return;
        }

        finish();
    }


    private long touchDownTime = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.llDemoMainServerSettingActivity) {
                    touchDownTime = System.currentTimeMillis();
                    Log.i(TAG, "onTouch MotionEvent.ACTION: touchDownTime=" + touchDownTime);
                    return true;
                }
            case MotionEvent.ACTION_UP:
                if (v.getId() == R.id.llDemoMainServerSettingActivity) {
                    long time = System.currentTimeMillis() - touchDownTime;
                    if (time < 5000 || time > 8000) {
                        CommonUtil.showShortToast(getActivity(),"请长按5-8秒");
                    } else {
                        toActivity(ServerSettingActivity.createIntent(context
                                , SettingUtil.getServerAddress(false), SettingUtil.getServerAddress(true)
                                , SettingUtil.APP_SETTING, Context.MODE_PRIVATE
                                , SettingUtil.KEY_SERVER_ADDRESS_NORMAL, SettingUtil.KEY_SERVER_ADDRESS_TEST));
                        return true;
                    }
                }
                break;
            default:
                break;
        }

        return false;
    }

    private int[] selectedTime = new int[]{12, 0, 0};

    @Override
    public void onClick(View v) {//直接调用不会显示v被点击效果
        switch (v.getId()) {
            case R.id.ivDemoMainHead:
            case R.id.llDemoMainSelectPictureActivity:
                selectPicture();
                break;
            case R.id.tvDemoMainHeadName:
            case R.id.llDemoMainEditTextInfoWindow:
                editName(true);
                break;


            case R.id.llDemoMainItemDialog:
                showItemDialog();
                break;
            case R.id.llDemoMainAlertDialog:
                new AlertDialog(context, "更改颜色", "确定将导航栏颜色改为红色？", true, 0, this).show();
                break;


            case R.id.llDemoMainScanActivity:
                toActivity(ScanActivity.createIntent(context), REQUEST_TO_CAMERA_SCAN);
                break;
            case R.id.llDemoMainCutPictureActivity:
                cutPicture(picturePath);
                break;
            case R.id.llDemoMainWebViewActivity:
                toActivity(WebViewActivity.createIntent(context, SettingUtil.isOnTestMode ? "测试服务器" : "正式服务器"
                        , SettingUtil.getCurrentServerAddress()));
                break;
            case R.id.llDemoMainEditTextInfoActivity:
                editName(false);
                break;

            case R.id.llDemoMainDemoActivity:
                toActivity(DemoActivity.createIntent(context, 0));
                break;
            case R.id.llDemoMainDemoListActivity:
                toActivity(DemoListActivity.createIntent(context, 0));
                break;
            case R.id.llDemoMainDemoRecyclerActivity:
                toActivity(DemoRecyclerActivity.createIntent(context, 0));
                break;
            case R.id.llDemoMainDemoHttpListActivity:
                toActivity(DemoHttpListActivity.createIntent(context, DemoHttpListActivity.RANGE_RECOMMEND));
                break;
            case R.id.llDemoMainDemoHttpRecyclerActivity:
                toActivity(DemoHttpRecyclerActivity.createIntent(context, DemoHttpRecyclerActivity.RANGE_RECOMMEND));
                break;
            case R.id.llDemoMainDemoFragmentActivity:
                toActivity(DemoFragmentActivity.createIntent(context, 0));
                break;
            case R.id.llDemoMainDemoTabActivity:
                toActivity(DemoTabActivity.createIntent(context));
                break;
            case R.id.llDemoMainDemoSQLActivity:
                toActivity(DemoSQLActivity.createIntent(context));
                break;
            case R.id.llDemoMainDemoTimeRefresherActivity:
                toActivity(DemoTimeRefresherActivity.createIntent(context));
                break;
            case R.id.llDemoMainDemoBroadcastReceiverActivity:
                toActivity(DemoBroadcastReceiverActivity.createIntent(context));
                break;
            case R.id.llDemoMainDemoThreadPoolActivity:
                toActivity(DemoThreadPoolActivity.createIntent(context));
                break;
            case R.id.llDemoMainDemoBottomWindow:
                toActivity(DemoBottomWindow.createIntent(context, ""), REQUEST_TO_DEMO_BOTTOM_WINDOW, false);
                break;


            case R.id.llDemoMainTopMenuWindow:
                showTopMenu();
                break;
            case R.id.llDemoMainBottomMenuWindow:
                toActivity(BottomMenuWindow.createIntent(context, TOPBAR_COLOR_NAMES)
                        .putExtra(BottomMenuWindow.INTENT_TITLE, "选择颜色"), REQUEST_TO_BOTTOM_MENU, false);
                break;
            case R.id.llDemoMainPlacePickerWindow:
                toActivity(PlacePickerWindow.createIntent(context, getPackageName(), 2), REQUEST_TO_PLACE_PICKER, false);
                break;
            case R.id.llDemoMainDatePickerWindow:
                toActivity(DatePickerWindow.createIntent(context, new int[]{1971, 0, 1}
                        , TimeUtil.getDateDetail(System.currentTimeMillis())), REQUEST_TO_DATE_PICKER, false);
                break;
            case R.id.llDemoMainTimePickerWindow:
                toActivity(TimePickerWindow.createIntent(context, selectedTime), REQUEST_TO_TIME_PICKER, false);
                break;

            default:
                break;
        }
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private static final int REQUEST_TO_SELECT_PICTURE = 20;
    private static final int REQUEST_TO_CUT_PICTURE = 21;
    public static final int REQUEST_TO_CAMERA_SCAN = 22;
    private static final int REQUEST_TO_EDIT_TEXT_INFO = 23;
    private static final int REQUEST_TO_SERVER_SETTING = 24;
    private static final int REQUEST_TO_DEMO_BOTTOM_WINDOW = 25;

    private static final int REQUEST_TO_TOP_MENU = 30;
    private static final int REQUEST_TO_BOTTOM_MENU = 31;
    private static final int REQUEST_TO_PLACE_PICKER = 32;
    private static final int REQUEST_TO_DATE_PICKER = 33;
    private static final int REQUEST_TO_TIME_PICKER = 34;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_TO_SELECT_PICTURE:
                if (data != null) {
                    cutPicture(data.getStringExtra(SelectPictureActivity.RESULT_PICTURE_PATH));
                }
                break;
            case REQUEST_TO_CUT_PICTURE:
                if (data != null) {
                    setPicture(data.getStringExtra(CutPictureActivity.RESULT_PICTURE_PATH));
                }
                break;
            case REQUEST_TO_CAMERA_SCAN:
                if (data != null) {
                    String result = data.getStringExtra(ScanActivity.RESULT_DATA);
                    CommonUtil.copyText(context, result);
//				toActivity(WebViewActivity.createIntent(context, "扫描结果", result));
                    CommonUtil.openWebSite(context, result);
                }
                break;
            case REQUEST_TO_EDIT_TEXT_INFO:
                if (data != null) {
                    svDemoMain.smoothScrollTo(0, 0);
                    tvDemoMainHeadName.setText(StringUtil.getTrimedString(
                            data.getStringExtra(EditTextInfoWindow.RESULT_VALUE)));
                }
                break;
            case REQUEST_TO_SERVER_SETTING:
                //TODO
                break;
            case REQUEST_TO_DEMO_BOTTOM_WINDOW:
                if (data != null) {
                    CommonUtil.showShortToast(getActivity(), data.getStringExtra(DemoBottomWindow.RESULT_DATA));
                }
                break;

            case REQUEST_TO_TOP_MENU:
                if (data != null) {
                    switch (data.getIntExtra(TopMenuWindow.RESULT_POSITION, -1)) {
                        case 0:
                            showItemDialog();
                            break;
                        case 1:
                            selectPicture();
                            break;
                        default:
                            break;
                    }
                }
                break;
            case REQUEST_TO_BOTTOM_MENU:
                if (data != null) {
                    int position = data.getIntExtra(BottomMenuWindow.RESULT_ITEM_ID, -1);
                    if (position >= 0) {
                        setTintColor(position);
                    }
                }
                break;

            case REQUEST_TO_PLACE_PICKER:
                if (data != null) {
                    ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
                    if (placeList != null) {
                        StringBuilder place = new StringBuilder();
                        for (String s : placeList) {
                            place.append(StringUtil.getTrimedString(s));
                        }
                        CommonUtil.showShortToast(getActivity(), "选择的地区为: " + place);
                    }
                }
                break;
            case REQUEST_TO_DATE_PICKER:
                if (data != null) {
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(DatePickerWindow.RESULT_DATE_DETAIL_LIST);
                    if (list != null && list.size() >= 3) {

                        int[] selectedDate = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedDate[i] = list.get(i);
                        }

                        CommonUtil.showShortToast(getActivity(), "选择的日期为" + selectedDate[0] + "-" + (selectedDate[1] + 1) + "-" + selectedDate[2]);
                    }
                }
                break;
            case REQUEST_TO_TIME_PICKER:
                if (data != null) {
                    ArrayList<Integer> list = data.getIntegerArrayListExtra(TimePickerWindow.RESULT_TIME_DETAIL_LIST);
                    if (list != null && list.size() >= 2) {

                        selectedTime = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            selectedTime[i] = list.get(i);
                        }

                        String minute = "" + selectedTime[1];
                        if (minute.length() < 2) {
                            minute = "0" + minute;
                        }
                        CommonUtil.showShortToast(getActivity(), "选择的时间为" + selectedTime[0] + ":" + minute);
                    }
                }
                break;
            default:
                break;
        }

    }

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}