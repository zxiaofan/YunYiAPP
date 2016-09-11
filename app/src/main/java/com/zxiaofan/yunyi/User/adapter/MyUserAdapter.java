package com.zxiaofan.yunyi.User.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zxiaofan.yunyi.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describe:
 * Created by ${苗}
 * on 2016/3/29.
 */

public class MyUserAdapter extends BaseAdapter {
    List<Map<String, Object>> list=new ArrayList<>();
    Context context;

    public MyUserAdapter(List<Map<String, Object>> list, Context context) {
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
            view=View.inflate(context, R.layout.item_jiuzhenren,null);
            holder=new ViewHolder();
            holder.imageView1= (ImageView) view.findViewById(R.id.imageView13);
            holder.textView1= (TextView) view.findViewById(R.id.textView51);
            holder.textView2= (TextView) view.findViewById(R.id.textView52);
            holder.textView3= (TextView) view.findViewById(R.id.textView53);
            holder.textView4= (TextView) view.findViewById(R.id.textView54);
            holder.textView5= (TextView) view.findViewById(R.id.textView55);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        if (list.get(i).get("gender").toString().equals("男")){
            holder.imageView1.setImageResource(R.drawable.man);
        }else {
            holder.imageView1.setImageResource(R.drawable.girl);
        }
        holder.textView1.setText(list.get(i).get("name").toString());
        holder.textView2.setText(list.get(i).get("gender").toString());
      //  holder.textView3.setText(list.get(i).get("idNo").toString());
        holder.textView4.setText(list.get(i).get("idNo").toString());

        holder.textView5.setText(list.get(i).get("phone").toString());
        return view;

    }
    class ViewHolder{
        ImageView imageView1;
        TextView textView1,textView2,textView3,textView4,textView5;
    }
}
