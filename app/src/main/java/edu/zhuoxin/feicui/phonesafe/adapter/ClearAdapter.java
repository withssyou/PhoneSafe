package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.AppRubish;
import edu.zhuoxin.feicui.phonesafe.entity.FileInfo;
import edu.zhuoxin.feicui.phonesafe.utils.FileTypeUtil;

/**
 * Created by Administrator on 2016/12/23.
 */
public class ClearAdapter extends BaseBaseAdapter<AppRubish>{

    public ClearAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClearHolder holder = null;
        if (convertView == null){
            holder = new ClearHolder();
            convertView = inflater.inflate(R.layout.adapter_clear_item,null);
            holder.isCheck = (CheckBox) convertView.findViewById(R.id.adapter_clear_item_ischeck_cb);
            holder.icon = (ImageView) convertView.findViewById(R.id.adapter_clear_item_icon_iv);
            holder.label = (TextView) convertView.findViewById(R.id.adapter_clear_item_label_tv);
            holder.size = (TextView) convertView.findViewById(R.id.adapter_clear_item_size_tv);
            convertView.setTag(holder);
        }else {
            holder = (ClearHolder) convertView.getTag();
        }

        //给控件赋值
        final AppRubish info = getItem(position);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setIscheck(!info.ischeck());
            }
        };
        holder.size.setText(info.getFileSize());
        holder.label.setText(info.getEnglishName());
        if (info.getIcon() != null) {
            holder.icon.setImageDrawable(info.getIcon());
        }else {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.isCheck.setChecked(info.ischeck());
        holder.isCheck.setOnClickListener(listener);


        return convertView;
    }
    /**将文件转换为图片*/
    private Bitmap getBitmap(FileInfo info){
        Bitmap bitmap = null;
        if (info.getFileType().equals(FileTypeUtil.TYPE_IMAGE)){
            //如果是图片，进行图片压缩（图片的二次采样）
            BitmapFactory.Options options = new BitmapFactory.Options();
            //打开边界处理
            options.inJustDecodeBounds = true;
            //将文件中的图片压缩至options中,并不会真正的返回一个bitmap
            BitmapFactory.decodeFile(info.getFile().getAbsolutePath(),options);
            //获取图片短边的像素
            int scale = options.outWidth > options.outHeight ? options.outHeight : options.outWidth;
            //缩放为原图的三分之一
            options.inSampleSize = scale/60; // = 3;
            //关闭边界操作
            options.inJustDecodeBounds = false;
            //返回一个真正的bitmap
            bitmap = BitmapFactory.decodeFile(info.getFile().getAbsolutePath(),options);
        }else {
            //根据图标名字获取资源id
            int res = context.getResources().getIdentifier(info.getIconName(),"drawable",context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(),res);
        }
        return  bitmap;
    }

    class ClearHolder{
        CheckBox isCheck;
        ImageView icon;
        TextView label;
        TextView size;
    }
}
