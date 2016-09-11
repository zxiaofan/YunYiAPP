package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.zxiaofan.yunyi.MyApplication;

/**
 * Created by Pc on 2016/1/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "message.db";

    public final static String Message_TABLE = "m"+ MyApplication.phone;

    private final static int DATABASE_VERSION = 1;


    private final static String CREATE_WEATHER_MANHUA_SQL = "CREATE TABLE " + Message_TABLE
            + "(_id Integer primary key autoincrement,"//id
            +"context1 text,"
            + "messagetype text,"//漫画名
            + "update1 text,"//图片地址
             + "context text,"
            + "mesid text);";//漫画编号




    public DBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String dbName, CursorFactory factory, int version) {
        super(context, dbName, factory, version);

    }

    /**
     * 初次见表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER_MANHUA_SQL);
       // db.execSQL(CREATEWEATHER_CITYS_SQL);
    }

    /**
     * 升级数据库
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Message_TABLE);
        db.execSQL("DROP TABLE ID EXISTS" + Message_TABLE);
        onCreate(db);

    }
}
