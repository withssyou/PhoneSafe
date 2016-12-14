package edu.zhuoxin.feicui.phonesafe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BaseActivity extends AppCompatActivity{
    ActionBar actionBar;
    ImageView back;
    ImageView menu;
    TextView title;
    /**开启一个activity*/
    public void startActivity(Class targetClass){
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
    }
    /**带值跳转页面*/
    public  void startActivity(Class targetClass , Bundle bundle){
        Intent intent = new Intent(this,targetClass);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    /**自定义ActionBar*/
    public  void initActionBar(boolean isBackShow,boolean isMenuShow,String titleName ,View.OnClickListener listener){
        //获取系统的actionBar
         actionBar = getSupportActionBar();
        //设置actionbar的显示操作--显示自定义
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //设置自定义actionBar的布局
        actionBar.setCustomView(R.layout.actionbar);
        //找控件
        back = (ImageView) findViewById(R.id.actionbar_back_iv);
        menu = (ImageView) findViewById(R.id.actionbar_menu_iv);
        title = (TextView) findViewById(R.id.actionbar_title_tv);
        //判断back按钮是否显示
        if (isBackShow){
            back.setVisibility(View.VISIBLE);
        }else {
            back.setVisibility(View.INVISIBLE);
        }
        //判断menu按钮是否显示
        if (isMenuShow){
            menu.setVisibility(View.VISIBLE);
        }else {
            menu.setVisibility(View.INVISIBLE);
        }
        title.setText(titleName);
        //设置监听事件
        back.setOnClickListener(listener);
        menu.setOnClickListener(listener);
    }
}
