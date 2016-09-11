package com.zxiaofan.yunyi.xzqh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import util.Constants;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class DBUtil {

    private static SQLiteDatabase database;
    public static SQLiteDatabase GetDataBase(Context context) {

        String DB_NAME="zones.db";
        //data/data/com.zxiaofan.yunyi/files/zones.db
        String DB_PATH = context.getApplicationContext().getFilesDir().getAbsolutePath() ;
        Log.e(Constants.TAG,DB_PATH);
        if (!(new File(DB_PATH+ File.separator + DB_NAME)).exists()) {
            try {
                // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
                File f = new File(DB_PATH);
                // 如 database 目录不存在，新建该目录
                if (!f.exists()) {
                    f.mkdir();
                }
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = context.getAssets().open(DB_NAME);
                // 输出流
                OutputStream os = new FileOutputStream(DB_PATH+ File.separator + DB_NAME);
                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                // 关闭文件流
                os.flush();
                os.close();
                is.close();
                database=SQLiteDatabase.openOrCreateDatabase(DB_PATH+ File.separator + DB_NAME,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            database=SQLiteDatabase.openOrCreateDatabase(DB_PATH+ File.separator + DB_NAME,null);
        }
        return database;
    }
}
