package hidden.edu.util

import qian.xin.library.interfaces.OnHttpResponseListener
import qian.xin.library.manager.HttpManager
import qian.xin.library.model.Parameter
import qian.xin.library.util.SettingUtil
import qian.xin.library.util.StringUtil
import java.util.*

/*HTTP请求工具类
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 *       都要符合后端给的接口文档
 */
object HttpRequest {
    //	private static final String TAG = "HttpRequest";
    /*基础URL，这里服务器设置可切换 */
    @JvmField
    val URL_BASE: String = SettingUtil.getCurrentServerAddress()

    /*翻译，根据有道翻译API文档请求
     * http://fanyi.youdao.com/openapi?path=data-mode
     * <br></br> 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
     */
    @JvmStatic
    fun translate(word: String, requestCode: Int, listener: OnHttpResponseListener?) {
        val request: MutableMap<String, Any> = HashMap()
        request["q"] = word
        request["keyfrom"] = "HiddenTale"
        request["key"] = 1430082675
        request["type"] = "data"
        request["doctype"] = "json"
        request["version"] = "1.1"
        HttpManager.getInstance()[request, "https://fanyi.youdao.com/openapi.do", requestCode, listener]
    }

    const val USER_LIST_RANGE_RECOMMEND = 1

    //user>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /*添加请求参数，value为空时不添加，最快在 19.0 删除
     */
    @Deprecated("")
    fun addExistParameter(list: MutableList<Parameter?>?, key: String?, value: Any?) {
        var list = list
        if (list == null) {
            list = ArrayList()
        }
        if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true)) {
            list.add(Parameter(key, value))
        }
    }
}