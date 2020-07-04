package qian.xin.library.util

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import qian.xin.library.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/*
* 下载工具类
*
* @author Lemon
*/
object DownloadUtil {
    private const val TAG = "DownloadUtil"
    fun downLoadFile(name: String, httpUrl: String?): File {
        val httpUrl = httpUrl
        val file = File(DataKeeper.fileRootPath + name)
        try {
            //httpUrl = StringUtil.getCorrectUrl(httpUrl);
            val url = URL(httpUrl)
            try {
                val conn = url
                        .openConnection() as HttpURLConnection
                val `is` = conn.inputStream
                val fos = FileOutputStream(file)
                val buf = ByteArray(256)
                conn.connect()
                while (true) {
                    if (`is` != null) {
                        val numRead = `is`.read(buf)
                        if (numRead <= 0) {
                            break
                        } else {
                            fos.write(buf, 0, numRead)
                        }
                    } else {
                        break
                    }
                }
                conn.disconnect()
                fos.close()
                if (BuildConfig.DEBUG && `is` == null) {
                    error("Assertion failed")
                }
                `is`!!.close()
            } catch (e: IOException) {
                Log.e(TAG, """
     downLoadFile   try { HttpURLConnection conn = (HttpURLConnection) url ... } catch (IOException e) {
     ${e.message}
     """.trimIndent())
            }
        } catch (e: MalformedURLException) {
            Log.e(TAG, """
     downLoadFile   try {  URL url = new URL(httpUrl); ... } catch (IOException e) {
     ${e.message}
     """.trimIndent())
        }
        return file
    }

    //打开APK程序代码
    fun openFile(context: AppCompatActivity?, file: File) {
        if (context == null) {
            Log.e(TAG, "openFile  context == null >> return;")
            return
        }
        Log.e("OpenFile", file.name)
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        context.startActivity(intent)
    }
}