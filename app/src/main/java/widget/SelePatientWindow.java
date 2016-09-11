package widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

import java.util.List;
import java.util.Map;

import myinterface.IMyItemClick;
import util.ConvertDpAndPx;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class SelePatientWindow extends PopupWindow {
    private GridView lv;
    private View mMenuView;
    private List<Map<String,Object>> mLists;

    public IMyItemClick getiMyItemClick() {
        return iMyItemClick;
    }

    public void setiMyItemClick(IMyItemClick iMyItemClick) {
        this.iMyItemClick = iMyItemClick;
    }

    private IMyItemClick iMyItemClick;

    public SelePatientWindow(Context context,  List<Map<String,Object>> lists) {
        super(context);
        this.mLists=lists;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.sele_patient_window, null);
        lv = (GridView) mMenuView.findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter(context));
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        this.setBackgroundDrawable(null);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ConvertDpAndPx.Dp2Px(context,240));
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupWindowAnimationFade);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.lv).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }



    class MyAdapter extends BaseAdapter{
        private Context mContext;
        public MyAdapter(Context context){
            this.mContext=context;
        }

        @Override
        public int getCount() {
            return mLists.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null){
                holder=new ViewHolder();
                convertView=LayoutInflater.from(mContext).inflate(R.layout.sele_patient_window_item,null);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            holder.tv_name.setText(mLists.get(position).get("name").toString());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyItemClick.myItemOnClick(position);
                }
            });
            return convertView;
        }

        class ViewHolder{
            TextView tv_name;
        }
    }
}
