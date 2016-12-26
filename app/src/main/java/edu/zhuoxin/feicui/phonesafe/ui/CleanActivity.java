package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.ClearAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.FileManager;
import edu.zhuoxin.feicui.phonesafe.biz.MemeryManager;
import edu.zhuoxin.feicui.phonesafe.db.DBManager;
import edu.zhuoxin.feicui.phonesafe.entity.AppRubish;

/**
 * Created by Administrator on 2016/12/23.
 *  Handler：发消息
 *  Handler  Message   MessageQueue  Looper
 */
public class CleanActivity extends BaseActivity implements View.OnClickListener {

//    List<RubbishInfo> data = new ArrayList<>();
    private ListView lv;
    private ClearAdapter adapter;
    private TextView allFileSize;
    private ProgressBar pbar;
    private LinearLayout ll;
    private long sizeAll;
    private Button delete;
    private Handler handler  = new Handler(){
        //处理子线程发送的消息
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null){
                //文件检索过程中，处理的消息
                if (msg.what == 0x10 ){
                    sizeAll += msg.arg1;
                    allFileSize.setText(Formatter.formatFileSize(CleanActivity.this,sizeAll));
                }
                //文件检索完毕后处理的消息
                if (msg.what == 0x12){
                    pbar.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
                //文件删除完毕后处理的消息
                if (msg.what == 0x14){
                    sizeAll -= msg.arg1;
                    allFileSize.setText(Formatter.formatFileSize(CleanActivity.this,sizeAll));
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);

        initActionBar(true,false,"垃圾清理",this);
        initUI();
        //添加适配器数据
        setAdapterData();
    }
    /**添加适配器数据*/
    private void setAdapterData() {
        adapter = new ClearAdapter(this);
        lv.setAdapter(adapter);
        pbar.setVisibility(View.VISIBLE);
        ll.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                asyncLoadData();
            }
        }).start();
    }

    private void initUI() {
        lv = (ListView) findViewById(R.id.activity_clear_lv);
        allFileSize = (TextView) findViewById(R.id.activity_clear_size_tv);
        pbar = (ProgressBar) findViewById(R.id.activity_clear_pb);
        ll = (LinearLayout) findViewById(R.id.activity_clear_ll);
        delete = (Button) findViewById(R.id.activity_clear_btn);
        delete.setOnClickListener(this);
    }

    private void asyncLoadData() {
        //清空数据源数据
        adapter.getData().clear();
        //获取数据库中的查询结果
        List<AppRubish> list = DBManager.readAppRubish(CleanActivity.this);
        String rootPath = MemeryManager.getSDstoragePath();
        File file = null;
        for (AppRubish rubish : list) {
            file = new File(rootPath + rubish.getFilePath());
            if (file.exists()) {
                Drawable icon = null;
                try {
                     icon = getPackageManager().getApplicationIcon(rubish.getPackageName());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String fileSize = Formatter.formatFileSize(this, FileManager.getFileSize(file));
//                for (int i = 0 ; i < 50 ; i++) {
                    //添加数据
                    adapter.getData().add(new AppRubish(true, icon, fileSize, rubish.getEnglishName(),file));
                    //子线程查找到一个文件，然后给主线程发消息
//                Message msg = new Message();
//                Message msg = Message.obtain();
                    Message msg = handler.obtainMessage();
                    msg.what = 0x10;
                    msg.arg1 = (int) (FileManager.getFileSize(file));
                    handler.sendMessage(msg);
                }
//            }
        }
        //发送空消息
        handler.sendEmptyMessage(0x12);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionbar_back_iv:
                finish();
                break;

            case R.id.activity_clear_btn:
                delete.setClickable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<AppRubish> temp = new ArrayList<AppRubish>();
                        for (AppRubish rubish : adapter.getData()){
                            if (rubish.ischeck()){
                                long size = FileManager.getFileSize(rubish.getFile());
                                //删除垃圾
                                FileManager.deleteFile(rubish.getFile());
                                //将要删除的数据添加到临时集合里面
                                temp.add(rubish);
                                Message msg = handler.obtainMessage();
                                msg.what = 0x14;
                                msg.arg1 = (int)size;
                                handler.sendMessage(msg);
                            }
                        }
                        adapter.getData().removeAll(temp);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                delete.setClickable(true);
                            }
                        });
                    }
                }).start();

                break;
        }

    }
}