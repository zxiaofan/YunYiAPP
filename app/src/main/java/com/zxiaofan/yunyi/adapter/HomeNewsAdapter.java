package com.zxiaofan.yunyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.List;
import java.util.Map;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class HomeNewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String,Object>> mLists;
    public HomeNewsAdapter(Context context, List<Map<String,Object>> lists){
        this.mContext=context;
        this.mLists=lists;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder=null;
        if(convertView==null){
            holder=new MyHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.home_news_list_item,null);
            holder.iv_news_img= (ImageView) convertView.findViewById(R.id.iv_news_img);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else{
            holder= (MyHolder) convertView.getTag();
        }
//        holder.tv_title.setText(mLists.get(position).get("title").toString());
//        holder.tv_content.setText(mLists.get(position).get("content").toString());
        return convertView;
    }

    class  MyHolder{
        ImageView iv_news_img;
        TextView tv_title,tv_content;
    }
}
