package com.beestar.jzb.goglebleweather.ui;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.utils.ActivityController;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        ActivityController.addActivity(this);


       // requestLocation3();
       // requestLocation4();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityController.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }


    private void requestLocation3() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(this, "获取您的内存卡读权限", Toast.LENGTH_LONG).show();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户授予获取内存卡读权限", Toast.LENGTH_LONG).show();
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户拒绝了获取内存卡读权限", Toast.LENGTH_LONG).show();
                }
            }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }

}
