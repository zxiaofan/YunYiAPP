package com.zxiaofan.yunyi.record.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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
public class ReportQueryJcbgAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Map<String, Object>> list = new ArrayList<>();
    List<Map<String, Object>> listChild = new ArrayList<>();
    List<Map<String, Object>> listChildSon = new ArrayList<>();

    Map<String, Object> obj = null;
    Map<String, Object> objSon = null;
    TextView tvAdd = null;

    public ReportQueryJcbgAdapter(Context context, List<Map<String, Object>> lists) {
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
            listChild = JsonUtils.getListMap(list.get(i).get("checks").toString());
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
            listChild = JsonUtils.getListMap(list.get(i).get("checks").toString());
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
            view = View.inflate(context, R.layout.see_doc_detail_jcd_item, null);
            holder.tv_project = (TextView) view.findViewById(R.id.tv_project);
            holder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            holder.tv_body = (TextView) view.findViewById(R.id.tv_body);
            holder.tv_zg = (TextView) view.findViewById(R.id.tv_zg);
            holder.tv_kg = (TextView) view.findViewById(R.id.tv_kg);
            holder.tv_apply_reason = (TextView) view.findViewById(R.id.tv_apply_reason);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        try {
            listChild = JsonUtils.getListMap(list.get(i).get("checks").toString());
            obj = listChild.get(i1);
            objSon = JsonUtils.getMapObj(obj.get("checkReport").toString());
        } catch (Exception e) {
        }
        //项目名称
        if (TextUtils.isEmpty(obj.get("itemName").toString())) {
            holder.tv_project.setText("无");
        } else {
            holder.tv_project.setText(obj.get("itemName").toString());
        }
        //单号
        if (TextUtils.isEmpty(obj.get("checkNo").toString())) {
            holder.tv_num.setText("无");
        } else {
            holder.tv_num.setText(obj.get("checkNo").toString());
        }
        //日期
        if (TextUtils.isEmpty(obj.get("applydate").toString())) {
            holder.tv_date.setText("无");
        } else {
            holder.tv_date.setText(DateUtil.formatedDateTime("yyyy-MM-dd",Long.parseLong(obj.get("applydate").toString())));
        }
        //检查部位
        if (TextUtils.isEmpty(obj.get("checkPartDn").toString())) {
            holder.tv_body.setText("无");
        } else {
            holder.tv_body.setText(obj.get("checkPartDn").toString());
        }
        //检查。申请原因
        if (TextUtils.isEmpty(obj.get("applyReason").toString())) {
            holder.tv_body.setText("无");
        } else {
            holder.tv_body.setText(obj.get("applyReason").toString());
        }
        //主观描述
        if (TextUtils.isEmpty(objSon.get("reportImpression").toString())) {
            holder.tv_zg.setText("无");
        } else {
            holder.tv_zg.setText(objSon.get("reportImpression").toString());
        }
        //客观描述
        if (TextUtils.isEmpty(objSon.get("reportDescription").toString())) {
            holder.tv_kg.setText("无");
        } else {
            holder.tv_kg.setText(objSon.get("reportDescription").toString());
        }

        return view;
    }

    class ChildHolder {
        TextView tv_project, tv_num, tv_date, tv_apply_reason, tv_body, tv_zg, tv_kg;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;

    }

    class ViewHolder {
        TextView tv_title, tv_date, tv_hospital, textView48, textView1;
    }
}
