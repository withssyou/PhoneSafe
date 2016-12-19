package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.MemeryManager;

/**
 * Created by Administrator on 2016/12/16.
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar battery; //电池pb
    private TextView battery_info;//电池tv
    private BroadcastReceiver receiver;
    private int temperature;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initData();
        initUI();
        initBattery();

    }
    /**初始化电池电量*/
    private void initBattery() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                    //获取电池当前的电量
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                    //获取电池总电量
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,100);
                    //获取电池的温度
                    temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
                    Toast.makeText(context,temperature+"",Toast.LENGTH_LONG).show();
                    //计算电量的百分比
                    int progress = (level*100)/scale;
                    //设置当前电量
                    battery.setProgress(progress);
                    //设置显示电量
                    battery_info.setText(progress+"%");
                }
            }
        };
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,filter);
    }

    private void initUI() {
        battery = (ProgressBar) findViewById(R.id.activity_phone_battery_pb);
        battery_info = (TextView) findViewById(R.id.activity_phone_battery_show_tv);
        battery.setOnClickListener(this);

    }

    //获取数据
    private void initData() {
        String name = Build.BOARD;
        String version = Build.VERSION.RELEASE;
        String base_band = Build.VERSION.INCREMENTAL;
        String cpu  = getCPUtype();
        int num = getCPUnumber();
        boolean isRoot = checkIsRoot();
        String totalMem = MemeryManager.getFamaterMem(this,MemeryManager.getAllMemeray(this));
        String avialMem = MemeryManager.getFamaterMem(this,MemeryManager.getAvailMemary(this));
        String screenRes = getScreenResolution();
        String cameraRes = getCameraResolution();

    }

    private boolean checkIsRoot() {
        String path1 = "/system/bin/su";
        String path2 = "/system/xbin/su";
        boolean isRoot = false;
        if (new File(path1).exists() || new File(path2).exists()){
            isRoot = true;
        }else {
            isRoot = false;
        }
        return  isRoot;

    }

    public String getCPUtype() {
        String path = "/proc/cpuinfo";
        String type = "";
        BufferedReader br = null;
        try {
            //开流读取
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while ((type = br.readLine()) != null){
                if (type.contains("model name")){
                    return type.split(":")[1];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public int getCPUnumber() {
        String path = "/proc/cpuinfo";
        String str = "";
        int count = 0 ;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while ((str = br.readLine()) != null){
                if (str.contains("processor")){
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }
    /**获取屏幕分辨率*/
    public String getScreenResolution(){
        //获取窗口的管理器对象
        WindowManager wm = getWindowManager();
        //获的屏幕对象
        Display display = wm.getDefaultDisplay();
        //创建Point对象，用来接收屏幕的大小信息
        Point point = new Point();
        //获取大小信息，并且保存在point对象中
        display.getSize(point);
        return  point.y+" * "+point.x;
    }
    /**获取相机的最大分辨率*/
    public String getCameraResolution(){
        String  maxSize = "";
        //打开相机
        Camera camera = Camera.open();
        //获取相机参数
        Camera.Parameters parameters = camera.getParameters();
        //返回相机所支持的所有的分辨率
        List<Camera.Size>  sizes = parameters.getSupportedPictureSizes();
        //获取最大分辨率
        Camera.Size size = null;
        for (Camera.Size s : sizes){
            if (size == null){
                size = s;
            }else if (size.width * s.height < s.width * s.height){
                size = s;
            }
        }
        //释放相机
        camera.release();
//        maxSize = size.height + "*"+size.width;
        return  size.height + "*"+size.width;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,temperature+"",Toast.LENGTH_LONG).show();
    }
}
