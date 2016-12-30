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
    private TextView tv, tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    private ProgressBar pb1, pb2, pb3, pb4, pb5, pb6, pb7;
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
    private FileManager fileManager = FileManager.getFileManager();
    /**记录是否检索完毕*/
    private boolean showFlag = false;
    private SearchFileCallback listener = new SearchFileCallback() {
        @Override
        public void searching(final long fileSize) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(Formatter.formatFileSize(FileActivity.this, fileSize));
                }
            });
        }

        @Override
        public void ending(boolean isEnd) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pb1.setVisibility(View.GONE);
                    iv1.setVisibility(View.VISIBLE);
                    pb2.setVisibility(View.GONE);
                    iv2.setVisibility(View.VISIBLE);
                    pb3.setVisibility(View.GONE);
                    iv3.setVisibility(View.VISIBLE);
                    pb4.setVisibility(View.GONE);
                    iv4.setVisibility(View.VISIBLE);
                    pb5.setVisibility(View.GONE);
                    iv5.setVisibility(View.VISIBLE);
                    pb6.setVisibility(View.GONE);
                    iv6.setVisibility(View.VISIBLE);
                    pb7.setVisibility(View.GONE);
                    iv7.setVisibility(View.VISIBLE);
                    tv1.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getAllFileSize()));
                    tv2.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getDocFileSize()));
                    tv3.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getVideofFileSize()));
                    tv4.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getAudioFileSize()));
                    tv5.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getImageFileSize()));
                    tv6.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getZipFileSize()));
                    tv7.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getApkFileSize()));
                    //更改标记，使之退回时更改状态
                    showFlag = true;
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

    @Override
    protected void onStart() {
        super.onStart();
        tv.setText(Formatter.formatFileSize(this, fileManager.getAllFileSize()));
        if (showFlag){
            tv1.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getAllFileSize()));
            tv2.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getDocFileSize()));
            tv3.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getVideofFileSize()));
            tv4.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getAudioFileSize()));
            tv5.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getImageFileSize()));
            tv6.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getZipFileSize()));
            tv6.setText(Formatter.formatFileSize(FileActivity.this,fileManager.getApkFileSize()));
        }
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
        tv1 = (TextView) findViewById(R.id.activity_file_type_allsize_tv);
        tv7 = (TextView) findViewById(R.id.activity_file_type_apksize_tv);
        tv4 = (TextView) findViewById(R.id.activity_file_type_audiosize_tv);
        tv2 = (TextView) findViewById(R.id.activity_file_type_txtsize_tv);
        tv3 = (TextView) findViewById(R.id.activity_file_type_videosize_tv);
        tv5 = (TextView) findViewById(R.id.activity_file_type_imagesize_tv);
        tv6 = (TextView) findViewById(R.id.activity_file_type_zipsize_tv);

        pb1 = (ProgressBar) findViewById(R.id.activity_file_pb1);
        pb2 = (ProgressBar) findViewById(R.id.activity_file_pb2);
        pb3 = (ProgressBar) findViewById(R.id.activity_file_pb3);
        pb4 = (ProgressBar) findViewById(R.id.activity_file_pb4);
        pb5 = (ProgressBar) findViewById(R.id.activity_file_pb5);
        pb6 = (ProgressBar) findViewById(R.id.activity_file_pb6);
        pb7 = (ProgressBar) findViewById(R.id.activity_file_pb7);

        iv1 = (ImageView) findViewById(R.id.activity_file_iv1);
        iv2 = (ImageView) findViewById(R.id.activity_file_iv2);
        iv3 = (ImageView) findViewById(R.id.activity_file_iv3);
        iv4 = (ImageView) findViewById(R.id.activity_file_iv4);
        iv5 = (ImageView) findViewById(R.id.activity_file_iv5);
        iv6 = (ImageView) findViewById(R.id.activity_file_iv6);
        iv7 = (ImageView) findViewById(R.id.activity_file_iv7);

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        iv7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = null;
        switch (v.getId()) {
            case R.id.activity_file_iv1:
                bundle = new Bundle();
                bundle.putString("type", "all");
                break;
            case R.id.activity_file_iv2:
                bundle = new Bundle();
                bundle.putString("type", "txt");
                break;
            case R.id.activity_file_iv3:
                bundle = new Bundle();
                bundle.putString("type", "video");
                break;
            case R.id.activity_file_iv4:
                bundle = new Bundle();
                bundle.putString("type", "audio");
                break;
            case R.id.activity_file_iv5:
                bundle = new Bundle();
                bundle.putString("type", "image");
                break;
            case R.id.activity_file_iv6:
                bundle = new Bundle();
                bundle.putString("type", "zip");
                break;
            case R.id.activity_file_iv7:
                bundle = new Bundle();
                bundle.putString("type", "apk");
                break;
        }
        startActivity(FileDetailsActivity.class, bundle);
    }
}
