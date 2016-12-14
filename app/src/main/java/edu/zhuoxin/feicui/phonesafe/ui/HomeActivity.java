package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener{
    /**电话助手*/
    private TextView telnumber;
    private TextView software;
    private TextView clean;
    private TextView rocket;
    private TextView phone;
    private TextView filemgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化actionBar
        initActionBar(false,true,"手机管家",this);
        initUI();
    }

    private void initUI() {
        telnumber = (TextView) findViewById(R.id.activity_home_telnumber_tv);
        software = (TextView) findViewById(R.id.activity_home_software_tv);
        clean = (TextView) findViewById(R.id.activity_home_clean_tv);
        rocket = (TextView) findViewById(R.id.activity_home_rocket_tv);
        phone = (TextView) findViewById(R.id.activity_home_phone_tv);
        filemgr = (TextView) findViewById(R.id.activity_home_filemgr_tv);
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
        switch (view.getId()){
            case R.id.activity_home_telnumber_tv :
                startActivity(TelNumberActivity.class);
                break;
            case R.id.activity_home_software_tv :
                break;
            case R.id.activity_home_clean_tv :
                break;
            case R.id.activity_home_rocket_tv :
                break;
            case R.id.activity_home_phone_tv :
                break;
            case R.id.activity_home_filemgr_tv :
                break;
            case R.id.actionbar_menu_iv:
                startActivity(SettingActivity.class);
                break;

        }
    }
}
