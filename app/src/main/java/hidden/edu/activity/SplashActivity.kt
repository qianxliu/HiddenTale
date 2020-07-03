package hidden.edu.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import hidden.edu.R
import hidden.edu.activity.MainTabActivity.Companion.createIntent
import zuo.biao.library.util.DataKeeper
import zuo.biao.library.util.SettingUtil

/**
 * 闪屏activity，保证点击桌面应用图标后无延时响应
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SettingUtil.isSplash) {
            startActivity(createIntent(this))
            finish()
        }
        Handler().postDelayed({
            DataKeeper.init(this.application)
            startActivity(createIntent(this))
            finish()
        }, 500)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade, R.anim.hold)
    }
}