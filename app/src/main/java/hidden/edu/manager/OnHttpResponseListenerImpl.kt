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
package hidden.edu.manager

import hidden.edu.interfaces.OnHttpResponseListener
import org.json.JSONObject
import qian.xin.library.util.Log
import qian.xin.library.util.StringUtil

/**Http请求结果解析类
 * *适合类似以下固定的json格式
 * <br></br>   {
 * <br></br>     "code": 100,
 * <br></br>     "data": {//可以为任何实体类json，通过Json.parseObject(json, Class<T>)解析；
 * 或者是其它类型的JSONObject，解析方式如 [.onHttpResponse] 内所示
 * <br></br>         ...
 * <br></br>      }
 * <br></br>   }
 * @author Lemon
 * @see UserActivity.initData
 * @use 把请求中的listener替换成new OnHttpResponseListenerImpl(listener)
</T> */
class OnHttpResponseListenerImpl(private var listener: OnHttpResponseListener?) : OnHttpResponseListener, qian.xin.library.interfaces.OnHttpResponseListener {

    /**qian.xin.library.manager.HttpManager.OnHttpResponseListener的回调方法，这里转用listener处理
     */
    override fun onHttpResponse(requestCode: Int, resultJson: String, e: Exception) {
        Log.i(TAG, """
     onHttpResponse  requestCode = $requestCode; resultJson = $resultJson;

     e = ${e.message}
     """.trimIndent())
        var resultCode = 0
        var resultData: String? = null
        var exception: Exception? = null
        try {
            val jsonObject = JSONObject(resultJson)
            resultCode = jsonObject.getInt("code") //TODO code改为接口文档给的key
            resultData = jsonObject.getString("data") //TODO data改为接口文档给的key
        } catch (e1: Exception) {
            Log.e(TAG, """
     onHttpResponse  try { sonObject = new JSONObject(resultJson);... >> } catch (JSONException e1) {
     ${e1.message}
     """.trimIndent())
            exception = e1
        }
        Log.i(TAG, "onHttpResponse  resultCode = $resultCode; resultData = $resultData")
        if (listener == null) {
            listener = this
        }
        if (resultCode > 0 || StringUtil.isNotEmpty(resultData, true)) {
            listener!!.onHttpSuccess(requestCode, resultCode, resultData)
        } else {
            listener!!.onHttpError(requestCode, Exception("$TAG: e = $e; \n exception = $exception"))
        }
    }

    override fun onHttpSuccess(requestCode: Int, resultCode: Int, resultData: String?) {
        Log.i(TAG, """
     onHttpSuccess  requestCode = $requestCode; resultCode = $resultCode; resultData =
     $resultData
     """.trimIndent())
    }

    override fun onHttpError(requestCode: Int, e: Exception?) {
        Log.i(TAG, "onHttpSuccess  requestCode = $requestCode; e = $e")
    }

    companion object {
        private const val TAG = "OnHttpResponseListenerImpl"
    }

}