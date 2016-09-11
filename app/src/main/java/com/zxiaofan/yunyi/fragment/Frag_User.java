package com.zxiaofan.yunyi.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zxiaofan.yunyi.MyApplication;
import com.zxiaofan.yunyi.R;
import com.zxiaofan.yunyi.User.AboutUs;
import com.zxiaofan.yunyi.User.ChangePasswordActivity;
import com.zxiaofan.yunyi.User.FeedBackActivity;
import com.zxiaofan.yunyi.User.MyCard;
import com.zxiaofan.yunyi.User.MyUserActivity;
import com.zxiaofan.yunyi.User.PersonalDetail;
import com.zxiaofan.yunyi.User.UnreadMessagesActivity;
import com.zxiaofan.yunyi.activity.MainActivity;
import com.zxiaofan.yunyi.login.LoginActivity;
import com.zxiaofan.yunyi.registered.RegisteredHistory;
import com.zxiaofan.yunyi.update.CheckVersionTask;
import com.zxiaofan.yunyi.update.Config;
import com.zxiaofan.yunyi.update.NetworkTool;
import com.zxiaofan.yunyi.update.TaskInf;
import com.zxiaofan.yunyi.update.UpdateVersion;
import com.zxiaofan.yunyi.update.service.UpdateService;

import java.util.List;
import java.util.Map;

import base.BaseFragment;
import base.OptsharepreInterface;
import base.PushSmsService;
import bean.MessageBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import db.DataBaseManager;
import util.Constants;
import util.IStringRequest;
import util.JsonUtils;
import util.TitleBarUtils;
import util.ToastUtil;
import widget.ProgressDialogStyle;

/**
 * Created by lenovo on 2016/3/16.
 */

public class Frag_User extends BaseFragment implements View.OnClickListener {

