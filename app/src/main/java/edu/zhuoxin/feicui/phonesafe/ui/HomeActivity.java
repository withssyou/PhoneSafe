package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.view.CircleView;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 电话助手
     */
    private TextView telnumber;
    private TextView software;
    private TextView clean;
    private TextView rocket;
    private TextView phone;
    private TextView filemgr;
    private CircleView cv_circle;
    private ImageView iv_circle;
    private TextView tv_circle;
    private int count;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0x10){
//                int num = (int) msg.obj;
//                tv_circle.setText(num+"%");
//            }
//
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化actionBar
        initActionBar(false, true, "手机管家", this);
        initUI();
    }

    private void initUI() {
        telnumber = (TextView) findViewById(R.id.activity_home_telnumber_tv);
        software = (TextView) findViewById(R.id.activity_home_software_tv);
        clean = (TextView) findViewById(R.id.activity_home_clean_tv);
        rocket = (TextView) findViewById(R.id.activity_home_rocket_tv);
        phone = (TextView) findViewById(R.id.activity_home_phone_tv);
        filemgr = (TextView) findViewById(R.id.activity_home_filemgr_tv);

        tv_circle = (TextView) findViewById(R.id.activity_home_circle_tv);
        cv_circle = (CircleView) findViewById(R.id.activity_home_circle_cv);
        iv_circle = (ImageView) findViewById(R.id.activity_home_circle_iv);
        //设置假数据
//        tv_circle.setText("40%");

        final Timer timer  = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (count <=49){
                    count += 1;
//                    Message message = Message.obtain();
//                    message.what = 0x10;
//                    message.obj = count;
//                    handler.sendMessageDelayed(message,500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_circle.setText(count+"%");
                        }
                    });
                }else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task,400,20);
        cv_circle.setAngle(180);
        iv_circle.setOnClickListener(this);
        //设置监听事件
        telnumber.setOnClickListener(this);
        software.setOnClickListener(this);
        clean.setOnClickListener(this);
        rocket.setOnClickListener(this);
        phone.setOnClickListener(this);
        filemgr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_home_telnumber_tv:
                startActivity(TelClassListActivity.class);
                break;
            case R.id.activity_home_software_tv:
                startActivity(SoftWareActivity.class);
                break;
            case R.id.activity_home_clean_tv:
                startActivity(CleanActivity.class);
                break;
            case R.id.activity_home_rocket_tv:
                break;
            case R.id.activity_home_phone_tv:
                startActivity(PhoneActivity.class);
                break;
            case R.id.activity_home_filemgr_tv:
                break;
            case R.id.actionbar_menu_iv:
                startActivity(SettingActivity.class);
                break;
            case R.id.activity_home_circle_iv:
                cv_circle.setAngle(180);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(receiver);
    }
}