package com.sz.ljs.cargomanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sz.ljs.base.BaseActivity;
import com.sz.ljs.cargomanage.R;

public class SplashActivity extends BaseActivity{
    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goLogin();
            }
        }, SPLASH_DELAY_MILLIS);
    }

    private void goLogin(){
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}
