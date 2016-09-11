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
 * Describe:
 * Created by ${苗}
 * on 2016/4/19.
 */

public class DocCardAdapter extends BaseAdapter {
    List<Map<String,Object>> list1=new ArrayList<>();
    Context context;

    public DocCardAdapter(List<Map<String, Object>> list, Context context) {
        this.list1 = list;
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
        ViewHold hold;
        if (view==null){
            view=View.inflate(context, R.layout.doc_card_lists_item,null);
            hold=new ViewHold();
            hold.text1= (TextView) view.findViewById(R.id.tv_date);
            hold.text2= (TextView) view.findViewById(R.id.tv_time);
            hold.text3= (TextView) view.findViewById(R.id.tv_price);
            hold.text4= (TextView) view.findViewById(R.id.tv_note);
            view.setTag(hold);
        }
        else {
            hold= (ViewHold) view.getTag();
        }
            hold= (ViewHold) view.getTag();
            String time=list1.get(i).get("optime").toString();
            long l = Long.parseLong(time);
            Date date=new Date(l);

            String oper=list1.get(i).get("oper").toString();
            String cardval=list1.get(i).get("cardval").toString();
            hold.text1.setText(riqi1(date));
            //hold.text2.setText(time);
            hold.text2.setText(riqi(date));
            hold.text3.setText(oper);
            hold.text4.setText(cardval);

        return view;
    }
    class ViewHold{
        TextView text1,text2,text3,text4;
    }
    private String riqi(Date date) {
        SimpleDateFormat format =  new SimpleDateFormat("HH:mm");
        String riqi = format.format(date);

        return riqi;
    }
    private String riqi1(Date date) {
        SimpleDateFormat format =  new SimpleDateFormat("MM/dd");
        String riqi = format.format(date);

        return riqi;
    }
}