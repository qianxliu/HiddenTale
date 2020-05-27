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

package hidden.edu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import hidden.edu.R;

import static zuo.biao.library.util.SettingUtil.isSplash;

/**
 * 闪屏activity，保证点击桌面应用图标后无延时响应
 *
 * @author Lemon
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isSplash) {
            Intent intent = new Intent(SplashActivity.this, MainTabActivity.class);
            startActivity(intent);
            finish();
        }
        new Handler().postDelayed(() -> {
            startActivity(MainTabActivity.createIntent(SplashActivity.this));
            finish();
        }, 500);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }
}