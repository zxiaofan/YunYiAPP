package com.zxiaofan.yunyi.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxiaofan.yunyi.R;

import base.BaseFragment;

public class NewsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_news_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        Bundle bundle=getArguments();
        int index=bundle.getInt("index");
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
}
