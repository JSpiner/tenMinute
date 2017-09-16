package com.noname.tenminute.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noname.tenminute.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SplashActivity extends AppCompatActivity {

    private final String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private final int PERMISSIOIN_REQUEST_CODE = 10518;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {
        checkPermission();
    }

    private void checkPermission() {
        if (isAllPermissionGranted()) {
            startLoginActivity();
        } else {
            requestPermission();
        }
    }

    private void startLoginActivity() {
        Observable.create(
                emitter -> emitter.onNext(
                        new Intent(SplashActivity.this, LoginActivity.class)
                )
        ).delay(
                1000,
                TimeUnit.MILLISECONDS
        ).subscribe(intent -> startActivityAndFinish((Intent) intent));
    }

    private void startActivityAndFinish(Intent intent){
        startActivity(intent);
        finish();
    }

    private boolean isAllPermissionGranted() {
        for (String permission : permissions) {
            if (isPermissionGranted(permission) == false) return false;
        }
        return true;
    }

    private boolean isPermissionGranted(String permission) {
        return ActivityCompat.checkSelfPermission(
                this,
                permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                permissions,
                PERMISSIOIN_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIOIN_REQUEST_CODE) {
            checkPermission();
        }
    }
}
