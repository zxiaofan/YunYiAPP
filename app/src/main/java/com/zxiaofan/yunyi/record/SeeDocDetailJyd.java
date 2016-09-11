package com.zxiaofan.yunyi.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.JsonUtils;
import util.SerializableMap;

/**
 * Describe:     就诊明细的检验单
 * User:         xiaofan
 * Date:         2016/3/25 15:11
 */
public class SeeDocDetailJyd extends BaseFragment {

    Map<String, Object> object = null;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> listSec = new ArrayList<>();
    SerializableMap map;
    @Bind(R.id.rl)
    RecyclerView rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_see_doc_detail_jyd, container, false);
        map = (SerializableMap) getArguments().getSerializable("model");
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        try {
            list = JsonUtils.getListMap(map.getMap().get("tests").toString());
            setData();
        } catch (Exception e) {

        }
    }

    private void setData() {
        rl.setLayoutManager(new LinearLayoutManager(getActivity()));
        rl.setAdapter(new RecyclerFirstAdapter());
    }

    class RecyclerFirstAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LinearLayout ll;
        LinearLayout.LayoutParams lp, lp1;
        TextView tv_name, tv_value, tv_unit, tv_ckz;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.jyd_first_detail_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                if (TextUtils.isEmpty(list.get(position).get("testSpecies").toString())) {
                    ((MyHolder) holder).tv_jy_type.setText("无");
                } else {
                    ((MyHolder) holder).tv_jy_type.setText(list.get(position).get("testSpecies").toString());
                }

                if (TextUtils.isEmpty(list.get(position).get("testReportNo").toString())) {
                    ((MyHolder) holder).tv_number.setText("无");
                } else {
                    ((MyHolder) holder).tv_number.setText(list.get(position).get("testReportNo").toString());
                }

                try {
                    object = JsonUtils.getMapObj(list.get(position).get("testReport").toString());
                    listSec = JsonUtils.getListMap(object.get("testReportDetails").toString());
                } catch (Exception e) {
                }

                for (int index = 0; index < listSec.size(); index++) {
                    object = listSec.get(index);
                    ll = new LinearLayout(getActivity());
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.setGravity(Gravity.CENTER);
                    ll.setLayoutParams(lp);
                    tv_name = new TextView(getActivity());
                    tv_value = new TextView(getActivity());
                    tv_unit = new TextView(getActivity());
                    tv_ckz = new TextView(getActivity());
                    lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lp1.setMargins(0,10,0,10);
                    tv_name.setGravity(Gravity.CENTER);
                    tv_name.setLayoutParams(lp1);
                    if (TextUtils.isEmpty(object.get("itemName").toString())) {
                        tv_name.setText("无");
                    } else {
                        tv_name.setText(object.get("itemName").toString());
                    }

                    tv_value.setGravity(Gravity.CENTER);
                    tv_value.setLayoutParams(lp1);
                    if (TextUtils.isEmpty(object.get("testValue").toString())) {
                        tv_value.setText("无");
                    } else {
                        tv_value.setText(object.get("testValue").toString());
                    }

                    tv_unit.setGravity(Gravity.CENTER);
                    tv_unit.setLayoutParams(lp1);
                    if (TextUtils.isEmpty(object.get("testMeUnit").toString())) {
                        tv_unit.setText("无");
                    } else {
                        tv_unit.setText(object.get("testMeUnit").toString());
                    }

                    tv_ckz.setGravity(Gravity.CENTER);
                    tv_ckz.setLayoutParams(lp1);
                    if (TextUtils.isEmpty(object.get("referenceLimit").toString())) {
                        tv_ckz.setText("无");
                    } else {
                        tv_ckz.setText(object.get("referenceLimit").toString());
                    }

                    ll.addView(tv_name);
                    ll.addView(tv_value);
                    ll.addView(tv_unit);
                    ll.addView(tv_ckz);
                    ((MyHolder) holder).ll_contain.addView(ll);
                }


            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder {
            TextView tv_jy_type, tv_number;
            RecyclerView rl;
            LinearLayout ll_contain;

            public MyHolder(View itemView) {
                super(itemView);
                tv_jy_type = (TextView) itemView.findViewById(R.id.tv_jy_type);//检验类型
                tv_number = (TextView) itemView.findViewById(R.id.tv_number);//单号
                ll_contain = (LinearLayout) itemView.findViewById(R.id.ll_contain);//列表
            }
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
