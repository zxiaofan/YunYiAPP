package com.zxiaofan.yunyi.record.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.DateUtil;
import util.JsonUtils;


/**
* Describe:     门诊处方适配器
* User:         xiaofan
* Date:         2016/4/1 14:34
*/
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> listChild = new ArrayList<>();
    List<Map<String, Object>> listChildSon = new ArrayList<>();

    Map<String, Object> obj = null;
    Map<String, Object> objSon = null;

    LinearLayout ll, ll1;
    LinearLayout.LayoutParams lp, lp1;
    TextView tv_name, gui_ge, tv_count, tv_method;

    public MyExpandableListAdapter(Context context, List<Map<String, Object>> lists) {
        this.context = context;
        this.list = lists;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        try {
            listChild = JsonUtils.getListMap(list.get(i).get("prescribes").toString());
        } catch (Exception e) {
        }
        return listChild.size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);

    }

    @Override
    public Object getChild(int i, int i1) {
        try {
            listChild = JsonUtils.getListMap(list.get(i).get("prescribes").toString());
        } catch (Exception e) {
        }
        return listChild.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;

    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = View.inflate(context, R.layout.item_chufang1, null);
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_hospital = (TextView) view.findViewById(R.id.tv_hospital);
            holder.textView48 = (TextView) view.findViewById(R.id.textView48);
            holder.textView1 = (TextView) view.findViewById(R.id.textView50);
            view.setTag(holder);
            // convertView.setBackgroundResource(R.drawable.group);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        try {
            obj = JsonUtils.getMapObj(list.get(i).get("outpatientRecord").toString());
        } catch (Exception e) {
        }

        holder.tv_date.setText(DateUtil.formatedDateTime("yyyy-MM-dd",Long.parseLong(obj.get("diagDate").toString())));
        holder.tv_title.setText(obj.get("diag").toString());
        holder.tv_hospital.setText(obj.get("hospitalName").toString());
        holder.textView48.setText(obj.get("departmentName").toString());

        if (b) {
            holder.textView1.setText("收起▲");
        } else {
            holder.textView1.setText("展开▼");
        }


        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder = null;
        if (view == null) {
            holder = new ChildHolder();
            view = View.inflate(context, R.layout.cfd_detail_item, null);
            holder.medicines_name = (TextView) view.findViewById(R.id.medicines_name);
            holder.medicines_num = (TextView) view.findViewById(R.id.medicines_num);
            holder.ll_contain = (LinearLayout) view.findViewById(R.id.ll_contain);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }

        try {
            listChild = JsonUtils.getListMap(list.get(i).get("prescribes").toString());
            objSon = listChild.get(i1);
            listChildSon.clear();
            listChildSon = JsonUtils.getListMap(objSon.get("prescribeDetails").toString());
        } catch (Exception e) {
        }
        //西药、中成药
        if (TextUtils.isEmpty(listChild.get(i1).get("cmWMark").toString())) {
            holder.medicines_name.setText("无");
        } else {
            holder.medicines_name.setText(listChild.get(i1).get("cmWMark").toString() );
        }
        //单号
        if (TextUtils.isEmpty(listChild.get(i1).get("prescribeNo").toString())) {
            holder.medicines_num.setText("无");
        } else {
            holder.medicines_num.setText(listChild.get(i1).get("prescribeNo").toString());
        }
        holder.ll_contain.removeAllViewsInLayout();//展开之前移除之前添加的素有view,viewgroup;

        for (int index = 0; index < listChildSon.size(); index++) {
            objSon = listChildSon.get(index);
            ll = new LinearLayout(context);
            ll1 = new LinearLayout(context);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(lp);
            ll1.setLayoutParams(lp);
            tv_name = new TextView(context);
            gui_ge = new TextView(context);
            tv_count = new TextView(context);
            tv_method = new TextView(context);
            lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lp1.setMargins(10, 10, 0, 10);
            tv_name.setLayoutParams(lp1);
            gui_ge.setLayoutParams(lp1);
            tv_count.setLayoutParams(lp1);
            tv_method.setLayoutParams(lp1);
            //药品名称
            if (TextUtils.isEmpty(objSon.get("detailItemName").toString())) {
                tv_name.setText("无");
            } else {
                tv_name.setText(objSon.get("detailItemName").toString());
            }
            //药品规格
            if (TextUtils.isEmpty(objSon.get("drugSpec").toString())) {
                gui_ge.setText("无");
            } else {
                gui_ge.setText(objSon.get("drugSpec").toString());
            }

            //发放数量
            if (TextUtils.isEmpty(objSon.get("amount").toString())) {
                tv_count.setText("0");
            } else {
                if (TextUtils.isEmpty(objSon.get("unit").toString())) {
                    tv_count.setText("0");
                } else {
                    tv_count.setText(objSon.get("amount").toString() + objSon.get("unit").toString());
                }
            }

            //用法
            if (TextUtils.isEmpty(objSon.get("usingDrugFreq").toString())) {
                tv_method.setText("无");
            } else {
                tv_method.setText(objSon.get("usingDrugFreq").toString());
            }
            ll1.addView(tv_method);
            ll.addView(tv_name);
            ll.addView(gui_ge);
            ll.addView(tv_count);

            holder.ll_contain.addView(ll);
            holder.ll_contain.addView(ll1);
        }
        return view;
    }

    private class ChildHolder {
        TextView medicines_name, medicines_num;
        LinearLayout ll_contain;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;

    }

    class ViewHolder {
        TextView tv_title, tv_date, tv_hospital, textView48, textView1;
    }


}
