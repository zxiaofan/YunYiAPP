package com.zxiaofan.yunyi.User.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/3/25.
 */

public class MessageAdapter extends BaseAdapter {
    List<Map<String, Object>> list=new ArrayList<>();
    Context context;

    public MessageAdapter(List<Map<String, Object>> list, Context context) {
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
        ViewHolder holder;
        if (view==null){
            view=View.inflate(context, R.layout.item_message,null);
            holder=new ViewHolder();

            holder.textView1= (TextView) view.findViewById(R.id.textView43);
            holder.textView2= (TextView) view.findViewById(R.id.textView44);
            holder.textView3= (TextView) view.findViewById(R.id.textView45);

            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        getStrToDate(list.get(i).get("update1").toString());
       // long l=Long.parseLong(list.get(i).get("update1").toString());
      holder.textView1.setText(list.get(i).get("context1").toString());
      holder.textView2.setText(getStrToDate(list.get(i).get("update1").toString()));
        holder.textView3.setText(list.get(i).get("messagetype").toString());


        return view;
    }
    public static String getStrToDate(String lo){
        long time = Long.parseLong(lo);
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年-MM月-dd日    HH:mm");
        return sd.format(date);
    }
    class ViewHolder{

        TextView textView1,textView2,textView3;
    }

}
