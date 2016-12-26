package edu.zhuoxin.feicui.phonesafe.adapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.ProcessInfo;

/**
 * Created by Administrator on 2016/12/26.
 */
public class ProcessAdapter extends BaseBaseAdapter<ProcessInfo>{
    public ProcessAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProcessHolder holder = null;
        if (convertView == null){
            convertView  = inflater.inflate(R.layout.adapter_process_item,null);
            holder = new ProcessHolder();
            holder.isCheck = (CheckBox) convertView.findViewById(R.id.adapter_process_item_ischeck_cb);
            holder.icon = (ImageView) convertView.findViewById(R.id.adapter_process_item_icon_iv);
            holder.label = (TextView) convertView.findViewById(R.id.adapter_process_item_label_tv);
            holder.packageName = (TextView) convertView.findViewById(R.id.adapter_process_item_pname_tv);
            convertView.setTag(holder);
        }else {
            holder = (ProcessHolder) convertView.getTag();
        }

        //给控件赋值
        final ProcessInfo info = getItem(position);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setCheck(!info.isCheck());
            }
        };

        holder.isCheck.setChecked(info.isCheck());
        holder.isCheck.setOnClickListener(listener);
        //设置包名
        holder.packageName.setText(info.getInfo().processName);
        try {
            //设置图标
            holder.icon.setImageDrawable(context.getPackageManager().getApplicationIcon(info.getInfo().processName));
            //设置应用名
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(info.getInfo().processName,PackageManager.MATCH_UNINSTALLED_PACKAGES);
            holder.label.setText(context.getPackageManager().getApplicationLabel(appInfo));
        } catch (PackageManager.NameNotFoundException e) {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
            holder.label.setText("未知应用");
        }
        return convertView;
    }
    class ProcessHolder{
        CheckBox isCheck;
        ImageView icon;
        TextView label;
        TextView packageName;
    }

}
