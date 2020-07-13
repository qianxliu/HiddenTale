package qian.xin.library.interfaces;

/*网络请求回调接口
 */
public interface OnHttpResponseListener {
    /*
     * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
     * @param resultJson 服务器返回的Json串
     * @param e 异常
     */
    void onHttpResponse(int requestCode, String resultJson, Exception e);
}