    MessageBean message = new MessageBean();
    @Bind(R.id.titleBar)
    TitleBarUtils titleBar;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.username_text)
    TextView usernameText;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.textView8)
    TextView textView8;
    @Bind(R.id.textView9)
    TextView textView9;
    @Bind(R.id.dangan)
    LinearLayout dangan;
    @Bind(R.id.yuyue)
    LinearLayout yuyue;
    @Bind(R.id.func)
    LinearLayout func;
    @Bind(R.id.yijian)
    LinearLayout yijian;
    @Bind(R.id.about)
    LinearLayout about;
    @Bind(R.id.update)
    LinearLayout update;
    @Bind(R.id.xiugaimima)
    LinearLayout xiugaimima;
    @Bind(R.id.resiglogin)
    LinearLayout resiglogin;
    @Bind(R.id.jiuyika)
    LinearLayout jiuyika;
    @Bind(R.id.tab_message)
    RelativeLayout tabMessage;
    @Bind(R.id.wodejiuzhenren)
    RelativeLayout wodejiuzhenren;
    @Bind(R.id.id_sum)
    TextView idSum;
    private Intent intent;
    private String name;
    private String idNo, gender;
    private RequestQueue queue;
    private String token;
    boolean first = true;
    private DataBaseManager db;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Log.i("hand", "消息是" + msg.arg1);
                    idSum.setText(msg.arg1 + "");
                    break;

            }
            super.handleMessage(msg);
        }
    };
    private Dialog pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_user, null);

        ButterKnife.bind(this, view);
        initView();
        initTitle();
        return view;
    }

    private void initTitle() {
        TitleBarUtils titleBarUtils = (TitleBarUtils) view.findViewById(R.id.titleBar);
        titleBarUtils.setTitle("个人中心");
        titleBarUtils.setLeftButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (!MyApplication.loginFlag) {
            usernameText.setText("未登录");
        }
        initView();
        initAction();
    }

    @Override
    protected void initView() {
        xiugaimima.setOnClickListener(this);
        update.setOnClickListener(this);
        jiuyika.setOnClickListener(this);
        about.setOnClickListener(this);
        usernameText.setOnClickListener(this);
        resiglogin.setOnClickListener(this);
        dangan.setOnClickListener(this);
        tabMessage.setOnClickListener(this);
        yijian.setOnClickListener(this);
        wodejiuzhenren.setOnClickListener(this);
        o = new OptsharepreInterface(getActivity());
        token = o.getPres("token");
        idSum.setText(o.getPres("unmsg"));

        if (MyApplication.loginFlag) {
            queue = Volley.newRequestQueue(getActivity());
            IStringRequest requset = new IStringRequest(Request.Method.GET,
                    Constants.SERVER_ADDRESS + "userinfo/summary?token=" + token,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("aaa", response);
                            parseUser(response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("err", error.toString());

                        }
                    }
            );
            queue.add(requset);

        }


    }

    private void insertdb(String response) {
        Map<String, Object> object = null;
        List<Map<String, Object>> data = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getListMap(object.get("data").toString());
            Message msg = Message.obtain();
            msg.arg1 = data.size();
            msg.what = 0x123;
            Log.i("sizem", msg.arg1 + "");
            handler.sendMessage(msg);

            Log.i("size", data.size() + "");
            o.putPres("unmsg", data.size() + "");

            for (int i = 0; i < data.size(); i++) {
                message.setContext1(data.get(i).get("msg").toString());
                message.setContext(data.get(i).get("msg").toString());
                message.setMesid(data.get(i).get("id").toString());
                message.setMessagetype(data.get(i).get("msgtype").toString());
                message.setUpdate1(data.get(i).get("createtime").toString());
                db = DataBaseManager.getInstance(getActivity());
                db.insertData1("m" + MyApplication.phone, message);
                db.getDataCounts("m" + MyApplication.phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseUser(String response) {
        Map<String, Object> object = null;
        Map<String, Object> data = null;
        Map<String, Object> user = null;
        try {
            object = JsonUtils.getMapObj(response);
            data = JsonUtils.getMapObj(object.get("data").toString());
            if (data.size() == 0) {
            } else {
                String sum = data.get("myPatientCount").toString();
                user = JsonUtils.getMapObj(data.get("user").toString());
                name = user.get("name").toString();
                Log.i("bbb", name);

                idNo = user.get("idNo").toString();
                gender = user.get("gender").toString();
                textView9.setText(sum);
                if (name.length() == 0) {
                    usernameText.setText("请设置用户名");
                } else {
                    usernameText.setText(name);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    protected void initAction() {

        if (first && MyApplication.loginFlag) {
            IStringRequest requset1 = new IStringRequest(Request.Method.GET,
                    Constants.SERVER_ADDRESS + "usermessage/unread?token=" + token,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("aaa", response);
                            // insertdb(response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("err", error.toString());

                        }
                    }
            );

            //queue.add(requset1);
            first = false;
        }
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


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.username_text://个人信息
                if (MyApplication.loginFlag) {
                    intent = new Intent(getActivity(), PersonalDetail.class);
                    intent.putExtra("name", name);
                    intent.putExtra("idNo", idNo);
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                } else {
                    ToastUtil.ToastShow(getActivity(), "您还没有登录，登录账号后再来吧", true);
                }
                break;
            case R.id.dangan:
                ToastUtil.ToastShow(getActivity(), "点击了健康档案", true);
                // Toast.makeText(getActivity(),"点击了健康档案",Toast.LENGTH_SHORT).show();
                break;
            case R.id.yuyue:
                intent = new Intent(getActivity(), RegisteredHistory.class);
                startActivity(intent);
                break;
//            case R.id.setaccount:
//                Toast.makeText(getActivity(), "点击了退出登录", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.wodejiuzhenren://我的就诊人
                if (MyApplication.loginFlag == false) {
                    ToastUtil.ToastShow(getActivity(), "您还没有登录，登录账号后再来吧", true);
                } else {
                    intent = new Intent(getActivity(), MyUserActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
                break;
            case R.id.func:
                Toast.makeText(getActivity(), "点击了退出登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.yijian://意见反馈
                if (MyApplication.loginFlag == false) {
                    ToastUtil.ToastShow(getActivity(), "您还没有登录，登录账号后再来吧", true);
                } else {
                    intent = new Intent(getActivity(), FeedBackActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.about://关于
                intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
                break;
            case R.id.update://检查更新
                checkUpdate();
                break;
            case R.id.resiglogin:
                resiglogin1();
              //  Toast.makeText(getActivity(), "点击了退出登录", Toast.LENGTH_SHORT).show();
                o.putPres("loginFlag","false");
                Intent intent1=new Intent(getActivity(), PushSmsService.class);
                getActivity().stopService(intent1);


                MyApplication.loginFlag = false;
                onResume();
                break;
            case R.id.jiuyika://我的就医卡
                intent = new Intent(getActivity(), MyCard.class);
                startActivity(intent);
                break;
            case R.id.tab_message://未读消息
                if (MyApplication.loginFlag == false) {
                    ToastUtil.ToastShow(getActivity(), "您还没有登录，登录账号后再来吧", true);
                } else {
                    intent = new Intent(getActivity(), UnreadMessagesActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.xiugaimima://修改密码
                intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
    //退出登录
    private void resiglogin1() {
        ToastUtil.show(getActivity(),"退出登录");
//
//        IStringRequest requset1 = new IStringRequest(Request.Method.GET,
//                Constants.SERVER_ADDRESS + "logout?token=" + token,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i("aaa", response);
//                      //  parseUser(response);
//
//                        ToastUtil.ToastShow(getActivity(),"退出登录成功",false);
//                        Intent intent=new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("err", error.toString());
//
//                    }
//                }
//        );
//
//
//        queue.add(requset1);

    }

    /**
    * Describe:     检查更新
    * User:         xiaofan
    * Date:         2016/4/12 15:21
    */
    private void checkUpdate(){
        CheckVersionTask checkTask = new CheckVersionTask(getActivity());
        checkTask.setListener(new TaskInf() {
            @Override
            public void onPreExecute() {
                pb = ProgressDialogStyle.createLoadingDialog(getActivity(), "请求中...");
                pb.show();
            }

            @Override
            public void isSuccess(Map<String, Object> b) {
                if (null !=pb) {
                    pb.dismiss();
                }
                final Map<String, Object> map = (Map<String, Object>)b;
                if ((Boolean) map.get("isUpdate")) {//版本需要更新
                    String verName = Config.getVerName(getActivity());
                    StringBuffer sb = new StringBuffer();
                    sb.append("检测到新版本:");
                    sb.append(verName.replace("ver:", "").replace("build", "日期"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    sb.append(",\n更新内容："+map.get("content").toString());
                    builder.setMessage(sb.toString());
                    builder.setTitle("软件更新");
                    builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(UpdateVersion.getUninstallAPKInfo(getActivity(),Integer.parseInt(map.get("version").toString()))){
                                UpdateVersion.showInstallDialog(getActivity());
                            }else{
                                // 启动下载安装任务
                                boolean isupLocStart = NetworkTool
                                        .isServiceRunning(getActivity(),
                                                UpdateService.class.getName());
                                if (isupLocStart) {
                                    ToastUtil.ToastShow(getActivity(),"正在更新客户端",false);
                                } else {
                                    getActivity().startService(new Intent(getActivity(),
                                            UpdateService.class));
                                }
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }else {//版本不需要更新
                    String verName = Config.getVerName(getActivity());
                    StringBuffer sb = new StringBuffer();
                    sb.append("当前版本:");
                    sb.append(verName.replace("ver:", "").replace("build", "日期"));
                    sb.append(",\n已是最新版本，无需更新!");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(sb.toString());
                    builder.setTitle("软件更新");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });
        checkTask.execute();
    }
}
