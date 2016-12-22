package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.view.SectorView;

/**
 * Created by Administrator on 2016/12/20.
 */
public class SoftWareActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar pb_storage;
    private TextView tv_storage;
    private TextView all,system,user;
    private SectorView sv_storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        initActionBar(true,false,"软件信息",this);
        initUI();
        initData();
    }

    private void initData() {
        //获取外部存储文件
        File file = Environment.getExternalStorageDirectory();
//        //获取外部存储的根路径
//        String path = Environment.getExternalStorageDirectory().getPath();
        //获取总空间
        long total = file.getTotalSpace();
        //获取可用空间
        long free = file.getFreeSpace();
        //计算
        int progress = (int) ((free*100)/total);
        String f_total = Formatter.formatFileSize(this,total);
        String f_free = Formatter.formatFileSize(this,free);
        //计算角度
        int angle = (int)((total-free)*360f/total);
        //设置进度
        pb_storage.setProgress(progress);
        tv_storage.setText("可用空间:"+f_free+"/"+f_total);
        sv_storage.drawAnim(0,angle+1);
    }

    private void initUI() {
        pb_storage = (ProgressBar) findViewById(R.id.activity_software_pb);
        tv_storage = (TextView) findViewById(R.id.activity_software_storag_tv);
        all = (TextView) findViewById(R.id.activity_soft_all_tv);
        system = (TextView) findViewById(R.id.activity_soft_sys_tv);
        user = (TextView) findViewById(R.id.activity_soft_user_tv);
        sv_storage = (SectorView) findViewById(R.id.includ_software_sv);
        //设置监听事件
        all.setOnClickListener(this);
        system.setOnClickListener(this);
        user.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Bundle bundle = null;
        switch (v.getId()){
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.activity_soft_all_tv:
                bundle = new Bundle();
                bundle.putString("softType","all");
                startActivity(SoftManager.class,bundle);
                break;
            case R.id.activity_soft_sys_tv:
                bundle = new Bundle();
                bundle.putString("softType","sys");
                startActivity(SoftManager.class,bundle);
                break;
            case R.id.activity_soft_user_tv:
                bundle = new Bundle();
                bundle.putString("softType","user");
                startActivity(SoftManager.class,bundle);
                break;
        }
    }
}
