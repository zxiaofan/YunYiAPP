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
 * Describe:
 * Created by ${苗}
 * on 2016/3/25.
 */

public class MyExpandableListAdapter1 extends BaseExpandableListAdapter {
    Context context;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> listChild = new ArrayList<>();
    List<Map<String, Object>> listChildSon = new ArrayList<>();
    public String[] groups = {"我的好友", "新疆同学", "亲戚", "同事"};
    public String[][] children = {
            {"胡算林", "张俊峰", "王志军", "二人"},
            {"李秀婷", "蔡乔", "别高", "余音"},
            {"摊派新", "张爱明"},
            {"马超", "司道光"}
    };
    Map<String, Object> obj = null;
    Map<String, Object> objSon = null;


    LinearLayout ll;
    LinearLayout.LayoutParams lp, lp1;
    TextView tv_name, tv_amount, tv_count;
    float reimbursementCost, otherCost, totalCost;

    public MyExpandableListAdapter1(Context context, List<Map<String, Object>> lists) {
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
            listChild = JsonUtils.getListMap(list.get(i).get("fees").toString());
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
            listChild = JsonUtils.getListMap(list.get(i).get("fees").toString());
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
            view = View.inflate(context, R.layout.fee_detail_child_item, null);
            holder.tv_mine_pay = (TextView) view.findViewById(R.id.tv_mine_pay);
            holder.tv_total_count = (TextView) view.findViewById(R.id.tv_total_count);
            holder.ll_ypxm = (LinearLayout) view.findViewById(R.id.ll_ypxm);
            holder.ll_ylxm = (LinearLayout) view.findViewById(R.id.ll_ylxm);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        try {
            listChild = JsonUtils.getListMap(list.get(i).get("fees").toString());
            objSon = listChild.get(i1);
            listChildSon.clear();
            listChildSon = JsonUtils.getListMap(objSon.get("feeDetails").toString());
        } catch (Exception e) {
        }
        holder.ll_ypxm.removeAllViewsInLayout();
        holder.ll_ylxm.removeAllViewsInLayout();
        //总金额
        if (TextUtils.isEmpty(objSon.get("clinCost").toString())) {
            holder.tv_total_count.setText("无");
            totalCost = 0;
        } else {
            holder.tv_total_count.setText(objSon.get("clinCost").toString());
        }
        //个人支付
        totalCost = Float.parseFloat(objSon.get("clinCost").toString()) ;
        if (TextUtils.isEmpty(objSon.get("reimbursementCost").toString())) {
            reimbursementCost = 0;
        } else {
            reimbursementCost = Float.parseFloat(objSon.get("reimbursementCost").toString()) ;
        }
        if (TextUtils.isEmpty(objSon.get("otherCost").toString())) {
            otherCost = 0;
        } else {
            otherCost =  Float.parseFloat(objSon.get("otherCost").toString()) ;
        }
        holder.tv_mine_pay.setText("￥"+String.valueOf(totalCost-reimbursementCost-otherCost)+"元");

        for(int index=0;index<listChildSon.size();index++){
            objSon=listChildSon.get(index);

                ll = new LinearLayout(context);
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(lp);
                tv_name = new TextView(context);//项目名称
                tv_amount = new TextView(context);//金额
                tv_count = new TextView(context);//药品数量
                lp1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                lp1.setMargins(10,10,0,10);
                tv_name.setLayoutParams(lp1);
                tv_amount.setLayoutParams(lp1);
                tv_count.setLayoutParams(lp1);
                //项目名称
                if (TextUtils.isEmpty(objSon.get("detailItemName").toString())) {
                    tv_name.setText("无");
                } else {
                    tv_name.setText(objSon.get("detailItemName").toString()+":");
                }
                //金额
                if (TextUtils.isEmpty(objSon.get("detailItemCost").toString())) {
                    tv_amount.setText("无");
                } else {
                    tv_amount.setText("￥"+objSon.get("detailItemCost").toString()+"元");
                }
                //数量
                if (TextUtils.isEmpty(objSon.get("detailItemAmount").toString())) {
                    tv_count.setText("无");
                } else {
                    if (TextUtils.isEmpty(objSon.get("detailItemUnit").toString())) {
                        tv_count.setText("数量："+objSon.get("detailItemAmount").toString());
                    } else {
                        tv_count.setText("数量："+objSon.get("detailItemAmount").toString()+objSon.get("detailItemUnit").toString());
                    }
                }

                ll.addView(tv_name);
                ll.addView(tv_amount);
                ll.addView(tv_count);
            if(objSon.get("drugMark").toString().equals("1")){//药品项目
                holder.ll_ypxm.addView(ll);
            }else{//医疗项目
                holder.ll_ylxm.addView(ll);
            }
        }
        return view;
    }

    class ChildHolder {
        TextView tv_total_count, tv_mine_pay;
        LinearLayout ll_ypxm, ll_ylxm;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;

    }

    class ViewHolder {
        TextView tv_title, tv_date, tv_hospital, textView48, textView1;
    }
}
