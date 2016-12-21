package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.SoftWareInfo;
import edu.zhuoxin.feicui.phonesafe.utils.LogUtil;

/**
 * Created by Administrator on 2016/12/20.
 * 空指针异常：
 *      空的对象调用属性或方法
 */

public class SoftWareAdapter extends BaseBaseAdapter<SoftWareInfo> {

    public SoftWareAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SoftHolder holder = null;
        if (convertView == null){
            holder = new SoftHolder();
            convertView = inflater.inflate(R.layout.adapter_software_item,null);
            holder.isCheck = (CheckBox) convertView.findViewById(R.id.adapter_software_item_cb);
            holder.icon = (ImageView) convertView.findViewById(R.id.adapter_software_item_icon_iv);
            holder.label = (TextView) convertView.findViewById(R.id.adapter_software_item_label_tv);
            holder.packageName = (TextView) convertView.findViewById(R.id.adapter_software_item_packageName_tv);
            holder.veision = (TextView) convertView.findViewById(R.id.adapter_software_item_version_tv);

            convertView.setTag(holder);
        }else {
            holder = (SoftHolder) convertView.getTag();
        }
        //给控件赋值
        final SoftWareInfo info = getItem(position);
        //监听事件
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setCheck(!info.isCheck());
            }
        };
//        Log.i("Tag",holder.label+"===="+info);
        LogUtil.LogI(holder.label+"=="+info);
        holder.label.setText(info.getLabel());

        holder.packageName.setText(info.getPackageName());
        if (info.getVersion().length() > 5){
            holder.veision.setText(info.getVersion().substring(0,5));
        }else {
            holder.veision.setText(info.getVersion());
        }
        if (info.getIcon() != null) {
            holder.icon.setImageDrawable(info.getIcon());
        }else {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        //问题：系统应用不允许被删除，所以，系统应用的checkBox不能点击

        if (info.canDelete()){ //如果能被删除，就给holder.ischeck赋值
            holder.isCheck.setChecked(info.isCheck());
            holder.isCheck.setOnClickListener(listener);
        }else {
            //如果不能被删除，那么设置按钮不可点击
            holder.isCheck.setChecked(false);
            holder.isCheck.setClickable(false);
        }
        return convertView;
    }
    class SoftHolder{
        CheckBox isCheck;
        ImageView icon;
        TextView label,packageName,veision;

    }
}
