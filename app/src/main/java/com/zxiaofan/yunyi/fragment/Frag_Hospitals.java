package com.zxiaofan.yunyi.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.activity.MainActivity;

import com.zxiaofan.yunyi.hospital.ExpertIntroductionActivity;
import com.zxiaofan.yunyi.hospital.HospitalNavigationActivity;
import com.zxiaofan.yunyi.hospital.MedicalNavigationActivity;
import com.zxiaofan.yunyi.hospital.PriceSearchActivity;
import com.zxiaofan.yunyi.hospital.SpecialDepartmentActivity;
import com.zxiaofan.yunyi.hospital.TrackingStationActivity;

import com.zxiaofan.yunyi.hospital.adapter.PupAdapter1;
import com.zxiaofan.yunyi.registered.DepartmentRegistered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;

/**
 * Created by xiaofan on 2016/3/16.
 */
public class Frag_Hospitals extends BaseFragment implements View.OnClickListener {
    Intent intent;

    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.textView11)
    TextView textView11;
    @Bind(R.id.textView100)
    TextView textView100;
    @Bind(R.id.textView12)
    TextView textView12;
    @Bind(R.id.textView13)
    TextView textView13;
    @Bind(R.id.textView14)
    TextView textView14;
    @Bind(R.id.textView15)
    TextView textView15;
    @Bind(R.id.textView16)
    TextView textView16;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.tab_yuyueguahao)
    RelativeLayout tabYuyueguahao;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.tab_jiaohaogenzong)
    RelativeLayout tabJiaohaogenzong;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.tab_jiuyidaohang)
    RelativeLayout tabJiuyidaohang;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.textView8)
    TextView textView8;
    @Bind(R.id.tab_jiagechaxun)
    RelativeLayout tabJiagechaxun;
    @Bind(R.id.imageView5)
    ImageView imageView5;
    @Bind(R.id.textView9)
    TextView textView9;
    @Bind(R.id.textView10)
    TextView textView10;
    @Bind(R.id.tab_menzhenchufangchaxun)
    RelativeLayout tabMenzhenchufangchaxun;
    @Bind(R.id.imageView6)
    ImageView imageView6;
    @Bind(R.id.textView28)
    TextView textView28;
    @Bind(R.id.textView29)
    TextView textView29;
    @Bind(R.id.tab_jiancha)
    RelativeLayout tabJiancha;
    @Bind(R.id.imageView7)
    ImageView imageView7;
    @Bind(R.id.textView30)
    TextView textView30;
    @Bind(R.id.textView31)
    TextView textView31;
    @Bind(R.id.tab_menzhenfei)
    RelativeLayout tabMenzhenfei;
    @Bind(R.id.tab_tesekeshi)
    LinearLayout tabTesekeshi;
    @Bind(R.id.tab_zhuanjiajieshao)
    LinearLayout tabZhuanjiajieshao;
    @Bind(R.id.tab_yiyuandaohang)
    LinearLayout tabYiyuandaohang;
    @Bind(R.id.pup)
    RelativeLayout pup;
    private PopupWindow popupWindow;
    private List<Map<String, Object>> hospitalSimples=new ArrayList<>();
    private List<Map<String, Object>> list1=new ArrayList<>();
    private String hosId;
    private String hosname;
    private String hosOrgCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_hospitals, null);
        initTitle();
        ButterKnife.bind(this, view);
        hosname=getActivity().getIntent().getStringExtra("hosOrgName");
        hosId=getActivity().getIntent().getStringExtra("hosId");
        textView10.setText(getActivity().getIntent().getStringExtra("hosLevel"));

          //  textView14.setText(getActivity().getIntent().getStringExtra("hosAddr"));

        textView12.setText(getActivity().getIntent().getStringExtra("hosType"));



        hosOrgCode=getActivity().getIntent().getStringExtra("hosOrgCode");
        textView11.setText(hosname);
        initView();
        return view;
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) view.findViewById(R.id.titleBar);
        titleBarUtils.setTitle("医院主站");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        hoslist();
        tabJiaohaogenzong.setOnClickListener(this);
        tabTesekeshi.setOnClickListener(this);
        tabZhuanjiajieshao.setOnClickListener(this);
        tabYiyuandaohang.setOnClickListener(this);
        tabJiuyidaohang.setOnClickListener(this);
        tabJiagechaxun.setOnClickListener(this);
        tabYuyueguahao.setOnClickListener(this);
        textView15.setOnClickListener(this);
        hosname=textView11.getText().toString();
    }

    @Override
    protected void initAction() {

    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_jiuyidaohang://就医导航
                intent = new Intent(getActivity(), MedicalNavigationActivity.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                startActivity(intent);
                break;
            case R.id.tab_yuyueguahao://预约挂号
                intent = new Intent(getActivity(), DepartmentRegistered.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                startActivity(intent);
                break;
            case R.id.tab_tesekeshi://特色科室
                intent = new Intent(getActivity(), SpecialDepartmentActivity.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                intent.putExtra("hosOrgCode",hosOrgCode);
                startActivity(intent);
                break;
            case R.id.tab_zhuanjiajieshao://专家介绍
                intent = new Intent(getActivity(), ExpertIntroductionActivity.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                intent.putExtra("hosOrgCode",hosOrgCode);
                startActivity(intent);
                break;
            case R.id.tab_yiyuandaohang://医院导航
                intent = new Intent(getActivity(), HospitalNavigationActivity.class);
                intent.putExtra("hosId",hosId);
                intent.putExtra("hosname",hosname);
                startActivity(intent);
                break;
            case R.id.tab_jiaohaogenzong://叫号跟踪
                intent = new Intent(getActivity(), TrackingStationActivity.class);
                intent.putExtra("hosOrgCode",hosId);
                intent.putExtra("hosname",hosname);
                startActivity(intent);
                break;
            case R.id.tab_jiagechaxun://价格查询
                intent = new Intent(getActivity(), PriceSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.textView15://切换医院
                ToastUtil.ToastShow(getActivity() ,"切换",false);
                initPopWindow();
                showPop(view, 50, 50, 20);
                break;


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    public void hoslist() {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        final IStringRequest requset = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS + "hospital",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hos",response);
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;

                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());
                            hospitalSimples = JsonUtils.getListMap(data.get("hospitalSimples").toString());
                            //listView.setAdapter(new FindHosAdapter(hospitalSimples, getBaseContext()

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("aaa", error.toString());

                    }
                }
        );
        final IStringRequest requset1 = new IStringRequest(Request.Method.GET,
                Constants.SERVER_ADDRESS + "hospital/default",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("hos",response);
                        Map<String, Object> object = null;
                        Map<String, Object> data = null;

                        try {
                            object = JsonUtils.getMapObj(response);
                            data = JsonUtils.getMapObj(object.get("data").toString());
                            hosId = data.get("id").toString();
                            hosname = data.get("hosOrgName").toString();
                            hosOrgCode = data.get("hosOrgCode").toString();
                            if (data.get("hosOrgName").toString().length() == 0) {
                                textView11.setText("暂无信息");
                            } else {
                                textView11.setText(data.get("hosOrgName").toString());
                            }

                            if (data.get("hosLevel").toString().length() == 0) {
                                textView10.setText("暂无信息");
                            } else {

                                textView10.setText(data.get("hosLevel").toString());

                            }

                            if (data.get("hosType").toString().length() == 0) {
                                textView12.setText("暂无信息");
                            } else {
                                textView12.setText(data.get("hosType").toString());
                            }
                            if (data.get("hosAddr").toString().length() == 0) {
                                textView14.setText("暂无信息");
                            } else {
                                textView14.setText(data.get("hosAddr").toString());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("aaa", error.toString());

                    }
                }
        );




            queue.add(requset);
            queue.add(requset1);
        };

    private void initPopWindow() {
        View popView = View.inflate(getActivity(), R.layout.pup_listview, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        // popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
        ListView listview = (ListView) popView.findViewById(R.id.listView8);

        listview.setAdapter(new PupAdapter1(getActivity(), hospitalSimples));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hosname=hospitalSimples.get(i).get("hosOrgName").toString();
                hosId=hospitalSimples.get(i).get("hosId").toString();
                hosOrgCode=hospitalSimples.get(i).get("hosOrgCode").toString();
                textView11.setText(hosname);
                popupWindow.dismiss();

            }
        });


    }

    public void showPop(View parent, int x, int y, int postion) {
        //设置popwindow显示位置
       // popupWindow.showAtLocation(parent, 0, x, y);
       // popupWindow.showAtLocation(view,5,0,10);
        popupWindow.showAsDropDown(titleBar);
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        if (popupWindow.isShowing()) {

        }

    }
}
