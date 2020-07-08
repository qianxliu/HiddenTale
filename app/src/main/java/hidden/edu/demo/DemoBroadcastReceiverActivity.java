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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import hidden.edu.R;
import qian.xin.library.base.BaseActivity;
import qian.xin.library.base.BaseBroadcastReceiver;
import qian.xin.library.interfaces.OnBottomDragListener;
import qian.xin.library.util.CommonUtil;
import qian.xin.library.util.Log;

/*
  使用方法：复制>粘贴>改名>改代码
 */

/*
 * 使用BroadcastReceiver的Activity示例
 *
 * @author Lemon
 * @use toActivity(DemoBroadcastReceiverActivity.createIntent ( ...));
 */
public class DemoBroadcastReceiverActivity extends BaseActivity implements OnBottomDragListener {
    private static final String TAG = "DemoBroadcastReceiverActivity";

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     */
    public static Intent createIntent(AppCompatActivity context) {
        return new Intent(context, DemoBroadcastReceiverActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_broadcast_receiver_activity, this);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initView() {//必须在onCreate方法内调用
        autoSetTitle();

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    @Override
    public void initData() {//必须在onCreate方法内调用

    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //示例代码<<<<<<<<<<<<<<<<<<<
    public static final String STATE = "state";

    private BaseBroadcastReceiver baseBroadcastReceiver;//BaseBroadcastReceiver直接使用示例
    private DemoBroadcastReceiver demoBroadcastReceiver;//内部类BaseBroadcastReceiver子类使用示例
    private DemoHeadsetConnectionReceiver demoHeadsetConnectionReceiver;//外部类BaseBroadcastReceiver子类使用示例

    //示例代码>>>>>>>>>>>>>>>>>>>
    @Override
    public void initEvent() {//必须在onCreate方法内调用
        //示例代码<<<<<<<<<<<<<<<<<<<

        //BaseBroadcastReceiver直接使用示例 <<<<<<<<<<<<<<
        baseBroadcastReceiver = new BaseBroadcastReceiver(context) {//除了onReceive内代码，其它代码都是复制过来的
            @Override
            public BaseBroadcastReceiver register() {//支持String, String[], List<String>
                return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
            }

            @Override
            public void unregister() {
                unregister(context, this);
            }

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.hasExtra(STATE)) {
                    Log.i(TAG, "baseBroadcastReceiver.onReceive intent.getIntExtra(STATE, 0) = "
                            + intent.getIntExtra(STATE, 0));
                    CommonUtil.showShortToast(this.context,"baseBroadcastReceiver\n" + (intent.getIntExtra(STATE, 0) == 1
                            ? "已插入耳机" : "请插入耳机"));
                }
            }
        }.register();
        //BaseBroadcastReceiver直接使用示例 >>>>>>>>>>>>>>


        //内部类BaseBroadcastReceiver子类使用示例 <<<<<<<<<<<<<<
        demoBroadcastReceiver = new DemoBroadcastReceiver(context);
        demoBroadcastReceiver.register();
        //内部类BaseBroadcastReceiver子类使用示例 >>>>>>>>>>>>>>


        //外部类BaseBroadcastReceiver子类使用示例 <<<<<<<<<<<<<<
        demoHeadsetConnectionReceiver = new DemoHeadsetConnectionReceiver(context)
                .register(isConnected -> {
                    Log.i(TAG, "demoHeadsetConnectionReceiver.onHeadsetConnectionChanged" +
                            " isConnected = " + isConnected);
                    CommonUtil.showShortToast(this.context,"demoHeadsetConnectionReceiver\n"
                            + (isConnected ? "已插入耳机" : "请插入耳机"));
                });
        //外部类BaseBroadcastReceiver子类使用示例 >>>>>>>>>>>>>>

        //示例代码>>>>>>>>>>>>>>>>>>>

    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {

            return;
        }

        finish();
    }


    //示例代码<<<<<<<<<<<<<<<<<<<
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BaseBroadcastReceiver直接使用示例 <<<<<<<<<<<<<<
        baseBroadcastReceiver.unregister();
        //BaseBroadcastReceiver直接使用示例 >>>>>>>>>>>>>>

        //内部类BaseBroadcastReceiver子类使用示例 <<<<<<<<<<<<<<
        demoBroadcastReceiver.unregister();
        //内部类BaseBroadcastReceiver子类使用示例 >>>>>>>>>>>>>>

        //外部类BaseBroadcastReceiver子类使用示例 <<<<<<<<<<<<<<
        demoHeadsetConnectionReceiver.unregister();
        //外部类BaseBroadcastReceiver子类使用示例 >>>>>>>>>>>>>>
    }
    //示例代码>>>>>>>>>>>>>>>>>>>


    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //内部类BaseBroadcastReceiver子类使用示例 <<<<<<<<<<<<<<

    /**
     * 除了onReceive内代码，其它代码都是复制过来的
     */
    public class DemoBroadcastReceiver extends BaseBroadcastReceiver {

        public DemoBroadcastReceiver(AppCompatActivity context) {
            super(context);
        }

        @Override
        public BaseBroadcastReceiver register() {//支持String, String[], List<String>
            return (BaseBroadcastReceiver) register(context, this, "android.intent.action.HEADSET_PLUG");
        }

        @Override
        public void unregister() {
            unregister(context, this);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra(STATE)) {
                Log.i(TAG, "demoBroadcastReceiver.onReceive intent.getIntExtra(STATE, 0) = "
                        + intent.getIntExtra(STATE, 0));
                CommonUtil.showShortToast(this.context,"demoBroadcastReceiver\n" + (intent.getIntExtra(STATE, 0) == 1
                        ? "已插入耳机" : "请插入耳机"));
            }
        }
    }
    //内部类BaseBroadcastReceiver子类使用示例 >>>>>>>>>>>>>>

    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}