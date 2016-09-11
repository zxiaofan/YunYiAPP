package com.zxiaofan.yunyi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

/**
 * @Description: recyclerview实现上下拉刷新的改装适配器
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class RecyclerBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_more;
        public ProgressBar pb;
        public FooterViewHolder(View view) {
            super(view);
            tv_more= (TextView) view.findViewById(R.id.pull_to_refresh_loadmore_text);
            pb= (ProgressBar) view.findViewById(R.id.pull_to_refresh_load_progress);
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
