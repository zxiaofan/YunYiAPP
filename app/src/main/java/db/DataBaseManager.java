package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.MessageBean;

/**
 * Created by Pc on 2016/1/18.
 */
public class DataBaseManager<T> {
    private DBHelper dbHelper;
    private static DataBaseManager instance = null;
    private SQLiteDatabase sqLiteDatabase;

    /**
     * 构造函数
     */
    private DataBaseManager(Context context) {
        dbHelper = new DBHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();//执行DBHelper
    }

    /**
     * 获取本类对象实例
     */
    public static final DataBaseManager getInstance(Context context) {
        if (instance == null) {
            if (context == null) {
                throw new RuntimeException("context is null");
            }
            instance = new DataBaseManager(context);
        }
        return instance;
    }

    /**
     * 查询表中有多少条数据
     * @param table 表明
     */
    public int getDataCounts(String table) throws Exception {
        Cursor cursor = null;
        int counts = 0;
        if (sqLiteDatabase.isOpen()) {
            cursor = queryData2Cursor("select * from " + table, null);
            if (cursor != null && cursor.moveToFirst()){
                counts = cursor.getCount();
            }
        }else {
            throw new RuntimeException("数据库已关闭");
        }
        return counts;
    }

    /**
     * 查询数据
     * searchSQL 执行查询操作的语句
     * @param selectionArgs     查询条件
     * @return                  犯规查询的游标,可对数据自行操作,需要自己关闭游标
     */
    public Cursor queryData2Cursor(String sql, String[] selectionArgs)throws Exception{
        Cursor cursor = null;
        if (sqLiteDatabase.isOpen()){
            cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        }else {
            throw new RuntimeException("数据库已关闭");
        }
        return cursor;
    }

    /**
     * 批量插入数据
     * @param table 数据表名称
     * @param list  数据
     * @param args  数据的键名key
     * @return
     * @throws Exception
     */
    public long insertBatchData2(String table, List<HashMap<String,Object>> list,String[] args)throws Exception{
        sqLiteDatabase.beginTransaction();
        long returnNum = 0;
        try{
            ContentValues values = new ContentValues();
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < args.length; j++) {
                    values.put(args[j],list.get(i).get(args[j]).toString());
                }
                long num = insertData(table,values);
                returnNum = returnNum +num;
            }
            sqLiteDatabase.setTransactionSuccessful();
        }finally{
            sqLiteDatabase.endTransaction();
        }
        return returnNum;
    }

    /**
     * 插入数据
     * @param table		数据库表名称
     * @param values	数据
     * @return			返回插入对应的id
     * @throws Exception    0 --> 插入失败
     */
    public Long insertData(String table, ContentValues values)throws Exception{
        long result = 0;
        if (sqLiteDatabase.isOpen()){
            result = sqLiteDatabase.insertOrThrow(table,null,values);
        }else {
            throw new RuntimeException("数据库已关闭");
        }
        return result;
    }

    public  void Deletedata(String table,String values)throws Exception{
        if (sqLiteDatabase.isOpen()){
            String sql = "Delete from "+table+" where name = '"+values+"';";
            sqLiteDatabase.execSQL(sql);
        }else {
            throw new RuntimeException("数据库已关闭");
        }
    }
    public void insertData1(String table, MessageBean object)throws  Exception{
        long result = 0;
        if (sqLiteDatabase.isOpen()){
            String sql = "insert into "+table+"(context1,context,messagetype,update1,mesid) values ('"
            +object.getContext()+ "','" + object.getContext()+ "','" + object.getMessagetype()+ "','" + object.getUpdate1()+ "','" + object.getMesid()+ "')";
            sqLiteDatabase.execSQL(sql);
            Log.i("shuju","插入成功");

        }else {
            throw new RuntimeException("数据库已关闭");
        }

    }



    /*public void insertData1(String table, Manhua object)throws  Exception{
        long result = 0;
        if (sqLiteDatabase.isOpen()){
            String sql = "insert or ignore into "+table+"(chapter,name,picurl) values ('"
                    + object.getChapter()+ "','" + object.getName()+ "','" + object.getPicurl()+ "')";
            sqLiteDatabase.execSQL(sql);
            Log.i("shuju","插入成功");
            //ContentValues values1 = new ContentValues();
           // result = sqLiteDatabase.insertOrThrow(table,null,values);
        }else {
            throw new RuntimeException("数据库已关闭");
        }

    }
    */

    /*

    用于数据库查询，运用了反射
    */

    public List<Map<String, Object>> query2ListMap(String sql, String[] selectionArgs, Object object)throws Exception {
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        if(sqLiteDatabase.isOpen()){
            Cursor cursor = null;
            try {
                cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
                Field[] f;
                HashMap<String, Object> map;
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        map = new HashMap<String, Object>();
                        f = object.getClass().getDeclaredFields();
                        for (int i = 0; i < f.length; i++) {
                            map.put(f[i].getName(), cursor.getString(cursor.getColumnIndex(f[i].getName())));
                        }
                        mList.add(map);
                    }
                }
            } finally {
                cursor.close();
            }
        }else{
            throw new RuntimeException("数据库已关闭");
        }
        return mList;
    }





    public List<Map<String,Object>> queryyy(String sql,String[] selectionArg,Object object){
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        if(sqLiteDatabase.isOpen()){
            Cursor c=null;
            try{
                c=sqLiteDatabase.rawQuery(sql,selectionArg);
                Field[] f1;
                HashMap<String, Object> map;
                if(c!=null&&c.getCount()>0){
                    while (c.moveToNext()){
                        map=new HashMap<String, Object>();
                        f1=object.getClass().getDeclaredFields();
                        for (int i=1;i<f1.length;i++){
                            map.put(f1[i].getName(),c.getString(c.getColumnIndex(f1[i].getName())));
                        }
                        mList.add(map);

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                c.close();
            }
        }
        return  mList;
    }
    public Map<String,Object> query(String sql,String[] selectionArg,Object object){
        Map<String, Object> map = new HashMap<>();
        if(sqLiteDatabase.isOpen()){
            Cursor c=null;
            try{
                c=sqLiteDatabase.rawQuery(sql,selectionArg);
                Field[] f1;

                if(c!=null&&c.getCount()>0){
                    while (c.moveToNext()){
                        map=new HashMap<String, Object>();
                        f1=object.getClass().getDeclaredFields();
                        for (int i=0;i<f1.length;i++){
                            map.put(f1[i].getName(),c.getString(c.getColumnIndex(f1[i].getName())));
                        }


                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                c.close();
            }
        }
        return  map;
    }

}
