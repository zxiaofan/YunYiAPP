package com.zxiaofan.yunyi;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.Stack;

import base.OptsharepreInterface;
import util.Constants;
import util.LogUtil;

/**
 * Created by Administrator on 2016/2/22.
 */
public class MyApplication extends Application {
    String TAG = "MyApplication";
    //判断是否已登陆
    public static boolean loginFlag;
    public static String phone ;
    public static String token ;
    private static Stack<Activity> activityStack;
    private static MyApplication singleton;
    private OptsharepreInterface o;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        //读取配置信息 确认是否已登陆
       // String flag = SharedPreferenceUtil.readString(getApplicationContext(), "loginFlag", "false");
        o=new OptsharepreInterface(getApplicationContext());
        String flag=o.getPres("loginFlag");
        Log.e(Constants.TAG,flag);
        loginFlag = Boolean.parseBoolean(flag);
        //获取user对象

        initImageLoaderConfig();

    }


    private void initImageLoaderConfig() {
        /**
         * universal image loader 配置文件
         */
        @SuppressWarnings("deprecation")

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(180, 320)
                // max width, max height，即保存的每个缓存文件的最大长宽
                // .discCacheExtraOptions(180, 320, null)
                // 设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                // 线程池内加载的数量 推荐1-5
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100)
                .discCache(new UnlimitedDiskCache(cacheDir))
                // 缓存的文件数量
                //.discCache(
                       // new UnlimitedDiscCache(new File(Environment.getExternalStorageDirectory() + "/myApp/imgCache")))
               // .discCache((DiskCache) new File(Environment.getExternalStorageDirectory() + "/myApp/imgCache"))
                // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)).writeDebugLogs() // Remove
                // for
                // release
                // app
                .build();// 开始构建
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);

    }

    @SuppressWarnings("deprecation")
    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().showImageOnLoading(null) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(null)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(null) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        return options;
    }

    public static MyApplication getInstance() {

        return singleton;
    }

    /**
     * 把Activity添加到栈中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        LogUtil.i(TAG, "当前回退栈的Activity数量:" + activityStack.size());
    }

    /**
     * 当前Activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }


    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void ExitApp() {
        try {

            finishAllActivity();
        } catch (Exception e) {

        }
    }


}
