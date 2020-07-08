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

package hidden.edu.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import hidden.edu.manager.DataManager;
import hidden.edu.model.User;
import qian.xin.library.base.BaseApplication;
import qian.xin.library.util.StringUtil;

/**
 * Application
 *
 * @author Lemon
 */
public class DemoApplication extends BaseApplication {
    private static final String TAG = "DemoApplication";

    @SuppressLint("StaticFieldLeak")
    public static DemoApplication context;

    public static Application getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


    /**
     * 获取当前用户id
     */
    public static long getCurrentUserId() {
        currentUser = getCurrentUser();
        Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
        return currentUser == null ? 0 : currentUser.getId();
    }

    /**
     * 获取当前用户phone
     */
    public String getCurrentUserPhone() {
        currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getPhone();
    }


    private static User currentUser = null;

    public static User getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public void saveCurrentUser(User user) {
        if (user == null) {
            Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
            return;
        }
        if (user.getId() <= 0 && !StringUtil.isNotEmpty(user.getName(), true)) {
            Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
                    " && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
            return;
        }

        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    public void logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    /**
     * 判断是否为当前用户
     */
    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }


}
