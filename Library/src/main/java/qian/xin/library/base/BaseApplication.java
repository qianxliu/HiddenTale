package qian.xin.library.base;

import android.app.Application;

import qian.xin.library.util.DataKeeper;
import qian.xin.library.util.Log;
import qian.xin.library.util.SettingUtil;


/*基础Application
 * @author Lemon
 * @see #init
 * @use extends BaseApplication 或 在你的Application的onCreate方法中BaseApplication.init(this);
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    public BaseApplication() {
    }

    private static BaseApplication instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "项目启动 >>>>>>>>>>>>>>>>>>>> \n\n");

        init(this);
    }

    /*初始化方法
     * @param application
     * @must 调用init方法且只能调用一次，如果extends BaseApplication会自动调用
     */
    public static void init(BaseApplication application) {
        instance = application;
        if (instance == null) {
            Log.e(TAG, "\n\n\n\n\n !!!!!! 调用BaseApplication中的init方法，instance不能为null !!!" +
                    "\n <<<<<< init  instance == null ！！！ >>>>>>>> \n\n\n\n");
        }

        DataKeeper.init(instance);
        SettingUtil.init(instance);
    }
}
