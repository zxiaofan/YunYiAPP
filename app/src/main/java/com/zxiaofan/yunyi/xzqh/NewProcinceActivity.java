package com.zxiaofan.yunyi.xzqh;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.activity.FindHospitalActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import base.BaseActivity;
import bean.CityNewBean;
import util.CharacterParser;
import util.Constants;
import util.PinyinComparator;
import util.TitleBarUtils;

public class NewProcinceActivity extends BaseActivity {
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private RecyclerView xzqh_rl;
    List<CityNewBean> lists = new ArrayList<CityNewBean>();
    private XZQHDBHelper dbHelper;
    private MyAdapter adapter;
    private View headerView;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                xzqh_rl.setLayoutManager(new LinearLayoutManager(NewProcinceActivity.this));
                adapter = new MyAdapter();
                headerView = LayoutInflater.from(NewProcinceActivity.this).inflate(R.layout.new_procince_list_header, null);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                headerView.setLayoutParams(lp);
                adapter.setmHeaderView(headerView);
                xzqh_rl.setAdapter(adapter);
            } else if (msg.what == 1) {
            }
        }

        ;
    };

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_procince);
        dbHelper = new XZQHDBHelper(this);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        init();
    }

    private void init() {
        initLoc();
        initTitle();
        initWidget();
        initData();
    }

    private void initLoc() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在成都金牛区附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void initData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                lists = dbHelper.queryAllProcince();
                listSort();
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initWidget() {
        xzqh_rl = (RecyclerView) findViewById(R.id.xzqh_rl);
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("选择地区");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void listSort() {
        lists = filledData(lists);
        // 根据a-z进行排序源数据
        Collections.sort(lists, pinyinComparator);
    }

    /**
     * @Description: 为ListView填充数据
     * @param: @param date
     * @param: @return
     */
    private List<CityNewBean> filledData(List<CityNewBean> data) {
        List<CityNewBean> mSortList = new ArrayList<CityNewBean>();

        for (int i = 0; i < data.size(); i++) {
            CityNewBean sortModel = new CityNewBean();
            sortModel.setZone_code(data.get(i).getZone_code());
            sortModel.setZone_desc(data.get(i).getZone_desc());
            sortModel.setZone_code_par(data.get(i).getZone_code_par());
            sortModel.setZone_level(data.get(i).getZone_level());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(data.get(i).getZone_desc());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        String level;

        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;

        public View getmHeaderView() {
            return mHeaderView;
        }

        public void setmHeaderView(View mHeaderView) {
            this.mHeaderView = mHeaderView;
        }

        private View mHeaderView;

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return TYPE_HEADER;
            return TYPE_NORMAL;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = null;
            if (viewType == TYPE_NORMAL) {
                holder = new MyHolder(LayoutInflater.from(NewProcinceActivity.this).inflate(R.layout.activity_group_member_item, parent, false));
            } else {
                holder = new MyHolder(mHeaderView);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                if (position == 0) {

                } else {
                    position=position-1;
                    // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                    if (position == getPositionForSection(position)) {
                        ((MyHolder) holder).catalog.setVisibility(View.VISIBLE);
                        ((MyHolder) holder).catalog.setText(lists.get(position).getSortLetters());
                    } else {
                        ((MyHolder) holder).catalog.setVisibility(View.GONE);
                    }

                    ((MyHolder) holder).title.setText(lists.get(position).getZone_desc());
                }

            }
        }

        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        public int getPositionForSection(int position) {
            for (int i = 0; i < getItemCount() + 1; i++) {
                String sortStr = lists.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == lists.get(position).getSortLetters().charAt(0)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int getItemCount() {
            return lists.size() + 1;
        }

        class MyHolder extends RecyclerView.ViewHolder {

            TextView catalog, title;

            public MyHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView) {

                } else {
                    catalog = (TextView) itemView.findViewById(R.id.catalog);
                    title = (TextView) itemView.findViewById(R.id.title);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            level = lists.get(getPosition()-1).getZone_level();
                            if (level.equals("1")) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e(Constants.TAG, lists.get(getPosition()-1).getZone_code());
                                        lists = dbHelper.queryCityByCode(lists.get(getPosition()-1).getZone_code());
                                        listSort();
                                        handler.sendEmptyMessage(0);
                                    }
                                }).start();
                            } else if (level.equals("2")) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lists = dbHelper.queryCountyByCode(lists.get(getPosition()-1).getZone_code());
                                        listSort();
                                        handler.sendEmptyMessage(0);
                                    }
                                }).start();
                            } else {
                                Intent intent = new Intent(NewProcinceActivity.this, FindHospitalActivity.class);
                                intent.putExtra("cityNewBean", lists.get(getPosition()-1));
                                Log.e(Constants.TAG, lists.get(getPosition()-1).getZone_desc());
                                startActivity(intent);
                            }
                        }
                    });
                }

            }
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            sb.append("\ncity : ");
            sb.append(location.getCity());
            sb.append("\ncityCode : ");
            sb.append(location.getCityCode());
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("BaiduLocationApiDem", sb.toString());
        }

    }


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initAction() {

    }
}
