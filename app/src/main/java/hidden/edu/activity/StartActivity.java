package hidden.edu.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static zuo.biao.library.util.SettingUtil.isSplash;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isSplash) {
            startActivity(MainTabActivity.createIntent(this));
            finish();
        }
        if (isSplash) {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
