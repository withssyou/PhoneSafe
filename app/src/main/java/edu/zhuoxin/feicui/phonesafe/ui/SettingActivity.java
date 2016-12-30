package edu.zhuoxin.feicui.phonesafe.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/14.
 * <p>
 * Step 1. 获得NotificationManager对象： NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 * Step 2. 创建一个通知栏的Builder构造类： Notification.Builder mBuilder = new Notification.Builder(this);
 * Step 3. 对Builder进行相关的设置，比如标题，内容，图标，动作等！
 * Step 4.调用Builder的build()方法为notification赋值
 * Step 5.调用NotificationManager的notify()方法发送通知！
 * PS:另外我们还可以调用NotificationManager的cancel()方法取消通知
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ToggleButton tb;
    private NotificationManager manager;
    private Notification.Builder builder;
    private Notification notification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initActionBar(true, false, "设置", this);


        tb = (ToggleButton) findViewById(R.id.activity_setting_start_tb);


//
//        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//             @Override
//             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                 if (b){
//                     tb.setBackgroundResource(R.drawable.togglebutton_on);
//                 }else {
//                     tb.setBackgroundResource(R.drawable.togglebutton_off);
//                 }
//             }
//         });
        tb.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.activity_setting_start_tb:
                //打开开关，发送通知
                if (isChecked) {
                    tb.setBackgroundResource(R.drawable.togglebutton_on);
                    //获得NotificationManager对象
                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    //创建一个通知栏的Builder构造类
                    builder = new Notification.Builder(this);
                    Intent it = new Intent(SettingActivity.this, HomeActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(SettingActivity.this, 0, it, 0);
                    builder.setContentTitle("明天不上班")   //设置标题
                            .setContentText("但是我要敲代码")//内容
                            .setSubText("敲完代码就去约妹子")//子内容

                            .setTicker("八戒发来的消息")     //设置消息的显示信息

                            .setWhen(System.currentTimeMillis())//设置系统当前时间
                            .setSmallIcon(R.drawable.ic_contact_mail_black_24dp)//设置小图标
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mengyangge))
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                            .setAutoCancel(true) //设置自动取消
                            .setContentIntent(pendingIntent);   //设置点击通知之后要做的事
                    //初始化通知对象
                    notification = builder.build();
                    //发送通知  参数一：通知的id，当取消通知时，通过id来找到要取消的通知
                    manager.notify(0x10,notification);
                }
                //关闭开关，取消通知
                else{
                    //根据id 取消通知
                    manager.cancel(0x10);
                    tb.setBackgroundResource(R.drawable.togglebutton_off);
                }
                break;
        }
    }
}
