package base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.zxiaofan.yunyi.MyApplication;

/**
 * Created by lenovo on 2016/3/16.
 */
public abstract class BaseActivity extends FragmentActivity {
    public static String TAG = "";
    public OptsharepreInterface o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
        //添加Activity到堆栈
        MyApplication.getInstance().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }


        initView();
        initAction();

        TAG = this.getClass().getName();
    }

    /**
     * @return
     */
    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initAction();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().finishActivity(this);
    }
}
