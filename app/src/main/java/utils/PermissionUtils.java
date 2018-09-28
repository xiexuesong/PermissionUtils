package utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.security.Permission;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by admin on 2018/9/28.
 */

public class PermissionUtils {

    private static PermissionUtils permissionUtils = null;


    public static PermissionUtils getInstance() {
        if (permissionUtils == null) {
            synchronized (PermissionUtils.class) {
                if (permissionUtils == null) {
                    permissionUtils = new PermissionUtils();
                }
            }
        }
        return permissionUtils;
    }


    /**
     * 判断是否有权限
     */
    public boolean checkHasPermission(Context context, String permission) {
        int flag = ActivityCompat.checkSelfPermission(context, permission);
        if (flag == PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 6.0 及以上版本可以使用该api
     * 判断是否已经勾选禁止不在提示
     * true 显示
     * false 不显示，已经勾选禁止显示权限请求弹框或者权限已经被允许
     */
    public boolean selectNotAsk(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean flag = activity.shouldShowRequestPermissionRationale(permission);
            return flag;
        }
        return false;
    }

    /**
     * 请求权限
     */
    public void requestPermissions(Activity activity , String[] permission,int requestCode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        }
    }


}
