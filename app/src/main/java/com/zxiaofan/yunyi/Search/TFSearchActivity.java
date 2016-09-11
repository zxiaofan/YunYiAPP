package com.zxiaofan.yunyi.Search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.activity.FindHospitalActivity;
import com.zxiaofan.yunyi.activity.MainActivity;
import com.zxiaofan.yunyi.adapter.FindDocAdapter;
import com.zxiaofan.yunyi.adapter.FindHosAdapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;

public class TFSearchActivity extends BaseActivity implements OnClickListener {

    public static final String SEARCH_HISTORY = "search_history";
    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.auto_edit)
    EditText autoEdit;

    @Bind(R.id.search_button)
    TextView searchButton;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.thos)
    TextView thos;
    @Bind(R.id.auto_listview)
    ListView autoListview;
    @Bind(R.id.gengduo)
    TextView gengduo;
    @Bind(R.id.tdoc)
    TextView tdoc;
    @Bind(R.id.listview2)
    ListView listview2;
    @Bind(R.id.sousuo2)
    TextView sousuo2;
    private EditText ed1;
    private ListView mAutoListView;
    private TextView mSearchButtoon;
    private TextView mAutoEdit;
    private ImageView ivDeleteText;
    private SearchAutoAdapter mSearchAutoAdapter;
    private PopupWindow popupWindow;
    private List<Map<String, Object>> list;
    private List<Map<String, Object>> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        init();
        initPopWindow();

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initAction() {

    }

    private void init() {
        initTitle();

        mSearchAutoAdapter = new SearchAutoAdapter(this, 5);
        mAutoListView = (ListView) findViewById(R.id.auto_listview);
        //ed1= (EditText) findViewById(R.id.auto_edit);
        //mAutoListView.setAdapter(mSearchAutoAdapter);


        mSearchButtoon = (TextView) findViewById(R.id.search_button);
        mSearchButtoon.setOnClickListener(this);

        mAutoEdit = (TextView) findViewById(R.id.auto_edit);
        Log.i("sousuo", getIntent().getStringExtra("con"));

        mAutoEdit.setText(getIntent().getStringExtra("con"));
        if (getIntent().getStringExtra("con").length()>0){
           mSearchButtoon.performClick();
        }
        mAutoEdit.setOnClickListener(this);
        mAutoEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mSearchAutoAdapter.performFiltering(s);


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showPop();
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });

        ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
        ivDeleteText.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                mAutoEdit.setText("");
            }
        });
        mAutoListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                intent.putExtra("postion",1);
                intent.putExtra("hosOrgName",list.get(i).get("hosOrgName").toString());
                intent.putExtra("hosOrgCode",list.get(i).get("hosOrgCode").toString());
                intent.putExtra("hosId",list.get(i).get("hosId").toString());
                startActivity(intent);
            }
        });
        gengduo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),FindHospitalActivity.class);
                intent.putExtra("hosname",mAutoEdit.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_button) {//搜索按钮
            saveSearchHistory();

            mSearchAutoAdapter.initSearchHistory();
            initDate();
            initDate1();
            Toast.makeText(this, "点击搜索成功", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * 保存搜索记录
     */
    private void saveSearchHistory() {
        String text = mAutoEdit.getText().toString().trim();
        if (text.length() < 1) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        //检查历史记录是否已经存在当前输入的text，如果存在则删除
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            //如果记录大于4个，则移除最后一个数据再在最前面增加一个数据
            if (history.size() > 4) {
                history.remove(history.size() - 1);
            }
            history.add(0, text);
        }

        //重新加，提交
        if (history.size() > 0) //history.size()>1和history.size()>0一样
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");//这句一开始添加一个数据时加了两个,	why？ 用String也是 因为""
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("搜索");
        titleBarUtils.setLeftButtonClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDate() {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "hospital",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("chaxun", response);
                        parsehos(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                map.put("hosName", mAutoEdit.getText().toString());
                //map.put("info", adv.toString());
                //map.put("token", token);
                return map;
            }
        };


        queue.add(requset);

    }

    private void parsehos(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        List<Map<String, Object>> hospitalSimples = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            hospitalSimples = JsonUtils.getListMap(data.get("hospitalSimples").toString());
            list = hospitalSimples;
            mAutoListView.setAdapter(new FindHosAdapter1(list, getBaseContext()
            ));
            thos.setText("医院（共搜索到"+list.size()+"条结果）");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDate1() {
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        IStringRequest requset = new IStringRequest(Request.Method.POST,
                Constants.SERVER_ADDRESS + "doctor",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("chaxun", response);
                        parsedoc(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ToastUtil.ToastShow(getBaseContext(), "开发中，敬请期待 . . .", true);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                map.put("keyWord", mAutoEdit.getText().toString());

                return map;
            }
        };


        queue.add(requset);

    }

    private void parsedoc(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        List<Map<String, Object>> doctors = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            doctors = JsonUtils.getListMap(data.get("doctors").toString());
            list1 = doctors;
            listview2.setAdapter(new FindDocAdapter(list1, getBaseContext()
            ));

            tdoc.setText("医生（共搜索到"+list1.size()+"条结果）");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initPopWindow() {
        View popView = View.inflate(this, R.layout.pup_listview, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        // popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
        ListView listview = (ListView) popView.findViewById(R.id.listView8);

        listview.setAdapter(mSearchAutoAdapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //adapterView.getAdapter().getItem(i);
                mAutoEdit.setText(adapterView.getAdapter().getItem(i).toString());
                popupWindow.dismiss();
                mSearchButtoon.performClick();
            }
        });


    }

    public void showPop() {
        //设置popwindow显示位置
        // popupWindow.showAtLocation(parent, 0, x, y);
        // popupWindow.showAtLocation(view,5,0,10);
        popupWindow.showAsDropDown(mAutoEdit);
        //获取popwindow焦点
        //popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        mAutoEdit.setFocusable(true);
        mAutoEdit.setFocusableInTouchMode(true);
        mAutoEdit.requestFocus();
        mAutoEdit.requestFocusFromTouch();
//		popupWindow.update();

        if (popupWindow.isShowing()) {

        }
    }


}
