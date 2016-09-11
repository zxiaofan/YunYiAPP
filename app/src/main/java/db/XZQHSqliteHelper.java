package db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bean.CityBean;

/**
 * @Description: 行政区划数据库
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class XZQHSqliteHelper extends SQLiteOpenHelper{
    public static final String TB_NAME = "cities";

    public XZQHSqliteHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_NAME + "("
                + CityBean.CITY_ID + " integer primary key,"
                + CityBean.PROVINCE_NAME + " varchar," + CityBean.CITY_NAME
                + " varchar," + CityBean.COUNTY_NAME + " varchar,"
                + CityBean.COUNTRY_NAME + " varchar,"
                + CityBean.COUNTRY_NAME_IN_ENGLISH + " varchar" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
        Log.e("Database", "onUpgrade");
    }

    // 更新列
    public void updateColumn(SQLiteDatabase db, String oldColumn,
                             String newColumn, String typeColumn) {
        try {
            db.execSQL("ALTER TABLE " + TB_NAME + " CHANGE " + oldColumn + " "
                    + newColumn + " " + typeColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
