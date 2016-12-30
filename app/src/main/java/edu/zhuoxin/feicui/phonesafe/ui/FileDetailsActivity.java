package edu.zhuoxin.feicui.phonesafe.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.adapter.FileDetailsAdapter;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.FileManager;
import edu.zhuoxin.feicui.phonesafe.entity.FileInfo;
import edu.zhuoxin.feicui.phonesafe.utils.FileTypeUtil;

/**
 * Created by Administrator on 2016/12/29.
 */
public class FileDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private List<FileInfo> data;
    private long fileSize;
    private FileManager manager = FileManager.getFileManager();
    private ListView lv;
    private FileDetailsAdapter adapter;
    private TextView size, num;
    private Button delete;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedetails);
        initUI();
        initData();


    }

    private void initUI() {
        lv = (ListView) findViewById(R.id.activity_filedetails_lv);
        size = (TextView) findViewById(R.id.activity_filedetails_size_tv);
        num = (TextView) findViewById(R.id.activity_filedetails_num_tv);
        delete = (Button) findViewById(R.id.activity_filedetails_deleteall_btn);
        lv.setOnItemClickListener(this);
        delete.setOnClickListener(this);

    }

    private void initData() {
        //取出上个页面传递的值
        Bundle bundle = getIntent().getBundleExtra("bundle");
        type = bundle.getString("type");
        switch (type) {
            case "all":
                data = manager.getAllFile();
                fileSize = manager.getAllFileSize();
                break;
            case "apk":
                data = manager.getApkFile();
                fileSize = manager.getApkFileSize();
                break;
            case "audio":
                data = manager.getAudioFile();
                fileSize = manager.getAudioFileSize();
                break;
            case "image":
                data = manager.getImageFile();
                fileSize = manager.getImageFileSize();
                break;
            case "zip":
                data = manager.getApkFile();
                fileSize = manager.getApkFileSize();
                break;
            case "video":
                data = manager.getVideofFile();
                fileSize = manager.getVideofFileSize();
                break;
            case "txt":
                data = manager.getDocFile();
                fileSize = manager.getDocFileSize();
                break;
        }
        num.setText(data.size() + "");
        size.setText(Formatter.formatFileSize(this, fileSize));

        adapter = new FileDetailsAdapter(this);
        adapter.setData(data);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        for (FileInfo info : adapter.getData()) {
            info.setCheck(false);
        }
        super.onDestroy();
    }

    /**
     * listview 的监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击查看文件
        FileInfo info = adapter.getItem((int) id);
        //文件预览 隐式跳转
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);//设置动作
        //文件类型
        intent.setDataAndType(Uri.fromFile(info.getFile()), FileTypeUtil.getMIMEType(info.getFile()));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        List<FileInfo> temp = new ArrayList<>();
        for (FileInfo info : adapter.getData()) {
            if (info.isCheck()) {
                temp.add(info);
                changeData(type, info);
                info.getFile().delete();
            }
        }
        adapter.getData().removeAll(temp);
        adapter.notifyDataSetChanged();
    }

    private void changeData(String type, FileInfo info) {
        switch (type) {
            case "all":
                manager.setAllFileSize(manager.getAllFileSize() - info.getFile().length());
                manager.getAllFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getAllFileSize()));
                num.setText(manager.getAllFile().size()+"");
                break;
            case "apk":
                manager.setApkFileSize(manager.getApkFileSize() - info.getFile().length());
                manager.getApkFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getApkFileSize()));
                num.setText(manager.getApkFile().size()+"");
                break;
            case "audio":
                manager.setAudioFileSize(manager.getAudioFileSize() - info.getFile().length());
                manager.getAudioFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getAudioFileSize()));
                num.setText(manager.getAudioFile().size()+"");
                break;
            case "image":
                manager.setImageFileSize(manager.getImageFileSize() - info.getFile().length());
                manager.getImageFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getImageFileSize()));
                num.setText(manager.getImageFile().size()+"");
                break;
            case "zip":
                manager.setZipFileSize(manager.getZipFileSize()- info.getFile().length());
                manager.getZipFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getZipFileSize()));
                num.setText(manager.getZipFile().size()+"");
                break;
            case "video":
                manager.setVideofFileSize(manager.getVideofFileSize() - info.getFile().length());
                manager.getVideofFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getVideofFileSize()));
                num.setText(manager.getVideofFile().size()+"");
                break;
            case "txt":
                manager.setDocFileSize(manager.getDocFileSize() - info.getFile().length());
                manager.getDocFile().remove(info);
                size.setText(Formatter.formatFileSize(this, manager.getDocFileSize()));
                num.setText(manager.getDocFile().size()+"");
                break;
        }
    }
}
