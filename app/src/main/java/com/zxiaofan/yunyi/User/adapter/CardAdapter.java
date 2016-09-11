package com.zxiaofan.yunyi.User.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.List;
import java.util.Map;

/**
 * Describe:就医卡适配器
 * Created by ${苗}
 * on 2016/4/18.
 */

public class CardAdapter extends BaseAdapter {
    Context context;
    List<Map<String,Object>> list;

    public CardAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
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
        ViewHold hold;
        if (view==null){
            view=View.inflate(context, R.layout.item_mycard,null);
            hold=new ViewHold();
            hold.textView1= (TextView) view.findViewById(R.id.textView61);
            hold.textView2= (TextView) view.findViewById(R.id.textView62);
            view.setTag(hold);
        }else {
           hold= (ViewHold) view.getTag();

        }
        hold.textView1.setText(list.get(i).get("hosOrgName").toString());
        hold.textView2.setText("就医卡号： "+list.get(i).get("cardno").toString());
        return view;
    }
    class ViewHold{
        TextView textView1,textView2;
    }
}
