package com.beestar.jzb.goglebleweather.ui.haveLogin;
import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.BleVersionBean;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.io.File;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class UpDateActivity extends BaseActivity implements View.OnClickListener{
    private static String filesDir = Environment.getExternalStorageDirectory() + "/AMyWeather";
    private static String TAG="UpDateActivity";
    private final int REQUESTCODE = 101;
    private ImageView mBack;
    private DeviceBeanDao deviceBeanDao;
    DeviceBean unique;
    String vison=null;
    private String drownloadUrl=null;

    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MyServiceBlueTooth.HAVEGETVISION)){
                vison = intent.getStringExtra("vison");
                vison_view.setText("当前版本号为："+vison);
                Log.i("jzb", "onReceive: 获取下载地址---");
                getVisonOfService();

            }else if (intent.getAction().equals(MyServiceBlueTooth.HAVEERROR)){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle("更新出错")
                        .setMessage("当前升级出错，退出更新");
                dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }else if (intent.getAction().equals(MyServiceBlueTooth.PROGRESSBAR)){
                float progress_f = intent.getFloatExtra("progress",-1);
                int progress_int=(int)progress_f;
                if (progress_int==100){
                    progress_text.setText("已升级完毕，请重启随身气象站，重新绑定");

                }else {
                    progress_text.setText("请勿进行其他操作正在升级固件请稍候..."+progress_int+"%");
                }
            }
        }
    };
    private TextView vison_view;
    private Button update_button;
    private TextView progress_text;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_date);
        registerReceiver(mReceiver,getInFilter());
        initView();
        creatFile();
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();

        if (unique!=null){
            sendBroadcast(new Intent(MyServiceBlueTooth.GETBANBEN));
        }else {
            vison_view.setText("请先连接设备");
            update_button.setClickable(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                //用户同意
                creatFile();
            } else {
                //用户不同意
            }
        }
    }

    private void creatFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkSelfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.i(TAG, "creatFile: ===================校验权限======================="+checkSelfPermission+"--"+PackageManager.PERMISSION_DENIED);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
            }
        }
        File appDir = new File(filesDir);
        if (!appDir.exists()) {
            boolean isSuccess = appDir.mkdirs();
            Log.d("isSuccess:" ,"---------------------------"+isSuccess);
            Toast.makeText(getApplicationContext(),isSuccess+"",Toast.LENGTH_SHORT).show();
        }
    }

    private void getVisonOfService() {
        checkFile();
        MyApp.getContext().getMyOkHttp().get()
                .url(URL.url+URL.url_updata)
                .tag(this)
                .enqueue(new GsonResponseHandler<BleVersionBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.i("jzb", "onFailure: 获取版本号onFailure");
                    }
                    @Override
                    public void onSuccess(int statusCode, BleVersionBean response) {
                        if (response.getRtn_code()==0){
                            Log.i("jzb", "onSuccess: 获取版本号onSuccess");
                            if (vison!=null){
                                if (("v_"+response.getVersion()).equals(vison)){
                                    vison_view.setText("当前为最新版本");
                                    update_button.setClickable(false);
                                } else {
                                    vison_view.setText("有新版本更新");
                                    update_button.setClickable(true);
                                    drownloadUrl = response.getDownloadUrl();
                                    Log.i("jzb", "onSuccess: 下载地址"+drownloadUrl);
                                }
                            }else {
                                vison_view.setText("当前设备不支持升级操作");
                            }
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        vison_view = (TextView)findViewById(R.id.vison);
        update_button = ((Button) findViewById(R.id.update_button));
        progress_text = ((TextView) findViewById(R.id.progress));
        progress_text.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    private IntentFilter getInFilter(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyServiceBlueTooth.HAVEGETVISION);
        intentFilter.addAction(MyServiceBlueTooth.HAVEERROR);
        intentFilter.addAction(MyServiceBlueTooth.PROGRESSBAR);
        return intentFilter;
    }

    public void updat_ble(View view) {
        Log.i("jzb", "updat_ble: drownloadUrl"+drownloadUrl);
        MyApp.getContext().getMyOkHttp().download()
                .url(URL.url_2+drownloadUrl)
                .filePath(filesDir+"/ble_app_ota_580.img")
                .tag(this)
                .enqueue(new DownloadResponseHandler() {
                    @Override
                    public void onFinish(File downloadFile) {
                        Log.i("update", "onFinish: 文件下载成功");
                        Intent intent1=new Intent(MyServiceBlueTooth.FILENAME);
                        intent1.putExtra("filename","ble_app_ota_580.img");
                        sendBroadcast(intent1);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent();
                                intent.setAction(MyServiceBlueTooth.CESHI);
                                intent.putExtra("address",unique.getMac());
                                sendBroadcast(intent);
                            }
                        }).start();
                    }

                    @Override
                    public void onProgress(long currentBytes, long totalBytes) {

                    }

                    @Override
                    public void onFailure(String error_msg) {
                        Log.i("update", "onFinish: 文件下载失败");
                        Toast.makeText(MyApp.getContext().getApplicationContext(),"升级文件下载失败，请稍后重试...",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void checkFile() {
        File file = new File(filesDir);
        Log.i("jzb", "checkFile:文件夹地址 "+filesDir);
        if (!file.exists()){
            boolean b = file.mkdirs();
            Log.i("info", "checkFile: 创建文件夹："+b);
        }else {
            File[] files = file.listFiles();
            Log.i("info", "checkFile: 校验文件");
            for (File file1:files ){
                if (file1.getName().equals("ble_app_ota_580.img")){
                    file1.delete();
                }
            }
        }
    }
}
