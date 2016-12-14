package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/14.
 */
public class SettingActivity extends BaseActivity{
    private ToggleButton tb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tb = (ToggleButton) findViewById(R.id.activity_setting_start_tb);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                 if (b){
                     tb.setBackgroundResource(R.drawable.togglebutton_on);
                 }else {
                     tb.setBackgroundResource(R.drawable.togglebutton_off);
                 }
             }
         });


    }

}
