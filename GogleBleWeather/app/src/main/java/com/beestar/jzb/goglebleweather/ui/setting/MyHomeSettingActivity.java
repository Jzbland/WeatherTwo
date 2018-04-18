package com.beestar.jzb.goglebleweather.ui.setting;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.DialogFragment.MyChangeInfoDialog;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.ChangePwd;
import com.beestar.jzb.goglebleweather.bean.ChangeSex;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.ImageUtil;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyHomeSettingActivity extends BaseActivity implements View.OnClickListener,MyChangeInfoDialog.OnChangeInfoDialogListener,ImageUtil.CropHandler{
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    public final static String ALBUM_PATH
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

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_setting);
        initView();
        requestLocation();
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
    private void requestLocation() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {

                }
                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
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

        mSetName.setText(((String) SPUtils.get(MyApp.getContext(), "name", "")));
        if (((String)SPUtils.get(MyApp.getContext(),"sex","")).equals("0")){
            mSetSex.setText("男");
        }else if (((String)SPUtils.get(MyApp.getContext(),"sex","")).equals("1")){
            mSetSex.setText("女");
        }

        mSetNumber.setText((String)SPUtils.get(MyApp.getContext(),"phone",""));
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
                MyChangeInfoDialog myChangeInfoDialog = new MyChangeInfoDialog();
                myChangeInfoDialog.show(getFragmentManager(),"MyInfoChange");

                break;
            case R.id.setting_Sex:
                change_sex();
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
    /**
     * 修改名字回调
     * @param infoName
     */
    @Override
    public void onChangeInfoDialogNext(String infoName) {
       changeName(infoName);
    }
    /**
     * 修改男女
     */
    public void change_sex(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //定义一个AlertDialog
        String[] strarr = {"男","女"};

        builder.setItems(strarr, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                String sexinfo ;
                String sex = "0";
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    sex = "0";
                    sexinfo ="男";
                }else {//女
                    sex = "1";
                    sexinfo ="女";
                }
                changeSex(sex,sexinfo);
            }
        });
        builder.show();
    }
    /**
     * 修改性别
     */
    public void changeSex(final String sex, final String sexinfo){
        MyApp.getContext().getMyOkHttp().post()
                .url(URL.url+URL.url_change)
                .tag(this)
                .jsonParams(new Gson().toJson(new ChangeSex((String) SPUtils.get(MyApp.getContext(),"phone",""),sex)))
                .enqueue(new GsonResponseHandler<ReturnBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }

                    @Override
                    public void onSuccess(int statusCode, ReturnBean response) {
                        if (response.getRtn_code()==0){
                            Toast.makeText(MyApp.getContext(),"性别修改成功",Toast.LENGTH_SHORT).show();
                            SPUtils.put(MyApp.getContext(),"name",sex);
                            mSetSex.setText(sexinfo);
                        }else {
                            Toast.makeText(MyApp.getContext(),"性别修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /**
     * 修改名字
     */
    public void changeName(final String name){
        MyApp.getContext().getMyOkHttp().post()
                .url(URL.url+URL.url_change)
                .tag(this)
                .jsonParams(new Gson().toJson(new ChangePwd((String) SPUtils.get(MyApp.getContext(),"phone",""),name)))
                .enqueue(new GsonResponseHandler<ReturnBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }

                    @Override
                    public void onSuccess(int statusCode, ReturnBean response) {
                        if (response.getRtn_code()==0){
                            Toast.makeText(MyApp.getContext(),"名字修改成功",Toast.LENGTH_SHORT).show();
                            SPUtils.put(MyApp.getContext(),"name",name);
                            mSetName.setText(name);
                        }else {
                            Toast.makeText(MyApp.getContext(),"名字修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);

            //选择相册
//            Intent galleryIntent = ImageUtil
//                    .getCropHelperInstance()
//                    .buildGalleryIntent();
//            startActivityForResult(galleryIntent,
//                    ImageUtil.REQUEST_GALLERY);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        //获取图片路径
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
                Intent intent = new Intent();
                intent.setAction(Keyparameter.ACTTION_UPDATAUI);
                intent.putExtra("UPDATA","success");
                Log.i("info","--------==============发送ui广播===========-------");
                sendBroadcast(intent);
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
        bm.compress(Bitmap.CompressFormat.JPEG, 10, bos);
        Uri uri = Uri.fromFile(myCaptureFile);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        bos.flush();
        bos.close();
    }
    private Bitmap read() throws FileNotFoundException {
        Bitmap bm=null;
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f, 0.2f);
        Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH+"icon",null);
        if (bitmap!=null){
            bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        if (bm==null){
            Log.i("info","------null-------");
        }
        return bm;
    }
    private Bitmap x_To_Y(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bm;
    }

    public void exit_button(View view) {
        SPUtils.clear(getApplicationContext());
        finish();
    }

    @Override
    public void onPhotoCropped(Bitmap photo, int requesCode) {
        switch (requesCode){
            case ImageUtil.RE_GALLERY:
                try {
                    saveFile(photo,"icon");
                    mSetIconSimLeDraweeView.setImageBitmap(photo);
                    Intent intent = new Intent();
                    intent.setAction(Keyparameter.ACTTION_UPDATAUI);
                    intent.putExtra("UPDATA","success");
                    Log.i("info","--------==============发送ui广播===========-------");
                    sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String message) {

    }

    @Override
    public Activity getContext() {
        return MyHomeSettingActivity.this;
    }

}
