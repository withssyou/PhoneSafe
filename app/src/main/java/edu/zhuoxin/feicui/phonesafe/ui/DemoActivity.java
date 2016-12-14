package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/13.
 */

public class DemoActivity extends BaseActivity{
    private Button btn,show;
    EditText et1,et2;
    TextView tv ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        btn = (Button) findViewById(R.id.demo_cb);
        show = (Button) findViewById(R.id.demo_cb1);
        et1 = (EditText) findViewById(R.id.demo_et1);
        et2 = (EditText) findViewById(R.id.demo_et2);
        tv = (TextView) findViewById(R.id.demo_tv);

        /**
         * 安卓轻量级存储类 ---用户偏好类----sp存储       文件存储
         *  参数一：sp文件的名字
         *  参数二：模式
         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存用户名和密码
                //获取sp对象
                SharedPreferences sp = getSharedPreferences("myshared",MODE_PRIVATE);
                //获取编辑器对象
                SharedPreferences.Editor editor =  sp.edit();
                //存数据
                editor.putString("name",et1.getText().toString());
                editor.putString("pwd",et2.getText().toString());
                //提交
                editor.commit();
            }
        });
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //取出偏好类中的信息，并显示
                    SharedPreferences sp = getSharedPreferences("myshared",MODE_PRIVATE);
                    //取出信息
                    String uname = sp.getString("name","无姓名");
                    String upass = sp.getString("pwd","admin");

//                    tv.setText(uname+"==="+upass);
                    et1.setText(uname);
                    et2.setText(upass);
                }
            });
    }
}
