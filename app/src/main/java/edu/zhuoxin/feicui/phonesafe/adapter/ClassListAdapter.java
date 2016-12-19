package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.ClassListInfo;

/**
 * Created by Administrator on 2016/12/15.
 */
public class ClassListAdapter extends BaseBaseAdapter<ClassListInfo> {

    public ClassListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_classlist_item,null);
            holder = new Holder();
            holder.tv = (TextView) convertView.findViewById(R.id.adapter_classlist_item_class_tv);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv.setText(data.get(position).getName());

        return convertView;
    }
    class Holder{
        TextView tv;
    }
}
