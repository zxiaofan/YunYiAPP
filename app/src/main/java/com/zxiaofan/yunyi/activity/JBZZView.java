package com.zxiaofan.yunyi.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.ExpandableListActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;
import com.zxiaofan.yunyi.adapter.ExpandableListAdapter;
import com.zxiaofan.yunyi.model.DetailsFeaturesVo;

import util.ToastUtil;

/**
 * Created by lenovo on 2016/6/4.
 * <p>
 * 参考：http://www.cnblogs.com/yncxzdy/p/4298975.html?utm_source=tuicool&utm_medium=referral
 */
public class JBZZView extends ExpandableListActivity {
    private static final String TAG = "JBZZView";
    ServiceAPI api = new ServiceAPI();
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
        List<DetailsFeaturesVo> lists = new Gson().fromJson(data, new TypeToken<List<DetailsFeaturesVo>>() {
        }.getType());
        Map<String, String> title_param = new HashMap<String, String>();
        title_param.put("group", "====你勾选的症状：====");
        gruops.add(title_param);
        // 创建二个一级条目标题
        for (int i = 0; i < lists.size(); i++) {
            Map<String, String> title_1 = new HashMap<String, String>();
            title_1.put("group", lists.get(i).getDiseaseName());
            gruops.add(title_1);
        }
//
//        Map<String, String> title_1 = new HashMap<String, String>();
//        Map<String, String> title_2 = new HashMap<String, String>();
//        Map<String, String> title_3 = new HashMap<String, String>();
//        title_1.put("group", "zxiaofan.com");
//        title_2.put("group", "com.zxiaofan");
//        gruops.add(title_1);
//        gruops.add(title_2);
        List<Map<String, String>> childs_param = new ArrayList<Map<String, String>>();
        Map<String, String> content0 = new HashMap<String, String>();
        content0.put("child", param);
        childs_param.add(content0);
        childs.add(childs_param);
        // 创建二级条目内容

        for (int i = 0; i < lists.size(); i++) {
            List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();

            DetailsFeaturesVo vo = lists.get(i);
            if (vo.getPainPerception() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "疼痛的感觉：" + vo.getPainPerception());
                childs_1.add(content);
            }
            if (vo.getPainRegion() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "疼痛的部位：" + vo.getPainRegion());
                childs_1.add(content);
            }
            if (vo.getPainDuration() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "疼痛的持续时间：" + vo.getPainDuration());
                childs_1.add(content);
            }
            if (vo.getSymptomWorsen() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "病症在什么情况下会恶化：" + vo.getSymptomWorsen());
                childs_1.add(content);
            }
            if (vo.getOtherFeaturesOfPain() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "疼痛的其它特征：" + vo.getOtherFeaturesOfPain());
                childs_1.add(content);
            }
            if (vo.getSymptomReason() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "何种因素可引发此症状：" + vo.getSymptomReason());
                childs_1.add(content);
            }
            if (vo.getSymptomRelieved() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "何种做法可减轻症状：" + vo.getSymptomRelieved());
                childs_1.add(content);
            }
            if (vo.getSymptomStart() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "症状何时开始：" + vo.getSymptomStart());
                childs_1.add(content);
            }
            if (vo.getSymptomWith() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "伴有：" + vo.getSymptomWith());
                childs_1.add(content);
            }
            if (vo.getSymptomFelling() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "你会感觉到：" + vo.getSymptomFelling());
                childs_1.add(content);
            }
            if (vo.getBloodPosition() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "血出现在：" + vo.getBloodPosition());
                childs_1.add(content);
            }
            if (vo.getOtherFeatures() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "症状的其它特征：" + vo.getOtherFeatures());
                childs_1.add(content);
            }
            if (vo.getCoughing() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "咳嗽表现为：" + vo.getCoughing());
                childs_1.add(content);
            }
            if (vo.getAffectedArea() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "受影响或累及部位为：" + vo.getAffectedArea());
                childs_1.add(content);
            }
            if (vo.getSymptomAppears() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "病症出现在下面哪种情况之后：" + vo.getSymptomAppears());
                childs_1.add(content);
            }
            if (vo.getSwallowFelling() != null) {
                Map<String, String> content = new HashMap<String, String>();
                content.put("child", "吞咽时：" + vo.getSwallowFelling());
                childs_1.add(content);
            }

            childs.add(childs_1);
        }
        // 内容一
//        Map<String, String> title_1_content_1 = new HashMap<String, String>();
//        Map<String, String> title_1_content_2 = new HashMap<String, String>();
//        Map<String, String> title_1_content_3 = new HashMap<String, String>();
//        title_1_content_1.put("child", "工人");
//        title_1_content_2.put("child", "学生");
//        title_1_content_3.put("child", "农民");
//
//        List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
//        childs_1.add(title_1_content_1);
//        childs_1.add(title_1_content_2);
//        childs_1.add(title_1_content_3);
//
//        // 内容二
//        Map<String, String> title_2_content_1 = new HashMap<String, String>();
//        Map<String, String> title_2_content_2 = new HashMap<String, String>();
//        Map<String, String> title_2_content_3 = new HashMap<String, String>();
//        title_2_content_1.put("child", "猩猩");
//        title_2_content_2.put("child", "老虎");
//        title_2_content_3.put("child", "狮子");
//        List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
//        childs_2.add(title_2_content_1);
//        childs_2.add(title_2_content_2);
//        childs_2.add(title_2_content_3);

//        childs.add(childs_1);
//        childs.add(childs_2);

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
                                final int groupPosition, int childPosition, long id) {
//        Toast.makeText(JBZZView.this,
//                "您选择了" + gruops.get(groupPosition).toString()
//                        + "子编号"
//                        + childs.get(groupPosition).get(childPosition)
//                        .toString(), Toast.LENGTH_SHORT).show();


        AlertDialog.Builder dialog = new AlertDialog.Builder(JBZZView.this);
        dialog.setTitle("你需要获取医疗资源信息吗？");
//        dialog.setMessage("Something important.");
        final String[] items = {"医院推荐", "医生推荐", "疾病详情", "我不需要,谢谢"};
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String diseaseName = gruops.get(groupPosition).toString().replace("{group=", "").replace("}", "");
                if (which == 0) {
                    api.queryHospitalsByDisease(diseaseName, JBZZView.this);
                } else if (which == 1) {
                    api.queryDoctorsByDisease(diseaseName, JBZZView.this);
                } else if (which == 2) {
                    ToastUtil.show(JBZZView.this, "即将进入 " + diseaseName + " 详情页（待完善）");
                } else {
                }
            }
        }).show();
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