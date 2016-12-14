package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.GuidePagerAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/13.
 *  欢迎页面
 *      数据源：
 *          List<View>
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private ViewPager vp;
    private ImageView circle_01,circle_02,circle_03;
    private Button skip;
    private List<View> data = new ArrayList<>();
    private LayoutInflater inflater ;
    /**判断是否第一次运行*/
    private boolean isFirstCome; //flase 表示不是第一次来
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstCome = getSharedPreferences("share",MODE_PRIVATE).getBoolean("isFirstCome",false);
        if (!isFirstCome){
            setContentView(R.layout.acitivity_guide);
            //初始化控件
            initUI();
            //添加viewpager的数据源
            initData();
            //创建适配器
            GuidePagerAdapter adapter = new GuidePagerAdapter(data);
            //添加适配器
            vp.setAdapter(adapter);
            //更改文件中的状态
            SharedPreferences.Editor edit = getSharedPreferences("share",MODE_APPEND).edit();
            edit.putBoolean("isFirstCome",true);
            edit.commit();
            //viewpager设置监听事件
            vp.addOnPageChangeListener(this);
        }else {
            //跳转到欢迎页
            startActivity(WelcomeActivity.class);
            finish();
        }
    }
    /**初始化控件*/
    private void initUI() {
        vp = (ViewPager) findViewById(R.id.activity_guide_vp);
        skip = (Button) findViewById(R.id.activity_guide_skip_tv);
        circle_01 = (ImageView) findViewById(R.id.activity_guide_circle_iv1);
        circle_02 = (ImageView) findViewById(R.id.activity_guide_circle_iv2);
        circle_03 = (ImageView) findViewById(R.id.activity_guide_circle_iv3);
        skip.setOnClickListener(this);
    }
    /**
     * 初始化数据源
     */
    private void initData() {
        inflater = LayoutInflater.from(this);
        data.add(inflater.inflate(R.layout.guide_item_01,null));
        data.add(inflater.inflate(R.layout.guide_item_02,null));
        data.add(inflater.inflate(R.layout.guide_item_03,null));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_guide_skip_tv:
                startActivity(HomeActivity.class);
                finish();
                break;
        }
    }
    /**页面在滑动时会调用的方法*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.i("Tag","onPageScrolled==============================onPageScrolled");
    }
    /**页面最终停止时会调用的方法*/
    @Override
    public void onPageSelected(int position) {
//        Log.i("Tag","onPageSelected==============================onPageSelected");
            if(position == 0){
                circle_01.setImageResource(R.drawable.shape_guide_circle_red);
                circle_02.setImageResource(R.drawable.shape_guide_circle_gray);
                circle_03.setImageResource(R.drawable.shape_guide_circle_gray);
                skip.setVisibility(View.INVISIBLE);
            }else if (position == 1) {
                circle_01.setImageResource(R.drawable.shape_guide_circle_gray);
                circle_02.setImageResource(R.drawable.shape_guide_circle_red);
                circle_03.setImageResource(R.drawable.shape_guide_circle_gray);
                skip.setVisibility(View.INVISIBLE);
            }else if (position == 2){
                circle_01.setImageResource(R.drawable.shape_guide_circle_gray);
                circle_02.setImageResource(R.drawable.shape_guide_circle_gray);
                circle_03.setImageResource(R.drawable.shape_guide_circle_red);
                skip.setVisibility(View.VISIBLE);
            }
    }
    /**当页面状态发生变化时*/
    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.i("Tag","onPageScrollStateChanged==============================onPageScrollStateChanged");
    }
}
