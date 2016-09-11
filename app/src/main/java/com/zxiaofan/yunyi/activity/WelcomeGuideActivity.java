package com.zxiaofan.yunyi.activity;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zxiaofan.yunyi.R;

import base.BaseActivity;
import base.OptsharepreInterface;

/**
* Describe:     引导页
* User:         xiaofan and xiaofan
* Date:         2016/4/6 9:59
*/
public class WelcomeGuideActivity extends BaseActivity{


	private ViewPager pager;
	private OptsharepreInterface share;

	private List<Fragment> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_guide);
		share=new OptsharepreInterface(this);
		if(share.getPres("isFirstLogin").equals("0")){
			pager= (ViewPager) findViewById(R.id.welcome_guide_viewpager);
			initViewPager();
		}else{
			startActivity(new Intent(this, MainActivity.class));
			finish();
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

	public void initViewPager()
	{
		list=new ArrayList<Fragment>();
		GuidOne one=new GuidOne();
		list.add(one);
		GuidTwo two=new GuidTwo();
		list.add(two);
		GuidThree three=new GuidThree();
		list.add(three);
		pager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));


	}

	class MyViewPagerAdapter extends FragmentPagerAdapter {


		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	}

}
