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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import hidden.edu.R;
import hidden.edu.model.User;
import hidden.edu.util.MenuUtil;
import zuo.biao.library.base.BaseActivity;
import zuo.biao.library.base.BaseModel;
import zuo.biao.library.interfaces.OnBottomDragListener;
import zuo.biao.library.interfaces.OnHttpResponseListener;
import zuo.biao.library.manager.CacheManager;
import zuo.biao.library.ui.BottomMenuView;
import zuo.biao.library.util.JSON;
import zuo.biao.library.util.Log;

/**
 * 联系人资料界面
 *
 * @author Lemon
 */
public class UserActivity extends BaseActivity implements OnClickListener, OnBottomDragListener
        , OnHttpResponseListener {
    public static final String TAG = "UserActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 获取启动UserActivity的intent
     */
    public static Intent createIntent(AppCompatActivity context, long userId) {
        return new Intent(context, UserActivity.class).putExtra(INTENT_ID, userId);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    private long userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity, this);

        intent = getIntent();
        userId = intent.getLongExtra(INTENT_ID, userId);
        if (userId <= 0) {
            finishWithError("用户不存在！");
            return;
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //	private BaseViewLayout<User> bvlUser;//方式一
    //	private UserViewLayout uvlUser;//方式二

    //private UserView userView;

    //private EditText etUserRemark;
    //private TextView tvUserTag;

    private BottomMenuView bottomMenuView;

    @Override
    public void initView() {//必须调用

        //添加用户名片，这些方式都可<<<<<<<<<<<<<<<<<<<<<<
        //		//方式一
        //		bvlUser = findView(R.id.bvlUser);
        //		bvlUser.createView(new UserView(context, getResources()));
        //
        //		//方式二
        //		uvlUser = findView(R.id.uvlUser);

        //方式三
        //方式三
        //
        //        ViewGroup llUserBusinessCardContainer = findView(R.id.llUserBusinessCardContainer);
        //        llUserBusinessCardContainer.removeAllViews();
        //
        //        userView = new UserView(context, null);
        //        llUserBusinessCardContainer.addView(userView.createView());
        //
        //添加用户名片，这些方式都可>>>>>>>>>>>>>>>>>>>>>>>


        //etUserRemark = findView(R.id.etUserRemark);
        //tvUserTag = findView(R.id.tvUserTag);


        //添加底部菜单<<<<<<<<<<<<<<<<<<<<<<

        //添加底部菜单>>>>>>>>>>>>>>>>>>>>>>>

    }

    private User user;

    /**
     * 显示用户
     */
    private void setUser(User user_) {
        this.user = user_;
        if (user == null) {
            Log.w(TAG, "setUser  user == null >> user = new User();");
            user = new User();
        }

        runUiThread(() -> {
            //				bvlUser.bindView(user);//方式一
            //				uvlUser.bindView(user);//方式二
            //userView.bindView(user);//方式三

            //etUserRemark.setText(StringUtil.getTrimedString(user.getHead()));
            //tvUserTag.setText(StringUtil.getTrimedString(user.getTag()));
        });
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {//必须调用

        bottomMenuView.bindView(MenuUtil.getMenuList(MenuUtil.USER));

        runThread(TAG + "initData", () -> {
            setUser(CacheManager.getInstance().get(User.class, "" + userId));//先加载缓存数据，比网络请求快很多
            //TODO 修改以下请求
            //通用 HttpRequest.getUser(userId, 0, UserActivity.this);//http请求获取一个User
            //更方便但对字符串格式有要求 HttpRequest.getUser(userId, 0, new OnHttpResponseListenerImpl(UserActivity.this));
        });
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

        //findView(R.id.llUserTag).setOnClickListener(this);

        //new TextClearSuit().addClearListener(etUserRemark, findView(R.id.ivUserRemarkClear));//清空备注按钮点击监听

        //bottomMenuView.setOnMenuItemClickListener(this);//底部菜单点击监听

        //userView.setOnDataChangedListener(() -> user = userView.data);
    }

    //对应HttpRequest.getUser(userId, 0, UserActivity.this); <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Override
    public void onHttpResponse(int requestCode, String resultJson, Exception e) {
        User user = null;
        try {//如果服务器返回的json一定在最外层有个data，可以用OnHttpResponseListenerImpl解析
            JSONObject jsonObject = JSON.parseObject(resultJson);
            JSONObject data = jsonObject == null ? null : jsonObject.getJSONObject("data");
            user = JSON.parseObject(data, User.class);
        } catch (Exception e1) {
            Log.e(TAG, "onHttpResponse  try { user = Json.parseObject(... >>" +
                    " } catch (JSONException e1) {\n" + e1.getMessage());
        }

        if (!BaseModel.isCorrect(user) && e != null) {
            showShortToast(R.string.get_failed);
        } else {
            setUser(user);
        }
    }
    //对应HttpRequest.getUser(userId, 0, UserActivity.this); >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //	//对应HttpRequest.getUser(userId, 0, new OnHttpResponseListenerImpl(UserActivity.this)); <<<<<
    //	@Override
    //	public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
    //		setUser(JSON.parseObject(resultData, User.class));
    //	}
    //
    //	@Override
    //	public void onHttpError(int requestCode, Exception e) {
    //		showShortToast(R.string.get_failed);
    //	}
    //	//对应HttpRequest.getUser(userId, 0, new OnHttpResponseListenerImpl(UserActivity.this)); >>>>


    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) return;

        finish();
    }

    @Override
    public void onClick(View v) {
        /*
        if (v.getId() == R.id.llUserTag) {
            toActivity(EditTextInfoActivity.createIntent(context, "标签"
                    , StringUtil.getTrimedString(tvUserTag)), REQUEST_TO_EDIT_TEXT_INFO);
        }

         */
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private static final int REQUEST_TO_BOTTOM_MENU = 1;
    private static final int REQUEST_TO_EDIT_TEXT_INFO = 2;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void finish() {
        super.finish();
        if (user != null) {
            //user.setHead(StringUtil.getTrimedString(etUserRemark));
            CacheManager.getInstance().save(User.class, user, "" + user.getId());//更新缓存
        }
    }

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}