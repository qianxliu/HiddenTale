package hidden.edu.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import hidden.edu.R
import qian.xin.library.base.BaseActivity
import qian.xin.library.interfaces.OnBottomDragListener
import qian.xin.library.util.Log
import qian.xin.library.util.SettingUtil

/*
 * 设置界面Activity
 *
 * @author Lemon
 * @use toActivity(SettingActivity.createIntent ( ...));
 */
open class SettingActivity : BaseActivity(), OnBottomDragListener {
    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_activity, this)

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView()
        initData()
        initEvent()
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private var ivSettings: Array<ImageView?>? = null
    override fun initView() { //必须调用
        ivSettings = arrayOfNulls(8)
        ivSettings!![0] = findView(R.id.ivSettingCache)
        ivSettings!![1] = findView(R.id.ivSettingPreload)
        ivSettings!![2] = findView(R.id.ivSettingVoice)
        ivSettings!![3] = findView(R.id.ivSettingVibrate)
        ivSettings!![4] = findView(R.id.ivSettingNoDisturb)
        ivSettings!![5] = findView(R.id.ivSettingTestMode)
        ivSettings!![6] = findView(R.id.ivSettingFirstStart)
        ivSettings!![7] = findView(R.id.ivSettingDirect)
    }

    private var settings: BooleanArray? = null
    private val switchResIds = intArrayOf(R.drawable.off, R.drawable.on)

    /*
     * 设置开关
     */
    private fun setSwitch(which: Int, isToOn: Boolean) {
        if (ivSettings == null || which < 0 || which >= ivSettings!!.size) {
            Log.e(TAG, "ivSettings == null || which < 0 || which >= ivSettings.length >> reutrn;")
            return
        }
        ivSettings!![which]!!.setImageResource(switchResIds[if (isToOn) 1 else 0])
        settings!![which] = isToOn
    }

    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun initData() { //必须调用
        showProgressDialog(R.string.loading)
        runThread(TAG + "initData") {
            settings = SettingUtil.getAllBooleans(context)
            runUiThread {
                dismissProgressDialog()
                if (settings == null || settings!!.size <= 0) {
                    finish()
                    return@runUiThread
                }
                for (i in settings!!.indices) {
                    setSwitch(i, settings!![i])
                }
            }
        }
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    override fun initEvent() { //必须调用
        for (i in ivSettings!!.indices) {
            ivSettings!![i]!!.setOnClickListener {
                isSettingChanged = true
                setSwitch(i, !settings!![i])
            }
        }
    }

    override fun onDragBottom(rightToLeft: Boolean) {
        if (rightToLeft) {
            SettingUtil.restoreDefault()
            initData()
            return
        }
        finish()
    }

    //生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private var isSettingChanged = false
    override fun finish() {
        if (isSettingChanged) {
            showProgressDialog("正在保存设置，请稍后...")
            runThread(TAG) {
                SettingUtil.putAllBoolean(settings)
                isSettingChanged = false
                runUiThread { finish() }
            }
            return
        }
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        ivSettings = null
        settings = null
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {} //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    companion object {
        private const val TAG = "SettingActivity"

        //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        /*
         * 启动这个Activity的Intent
         */
        @JvmStatic
        fun createIntent(context: AppCompatActivity?): Intent {
            return Intent(context, SettingActivity::class.java)
        }
    }
}