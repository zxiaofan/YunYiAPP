package com.zxiaofan.yunyi.hospital;
//特色科室Activity
//xiaofan
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.hospital.adapter.SpecialAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import widget.ProgressDialogStyle;

public class SpecialDepartmentActivity extends BaseActivity {
    List<Map<String, Object>> departments = new ArrayList<>();

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.hosname)
    TextView hosname;
    @Bind(R.id.listView5)
    ListView listView5;
    String hosId, hosname1;
    private String hosOrgCode="";
    private Dialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_special_department);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        hosname1 = getIntent().getStringExtra("hosname");
        hosname.setText(hosname1);
        hosId = getIntent().getStringExtra("hosId");
        hosOrgCode = getIntent().getStringExtra("hosOrgCode");
        initTitle();
        network();
        listView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getBaseContext(),DepartmentInformationActivity.class);
                intent.putExtra("deptCode",departments.get(i).get("deptCode").toString());
                intent.putExtra("keshi",departments.get(i).get("deptName").toString());
                intent.putExtra("hosOrgCode",hosOrgCode);

                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initAction() {

    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) findViewById(R.id.titleBar);
        titleBarUtils.setTitle("特色科室");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    public void network() {
        if (pb==null){
            pb = ProgressDialogStyle.createLoadingDialog(this, "正在登录...");
            pb.show();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS + "department/?hosId=" + hosId+"&isSpec=0",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.dismiss();
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;

                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());
                            departments=JsonUtils.getListMap(data.get("departments").toString());

                            Log.i("spec",departments.size()+"");
                            listView5.setAdapter(new SpecialAdapter(getBaseContext(),departments));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pb.dismiss();
                        Log.i("aaa", error.toString());

                    }
                }
        );
        queue.add(requset);
    }
}
