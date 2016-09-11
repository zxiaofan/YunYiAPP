package com.zxiaofan.yunyi.activity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.model.DoctorsInfoBo;
import com.zxiaofan.yunyi.model.HospitalsInfoBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ToastUtil;

/**
 * Created by lenovo on 2016/6/5.
 */
public class FindDocByDisease extends ExpandableListActivity {
    private static final String TAG = "FindDocByDisease";
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
        List<DoctorsInfoBo>  lists = new Gson().fromJson(data, new TypeToken<List<DoctorsInfoBo>>() {
            }.getType());
        Map<String, String> title_param = new HashMap<String, String>();
        title_param.put("group", "==疾病【" + param + "】==");
        gruops.add(title_param);
        // 创建二个一级条目标题
        for (int i = 0; i < lists.size(); i++) {
            Map<String, String> title_1 = new HashMap<String, String>();
            title_1.put("group", lists.get(i).getDocName() + "--" + lists.get(i).getDocHospital());
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

            DoctorsInfoBo vo = lists.get(i);
            if (vo.getDocTitle() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "职称：" + vo.getDocTitle());
                childs_1.add(content);
            }
            if (vo.getDocHospital() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "所在医院：" + vo.getDocHospital());
                childs_1.add(content);
            }
            if (vo.getDocDepart() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "科室：" + vo.getDocDepart());
                childs_1.add(content);
            }
            if (vo.getRecommendHeat() != 0) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "推荐热度：" + vo.getRecommendHeat());
                childs_1.add(content);
            }
            if (vo.getThanksLetterNum() != 0) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "感谢信数量：" + vo.getThanksLetterNum());
                childs_1.add(content);
            }
            if (vo.getDocAdept() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "擅长：" + vo.getDocAdept());
                childs_1.add(content);
            }
            if (vo.getPracticeExperience() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "职业经历：" + vo.getPracticeExperience().substring(0, Math.min(200, vo.getPracticeExperience().length())));
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
        String docName = gruops.get(groupPosition).toString().replace("{group=", "").replace("}", "");
        ToastUtil.show(FindDocByDisease.this, "即将进入 " + docName + " 详情页（待完善）");
//        ToastUtil.show(FindDocByDisease.this, "即将进入" + childs.get(groupPosition).get(groupPosition).toString() + "详情页（待完善）");
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
