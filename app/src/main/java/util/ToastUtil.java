package util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zxiaofan.yunyi.R;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param context
     */

    public static void ToastShow(Context context, String msg, boolean warnFlag) {
        if (warnFlag) {
            View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml, null);
            TextView text = (TextView) layout.findViewById(R.id.tv);
            text.setText(msg);
            layout.getBackground().setAlpha(200);
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

    }

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
