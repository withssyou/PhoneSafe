package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.TelNumberAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.db.DBManager;
import edu.zhuoxin.feicui.phonesafe.entity.TelNumber;

/**
 * Created by Administrator on 2016/12/15.
 */
public class TelNumberActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<TelNumber> data;
    private String name;
    private int idx;
    private ListView lv;
    private TelNumberAdapter adapter;
    private AlertDialog.Builder builder; //创建一个对话框
    private AlertDialog alert;           //控制对话框
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telnumber_d);
        init();
//        Log.i("Tag","TelNumberActivity"+name+idx);
        initActionBar(true,false,name,this);

    }
    //初始化操作
    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        name = bundle.getString("name");
        idx  = bundle.getInt("idx");
        inflater = LayoutInflater.from(this);
        lv = (ListView) findViewById(R.id.activity_telnumber_d_number_lv);
        //数据源
        data = DBManager.readTelNumber(this,idx);
        //适配器
        adapter = new TelNumberAdapter(this);
        adapter.setData(data);
        lv.setAdapter(adapter);

        //给ListView添加监听事件
        lv.setOnItemClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_telnumber_cancle_tv :
                alert.dismiss();
                break;
            case R.id.actionbar_back_iv:
                    finish();
                    break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击弹出对话框
        builder = new AlertDialog.Builder(this);
        View dialogview = inflater.inflate(R.layout.dialog_telnumber,null);
        //给弹出对话框设置视图
        builder.setView(dialogview);
        alert = builder.create();
        alert.show();
        //找控件
        TextView message = (TextView) dialogview.findViewById(R.id.dialog_telnumber_message_tv);
        TextView cancle = (TextView) dialogview.findViewById(R.id.dialog_telnumber_cancle_tv);
        TextView confirm = (TextView) dialogview.findViewById(R.id.dialog_telnumber_confirm_tv);
        //给控件设置添加监听事件
        final String number = adapter.getData().get((int)id).getNumber();
        message.setText("您是否确认要拨打"+number);
        cancle.setOnClickListener(this);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打电话
                Toast.makeText(TelNumberActivity.this,"拨打电话"+number,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
                alert.dismiss();
            }
        });

    }
}
