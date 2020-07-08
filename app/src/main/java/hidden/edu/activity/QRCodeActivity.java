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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.king.zxing.util.CodeUtils;

import hidden.edu.R;
import hidden.edu.model.User;
import qian.xin.library.base.BaseActivity;
import qian.xin.library.interfaces.OnBottomDragListener;
import qian.xin.library.manager.CacheManager;
import qian.xin.library.util.JSON;
import qian.xin.library.util.Log;
import qian.xin.library.util.StringUtil;

/**
 * 二维码界面Activity
 *
 * @author Lemon
 */
public class QRCodeActivity extends BaseActivity implements OnBottomDragListener {
    private static final String TAG = "QRCodeActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    /**
     * 启动这个Activity的Intent
     */
    public static Intent createIntent(Context context, long userId) {
        return new Intent(context, QRCodeActivity.class).
                putExtra(INTENT_ID, userId);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_activity, this);

        intent = getIntent();
        userId = intent.getLongExtra(INTENT_ID, userId);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private ImageView ivQRCodeHead;
    private TextView tvQRCodeName;

    private ImageView ivQRCodeCode;
    private View ivQRCodeProgress;

    @Override
    public void initView() {//必须调用
        autoSetTitle();

        ivQRCodeHead = findView(R.id.ivQRCodeHead);
        tvQRCodeName = findView(R.id.tvQRCodeName);

        ivQRCodeCode = findView(R.id.ivQRCodeCode);
        ivQRCodeProgress = findView(R.id.ivQRCodeProgress);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private User user;

    @Override
    public void initData() {//必须调用

        ivQRCodeProgress.setVisibility(View.VISIBLE);
        runThread(TAG + "initData", () -> {

            user = CacheManager.getInstance().get(User.class, "" + userId);
            if (user == null) {
                user = new User(userId);
            }
            runUiThread(() -> {
                Glide.with(context).load(user.getHead()).into(ivQRCodeHead);
                tvQRCodeName.setText(StringUtil.getTrimedString(
                        StringUtil.isNotEmpty(user.getName(), true)
                                ? user.getName() : user.getPhone()));
            });

            setQRCode(user);
        });

    }

    private Bitmap qRCodeBitmap;

    protected void setQRCode(User user) {
        if (user == null) {
            Log.e(TAG, "setQRCode  user == null" +
                    " || StringUtil.isNotEmpty(user.getPhone(), true) == false >> return;");
            return;
        }

        qRCodeBitmap = CodeUtils.createQRCode(JSON.toJSONString(user)
                , (int) (2 * getResources().getDimension(R.dimen.qrcode_size)));

        runUiThread(() -> {
            ivQRCodeProgress.setVisibility(View.GONE);
            ivQRCodeCode.setImageBitmap(qRCodeBitmap);
        });
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ivQRCodeProgress = null;
        ivQRCodeCode = null;
        user = null;

        if (qRCodeBitmap != null) {
            if (!qRCodeBitmap.isRecycled()) {
                qRCodeBitmap.recycle();
            }
            qRCodeBitmap = null;
        }
        if (ivQRCodeCode != null) {
            ivQRCodeCode.setImageBitmap(null);
            ivQRCodeCode = null;
        }
    }

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}