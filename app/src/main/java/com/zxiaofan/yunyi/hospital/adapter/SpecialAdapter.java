package com.zxiaofan.yunyi.hospital.adapter;

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
 * Describe:
 * Created by ${苗}
 * on 2016/4/6.
 */

public class SpecialAdapter extends BaseAdapter {
    Context context;
    List<Map<String,Object>> list=new ArrayList<>();

    public SpecialAdapter(Context context, List<Map<String, Object>> list) {
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
        if (view==null) {
            hold=new ViewHold();
            view = View.inflate(context, R.layout.item_tesekeshi, null);
            hold.textView1= (TextView) view.findViewById(R.id.textView56);
            hold.textView2= (TextView) view.findViewById(R.id.textView57);
            view.setTag(hold);
        }else {
            hold= (ViewHold) view.getTag();

        }
        
        hold.textView1.setText(list.get(i).get("deptName").toString());
                String isSpec=list.get(i).get("isSpec").toString();

        return view;
    }
    class ViewHold{
        TextView textView1,textView2;
    }
}
