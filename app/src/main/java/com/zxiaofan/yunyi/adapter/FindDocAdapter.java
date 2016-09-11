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
 * Created by lenovo on 2016/3/21.
 */

public class FindDocAdapter extends BaseAdapter {
    private List<Map<String,Object>> list1=new ArrayList<>();
    List<String> list=new ArrayList<>();
    private Context context;

    public FindDocAdapter(List<Map<String,Object>> list1, Context context) {
        this.list1 = list1;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list1.size();
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
        ViewHolder hold;
        if (view==null){
            hold=new ViewHolder();
            view=View.inflate(context, R.layout.item_doctoryuyue1,null);
            hold.textView1= (TextView) view.findViewById(R.id.textView21);
            hold.textView2= (TextView) view.findViewById(R.id.textView23);
            hold.textView3= (TextView) view.findViewById(R.id.textView24);
            hold.textView4= (TextView) view.findViewById(R.id.textView22);
             view.setTag(hold);
        }else {
            hold= (ViewHolder) view.getTag();
        }
        hold.textView1.setText(list1.get(i).get("name").toString());
        hold.textView2.setText(list1.get(i).get("hosOrgName").toString());
        hold.textView3.setText(list1.get(i).get("occuRange").toString());
        hold.textView4.setText(list1.get(i).get("profeTitleDn").toString());
        return view;
    }
    class ViewHolder{
        TextView textView1,textView2,textView3,textView4;

    }
}
