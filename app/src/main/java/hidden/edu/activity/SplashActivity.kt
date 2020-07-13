package hidden.edu.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import hidden.edu.R
import hidden.edu.activity.MainTabActivity.Companion.createIntent
import qian.xin.library.util.DataKeeper.init
import qian.xin.library.util.SettingUtil

/*
 * 闪屏activity，保证点击桌面应用图标后无延时响应
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SettingUtil.isSplash) {
            startActivity(createIntent(this))
            finish()
        } else
            Handler().postDelayed({
                init(this.application)
                setContentView(R.layout.activity_splash)
                startActivity(createIntent(this))
                finish()
            }, 500)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade, R.anim.hold)
    }
}