package com.zxiaofan.yunyi.record.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
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
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class ReportQueryJybgAdapter extends BaseExpandableListAdapter{
    Context context;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> listChild = new ArrayList<>();
    List<Map<String, Object>> listChildSon = new ArrayList<>();

    LinearLayout ll;
    LinearLayout.LayoutParams lp, lp1;
    TextView tv_name, tv_value, tv_unit, tv_ckz;

    Map<String, Object> obj = null;
    Map<String, Object> objSon = null;
    TextView tvAdd = null;

    public ReportQueryJybgAdapter(Context context, List<Map<String, Object>> lists) {
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
            listChild = JsonUtils.getListMap(list.get(i).get("tests").toString());
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
            listChild = JsonUtils.getListMap(list.get(i).get("tests").toString());
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
        ChildHolder holder = new ChildHolder();
        if (view == null) {
            view = View.inflate(context, R.layout.jyd_first_detail_item, null);
            holder.tv_jy_type = (TextView) view.findViewById(R.id.tv_jy_type);//检验类型
            holder.tv_number = (TextView) view.findViewById(R.id.tv_number);//单号
            holder.ll_contain = (LinearLayout) view.findViewById(R.id.ll_contain);//列表
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        try {
            listChild = JsonUtils.getListMap(list.get(i).get("tests").toString());
            obj =  JsonUtils.getMapObj(listChild.get(i1).get("testReport").toString());
            listChildSon = JsonUtils.getListMap(obj.get("testReportDetails").toString());
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(listChild.get(i1).get("testSpecies").toString())) {
            holder.tv_jy_type.setText("无");
        } else {
            holder.tv_jy_type.setText(listChild.get(i1).get("testSpecies").toString());
        }

        if (TextUtils.isEmpty(listChild.get(i1).get("testReportNo").toString())) {
            holder.tv_number.setText("无");
        } else {
            holder.tv_number.setText(listChild.get(i1).get("testReportNo").toString());
        }
        holder.ll_contain.removeAllViewsInLayout();

        if(listChildSon.size()>0){
            for (int index = 0; index < listChildSon.size(); index++) {
                objSon = listChildSon.get(index);
                ll = new LinearLayout(context);
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.setGravity(Gravity.CENTER);
                ll.setLayoutParams(lp);
                tv_name = new TextView(context);
                tv_value = new TextView(context);
                tv_unit = new TextView(context);
                tv_ckz = new TextView(context);
                lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                lp1.setMargins(0,10,0,10);
                tv_name.setGravity(Gravity.CENTER);
                tv_name.setLayoutParams(lp1);
                if (TextUtils.isEmpty(objSon.get("itemName").toString())) {
                    tv_name.setText("无");
                } else {
                    tv_name.setText(objSon.get("itemName").toString());
                }

                tv_value.setGravity(Gravity.CENTER);
                tv_value.setLayoutParams(lp1);
                if (TextUtils.isEmpty(objSon.get("testValue").toString())) {
                    tv_value.setText("无");
                } else {
                    tv_value.setText(objSon.get("testValue").toString());
                }

                tv_unit.setGravity(Gravity.CENTER);
                tv_unit.setLayoutParams(lp1);
                if (TextUtils.isEmpty(objSon.get("testMeUnit").toString())) {
                    tv_unit.setText("无");
                } else {
                    tv_unit.setText(objSon.get("testMeUnit").toString());
                }

                tv_ckz.setGravity(Gravity.CENTER);
                tv_ckz.setLayoutParams(lp1);
                if (TextUtils.isEmpty(objSon.get("referenceLimit").toString())) {
                    tv_ckz.setText("无");
                } else {
                    tv_ckz.setText(objSon.get("referenceLimit").toString());
                }

                ll.addView(tv_name);
                ll.addView(tv_value);
                ll.addView(tv_unit);
                ll.addView(tv_ckz);
                holder.ll_contain.addView(ll);
            }
        }else{
            ll = new LinearLayout(context);
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setGravity(Gravity.CENTER);
            ll.setLayoutParams(lp);
            tv_name = new TextView(context);
            lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv_name.setLayoutParams(lp1);
            tv_name.setText("无数据");
            ll.addView(tv_name);
            holder.ll_contain.addView(ll);
        }

        return view;
    }

    class ChildHolder {
        TextView tv_jy_type, tv_number;
        RecyclerView rl;
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
