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
package hidden.edu.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSONObject
import hidden.edu.R
import hidden.edu.model.User
import qian.xin.library.base.BaseActivity
import qian.xin.library.base.BaseModel
import qian.xin.library.interfaces.OnBottomDragListener
import qian.xin.library.interfaces.OnHttpResponseListener
import qian.xin.library.manager.CacheManager
import qian.xin.library.ui.BottomMenuView
import qian.xin.library.util.JSON
import qian.xin.library.util.Log

/**
 * 联系人资料界面
 *
 * @author Lemon
 */
class UserActivity : BaseActivity(), View.OnClickListener, OnBottomDragListener, OnHttpResponseListener {
    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private var userId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity, this)
        intent = getIntent()
        userId = intent.getLongExtra(INTENT_ID, userId)
        if (userId <= 0) {
            finishWithError("用户不存在！")
            return
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView()
        initData()
        initEvent()
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //	private BaseViewLayout<User> bvlUser;//方式一
    //	private UserViewLayout uvlUser;//方式二
    //private UserView userView;
    //private EditText etUserRemark;
    //private TextView tvUserTag;
    private val bottomMenuView: BottomMenuView? = null
    override fun initView() { //必须调用

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

    private var user: User? = null

    /**
     * 显示用户
     */
    private fun setUser(user_: User?) {
        user = user_
        if (user == null) {
            Log.w(TAG, "setUser  user == null >> user = new User();")
            user = User()
        }
        runUiThread {}
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun initData() { //必须调用
        runThread(TAG + "initData") {
            setUser(CacheManager.getInstance().get(User::class.java, "" + userId)) //先加载缓存数据，比网络请求快很多
        }
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun initEvent() { //必须调用

        //findView(R.id.llUserTag).setOnClickListener(this);

        //new TextClearSuit().addClearListener(etUserRemark, findView(R.id.ivUserRemarkClear));//清空备注按钮点击监听

        //bottomMenuView.setOnMenuItemClickListener(this);//底部菜单点击监听

        //userView.setOnDataChangedListener(() -> user = userView.data);
    }

    //对应HttpRequest.getUser(userId, 0, UserActivity.this); <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun onHttpResponse(requestCode: Int, resultJson: String?, e: Exception?) {
        var user: User? = null
        try { //如果服务器返回的json一定在最外层有个data，可以用OnHttpResponseListenerImpl解析
            val jsonObject: JSONObject = JSON.parseObject(resultJson)
            val data = jsonObject.getJSONObject("data")
            user = JSON.parseObject(data, User::class.java)
        } catch (e1: Exception) {
            Log.e(TAG, """
     onHttpResponse  try { user = Json.parseObject(... >> } catch (JSONException e1) {
     ${e1.message}
     """.trimIndent())
        }
        if (!BaseModel.isCorrect(user) && e != null) {
            showShortToast(R.string.get_failed)
        } else {
            setUser(user)
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
    override fun onDragBottom(rightToLeft: Boolean) {
        if (rightToLeft) return
        finish()
    }

    override fun onClick(v: View) {
        /*
        if (v.getId() == R.id.llUserTag) {
            toActivity(EditTextInfoActivity.createIntent(context, "标签"
                    , StringUtil.getTrimedString(tvUserTag)), REQUEST_TO_EDIT_TEXT_INFO);
        }

         */
    }

    override fun finish() {
        super.finish()
        if (user != null) {
            //user.setHead(StringUtil.getTrimedString(etUserRemark));
            CacheManager.getInstance().save(User::class.java, user, "" + user!!.getId()) //更新缓存
        }
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {} //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    companion object {
        const val TAG = "UserActivity"
        //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        /**
         * 获取启动UserActivity的intent
         */
        @JvmStatic
        fun createIntent(context: AppCompatActivity?, userId: Long): Intent {
            return Intent(context, UserActivity::class.java).putExtra(INTENT_ID, userId)
        }

        //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        private const val REQUEST_TO_BOTTOM_MENU = 1
        private const val REQUEST_TO_EDIT_TEXT_INFO = 2
    }
}