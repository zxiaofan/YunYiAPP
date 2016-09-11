package com.zxiaofan.yunyi.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.DateUtil;
import util.JsonUtils;
import util.SerializableMap;

/**
 * Describe:     就诊明细的检查单
 * User:         xiaofan
 * Date:         2016/3/25 15:16
 */
public class SeeDocDetailJcd extends BaseFragment {
    @Bind(R.id.rl)
    RecyclerView rl;

    Map<String, Object> object = null;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    SerializableMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_see_doc_detail_jcd, container, false);
        ButterKnife.bind(this, view);
        map = (SerializableMap) getArguments().getSerializable("model");
        loadData();
        return view;
    }

    private void loadData() {
        String success = "";
        try {
            list = JsonUtils.getListMap(map.getMap().get("checks").toString());
            setData();
        } catch (Exception e) {

        }
    }

    private void setData() {
        rl.setLayoutManager(new LinearLayoutManager(getActivity()));
        rl.setAdapter(new MyRecyclerAdapter());
    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolders holder = new MyHolders(LayoutInflater.from(getActivity()).inflate(R.layout.see_doc_detail_jcd_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolders) {
                //项目名称
                if (TextUtils.isEmpty(list.get(position).get("itemName").toString())) {
                    ((MyHolders) holder).tv_project.setText("无");
                } else {
                    ((MyHolders) holder).tv_project.setText(list.get(position).get("itemName").toString());
                }
                //单号
                if (TextUtils.isEmpty(list.get(position).get("checkNo").toString())) {
                    ((MyHolders) holder).tv_num.setText("无");
                } else {
                    ((MyHolders) holder).tv_num.setText(list.get(position).get("checkNo").toString());
                }
                //日期
                if (TextUtils.isEmpty(list.get(position).get("applydate").toString())) {
                    ((MyHolders) holder).tv_date.setText("无");
                } else {
                    ((MyHolders) holder).tv_date.setText(DateUtil.formatedDateTime("yyyy-MM-dd",Long.parseLong(list.get(position).get("applydate").toString())));
                }
                //检查部位
                if (TextUtils.isEmpty(list.get(position).get("checkPartDn").toString())) {
                    ((MyHolders) holder).tv_body.setText("无");
                } else {
                    ((MyHolders) holder).tv_body.setText(list.get(position).get("checkPartDn").toString());
                }
                //检查。申请原因
                if (TextUtils.isEmpty(list.get(position).get("applyReason").toString())) {
                    ((MyHolders) holder).tv_body.setText("无");
                } else {
                    ((MyHolders) holder).tv_body.setText(list.get(position).get("applyReason").toString());
                }

                try {
                    object = JsonUtils.getMapObj(list.get(position).get("checkReport").toString());
                } catch (Exception e) {
                }
                if(object==null){
                    ((MyHolders) holder).tv_zg.setText("无");
                    ((MyHolders) holder).tv_kg.setText("无");
                }else{
                    //主观描述
                    if (TextUtils.isEmpty(object.get("reportImpression").toString())) {
                        ((MyHolders) holder).tv_zg.setText("无");
                    } else {
                        ((MyHolders) holder).tv_zg.setText(object.get("reportImpression").toString());
                    }
                    //客观描述
                    if (TextUtils.isEmpty(object.get("reportDescription").toString())) {
                        ((MyHolders) holder).tv_kg.setText("无");
                    } else {
                        ((MyHolders) holder).tv_kg.setText(object.get("reportDescription").toString());
                    }
                }

            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class MyHolders extends RecyclerView.ViewHolder{
            TextView tv_project,tv_num,tv_date,tv_apply_reason,tv_body,tv_zg,tv_kg;
            public MyHolders(View itemView) {
                super(itemView);
                tv_project= (TextView) itemView.findViewById(R.id.tv_project);
                tv_num= (TextView) itemView.findViewById(R.id.tv_num);
                tv_date= (TextView) itemView.findViewById(R.id.tv_date);
                tv_body= (TextView) itemView.findViewById(R.id.tv_body);
                tv_zg= (TextView) itemView.findViewById(R.id.tv_zg);
                tv_kg= (TextView) itemView.findViewById(R.id.tv_kg);
                tv_apply_reason= (TextView) itemView.findViewById(R.id.tv_apply_reason);
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
