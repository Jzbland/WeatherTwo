package com.beestar.jzb.goglebleweather.ui.setting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyHomeSettingActivity extends BaseActivity implements View.OnClickListener {
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/weather_icon/";
    private ImageView mBack;
    private ImageView mTurnPic1;
    private RelativeLayout mSetIcon;
    private ImageView mTurnPic2;
    /**
     * 用户名
     */
    private TextView mSetName;
    private RelativeLayout mSettingName;
    private ImageView mTurnPic3;
    /**
     * 男
     */
    private TextView mSetSex;
    private RelativeLayout mSettingSex;
    private ImageView mTurnPic4;
    /**
     * 123456789
     */
    private TextView mSetNumber;
    private RelativeLayout mSettingNumber;
    private RelativeLayout mChangePwd;
    private RelativeLayout mUnbindSet;
    private RoundedImageView mSetIconSimLeDraweeView;


    private Bitmap mBitmap;
    private String mSaveMessage;
    private Bitmap image_icon_bitmap;
    private Bitmap image_icon_bitmap2;

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mSetIconSimLeDraweeView.setImageBitmap(image_icon_bitmap);
                    break;
                case 2:
                    mSetIconSimLeDraweeView.setImageBitmap(image_icon_bitmap2);
                    Log.i("info","----------55555555555--------");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_setting);
        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    image_icon_bitmap2 =read();
                    if (image_icon_bitmap2!=null){
                        Message msg=new Message();
                        msg.what=2;
                        mhandler.sendMessage(msg);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).run();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTurnPic1 = (ImageView) findViewById(R.id.turn_pic_1);
        mSetIcon = (RelativeLayout) findViewById(R.id.setIcon);
        mSetIcon.setOnClickListener(this);
        mTurnPic2 = (ImageView) findViewById(R.id.turn_pic_2);
        mSetName = (TextView) findViewById(R.id.set_Name);
        mSettingName = (RelativeLayout) findViewById(R.id.setting_Name);
        mSettingName.setOnClickListener(this);
        mTurnPic3 = (ImageView) findViewById(R.id.turn_pic_3);
        mSetSex = (TextView) findViewById(R.id.set_Sex);
        mSettingSex = (RelativeLayout) findViewById(R.id.setting_Sex);
        mSettingSex.setOnClickListener(this);
        mTurnPic4 = (ImageView) findViewById(R.id.turn_pic_4);
        mSetNumber = (TextView) findViewById(R.id.set_Number);
        mSettingNumber = (RelativeLayout) findViewById(R.id.setting_Number);
        mSettingNumber.setOnClickListener(this);
        mChangePwd = (RelativeLayout) findViewById(R.id.change_pwd);
        mUnbindSet = (RelativeLayout) findViewById(R.id.unbind_set);
        mUnbindSet.setOnClickListener(this);
        mChangePwd.setOnClickListener(this);
        mSetIconSimLeDraweeView = (RoundedImageView) findViewById(R.id.setIcon_SimLeDraweeView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.setIcon:
                checkPermission();
                break;
            case R.id.setting_Name:
                break;
            case R.id.setting_Sex:
                break;
            case R.id.setting_Number:
                break;
            case R.id.unbind_set:
                startActivity(new Intent(MyHomeSettingActivity.this, UnbindActivity.class));
                break;
            case R.id.change_pwd:
                startActivity(new Intent(MyHomeSettingActivity.this, ChangePwdActivity.class));
                break;
        }
    }
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);

            c.close();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }else {
//                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        mBitmap=bm;
        new Thread(saveFileRunnable).start();
    }
    private Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                saveFile(mBitmap, "icon");
                mSaveMessage = "图片保存成功！";
                image_icon_bitmap2=read();
                if (image_icon_bitmap2!=null){
                    Log.i("info","----------2222222--------");
                    Message msg=new Message();
                    msg.what=2;
                    mhandler.sendMessage(msg);
                }
            } catch (IOException e) {
                mSaveMessage = "图片保存失败！";
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };
    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("info", mSaveMessage);
            Toast.makeText(MyHomeSettingActivity.this, mSaveMessage, Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        Uri uri = Uri.fromFile(myCaptureFile);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        bos.flush();
        bos.close();
    }
    private Bitmap read() throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH+"icon",null);
        Log.i("info","------------================================--------");
        if (bitmap==null){
            Log.i("info","----------11111111111111--------");
        }
        return bitmap;
    }
}
