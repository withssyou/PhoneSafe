package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.FileInfo;
import edu.zhuoxin.feicui.phonesafe.utils.FileTypeUtil;
import edu.zhuoxin.feicui.phonesafe.utils.TimeUtils;

/**
 * Created by Administrator on 2016/12/29.
 */
public class FileDetailsAdapter extends BaseBaseAdapter<FileInfo> {
    public FileDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_filedetails_item, null);
            holder = new FileHolder();
            holder.ischeck = (CheckBox) convertView.findViewById(R.id.adapter_filedetails_item_ischeck_cb);
            holder.icon = (ImageView) convertView.findViewById(R.id.adapter_filedetails_item_icon_iv);
            holder.name = (TextView) convertView.findViewById(R.id.adapter_filedetails_item_name_tv);
            holder.date = (TextView) convertView.findViewById(R.id.adapter_filedetails_item_time_tv);
            holder.size = (TextView) convertView.findViewById(R.id.adapter_fileDetails_item_size_tv);

            convertView.setTag(holder);
        } else {
            holder = (FileHolder) convertView.getTag();
        }
        //给控件赋值
        final FileInfo info = getItem(position);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setCheck(!info.isCheck());
            }
        };
        holder.name.setText(info.getFile().getName());
        holder.date.setText(TimeUtils.fomartTiem(info.getFile().lastModified()));
        holder.size.setText(Formatter.formatFileSize(context, info.getFile().length()));
        holder.ischeck.setChecked(info.isCheck());
        holder.ischeck.setOnClickListener(listener);

        if (info.getIconName() != null) {
            holder.icon.setImageBitmap(getBitmap(info));
        }else {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        return convertView;
    }

    class FileHolder {
        CheckBox ischeck;
        ImageView icon;
        TextView name, date, size;
    }

    /**
     * 将文件转换为图片
     */
    private Bitmap getBitmap(FileInfo info) {
        Bitmap bitmap = null;
        //判断文件类型如果是图像文件，就用图像作为icon，前提是将图像进行压缩
        if (info.getFileType().equals(FileTypeUtil.TYPE_IMAGE)) {
            //如果是图片，进行图片压缩（图片的二次采样）
            BitmapFactory.Options options = new BitmapFactory.Options();
            //打开边界处理
            options.inJustDecodeBounds = true;
            //将文件中的图片压缩至options中,并不会真正的返回一个bitmap
            BitmapFactory.decodeFile(info.getFile().getAbsolutePath(), options);
            //获取图片短边的像素
            int scale = options.outWidth > options.outHeight ? options.outHeight : options.outWidth;
            //缩放为原图的三分之一
            options.inSampleSize = scale / 60; // 如果= 3，则缩放为原来三分之一;
            //关闭边界操作
            options.inJustDecodeBounds = false;
            //返回一个真正的bitmap
            bitmap = BitmapFactory.decodeFile(info.getFile().getAbsolutePath(), options);
        } else {
            //根据图标名字获取资源id
            int res = context.getResources().getIdentifier(info.getIconName(), "drawable", context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        }
        return bitmap;
    }
}
