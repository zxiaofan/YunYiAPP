package com.zxiaofan.yunyi.xzqh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import bean.CityBean;
import db.XZQHDataHelper;
import util.Constants;
import util.TitleBarUtils;
import widget.FitGridView;

/**
* Describe:     此接口废弃
* User:         xiaofan
* Date:         2016/4/14 10:05
*/
public class ProcinceActivity extends BaseActivity {

	private LinearLayout ll_zxs,ll_s,ll_zzq;
	private TextView first_level;
	private FitGridView gv_zxs,gv_s,gv_zzq;
	private XZQHDataHelper dbHelper;
	private List<CityBean> zxsList,sList,zzqList,list;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==0){
				MyAdapter zxsAdapter=new MyAdapter(ProcinceActivity.this, zxsList,0);
				gv_zxs.setAdapter(zxsAdapter);

				MyAdapter sAdapter=new MyAdapter(ProcinceActivity.this, sList,0);
				gv_s.setAdapter(sAdapter);

				MyAdapter zzqAdapter=new MyAdapter(ProcinceActivity.this, zzqList,0);
				gv_zzq.setAdapter(zzqAdapter);
			}else if(msg.what==1){
				int level=(Integer) msg.obj;
				if(level==0){
					first_level.setText("市");
				}else if(level==1){
					first_level.setText("县/市区");
				}
				MyAdapter adapter=new MyAdapter(ProcinceActivity.this, list,level+1);
				gv_zxs.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_province);
		init();
	}


	private void init() {
		initTitle();
		initWidget();
		intiData();
	}

	private void initTitle() {
		TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
		titleBarUtils.setTitle("医疗助手");
		titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void intiData() {
		dbHelper=new XZQHDataHelper(this);
		new Thread(new Runnable() {

			@Override
			public void run() {
				zxsList=dbHelper.queryMunicipalitiesCity();
				sList=dbHelper.queryProvince();
				zzqList=dbHelper.queryAutonomousRegionCity();
				handler.sendEmptyMessage(0);
			}
		}).start();

	}

	private void initWidget() {
		ll_zxs=(LinearLayout) findViewById(R.id.ll_zxs);
		ll_s=(LinearLayout) findViewById(R.id.ll_s);
		ll_zzq=(LinearLayout) findViewById(R.id.ll_zzq);
		first_level=(TextView) findViewById(R.id.first_level);
		gv_zxs=(FitGridView) findViewById(R.id.gv_zxs);
		gv_s=(FitGridView) findViewById(R.id.gv_s);
		gv_zzq=(FitGridView) findViewById(R.id.gv_zzq);
	}

	private class MyAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<CityBean> mCities=new ArrayList<CityBean>();
		private Context mContext;
		private int level;

		public MyAdapter(Context context,List<CityBean> lists,int level){
			this.mContext=context;
			this.mCities=lists;
			inflater=LayoutInflater.from(mContext);
			this.level=level;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCities.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mCities.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			ViewHolder holder=null;
			if(convertView==null){
				holder=new ViewHolder();
				convertView=inflater.inflate(R.layout.channel_item, null);
				holder.tv_province=(TextView) convertView.findViewById(R.id.text_item);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			CityBean bean=mCities.get(position);
			if(level==0){//省级
				if(bean.getProvinceName().equals("直辖市")){
					holder.tv_province.setText(bean.getCityName());
				}else{
					holder.tv_province.setText(bean.getProvinceName());
				}
			}else if(level==1){//市级
				holder.tv_province.setText(bean.getCityName());
			}else if(level==2){//县级
				holder.tv_province.setText(bean.getCountyName());
			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					final CityBean bean=mCities.get(position);
					ll_s.setVisibility(View.GONE);
					ll_zzq.setVisibility(View.GONE);
					if(level==2){
						Intent intent=new Intent();
						intent.putExtra(Constants.XZQH_CODE,bean.getCountyName());
						setResult(RESULT_OK, intent);
						((Activity)mContext).finish();
					}else{

						new Thread(new Runnable() {

							@Override
							public void run() {
								Message msg=new Message();
								if(level==0){//查询省份下市
									if(bean.getProvinceName().equals("直辖市")){
										level=1;
										list=dbHelper.queryCityChildCounty(bean.getCityName());
									}else{
										list=dbHelper.queryProvinceChildCity(bean.getProvinceName());
									}

								}else if(level==1){//查询市下县
									list=dbHelper.queryCityChildCounty(bean.getCityName());
								}
								msg.obj=level;
								msg.what=1;
								handler.sendMessage(msg);
							}
						}).start();
					}

				}
			});
			return convertView;
		}

		class ViewHolder{
			TextView tv_province;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(Constants.TAG,"destory");
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
