package com.zxiaofan.yunyi.User;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.adapter.MyUserAdapter;
import java.util.List;
import java.util.Map;
import base.BaseActivity;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import widget.ProgressDialogStyle;
import widget.RefreshLayout;
public class MyUserActivity extends BaseActivity {
    List<Map<String, Object>> list1 = null;

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.listView7)
    ListView listView7;

    @Bind(R.id.slistview)
    RefreshLayout slistview;
    private Button button3;
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_user);
        ButterKnife.bind(this);
        initTitle();
        View v = View.inflate(this, R.layout.tianjiajiuzhenren, null);
        button3= (Button) v.findViewById(R.id.button3);
        listView7.addFooterView(v);
        super.onCreate(savedInstanceState);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),AddUserActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        listView7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (getIntent().getIntExtra("type",0)==0) {
                    Intent intent = new Intent(getBaseContext(), EditUserActivity.class);
                    intent.putExtra("name", list1.get(i).get("name").toString());
                    intent.putExtra("gender", list1.get(i).get("gender").toString());
                    intent.putExtra("idNo", list1.get(i).get("idNo").toString());
                    intent.putExtra("phone", list1.get(i).get("phone").toString());
                    intent.putExtra("id", list1.get(i).get("id").toString());
                    startActivity(intent);
                }else {
                     o.putPres("id",list1.get(i).get("id").toString());
                     finish();
                }
            }
        });
        o = new OptsharepreInterface(this);
        String token = o.getPres("token");
        RequestQueue queue = Volley.newRequestQueue(this);
        if(pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "请求中...");
            pb.show();
        }
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS+ "patients?limit=20&offset=0&token=" + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa", response);
                        pb.dismiss();
                        parseuser(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("err", error.toString());
                      pb.dismiss();
                    }
                }
        );
        IStringRequest requset1 = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS+ "patient/current?token="+token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("aaa", response);
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;
                        try {
                            object = JsonUtils.getMapObj(response);
                            data=JsonUtils.getMapObj(object.get("data").toString());
                            String id=data.get("id").toString();
                            o.putPres("id",id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("err", error.toString());

                    }
                }
        );
        queue.add(requset1);
        queue.add(requset);


    }

    private void parseuser(String response) {
        Map<String, Object> object = null;
        List<Map<String, Object>> data = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getListMap(object.get("data").toString());
            list1=data;


            MyUserAdapter adapter = new MyUserAdapter(data, this);
            listView7.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initAction() {
        slistview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                slistview.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("我的就诊人");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
