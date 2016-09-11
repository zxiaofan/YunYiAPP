package com.zxiaofan.yunyi.xzqh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bean.CityNewBean;

/**
 * @Description: 行政区划数据库查讯类
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class XZQHDBHelper {

    private Context mContext;
    private SQLiteDatabase database;
    private String TB_NAME="t_zone";

    public XZQHDBHelper(Context context) {
        this.mContext = context;
        database = DBUtil.GetDataBase(context);
    }

    /**
    * Describe:     获取所有省份，直辖市，自治区
    * User:         xiaofan
    * Date:         2016/4/13 15:16
    */
    public List<CityNewBean> queryAllProcince() {
        List<CityNewBean> lists = new ArrayList<CityNewBean>();
        String[] columns = new String[]{CityNewBean.ZONE_CODE,
                CityNewBean.ZONE_DESC, CityNewBean.ZONE_CODE_PAR,
                CityNewBean.ZONE_LEVEL};
        String selection = "zone_level=?";
        String[] selectionArgs = new String[]{"1"};
        Cursor cursor = database.query(TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityNewBean bean = null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            bean = new CityNewBean();
            bean.setZone_code(cursor.getString(cursor.getColumnIndex("zone_code")));
            bean.setZone_desc(cursor.getString(cursor.getColumnIndex("zone_desc")));
            bean.setZone_code_par(cursor.getString(cursor.getColumnIndex("zone_code_par")));
            bean.setZone_level(cursor.getString(cursor.getColumnIndex("zone_level")));
            lists.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return lists;
    }


    /**
     * Describe:     获取某省份下的市
     * User:         xiaofan
     * Date:         2016/4/13 15:16
     */
    public List<CityNewBean> queryCityByCode(String code) {
        List<CityNewBean> lists = new ArrayList<CityNewBean>();
        String[] columns = new String[]{CityNewBean.ZONE_CODE,
                CityNewBean.ZONE_DESC, CityNewBean.ZONE_CODE_PAR,
                CityNewBean.ZONE_LEVEL};
        String selection = "zone_code_par=?";
        String[] selectionArgs = new String[]{code};
        Cursor cursor = database.query(TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityNewBean bean = null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            bean = new CityNewBean();
            bean.setZone_code(cursor.getString(cursor.getColumnIndex("zone_code")));
            bean.setZone_desc(cursor.getString(cursor.getColumnIndex("zone_desc")));
            bean.setZone_code_par(cursor.getString(cursor.getColumnIndex("zone_code_par")));
            bean.setZone_level(cursor.getString(cursor.getColumnIndex("zone_level")));
            lists.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return lists;
    }

    /**
     * Describe:     获取某市下的区，县
     * User:         xiaofan
     * Date:         2016/4/13 15:16
     */
    public List<CityNewBean> queryCountyByCode(String code) {
        List<CityNewBean> lists = new ArrayList<CityNewBean>();
        String[] columns = new String[]{CityNewBean.ZONE_CODE,
                CityNewBean.ZONE_DESC, CityNewBean.ZONE_CODE_PAR,
                CityNewBean.ZONE_LEVEL};
        String selection = "zone_code_par=?";
        String[] selectionArgs = new String[]{code};
        Cursor cursor = database.query(TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityNewBean bean = null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            bean = new CityNewBean();
            bean.setZone_code(cursor.getString(cursor.getColumnIndex("zone_code")));
            bean.setZone_desc(cursor.getString(cursor.getColumnIndex("zone_desc")));
            bean.setZone_code_par(cursor.getString(cursor.getColumnIndex("zone_code_par")));
            bean.setZone_level(cursor.getString(cursor.getColumnIndex("zone_level")));
            lists.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return lists;
    }
}
