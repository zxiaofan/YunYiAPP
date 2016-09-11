package db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bean.CityBean;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class XZQHDataHelper {
    private XZQHSqliteHelper dbHelper;
    private SQLiteDatabase db;
    private String DATABASE_NAME = "pcc";
    private int DATABASE_VERSION = 1;

    public XZQHDataHelper(Context context) {
        dbHelper = new XZQHSqliteHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        db = dbHelper.getReadableDatabase();
    }

    /**
     *
     * @Title: addCityData
     * @date 2016-1-22 下午3:07:34
     * @Description: 添加所有省市县
     * @param: @param bean
     * @param: @return
     * @return: boolean
     * @throws
     */
    public void addCityData(List<CityBean> beans) {
        db = dbHelper.getWritableDatabase();
        for(CityBean bean:beans){
            ContentValues values = new ContentValues();
            values.put(CityBean.CITY_ID, bean.getCityId());
            values.put(CityBean.PROVINCE_NAME, bean.getProvinceName());
            values.put(CityBean.CITY_NAME, bean.getCityName());
            values.put(CityBean.COUNTY_NAME, bean.getCountyName());
            values.put(CityBean.COUNTRY_NAME, bean.getCountryName());
            values.put(CityBean.COUNTRY_NAME_IN_ENGLISH,
                    bean.getCountryNameInEnglish());
            db.insert(XZQHSqliteHelper.TB_NAME, null, values);
        }
    }

    /**
     *
     * @Title: queryAllCity
     * @date 2016-1-22 下午3:07:13
     * @Description: 查询所有省市县
     * @param: @return
     * @return: List<CityBean>
     * @throws
     */
    public List<CityBean> queryAllCity() {
        List<CityBean> lists = new ArrayList<CityBean>();
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[] { CityBean.CITY_ID,
                CityBean.PROVINCE_NAME, CityBean.CITY_NAME,
                CityBean.COUNTY_NAME, CityBean.COUNTRY_NAME,
                CityBean.COUNTRY_NAME_IN_ENGLISH };
        String selection = "countryName=? and countryNameInEnglish=?" ;
        String[] selectionArgs = new  String[]{ "中国","CHINA" };
        Cursor cursor = db.query(XZQHSqliteHelper.TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityBean bean=null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            bean = new CityBean();
            bean.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
            bean.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            bean.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            bean.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            bean.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
            bean.setCountryNameInEnglish(cursor.getString(cursor.getColumnIndex("countryNameInEnglish")));
            lists.add(bean);
            cursor.moveToNext();
        }
        cursor.close();
        return lists;
    }

    /**
     *
     * @Title: queryProvince
     * @date 2016-1-22 下午3:07:49
     * @Description: 添加所有省份
     * @param: @return
     * @return: List<CityBean>
     * @throws
     */
    public List<CityBean> queryProvince(){
        List<CityBean> cityList  = new ArrayList<CityBean>();
        List<String> cityListNew  = new ArrayList<String>();
        /**
         * 省分中去掉特别行政区和直辖市
         */
        cityListNew.add("特别行政区");
        cityListNew.add("直辖市");

        /**
         * 省份中去掉自治区
         */
        cityListNew.add("内蒙古");
        cityListNew.add("广西");
        cityListNew.add("西藏");
        cityListNew.add("宁夏");
        cityListNew.add("新疆");
        List<CityBean> provinceList  = new ArrayList<CityBean>();
        cityList = queryAllCity();
        for (CityBean cityBean : cityList) {
            if(!cityListNew.contains(cityBean.getProvinceName())){
                cityListNew.add(cityBean.getProvinceName());
                provinceList.add(cityBean);
            }
        }
        return provinceList;
    }


    /**
     * @Description: 	查询特定省份下的所有市
     * @param: 			@param provinceName
     * @param: 			@return
     * @return: 		List<CityBean>
     */
    public List<CityBean> queryProvinceChildCity(String provinceName){
        List<CityBean> lists = new ArrayList<CityBean>();
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[] { CityBean.CITY_ID,
                CityBean.PROVINCE_NAME, CityBean.CITY_NAME,
                CityBean.COUNTY_NAME, CityBean.COUNTRY_NAME,
                CityBean.COUNTRY_NAME_IN_ENGLISH };
        String selection = "countryName=? and countryNameInEnglish=? and provinceName=?" ;
        String[] selectionArgs = new  String[]{ "中国","CHINA",provinceName };
        Cursor cursor = db.query(XZQHSqliteHelper.TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityBean bean=null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            bean = new CityBean();
            bean.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
            bean.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            bean.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            bean.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            bean.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
            bean.setCountryNameInEnglish(cursor.getString(cursor.getColumnIndex("countryNameInEnglish")));
            lists.add(bean);
            cursor.moveToNext();
        }
        List<CityBean> cityList=new ArrayList<CityBean>();
        List<String> cityListNew  = new ArrayList<String>();
        for (CityBean city : lists) {
            if(!cityListNew.contains(city.getCityName())){
                cityListNew.add(city.getCityName());
                cityList.add(city);
            }
        }
        cursor.close();
        return cityList;
    }

    /**
     * @Description: 	查询特定城市下县
     * @param: 			@param cityName
     * @param: 			@return
     * @return: 		List<CityBean>
     */
    public List<CityBean> queryCityChildCounty(String cityName){
        List<CityBean> lists = new ArrayList<CityBean>();
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[] { CityBean.CITY_ID,
                CityBean.PROVINCE_NAME, CityBean.CITY_NAME,
                CityBean.COUNTY_NAME, CityBean.COUNTRY_NAME,
                CityBean.COUNTRY_NAME_IN_ENGLISH };
        String selection = "countryName=? and countryNameInEnglish=? and cityName=?" ;
        String[] selectionArgs = new  String[]{ "中国","CHINA",cityName };
        Cursor cursor = db.query(XZQHSqliteHelper.TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityBean bean=null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            bean = new CityBean();
            bean.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
            bean.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            bean.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            bean.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            bean.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
            bean.setCountryNameInEnglish(cursor.getString(cursor.getColumnIndex("countryNameInEnglish")));
            lists.add(bean);
            cursor.moveToNext();
        }
        List<CityBean> cityList=new ArrayList<CityBean>();
        List<String> cityListNew  = new ArrayList<String>();
        for (CityBean city : lists) {
            if(!cityListNew.contains(city.getCountyName())){
                cityListNew.add(city.getCountyName());
                cityList.add(city);
            }
        }
        cursor.close();
        return cityList;
    }

    /**
     *
     * @Title: queryAutonomousRegionCity
     * @date 2016-1-22 下午3:12:38
     * @Description: 获取所有自治区
     * @param: @return
     * @return: List<CityBean>
     * @throws
     */
    public  List<CityBean> queryAutonomousRegionCity(){
        List<CityBean> cityList  = new ArrayList<CityBean>();
        List<String> cityListNew  = new ArrayList<String>();
        cityListNew.add("内蒙古");
        cityListNew.add("广西");
        cityListNew.add("西藏");
        cityListNew.add("宁夏");
        cityListNew.add("新疆");
        for (String string : cityListNew) {
            CityBean city = new CityBean();
            city.setCountryName("中国");
            city.setProvinceName(string);
            cityList.add(city );
        }
        return cityList;
    }

    /**
     *
     * @Title: queryMunicipalitiesCity
     * @date 2016-1-22 下午3:16:09
     * @Description: 查询所有直辖市
     * @param: @return
     * @return: List<CityBean>
     * @throws
     */
    public List<CityBean> queryMunicipalitiesCity(){
        List<CityBean> lists = new ArrayList<CityBean>();
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[] { CityBean.CITY_ID,
                CityBean.PROVINCE_NAME, CityBean.CITY_NAME,
                CityBean.COUNTY_NAME, CityBean.COUNTRY_NAME,
                CityBean.COUNTRY_NAME_IN_ENGLISH };
        String selection = "countryName=? and countryNameInEnglish=? and provinceName=?" ;
        String[] selectionArgs = new  String[]{ "中国","CHINA","直辖市" };
        Cursor cursor = db.query(XZQHSqliteHelper.TB_NAME, columns, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        CityBean bean=null;
        while (!cursor.isAfterLast() && (cursor.getString(1) != null )) {
            bean = new CityBean();
            bean.setCityId(cursor.getString(cursor.getColumnIndex("cityId")));
            bean.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
            bean.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            bean.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            bean.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
            bean.setCountryNameInEnglish(cursor.getString(cursor.getColumnIndex("countryNameInEnglish")));
            lists.add(bean);
            cursor.moveToNext();
        }
        List<CityBean> zzsList=new ArrayList<CityBean>();
        List<String> cityListNew  = new ArrayList<String>();
        for (CityBean city : lists) {
            if(!cityListNew.contains(city.getCityName())){
                cityListNew.add(city.getCityName());
                zzsList.add(city);
            }
        }
        cursor.close();
        return zzsList;
    }

    private void Close() {
        dbHelper.close();
        db.close();
    }
}
