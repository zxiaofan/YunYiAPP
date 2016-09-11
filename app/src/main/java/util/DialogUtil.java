package util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class DialogUtil {

    /**
     * 显示退出对话框
     */
    public static void showExitSystemDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("您确定要退出吗？ ");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Log.v(UIUtil.LOG_TAG, "Do Exit MMC... PID: " +
                // android.os.Process.myPid());
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
