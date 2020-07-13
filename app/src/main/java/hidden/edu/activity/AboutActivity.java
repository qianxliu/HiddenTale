package hidden.edu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.king.zxing.util.CodeUtils;

import hidden.edu.R;
import hidden.edu.application.DemoApplication;
import hidden.edu.util.Constant;
import hidden.edu.util.HttpRequest;
import qian.xin.library.base.BaseActivity;
import qian.xin.library.interfaces.OnBottomDragListener;
import qian.xin.library.ui.WebViewActivity;
import qian.xin.library.util.CommonUtil;
import qian.xin.library.util.SettingUtil;

/*
 * 关于界面
 *
 * @author Lemon
 */
public class AboutActivity extends BaseActivity implements OnClickListener, OnLongClickListener, OnBottomDragListener {
    private static final String TAG = "AboutActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    /*
     * 启动这个Activity的Intent
     */
    public static Intent createIntent(AppCompatActivity context) {
        return new Intent(context, AboutActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity, this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        try {
            initData();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        if (SettingUtil.isOnTestMode) {
            CommonUtil.showShortToast(this, "测试服务器\n" + HttpRequest.URL_BASE);
        }


        //仅测试用
        HttpRequest.translate("library", 0, (requestCode, resultJson, e) -> CommonUtil.showShortToast(this, "测试Http请求:翻译library结果为\n" + resultJson));

    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //private ImageView ivAboutGesture;

    private TextView tvAboutAppInfo;

    private ImageView ivAboutQRCode;

    @Override
    public void initView() {

        //ivAboutGesture = findView(R.id.ivAboutGesture);
        //ivAboutGesture.setVisibility(SettingUtil.isFirstStart ? View.VISIBLE : View.GONE);

        /*
        if (SettingUtil.isFirstStart) {
            ivAboutGesture.setImageResource(R.drawable.gesture_left);
        }
         */
        tvAboutAppInfo = findView(R.id.tvAboutAppInfo);

        ivAboutQRCode = findView(R.id.ivAboutQRCode, this);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() throws PackageManager.NameNotFoundException {

        tvAboutAppInfo.setText(getResources().getString(R.string.app_name)
                + "\n" + DemoApplication.getInstance().getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);

        setQRCode();
    }


    private Bitmap qRCodeBitmap;

    /*
     * 显示二维码
     */
    protected void setQRCode() {
        runThread(TAG + "setQRCode", () -> {

            qRCodeBitmap = CodeUtils.createQRCode(Constant.APP_DOWNLOAD_WEBSITE, (int) (2 * getResources().getDimension(R.dimen.qrcode_size)));

            runUiThread(() -> ivAboutQRCode.setImageBitmap(qRCodeBitmap));
        });
    }

    /*
     * 下载应用
     */
    private void downloadApp() {
        toActivity(WebViewActivity.createIntent(context, "下载中心", Constant.APP_DOWNLOAD_WEBSITE));
        //CommonUtil.showShortToast("应用未上线!");

        /*
         showProgressDialog("正在下载...");
         runThread(TAG + "downloadApp", () -> {
         File file = DownloadUtil.downLoadFile(context, "app-debug", ".apk", Constant.APP_DOWNLOAD_WEBSITE);
         dismissProgressDialog();
         DownloadUtil.openFile(context, file);
         });
         */
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {

        //findView(R.id.llAboutZBLibraryMainActivity).setOnClickListener(this);

        findView(R.id.llAboutUpdate).setOnClickListener(this);
        findView(R.id.llAboutShare).setOnClickListener(this);
        findView(R.id.llAboutComment).setOnClickListener(this);

        findView(R.id.llAboutDeveloper, this).setOnLongClickListener(this);
        findView(R.id.llAboutWeibo, this).setOnLongClickListener(this);
        findView(R.id.llAboutContactUs, this).setOnLongClickListener(this);
    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            toActivity(WebViewActivity.createIntent(context, "工程", Constant.APP_OFFICIAL_BLOG));

            //ivAboutGesture.setImageResource(R.drawable.gesture_right);
            return;
        }

        if (SettingUtil.isFirstStart) {
            runThread(TAG + "onDragBottom", () -> {
                Log.i(TAG, "onDragBottom  >> SettingUtil.putBoolean(context, SettingUtil.KEY_IS_FIRST_IN, false);");
                SettingUtil.putBoolean(SettingUtil.KEY_IS_FIRST_START, false);
            });
        }

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*
            case R.id.llAboutMainTabActivity:
                startActivity(MainTabActivity.createIntent(context).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);

                enterAnim = exitAnim = R.anim.null_anim;
                finish();
                break;
            */

            /*
            case R.id.llAboutZBLibraryMainActivity:

                startActivity(DemoMainActivity.createIntent(context));
                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
                break;
*/
            case R.id.llAboutUpdate:
                toActivity(WebViewActivity.createIntent(context, "更新日志", Constant.UPDATE_LOG_WEBSITE));
                break;

            case R.id.llAboutShare:
                CommonUtil.shareInfo(context, getString(R.string.share_app) + "\n 点击链接直接查看长安宝藏\n" + Constant.APP_DEVELOPER_WEBSITE);
                break;
            case R.id.llAboutComment:
                CommonUtil.showShortToast(this, "应用未上线不能查看");
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://details?id=" + getPackageName())));
                break;

            case R.id.llAboutDeveloper:
                toActivity(WebViewActivity.createIntent(context, "开发者", Constant.APP_DEVELOPER_WEBSITE));
                break;

            case R.id.llAboutWeibo:
                toActivity(WebViewActivity.createIntent(context, "工程", Constant.APP_OFFICIAL_BLOG));
                break;
            case R.id.llAboutContactUs:
                CommonUtil.sendEmail(context, Constant.APP_OFFICIAL_EMAIL);
                break;

            case R.id.ivAboutQRCode:
                downloadApp();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.llAboutDeveloper:
                CommonUtil.copyText(context, Constant.APP_DEVELOPER_WEBSITE);
                return true;
            case R.id.llAboutWeibo:
                CommonUtil.copyText(context, Constant.APP_OFFICIAL_BLOG);
                return true;
            case R.id.llAboutContactUs:
                CommonUtil.copyText(context, Constant.APP_OFFICIAL_EMAIL);
                return true;
            default:
                break;
        }
        return false;
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
