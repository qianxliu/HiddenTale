package hidden.edu.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import hidden.edu.application.DemoApplication
import hidden.edu.model.User
import qian.xin.library.util.JSON
import qian.xin.library.util.Log
import qian.xin.library.util.StringUtil

/*
 * 数据工具类
 *
 * @author Lemon
 */
class DataManager private constructor(private val context: Context) {
    private val TAG = "DataManager"

    //用户 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private val PATH_USER = "PATH_USER"
    private val KEY_CURRENT_USER_ID = "KEY_CURRENT_USER_ID"
    private val KEY_LAST_USER_ID = "KEY_LAST_USER_ID"

    /*
     * 判断是否为当前用户
     */
    fun isCurrentUser(userId: Long): Boolean {
        return userId > 0 && userId == currentUserId
    }

    /*
     * 获取当前用户id
     */
    private val currentUserId: Long
        get() {
            val user = currentUser
            return user?.getId() ?: 0
        }

    /*
     * 获取当前用户
     */
    val currentUser: User?
        get() {
            val sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE)
            return if (sdf == null) null else getUser(sdf.getLong(KEY_CURRENT_USER_ID, 0))
        }

    /*
     * 获取用户
     */
    private fun getUser(userId: Long): User? {
        val sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE)
        if (sdf == null) {
            Log.e(TAG, "get sdf == null >>  return;")
            return null
        }
        Log.i(TAG, "getUser  userId = $userId")
        return JSON.parseObject(sdf.getString(StringUtil.getTrimedString(userId), null), User::class.java)
    }

    /*
     * 保存当前用户,只在应用或注销时调用
     *
     * @param user user == null >> user = new User();
     */
    fun saveCurrentUser(user: User?) {
        var user = user
        val sdf = context.getSharedPreferences(PATH_USER, Context.MODE_PRIVATE)
        if (sdf == null) {
            Log.e(TAG, "saveUser sdf == null  >> return;")
            return
        }
        if (user == null) {
            Log.w(TAG, "saveUser  user == null >>  user = new User();")
            user = User()
        }
        val editor = sdf.edit()
        editor.remove(KEY_LAST_USER_ID).putLong(KEY_LAST_USER_ID, currentUserId)
        editor.remove(KEY_CURRENT_USER_ID).putLong(KEY_CURRENT_USER_ID, user.getId())
        editor.apply()
        saveUser(sdf, user)
    }

    /*
     * 保存用户
     */
    private fun saveUser(sdf: SharedPreferences?, user: User?) {
        if (sdf == null || user == null) {
            Log.e(TAG, "saveUser sdf == null || user == null >> return;")
            return
        }
        val key = StringUtil.getTrimedString(user.getId())
        Log.i(TAG, "saveUser  key = user.getId() = " + user.getId())
        sdf.edit().remove(key).putString(key, JSON.toJSONString(user)).apply()
    }

    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        var instance: DataManager? = null
            get() {
                if (field == null) {
                    synchronized(DataManager::class.java) {
                        if (field == null) {
                            field = DataManager(DemoApplication.getInstance())
                        }
                    }
                }
                return field
            }
            private set
    }

}