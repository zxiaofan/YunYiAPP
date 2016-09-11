package com.zxiaofan.yunyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.ServiceAPI;
import com.zxiaofan.yunyi.SoapHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import util.ToastUtil;

/**
 * Created by lenovo on 2016/6/1.
 */
public class A_jbzz_toutong extends Activity {
    String TAG = "A_jbzz_toutong";
    ServiceAPI api = new ServiceAPI();
    int[] checkId = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4, R.id.checkBox5, R.id.checkBox6, R.id.checkBox7, R.id.checkBox8, R.id.checkBox9, R.id.checkBox10, R.id.checkBox11, R.id.checkBox12, R.id.checkBox13, R.id.checkBox14, R.id.checkBox15, R.id.checkBox16, R.id.checkBox17, R.id.checkBox18, R.id.checkBox19, R.id.checkBox20, R.id.checkBox21, R.id.checkBox22, R.id.checkBox23, R.id.checkBox24, R.id.checkBox25, R.id.checkBox26, R.id.checkBox27, R.id.checkBox28, R.id.checkBox29, R.id.checkBox30, R.id.checkBox31, R.id.checkBox32, R.id.checkBox33, R.id.checkBox34, R.id.checkBox35, R.id.checkBox36, R.id.checkBox37, R.id.checkBox38};
    List<String> listSym = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.jbzz_toutong);
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
                    ToastUtil.show(A_jbzz_toutong.this, "您尚未勾选任何症状！");
                } else {
                    api.queryDiseaseBySymptom(listSym.toString(), A_jbzz_toutong.this);
                }
            }
        });
    }
}
