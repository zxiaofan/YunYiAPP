package com.zxiaofan.yunyi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.List;
import java.util.Map;

/**
 * Describe:
 * Created by ${苗}
 * on 2016/4/14.
 */

public class DrugPriceAdapter extends BaseAdapter {
    List<Map<String,Object>> list;
    Context context;

    public DrugPriceAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewhold hold;
        if (view==null){
            view=View.inflate(context, R.layout.item_drug_price,null);
            hold=new Viewhold();
            hold.textView1= (TextView) view.findViewById(R.id.textView34);
            hold.textView2= (TextView) view.findViewById(R.id.textView27);
            hold.textView3= (TextView) view.findViewById(R.id.textView32);
            hold.textView4= (TextView) view.findViewById(R.id.textView33);
            hold.textView5= (TextView) view.findViewById(R.id.textView35);
            view.setTag(hold);
        }else {
            hold= (Viewhold) view.getTag();
        }
        hold.textView1.setText(list.get(i).get("drugName").toString());
        hold.textView2.setText(list.get(i).get("drugType").toString());
        hold.textView3.setText(list.get(i).get("unit").toString());
        hold.textView4.setText(list.get(i).get("price").toString());
        hold.textView5.setText("通用名："+list.get(i).get("cmnname").toString());

        return view;
    }
    class Viewhold{
        TextView textView1,textView2,textView3,textView4,textView5;
    }
}
