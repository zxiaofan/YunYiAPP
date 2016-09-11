package com.zxiaofan.yunyi.hospital.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created by ${苗}
 * on 2016/4/5.
 */

public class PupAdapter extends BaseAdapter {
    Context context;
    List<String> l=new ArrayList<>();

    public PupAdapter(Context context, List<String> l) {
        this.context = context;
        this.l = l;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Object getItem(int i) {
        return l.get(i);
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
            view = View.inflate(context, R.layout.item_spinner, null);
            hold.textView1= (TextView) view.findViewById(R.id.text);
            view.setTag(hold);
        }else {
            hold= (ViewHold) view.getTag();

        }

        hold.textView1.setText(l.get(i).toString());
        return view;
    }
    class ViewHold{
        TextView textView1;
    }
}
