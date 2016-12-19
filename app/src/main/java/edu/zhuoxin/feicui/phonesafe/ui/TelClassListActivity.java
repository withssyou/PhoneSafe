package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.ClassListAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.db.DBManager;
import edu.zhuoxin.feicui.phonesafe.entity.ClassListInfo;

/**
 * Created by Administrator on 2016/12/14.
 */
public class TelClassListActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<ClassListInfo> list;
    private ListView lv;
    private ClassListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telnumber);
        initActionBar(true,false,"通讯大全",this);
        //初始化UI
        init();
    }
    //初始化操作
    private void init() {
        lv = (ListView) findViewById(R.id.activity_telnumber_classlist_lv);
        list = DBManager.readClassListInfo(this);
        System.out.println(list.size());
        adapter = new ClassListAdapter(this);
        adapter.setData(list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actionbar_back_iv:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundle = new Bundle();
        ClassListInfo info = adapter.getData().get((int)id);
        bundle.putString("name",info.getName());
        bundle.putInt("idx",info.getIdx());
        Log.i("Tag",info.getName()+"========"+info.getIdx());
        startActivity(TelNumberActivity.class,bundle);

    }
}
