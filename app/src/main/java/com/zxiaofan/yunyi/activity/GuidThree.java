package com.zxiaofan.yunyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import base.BaseFragment;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;

public class GuidThree extends BaseFragment {

    @Bind(R.id.welcome_guide_btn)
    TextView welcomeGuideBtn;

    private OptsharepreInterface share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_guid_three, null);
        ButterKnife.bind(this, view);
        share=new OptsharepreInterface(getActivity());
        welcomeGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.putPres("isFirstLogin","1");
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return view;
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
