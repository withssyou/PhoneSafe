package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.entity.SoftWareInfo;

/**
 * Created by Administrator on 2016/12/20.
 *  软件管理
 */
public class SoftManager extends BaseActivity implements View.OnClickListener {
    private String softType;
    private ListView lv ;
    private ProgressBar pb;
    private List<SoftWareInfo> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softmanager);
        initActionBar(true,false,"软件管理",this);
        //取值
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        softType = bundle.getString("softType");
        initUI();
        asyncLoadData();
    }
    /**异步加载数据*/
    private void asyncLoadData() {
        lv.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        data.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取应用程序信息
                //获取设备上的所有的应用详情
            List<ApplicationInfo> applicationinfo = getPackageManager().getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            for(ApplicationInfo info :applicationinfo){
                //获取应用名
                String label = (String) getPackageManager().getApplicationLabel(info);
                //判断是否为系统应用
                boolean isSystem = true;
                if ((info.flags & ApplicationInfo.FLAG_SYSTEM ) > 0){
                    isSystem = true;
                }else {
                    isSystem = false;
                }
                //包名
                String packageName = info.packageName;
                //版本号
                String version = "";
                try {
                    version = getPackageManager().getPackageInfo(packageName,0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                //图标
                Drawable icon = info.loadIcon(getPackageManager());

                SoftWareInfo soft = new SoftWareInfo(label,icon,packageName,version,false,true);
                //添加到集合
                if (softType.equals("sys")){ //存系统应用
                    if (isSystem){
                        data.add(soft);
                    }
                }else  if (softType .equals("user")){
                    if (!isSystem){
                        data.add(soft);
                    }
                }else {
                    data.add(soft);
                }
                for(SoftWareInfo i : data){
                    System.out.println(i);
                }
            }
            //子线程不能更新UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //写主线程执行的操作
                    pb.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    //添加适配器

                }
            });
            }
        }).start();

    }

    private void initUI() {
        lv = (ListView) findViewById(R.id.activity_softmanager_lv);
        pb = (ProgressBar) findViewById(R.id.activity_softmanager_pb);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionbar_back_iv:
                finish();
                break;
        }
    }
}
