package cn.contactbook.androidUI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import cn.contactbook.R;
import cn.contactbook.controller.Controller;
import cn.contactbook.model.Contact;
import cn.contactbook.utils.PhotoHelper;
import mabbas007.tagsedittext.TagsEditText;

import static cn.contactbook.model.DBAdapter.KEY_ARMYFRIENDS;
import static cn.contactbook.model.DBAdapter.KEY_CLASSMATES;
import static cn.contactbook.model.DBAdapter.KEY_FAMILY;
import static cn.contactbook.model.DBAdapter.KEY_FELLOWTOWNSMAN;
import static cn.contactbook.model.DBAdapter.KEY_FRIENDS;

public class EditActivity extends AppCompatActivity {
    private String name;
    private String phone;
    private String phone2;
    private String email;
    private String photo;
    private String sex;
    private String company;
    private int id;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_phone2;
    private EditText et_email;
    private EditText et_sex;
    private EditText et_company;
    private ImageView imageView;
    private String imgPath = "";
    //
    private String army_friends = "";
    private String friends = "";
    private String classmates = "";
    private String family = "";
    private String fellowdownsman = "";
    private TagsEditText edit_army_friends;
    private EditText edit_friends;
    private EditText edit_classmates;
    private EditText edit_family;
    private EditText edit_fellowdownsman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit);
        setContentView(R.layout.activity_add);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("EditActivity onCreate()");

     /*   et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_phone2 = (EditText) findViewById(R.id.et_phone2);
        et_email = (EditText) findViewById(R.id.et_email);
        et_sex = (EditText) findViewById(R.id.et_sex);
        et_company = (EditText) findViewById(R.id.et_company);
        imageView = (ImageView) findViewById(R.id.edit_imageView);
        //
        edit_army_friends = (TagsEditText)findViewById(R.id.edit_army_friends);
        edit_friends = (EditText)findViewById(R.id.edit_friends);
        edit_classmates = (EditText)findViewById(R.id.ed_classmates);
        edit_family = (EditText)findViewById(R.id.edit_family);
        edit_fellowdownsman = (EditText)findViewById(R.id.edit_fellow_townsman);*/

        Intent intent = getIntent();//接收上个活动(DetailActivity)传来的数据
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        phone2 = intent.getStringExtra("phone2");
        email = intent.getStringExtra("email");
        photo = intent.getStringExtra("photo");
        imgPath=photo;//把初始路径先赋给imgPath
        sex = intent.getStringExtra("sex");
        company = intent.getStringExtra("company");
        id = intent.getIntExtra("id", 0);
        //
        army_friends = intent.getStringExtra(KEY_ARMYFRIENDS);
        friends = intent.getStringExtra(KEY_FRIENDS);
        classmates = intent.getStringExtra(KEY_CLASSMATES);
        family = intent.getStringExtra(KEY_FAMILY);
        fellowdownsman = intent.getStringExtra(KEY_FELLOWTOWNSMAN);
        edit_army_friends.setText(army_friends);
        edit_friends.setText(friends);
        edit_classmates.setText(classmates);
        edit_family.setText(family);
        edit_fellowdownsman.setText(fellowdownsman);
      /*  edit_army_friends.setOnFocusChangeListener(mOnFocusChangeListener);
        edit_friends.setOnFocusChangeListener(mOnFocusChangeListener);
        edit_classmates.setOnFocusChangeListener(mOnFocusChangeListener);
        edit_family.setOnFocusChangeListener(mOnFocusChangeListener);
        edit_fellowdownsman.setOnFocusChangeListener(mOnFocusChangeListener);*/
      //无效， 不知道原因

        et_name.setText(name);
        et_phone.setText(phone);
        et_phone2.setText(phone2);
        et_email.setText(email);
        et_sex.setText(sex);
        et_company.setText(company);
        Bitmap bitmap = getLoacalBitmap(photo); //根据路径从本地取图片
        imageView.setImageBitmap(bitmap);    //设置Bitmap
    }

  /*  View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String hint;
            EditText mEditText = (EditText)v;
            if(hasFocus) {
                Toast.makeText(EditActivity.this, "focus", Toast.LENGTH_SHORT).show();
                hint = mEditText.getHint().toString();
                mEditText.setTag(hint);
                mEditText.setHint("");
            } else {
                Toast.makeText(EditActivity.this, "lose focus", Toast.LENGTH_SHORT).show();
                hint = mEditText.getTag().toString();
                mEditText.setHint(hint);
            }
        }
    };*/


    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            if (url !=null &&!url.equals("")) {
                FileInputStream fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis);
            } else return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //点击确定时更新数据
    public void sure(View v) {
        String phone_new = et_phone.getText().toString().trim();
        String name_new = et_name.getText().toString().trim();
        String phone2_new = et_phone2.getText().toString().trim();
        String email_new = et_email.getText().toString().trim();
        String sex_new = et_sex.getText().toString().trim();
        String company_new = et_company.getText().toString().trim();
        //
        String new_army_friends = edit_army_friends.getText().toString().trim();
        String new_friends = edit_friends.getText().toString().trim();
        String new_classmates = edit_classmates.getText().toString().trim();
        String new_family = edit_family.getText().toString().trim();
        String new_fellowdownsman = edit_fellowdownsman.getText().toString().trim();
        photo=imgPath;//如果onActivityResult被调用就会改变imgPath的值，把新的路径赋给photo，没调用就用原来的照片路径
        String photo_new = photo;
        Contact contact = new Contact(name_new, phone_new, phone2_new, email_new, photo_new, sex_new, company_new, new_army_friends, new_friends, new_classmates, new_family, new_fellowdownsman);
        Controller controller = new Controller(this);
        controller.update(id, contact);
        finish();
    }

    /**
     * 点击头像选择系统图库或调用相机来选择图片
     *
     * @param view
     */

    public void selectPhoto(View view) {
        //如果版本>=Android6.0并且检查自身权限没有被赋予时，请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            //点击打开相册
            PhotoHelper.selectMyPhotoFormGallery(this, PhotoHelper.REQUEST_LOAD_PHOTO_PICKED);
        }
    }
    //处理权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 2) {
            // 用户取消授权这个数组为空，如果你同时申请两个权限，那么grantResults的length就为2，分别记录你两个权限的申请结果
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //点击打开相册
                PhotoHelper.selectMyPhotoFormGallery(this, PhotoHelper.REQUEST_LOAD_PHOTO_PICKED);
            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!this.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                        //用户已经完全拒绝，或手动关闭了权限开启此对话框缓解一下尴尬...
                        AlertDialog dialog = new AlertDialog.Builder(this)
                                .setMessage("不开启该权限将无法正常工作，请在设置中手动开启！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getAppDetailSettingIntent(EditActivity.this);
                                    }
                                })
                                .setNegativeButton("取消", null).create();
                        dialog.show();

                    } else {
                        //用户一直拒绝并一直不勾选“不再提醒”
                        //不执行该权限对应功能模块，也不用提示，因为下次需要权限还会弹出对话框
                    }
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
    /**
     * 在onActivityResult中实现裁剪功能,并把图片显示出来
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoHelper.REQUEST_LOAD_PHOTO_PICKED:
                    //imgPath 为裁剪后保存的图片的路径
                    imgPath = PhotoHelper.doCropPhoto(this, data.getData(), PhotoHelper.REQUEST_PHOTO_CROP, true);
                    break;
                case PhotoHelper.REQUEST_PHOTO_CROP:
                    //在这里显示或处理裁剪后的照片
                    Bitmap bitmap = getLoacalBitmap(imgPath); //从本地取图片
                    imageView.setImageBitmap(bitmap);    //设置Bitmap
                    break;
            }
        }
    }

}
