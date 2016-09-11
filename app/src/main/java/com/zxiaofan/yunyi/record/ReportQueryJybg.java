package com.zxiaofan.yunyi.record;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.login.LoginActivity;
import com.zxiaofan.yunyi.record.adapter.ReportQueryJybgAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import base.OptsharepreInterface;
import butterknife.Bind;
import butterknife.ButterKnife;
import util.Constants;
import util.JsonUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Describe:     报告查询中的检验报告
 * User:         xiaofan
 * Date:         2016/3/25 17:00
 */
public class ReportQueryJybg extends BaseFragment {

    @Bind(R.id.mrjp_lv)
    ExpandableListView mrjpLv;
    @Bind(R.id.sr)
    SwipeRefreshLayout sr;

    private Dialog pb;
    private OptsharepreInterface share;
    RequestQueue queue;
    private List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_report_query_jybg, container, false);
        share = new OptsharepreInterface(getActivity());
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        pb = ProgressDialogStyle.createLoadingDialog(getActivity(), "请求中...");
        pb.show();
        queue = Volley.newRequestQueue(getActivity());
        Log.e(Constants.TAG, share.getPres("token"));
        String url = Constants.SERVER_ADDRESS + "medicalRecords-testreportdata/patient-" + Constants.getPatientId(getActivity()) + "?token=" + share.getPres("token");
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadData(response);
                pb.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.ToastShow(getActivity(), "开发中...", true);
                pb.dismiss();
            }
        });
        queue.add(request);
    }

    /**
     * Describe:     筛选数据
     * User:         xiaofan
     * Date:         2016/3/30 10:53
     */
    private void loadData(String json) {
        Map<String, Object> object = null;
        String success = "";
        try {
            object = JsonUtils.getMapObj(json);
            success = object.get("success").toString();
            if (success.equals("0")) {
                ToastUtil.ToastShow(getActivity(), object.get("msg").toString(), true);
            } else if (success.equals("1")) {
                lists = JsonUtils.getListMap(object.get("data").toString());
                initWidget();
            } else {
                ToastUtil.ToastShow(getActivity(), "登录过期", true);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        } catch (Exception e) {

        }
    }

    public void initWidget() {
        ReportQueryJybgAdapter adapter = new ReportQueryJybgAdapter(getActivity(), lists);
        mrjpLv.setAdapter(adapter);
        mrjpLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                // ToastUtil.ToastShow(getBaseContext(), "dianji", true);
                return false;
            }

        });

        sr.setColorSchemeColors(R.color.switch_thumb_disabled_material_dark,
                R.color.switch_thumb_normal_material_dark, R.color.switch_thumb_normal_material_light,
                R.color.abc_secondary_text_material_light);
        // 设置下拉刷新监听器
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
                sr.setRefreshing(false);
            }
        });

    }


    @Override
    protected void initView() {

    }
    @Override
    protected void initAction() {

    }
    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
