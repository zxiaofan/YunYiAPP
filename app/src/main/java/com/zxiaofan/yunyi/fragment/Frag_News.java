package com.zxiaofan.yunyi.fragment;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.zxiaofan.yunyi.News.NewsFragment;
import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;
import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class Frag_News extends BaseFragment implements ViewPager.OnPageChangeListener {


    @Bind(R.id.rg)
    RadioGroup rg;
    @Bind(R.id.vp)
    ViewPager vp;

    private List<NewsFragment> listFragment =new ArrayList<NewsFragment>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_frag__news, container,false);
        init();
        return view;
    }

    private void init() {
        ButterKnife.bind(this, view);
        initFragment();
        initViewPager();
        initListener();
    }

    private void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                seleVp(checkedId-1);
                vp.setCurrentItem(checkedId-1);
            }
        });
    }

    private void initViewPager() {
        vp.setAdapter(new MyViewPagerAdapter(getFragmentManager()));
        vp.setOnPageChangeListener(this);
    }

    private void initFragment() {
        for(int i=0;i<4;i++){
            NewsFragment news=new NewsFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("index",i);
            news.setArguments(bundle);
            listFragment.add(news);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        seleVp(position);
    }

    private void seleVp(int position){
        View view=null;
        switch (position){
            case 0:
                view= rg.getChildAt(0);
                break;
            case 1:
                view= rg.getChildAt(2);
                break;
            case 2:
                view=  rg.getChildAt(4);
                break;
            case 3:
                view= rg.getChildAt(6);
                break;
        }
        if(view instanceof RadioButton){
            for(int i=0;i<rg.getChildCount();i++){
                if(rg.getChildAt(i) instanceof RadioButton){
                    if(view.getId()==rg.getChildAt(i).getId()){
                        setScaleAnim(view);
                    }else{
                        rg.getChildAt(i).clearAnimation();
                    }
                }
            }
        }
    }

    private void setScaleAnim(View view){
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f,
                1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.clearAnimation();
        view.setAnimation(animation);
        animation.start();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /**
    * Describe:     viewpager适配器
    * User:         xiaofan
    * Date:         2016/3/28 15:20
    */
    class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                setScaleAnim(rg.getChildAt(0));
            }
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initAction() {

    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
