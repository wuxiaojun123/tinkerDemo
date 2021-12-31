package com.wxj.hotfixdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBetaPatchListener();


//        String path = "file:///android_asset/patch.apk";
//        Log.d(TAG,"path" + path);
//        Beta.applyTinkerPatch(this, path);


//        askForRequiredPermissions();

        findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"我是补丁的toast",Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,"我是正常的toast",Toast.LENGTH_SHORT).show();
                String s = "我是正常的toast";
//                String s = "我是补丁的toast";
                showToast(s);
            }
        });

        TextView id_tv_tinker = findViewById(R.id.id_tv_tinker);
//        id_tv_tinker.setText("我是补丁的text hot fix文本");
        id_tv_tinker.setText("我是正常的text文本");

    }

//    private void askForRequiredPermissions() {
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }
//        if (!hasRequiredPermissions()) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//        } else {
//            //权限被赋予，继续操作
//            File directory_doc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            File patchFile = new File(directory_doc+"/patch.apk"); // /storage/emulated/0/Download/patch.apk
//            Log.d(TAG,"路径是" + patchFile.getAbsolutePath());
//            if(patchFile.exists()) {
//                Log.d(TAG,"应用补丁");
//                Beta.applyTinkerPatch(this, patchFile.getAbsolutePath());
//            }
//        }
//    }
//
//    private boolean hasRequiredPermissions() {
//        if (Build.VERSION.SDK_INT >= 16) {
//            final int res = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//            return res == PackageManager.PERMISSION_GRANTED;
//        } else {
//            // When SDK_INT is below 16, READ_EXTERNAL_STORAGE will also be granted if WRITE_EXTERNAL_STORAGE is granted.
//            final int res = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            return res == PackageManager.PERMISSION_GRANTED;
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        requestPer();
    }

    private void requestPer() {
        final int res = ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean flag = (res == PackageManager.PERMISSION_GRANTED);
        if (flag) {
            loadTinkerApk();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                    },1001);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG,"requestCode" + requestCode);
        switch (requestCode) {
            case 1001:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"获取到权限了");
                    loadTinkerApk();
                }  else {
                    //向用户解释，拒绝了该权限，一些功能将无法使用
                    Toast.makeText(this, "拒绝了该权限，一些功能将无法使用", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void loadTinkerApk() {
        //权限被赋予，继续操作
//        File directory_doc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File directory_doc = Environment.getExternalStorageDirectory();
        File patchFile = new File(directory_doc+"/patch.apk"); // /storage/emulated/0/Download/patch.apk
        Log.d(TAG,"路径是" + patchFile.getAbsolutePath());
        if(patchFile.exists()) {
            Log.d(TAG,"应用补丁");
            Beta.applyTinkerPatch(this, patchFile.getAbsolutePath());
        }



    }

    private void addBetaPatchListener() {

        BetaPatchListener listener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String s) {

            }

            @Override
            public void onDownloadReceived(long l, long l1) {

            }

            @Override
            public void onDownloadSuccess(String s) {

            }

            @Override
            public void onDownloadFailure(String s) {

            }

            @Override
            public void onApplySuccess(String s) {
                android.util.Log.d(TAG, "补丁应用成功：-->$msg" + s);
                showToast(s);
            }

            @Override
            public void onApplyFailure(String s) {
                android.util.Log.d(TAG, "补丁应用失败：-->$msg" + s);
                showToast(s);
            }

            @Override
            public void onPatchRollback() {

            }
        };
        Beta.betaPatchListener = listener;
    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
