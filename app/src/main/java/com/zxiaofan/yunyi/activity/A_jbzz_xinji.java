package com.zxiaofan.yunyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import util.ToastUtil;

/**
 * Created by lenovo on 2016/6/1.
 */
public class A_jbzz_xinji extends Activity {
    String TAG = "A_jbzz_xinji";
    ServiceAPI api = new ServiceAPI();
    int[] checkId = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox5, R.id.checkBox6, R.id.checkBox7, R.id.checkBox8, R.id.checkBox9, R.id.checkBox10, R.id.checkBox11, R.id.checkBox12};
    List<String> listSym = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.jbzz_xinji);
        super.onCreate(savedInstanceState);

        Button bt_jbcc = (Button) findViewById(R.id.bt_jbcc);
        bt_jbcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSym.clear();
                for (int id : checkId) {
                    CheckBox checkBox = (CheckBox) findViewById(id);
                    if (checkBox.isChecked()) {
                        listSym.add((String) checkBox.getText());
                    } else {
//                                listSym.remove((String) checkBox.getText());
                    }
                }
                HashSet<String> set = new HashSet<>(listSym); // 利用hashSet去重
                listSym.clear();
                listSym.addAll(set);
                Log.i(TAG, "onCreate: " + listSym.toString());
                if (listSym.isEmpty()) {
                    ToastUtil.show(A_jbzz_xinji.this, "您尚未勾选任何症状！");
                } else {
                    api.queryDiseaseBySymptom(listSym.toString(), A_jbzz_xinji.this);
                }
            }
        });
    }
}
