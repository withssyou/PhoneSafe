package edu.zhuoxin.feicui.phonesafe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.api.SearchFileCallback;
import edu.zhuoxin.feicui.phonesafe.base.BaseActivity;
import edu.zhuoxin.feicui.phonesafe.biz.FileManager;

/**
 * Created by Administrator on 2016/12/28.
 */
public class FileActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv;
    private ProgressBar pb;
    private ImageView iv;
    private FileManager fileManager = FileManager.getFileManager();
    private SearchFileCallback listener = new SearchFileCallback() {
        @Override
        public void searching(final long fileSize) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(Formatter.formatFileSize(FileActivity.this,fileSize));
                }
            });
        }

        @Override
        public void ending(boolean isEnd) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pb.setVisibility(View.GONE);
                    iv.setVisibility(View.VISIBLE);
                    iv.setOnClickListener(FileActivity.this);
                }
            });
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
            initUI();
        asyncLoadData();
    }

    private void asyncLoadData() {
        fileManager.setListener(listener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                fileManager.searchFiles();
            }
        }).start();
    }

    private void initUI() {
        tv = (TextView) findViewById(R.id.activity_file_size_tv);
        pb = (ProgressBar) findViewById(R.id.activity_file_pb);
        iv = (ImageView) findViewById(R.id.activity_file_iv);
    }

    @Override
    public void onClick(View v) {

    }
}
