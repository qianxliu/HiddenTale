package qian.xin.library.interfaces

import android.content.pm.PackageManager
import java.io.IOException

/*Activity和Fragment的公共逻辑接口
* @use Activity或Fragment implements Presenter
*/
interface Presenter {
    /*
     * UI显示方法(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    fun initView()

    /*
     * Data数据方法(存在数据获取或处理代码，但不存在事件监听代码)
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    @Throws(PackageManager.NameNotFoundException::class)
    fun initData()

    /*
     * Event事件方法(只要存在事件监听代码就是)
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    @Throws(IOException::class)
    fun initEvent()

    /*
     * 是否存活(已启动且未被销毁)
     */
    val isAlive: Boolean

    /*
     * 是否在运行
     */
    val isRunning: Boolean

    companion object {
        const val INTENT_TITLE = "INTENT_TITLE"
        const val INTENT_ID = "INTENT_ID"
        const val INTENT_TYPE = "INTENT_TYPE"
    }
}