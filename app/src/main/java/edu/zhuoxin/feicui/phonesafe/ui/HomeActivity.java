package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.MemeryManager;
import edu.zhuoxin.feicui.phonesafe.biz.ProcessManager;
import edu.zhuoxin.feicui.phonesafe.entity.ProcessInfo;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化actionBar
        initActionBar(false, true, "手机管家", this);
        initUI();
//        initCircle();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initCircle();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

       initCircle();
        iv_circle.setOnClickListener(this);
        //设置监听事件
        telnumber.setOnClickListener(this);
        software.setOnClickListener(this);
        clean.setOnClickListener(this);
        rocket.setOnClickListener(this);
        phone.setOnClickListener(this);
        filemgr.setOnClickListener(this);
    }
    /**初始化圆环数据*/
    private void initCircle() {
        long total = MemeryManager.getAllMemeray(this);
        long free = MemeryManager.getAvailMemary(this);
        //百分比
        final int progress = (int)((total-free)*100f/total);
        //角度
        int angle = (int)((progress/100f)*360);
        final Timer timer  = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (count <= progress){
                    count++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tv_circle.setText(count+"%");
                        }
                    });
                }else {
                    count = 0;
                    timer.cancel();
                }
            }
        };
        timer.schedule(task,400,20);
        cv_circle.setAngle(angle);
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
                startActivity(RocketActivity.class);
                break;
            case R.id.activity_home_phone_tv:
                startActivity(PhoneActivity.class);
                break;
            case R.id.activity_home_filemgr_tv:
                startActivity(FileActivity.class);
                break;
            case R.id.actionbar_menu_iv:
                startActivity(SettingActivity.class);
                break;
            case R.id.activity_home_circle_iv:
                ProcessManager.killRunningProcess(this);
                initCircle();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}