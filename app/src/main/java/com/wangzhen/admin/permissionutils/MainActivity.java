package com.wangzhen.admin.permissionutils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import utils.PermissionUtils;
import utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    private String[] permission = {Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtil.init(this);
        permissionUtils = PermissionUtils.getInstance();
        permissionUtils.requestPermissions(this, permission, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        Log.i("MDL", "permission:" + grantResults[i]);
                    }
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.i("MDL", "权限授予");
                    } else {
                        Log.i("MDL", "权限未被授予");
                    }
                }
                break;
            default:
                break;
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.camera:
                checkPermission(Manifest.permission.CAMERA,"相机");
                break;
            case R.id.callPhone:
                checkPermission(Manifest.permission.CALL_PHONE,"拨打电话");
                break;
            case R.id.read:
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,"读写权限");
                break;
            default:
                break;
        }
    }

    private void checkPermission(String permission,String text){
        boolean flag = PermissionUtils.getInstance().checkHasPermission(this,permission);
        if(flag){
            ToastUtil.show("当前已经拥有" + text + "权限");
        }else {
            boolean flag_ask = PermissionUtils.getInstance().selectNotAsk(this,permission);
            if(flag_ask){
                //显示权限对话框
                String per[] = {permission};
                PermissionUtils.getInstance().requestPermissions(this,per,2);
            }else {
                ToastUtil.show("当前" + text + "权限已被禁止,请手动授予");
            }
        }
    }
}
