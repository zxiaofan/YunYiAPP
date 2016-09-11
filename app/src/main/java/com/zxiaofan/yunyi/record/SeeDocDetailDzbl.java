package com.zxiaofan.yunyi.record;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.Map;

import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.JsonUtils;
import util.SerializableMap;

/**
 * Describe:     就诊明细的电子病历
 * User:         xiaofan
 * Date:         2016/3/25 15:07
 */
public class SeeDocDetailDzbl extends BaseFragment {

    @Bind(R.id.tv_zs)
    TextView tvZs;
    @Bind(R.id.tv_xbs)
    TextView tvXbs;
    @Bind(R.id.tv_tgjc)
    TextView tvTgjc;
    @Bind(R.id.tv_fzjc)
    TextView tvFzjc;
    @Bind(R.id.tv_zd)
    TextView tvZd;
    Map<String, Object> object = null;
    SerializableMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_see_doc_detail_dzbl, container, false);
        map = (SerializableMap) getArguments().getSerializable("model");
        ButterKnife.bind(this, view);
        loadData();
        return view;
    }

    private void loadData() {
        try {
            object = JsonUtils.getMapObj(map.getMap().get("electronicMedicalRecord").toString());
            setData();
        } catch (Exception e) {

        }
    }

    private void setData() {
        if (TextUtils.isEmpty(object.get("complaint").toString())) {//主诉
            tvZs.setText("无");
        } else {
            tvZs.setText(object.get("complaint").toString());
        }

        if (TextUtils.isEmpty(object.get("medhis").toString())) {//现病史
            tvXbs.setText("无");
        } else {
            tvXbs.setText(object.get("medhis").toString());
        }

        if (TextUtils.isEmpty(object.get("bodycheck").toString())) {//体格检查
            tvTgjc.setText("无");
        } else {
            tvTgjc.setText(object.get("bodycheck").toString());
        }

        if (TextUtils.isEmpty(object.get("asscheck").toString())) {//辅助检查
            tvFzjc.setText("无");
        } else {
            tvFzjc.setText(object.get("asscheck").toString());
        }

        if (TextUtils.isEmpty(object.get("diag").toString())) {//诊断
            tvZd.setText("无");
        } else {
            tvZd.setText(object.get("diag").toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(Constants.TAG, "destory");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(Constants.TAG, "stop");
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
