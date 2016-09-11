package com.zxiaofan.yunyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zxiaofan.yunyi.R;

public class JBZZActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.jbzz_main);
        super.onCreate(savedInstanceState);
        Button but_tt = (Button) findViewById(R.id.bt_toutong);
        but_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_toutong.class);
                startActivity(intent);
            }
        });
        Button but_bisai = (Button) findViewById(R.id.bt_bisai);
        but_bisai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_bisai.class);
                startActivity(intent);
            }
        });
        Button but_xinji = (Button) findViewById(R.id.bt_xinji);
        but_xinji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_xinji.class);
                startActivity(intent);
            }
        });
        Button but_gpttnx = (Button) findViewById(R.id.bt_gpttnx);
        but_gpttnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_gpttnx.class);
                startActivity(intent);
            }
        });
        Button but_smmct = (Button) findViewById(R.id.bt_smmct);
        but_smmct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_smmct.class);
                startActivity(intent);
            }
        });
        Button but_zbhgjtt = (Button) findViewById(R.id.bt_zbhgjtt);
        but_zbhgjtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_zbhgjtt.class);
                startActivity(intent);
            }
        });
        Button but_kesou = (Button) findViewById(R.id.bt_kesou);
        but_kesou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_kesou.class);
                startActivity(intent);
            }
        });
        Button but_yaotong = (Button) findViewById(R.id.bt_yaotong);
        but_yaotong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_yaotong.class);
                startActivity(intent);
            }
        });
        Button but_bianmi = (Button) findViewById(R.id.bt_bianmi);
        but_bianmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_bianmi.class);
                startActivity(intent);
            }
        });
        Button but_houlongt = (Button) findViewById(R.id.bt_houlongt);
        but_houlongt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JBZZActivity.this, A_jbzz_houlongt.class);
                startActivity(intent);
            }
        });
    }
}
