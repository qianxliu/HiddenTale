/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package hidden.edu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import hidden.edu.R;
import hidden.edu.activity.PdfViewActivity;
import hidden.edu.adapter.UserAdapter;
import hidden.edu.model.User;
import hidden.edu.util.HttpRequest;
import hidden.edu.view.UserView;
import zuo.biao.library.base.BaseHttpRecyclerFragment;
import zuo.biao.library.interfaces.AdapterCallBack;
import zuo.biao.library.interfaces.CacheCallBack;
import zuo.biao.library.util.DataKeeper;
import zuo.biao.library.util.JSON;

import static hidden.edu.util.TestUtil.getUserList;

/*用户列表界面fragment
 * @author Lemon
 * @use new UserListFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 * @must 查看 .httpsManager 中的@must和@warn
 *       查看 .SettingUtil 中的@must和@warn
 */
public class MyRecyclerFragment extends BaseHttpRecyclerFragment<User, UserView, UserAdapter> implements CacheCallBack<User> {

    //	private static final String TAG = "UserListFragment";

    //与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private static final String ARGUMENT_RANGE = "ARGUMENT_RANGE";

    public static MyRecyclerFragment createInstance(int range) {
        MyRecyclerFragment fragment = new MyRecyclerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_RANGE, range);

        fragment.setArguments(bundle);
        return fragment;
    }

    //与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //private static final int RANGE_ALL = httpRequest.USER_LIST_RANGE_ALL;
    public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;
    private int range = RANGE_RECOMMEND;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        argument = getArguments();
        if (argument != null) {
            range = argument.getInt(ARGUMENT_RANGE, range);
        }


        initCache(this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        srlBaseHttpRecycler.autoRefresh();
        srlBaseHttpRecycler.setBackgroundResource(R.drawable.texture3);

        return view;
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须调用
        super.initView();

    }

    @Override
    public void setList(final List<User> list) {
        setList(new AdapterCallBack<UserAdapter>() {

            @Override
            public UserAdapter createAdapter() {
                return new UserAdapter(context);
            }

            @Override
            public void refreshAdapter() {
                adapter.refresh(list);
            }
        });
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initData() {//必须调用
        super.initData();

    }

    @Override
    public void getListAsync(final int page) {
        //实际使用时用这个，需要配置服务器地址		httpsRequest.getUserList(range, page, -page, this);

        //仅测试用<<<<<<<<<<<
        //page显示次数
        new Handler().post(() -> onHttpResponse(-page, page >= 1 ? null : JSON.toJSONString(getUserList(page, getCacheCount())), null));
        //仅测试用>>>>>>>>>>>>
    }

    @Override
    public List<User> parseArray(String json) {
        return JSON.parseArray(json, User.class);
    }


    @Override
    public Class<User> getCacheClass() {
        return User.class;
    }

    @Override
    public String getCacheGroup() {
        return "range=" + range;
    }

    @Override
    public String getCacheId(User data) {
        return data == null ? null : "" + data.getId();
    }


    @Override
    public int getCacheCount() {
        return URLS.length;
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initEvent() {//必须调用
        super.initEvent();
        DataKeeper.init(this.context.getApplication());
    }

    static String[] URLS = {
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/1.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/2.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/3.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/4.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/5.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/6.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/7.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/8.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/9.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/10.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/11.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/12.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/13.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/14.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/15.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/16.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/17.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/18.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/19.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/20.pdf",
            "https://git.nwu.edu.cn/2018104171/pdf/raw/master/21.pdf",
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position % URLS.length > -1) {
            toActivity(PdfViewActivity.createIntent(context, URLS[position % URLS.length], String.valueOf(position + 1)));
        }
    }


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}