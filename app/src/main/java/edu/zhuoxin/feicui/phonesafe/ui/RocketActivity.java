package edu.zhuoxin.feicui.phonesafe.ui;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.ProcessAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.MemeryManager;
import edu.zhuoxin.feicui.phonesafe.biz.ProcessManager;
import edu.zhuoxin.feicui.phonesafe.entity.ProcessInfo;

/**
 * Created by Administrator on 2016/12/26.
 */
public class RocketActivity extends BaseActivity implements View.OnClickListener {
    //控件
    private TextView tv_brand;
    private TextView tv_version;
    private TextView tv_runspace;
    private ProgressBar pb_runspace;
    private ProgressBar pb_rocket_loading;
    private Button btn_rocket_shift;
    private ListView lv_rocket;
    private ProcessAdapter adapter;
    private List<ProcessInfo> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);
        initUI();
        initData();
    }

    private void initData() {
        tv_brand.setText(Build.BRAND);
        tv_version.setText("Android" + Build.VERSION.RELEASE);
        //获取内存信息
        long total = MemeryManager.getAllMemeray(this);
        long avail = MemeryManager.getAvailMemary(this);
        //计算比例
        final int progress = (int) ((total - avail) * 100 / total);
        pb_runspace.setProgress(progress);
        tv_runspace.setText("剩余运行内存:" + MemeryManager.getFamaterMem(this, avail)
                + "/" + MemeryManager.getFamaterMem(this, total));

        adapter = new ProcessAdapter(this);
        lv_rocket.setAdapter(adapter);

        pb_rocket_loading.setVisibility(View.VISIBLE);
        lv_rocket.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取第三方进程
                List<ActivityManager.RunningAppProcessInfo> list = ProcessManager.getRunningProcess(RocketActivity.this);
                ProcessInfo processInfo = null;
                for (ActivityManager.RunningAppProcessInfo info : list) {
                    try {
                        ApplicationInfo appinfo = getPackageManager().getApplicationInfo(info.processName, PackageManager.GET_META_DATA
                                | PackageManager.GET_SHARED_LIBRARY_FILES | PackageManager.MATCH_UNINSTALLED_PACKAGES);
                        if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                            processInfo = new ProcessInfo(true, 1, info);//1表示用户进程
                        } else {
                            processInfo = new ProcessInfo(false, 2, info);//2表示系统进程
                        }
                    } catch (PackageManager.NameNotFoundException e) {}
                    data.add(processInfo);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_rocket_loading.setVisibility(View.GONE);
                        lv_rocket.setVisibility(View.VISIBLE);
                        adapter.setData(checkAdapterData(data,2));
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        }).start();
    }

    private void initUI() {
        tv_brand = (TextView) findViewById(R.id.tv_brand);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_runspace = (TextView) findViewById(R.id.tv_runspace);
        pb_runspace = (ProgressBar) findViewById(R.id.pb_runspace);
        pb_rocket_loading = (ProgressBar) findViewById(R.id.pb_rocket_loading);
        btn_rocket_shift = (Button) findViewById(R.id.btn_rocket_shift);
        lv_rocket = (ListView) findViewById(R.id.lv_rocket);
        btn_rocket_shift.setOnClickListener(this);
    }
    /**判断显示用户数据还是系统数据*/
    private List<ProcessInfo> checkAdapterData(List<ProcessInfo> data ,int tag){
        List<ProcessInfo> temp = new ArrayList<>();
            for (ProcessInfo info : data){
            if (info.getTag() == tag){
                temp.add(info);
            }
        }
        return temp;
    }
    @Override
    public void onClick(View v) {

    }
}
