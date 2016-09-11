package com.zxiaofan.yunyi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/22.
 */

public class FindHosAdapter1 extends BaseAdapter{
    private List<Map<String,Object>> list1=new ArrayList<>();

    private Context context;

    public FindHosAdapter1(List<Map<String, Object>> list1, Context context) {
        this.list1 = list1;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list1.size()>=2){
            return 2;
        }else {
            return list1.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return list1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            view=View.inflate(context, R.layout.item_findhos,null);
            holder=new ViewHolder();
            holder.textView1= (TextView) view.findViewById(R.id.textView21);
            holder.textView2= (TextView) view.findViewById(R.id.textView22);
            holder.textView3= (TextView) view.findViewById(R.id.textView25);
            holder.textView4= (TextView) view.findViewById(R.id.textView26);
            view.setTag(holder);
            }else {
            holder= (ViewHolder) view.getTag();
        }
        if (list1.get(i).get("hosLevel").toString().equals("3")){
            holder.textView3.setText("三级");
        }else if (list1.get(i).get("hosLevel").toString().equals("2")) {
            holder.textView3.setText("二级");
        }else if (list1.get(i).get("hosLevel").toString().equals("1")) {
            holder.textView3.setText("一级");
        }else if (list1.get(i).get("hosLevel").toString().equals("")){
            holder.textView3.setText("未知医院等级");
        }
        holder.textView4.setText(list1.get(i).get("hosType").toString());
        holder.textView1.setText(list1.get(i).get("hosOrgName").toString());


        return view;
    }
    class ViewHolder{
        TextView textView1,textView2,textView3,textView4;


    }
}
