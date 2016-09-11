package view;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.Search.TFSearchActivity;
import com.zxiaofan.yunyi.activity.DrugPriceActivity;
import com.zxiaofan.yunyi.activity.JBZZActivity;



/**
 * Created by yetwish on 2015-05-11
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    /**
     * 输入框
     */
    private EditText etInput;

    /**
     * 删除键
     */
   // private ImageView ivDelete;
    private ImageView search;

    /**
     * 返回按钮
     */


    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 弹出列表
     */
    private ListView lvTips;

    /**
     * 提示adapter （推荐adapter）
     */
    private ArrayAdapter<String> mHintAdapter;

    /**
     * 自动补全adapter 只显示名字
     */
    private ArrayAdapter<String> mAutoCompleteAdapter;

    /**
     * 搜索回调接口
     */
    private SearchViewListener mListener;

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    private void initViews() {
        etInput = (EditText) findViewById(R.id.search_et_input);
        search = (ImageView) findViewById(R.id.search_in);




//        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //set edit text
//                String text = lvTips.getAdapter().getItem(i).toString();
//                etInput.setText(text);
//                etInput.setSelection(text.length());
//                //hint list view gone and result list view show
//                lvTips.setVisibility(View.GONE);
//                notifyStartSearching(text);
//            }
//        });

        search.setOnClickListener(this);
    }


//        etInput.addTextChangedListener(new EditChangedListener());
//       // etInput.setOnClickListener(this);
//        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    lvTips.setVisibility(GONE);
//                    notifyStartSearching(etInput.getText().toString());
//                }
//                return true;
//            }
//        });
//    }

    /**
     * 通知监听者 进行搜索操作
     * @param text
     */
//    private void notifyStartSearching(String text){
//        if (mListener != null) {
//            mListener.onSearch(etInput.getText().toString());
//        }
//        //隐藏软键盘
//        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//    }

    /**
     * 设置热搜版提示 adapter
     */
//    public void setTipsHintAdapter(ArrayAdapter<String> adapter) {
//        this.mHintAdapter = adapter;
//        if (lvTips.getAdapter() == null) {
//            lvTips.setAdapter(mHintAdapter);
//        }
//    }
    public  void setHint(Context context,String s){
        etInput.setHint(s);
    }

    /**
     * 设置自动补全adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            if (!"".equals(charSequence.toString())) {
//              //  ivDelete.setVisibility(VISIBLE);
//                //热搜提示
//               // lvTips.setVisibility(VISIBLE);
//                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
//                    lvTips.setAdapter(mAutoCompleteAdapter);
//                }
//                //更新autoComplete数据
//                if (mListener != null) {
//                    mListener.onRefreshAutoComplete(charSequence + "");
//                }
//            } else {
//              //  ivDelete.setVisibility(GONE);
//                if (mHintAdapter != null) {
//                    lvTips.setAdapter(mHintAdapter);
//                }
//                lvTips.setVisibility(GONE);
//            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.search_et_input:
//                lvTips.setVisibility(VISIBLE);
//                break;
            case R.id.search_in:
                //ToastUtil.ToastShow(getContext(),"搜索",true);
                switch (etInput.getHint().toString()){
                    case "医院/医生":
                        Intent intent=new Intent(mContext, TFSearchActivity.class);
                        Log.i("sousuo",etInput.getText().toString());
                        intent.putExtra("con",etInput.getText().toString());
                        mContext.startActivity(intent);
                        break;
                    case "医院":
                        Intent intent1=new Intent(mContext, TFSearchActivity.class);
                        Log.i("sousuo",etInput.getText().toString());
                        intent1.putExtra("con",etInput.getText().toString());
                        mContext.startActivity(intent1);
                        break;
                    case "医生":
                        Intent intent2=new Intent(mContext, TFSearchActivity.class);
                        Log.i("sousuo",etInput.getText().toString());
                        intent2.putExtra("con",etInput.getText().toString());
                        mContext.startActivity(intent2);
                        break;
                    case "药品":
                        Intent intent3=new Intent(mContext, DrugPriceActivity.class);
                        Log.i("sousuo",etInput.getText().toString());
                        intent3.putExtra("con",etInput.getText().toString());
                        mContext.startActivity(intent3);
                        break;
                    case "医疗服务项目":
                        Intent intent4=new Intent(mContext, JBZZActivity.class);
                        Log.i("sousuo",etInput.getText().toString());
                        intent4.putExtra("con",etInput.getText().toString());
                        mContext.startActivity(intent4);
                        break;
                }

                break;
        }
    }


    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

//        /**
//         * 提示列表项点击时回调方法 (提示/自动补全)
//         */
//        void onTipsItemClick(String text);
    }

}

