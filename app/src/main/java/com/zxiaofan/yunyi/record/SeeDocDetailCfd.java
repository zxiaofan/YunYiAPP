package com.zxiaofan.yunyi.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
 * Describe:     就诊明细的处方单
 * User:         xiaofan
 * Date:         2016/3/25 15:15
 */
public class SeeDocDetailCfd extends BaseFragment {

    @Bind(R.id.rl)
    RecyclerView rl;

    Map<String, Object> object = null;
    List<Map<String, Object>> list = new ArrayList<>();
    SerializableMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_see_doc_detail_cfd, container, false);
        ButterKnife.bind(this, view);
        map = (SerializableMap) getArguments().getSerializable("model");
        init();
        return view;
    }

    private void init() {
        loadData();
    }

    private void loadData() {
        try {
            list=JsonUtils.getListMap(map.getMap().get("prescribes").toString());
            setData();
        } catch (Exception e) {

        }
    }

    private void setData() {
        rl.setLayoutManager(new LinearLayoutManager(getActivity()));
        rl.setAdapter(new RecyclerAdapter());
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Map<String, Object>> listChild = new ArrayList<>();

        LinearLayout ll,ll1;
        LinearLayout.LayoutParams lp, lp1;
        TextView tv_name, gui_ge, tv_count, tv_method;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.cfd_detail_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                //西药、中成药
                if (TextUtils.isEmpty(list.get(position).get("cmWMark").toString())) {
                    ((MyHolder) holder).medicines_name.setText("无");
                } else {
                    ((MyHolder) holder).medicines_name.setText(list.get(position).get("cmWMark").toString());
                }
                //单号
                if (TextUtils.isEmpty(list.get(position).get("prescribeNo").toString())) {
                    ((MyHolder) holder).medicines_name.setText("无");
                } else {
                    ((MyHolder) holder).medicines_name.setText(list.get(position).get("prescribeNo").toString());
                }
                try {
                    listChild=JsonUtils.getListMap(list.get(position).get("prescribeDetails").toString());
                } catch (Exception e) {
                }

                for(int index=0;index<listChild.size();index++){
                    object=listChild.get(index);
                    ll = new LinearLayout(getActivity());
                    ll1 = new LinearLayout(getActivity());
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setLayoutParams(lp);
                    ll1.setLayoutParams(lp);
                    tv_name = new TextView(getActivity());
                    gui_ge = new TextView(getActivity());
                    tv_count = new TextView(getActivity());
                    tv_method = new TextView(getActivity());
                    lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    lp1.setMargins(0,10,0,10);
                    tv_name.setLayoutParams(lp1);
                    gui_ge.setLayoutParams(lp1);
                    tv_count.setLayoutParams(lp1);
                    tv_method.setLayoutParams(lp1);
                    //药品名称
                    if (TextUtils.isEmpty(object.get("detailItemName").toString())) {
                        tv_name.setText("无");
                    } else {
                        tv_name.setText(object.get("detailItemName").toString());
                    }
                    //药品规格
                    if (TextUtils.isEmpty(object.get("drugSpec").toString())) {
                        gui_ge.setText("无");
                    } else {
                        gui_ge.setText(object.get("drugSpec").toString());
                    }
                    gui_ge.setText("0.15g*7");
                    //发放数量
                    if (TextUtils.isEmpty(object.get("amount").toString())) {
                        tv_count.setText("0");
                    } else {
                        if (TextUtils.isEmpty(object.get("unit").toString())) {
                            tv_count.setText("0");
                        } else {
                            tv_count.setText(object.get("amount").toString() + object.get("unit").toString());
                        }
                    }
                    //用法
                    if (TextUtils.isEmpty(object.get("usingDrugFreq").toString())) {
                        tv_method.setText("无");
                    } else {
                        tv_method.setText(object.get("usingDrugFreq").toString());
                    }
                    ll1.addView(tv_method);
                    ll.addView(tv_name);
                    ll.addView(gui_ge);
                    ll.addView(tv_count);
                    ((MyHolder) holder).ll_contain.addView(ll);
                    ((MyHolder) holder).ll_contain.addView(ll1);
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView medicines_name, medicines_num;
            LinearLayout ll_contain;
            public MyHolder(View itemView) {
                super(itemView);
                medicines_name = (TextView) itemView.findViewById(R.id.medicines_name);//中西药
                medicines_num = (TextView) itemView.findViewById(R.id.medicines_num);//单号
                ll_contain = (LinearLayout) itemView.findViewById(R.id.ll_contain);//布局添加容器
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
