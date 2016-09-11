package com.zxiaofan.yunyi.activity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.model.DetailsFeaturesVo;
import com.zxiaofan.yunyi.model.HospitalsInfoBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ToastUtil;

/**
 * Created by lenovo on 2016/6/5.
 */
public class FindHosByDisease extends ExpandableListActivity {
    private static final String TAG = "FindHosByDisease";
    /**
     * 创建一级条目容器
     */
    List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
    /**
     * 存放内容, 以便显示在列表中
     */
    List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_jbzz_view);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        String param = intent.getStringExtra("param");
        Log.i(TAG, "onCreate: " + data);
        setListData(param, data);
    }

    /**
     * 设置列表内容
     */
    public void setListData(String param, String data) {
        List<HospitalsInfoBo> lists = new Gson().fromJson(data, new TypeToken<List<HospitalsInfoBo>>() {
        }.getType());
        Map<String, String> title_param = new HashMap<String, String>();
        title_param.put("group", "==疾病【" + param + "】==");
        gruops.add(title_param);
        // 创建二个一级条目标题
        for (int i = 0; i < lists.size(); i++) {
            Map<String, String> title_1 = new HashMap<String, String>();
            title_1.put("group", lists.get(i).getHospitalName());
            gruops.add(title_1);
        }

        List<Map<String, String>> childs_param = new ArrayList<Map<String, String>>();
        Map<String, String> content0 = new HashMap<String, String>();
        content0.put("child", param);
        childs_param.add(content0);
        childs.add(childs_param);
        // 创建二级条目内容

        for (int i = 0; i < lists.size(); i++) {
            List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();

            HospitalsInfoBo vo = lists.get(i);
            if (vo.getHospitalProvince() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "省份：" + vo.getHospitalProvince());
                childs_1.add(content);
            }
            if (vo.getHospitalCity() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "城市：" + vo.getHospitalCity());
                childs_1.add(content);
            }
            if (vo.getHospitalLevel() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "医院等级：" + vo.getHospitalLevel());
                childs_1.add(content);
            }
            if (vo.getAddress() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "医院地址：" + vo.getAddress());
                childs_1.add(content);
            }
            if (vo.getTelePhone() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "联系电话：" + vo.getTelePhone());
                childs_1.add(content);
            }

            childs.add(childs_1);
        }
        // 内容一

        /**
         * 创建ExpandableList的Adapter容器 参数： 1.上下文 2.一级集合 3.一级样式文件 4. 一级条目键值
         * 5.一级显示控件名 6. 二级集合 7. 二级样式 8.二级条目键值 9.二级显示控件名
         *
         */
        SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
                this, gruops, R.layout.groups, new String[]{"group"},
                new int[]{R.id.textGroup}, childs, R.layout.childs,
                new String[]{"child"}, new int[]{R.id.textChild});
        // 加入列表
        setListAdapter(sela);
        // 得到屏幕的大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //图标设置在右边
        getExpandableListView().setIndicatorBounds(dm.widthPixels - 60, dm.widthPixels); // 设置指示图标的位置

    }

    /**
     * 列表内容按下
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        String hosName = gruops.get(groupPosition).toString().replace("{group=", "").replace("}", "");
        ToastUtil.show(FindHosByDisease.this, "即将进入 " + hosName + " 详情页（待完善）");
        return super.onChildClick(parent, v, groupPosition, childPosition, id);
    }

    /**
     * 二级标题按下
     */
    @Override
    public boolean setSelectedChild(int groupPosition, int childPosition,
                                    boolean shouldExpandGroup) {
        return super.setSelectedChild(groupPosition, childPosition,
                shouldExpandGroup);
    }

    /**
     * 一级标题按下
     */
    @Override
    public void setSelectedGroup(int groupPosition) {
        super.setSelectedGroup(groupPosition);
    }
}
