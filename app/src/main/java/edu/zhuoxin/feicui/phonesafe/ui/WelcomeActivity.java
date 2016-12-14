package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/14.
 */

public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener{
    private ImageView logo;
    private Animation anim;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initActionBar(false,false,"手机管家",null);
        logo = (ImageView) findViewById(R.id.activity_welcome_iv);
        anim = AnimationUtils.loadAnimation(this,R.anim.animation_welcome_alpha);
        //设置动画停留在结束位置
        anim.setFillAfter(true);
        //设置动画的监听事件
        anim.setAnimationListener(this);
        logo.startAnimation(anim);

    }
    /**动画开始时触发*/
    @Override
    public void onAnimationStart(Animation animation) {

    }
    /**动画结束时触发*/
    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束时，启动页面
        startActivity(HomeActivity.class);
        finish();
    }
    /**动画重复执行时触发*/
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
