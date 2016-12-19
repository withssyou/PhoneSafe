package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.TelNumber;

/**
 * Created by Administrator on 2016/12/16.
 */

public class TelNumberAdapter extends BaseBaseAdapter<TelNumber> {
    public TelNumberAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_telnumber_item,null);
            holder = new MyHolder();
            holder.name = (TextView) convertView.findViewById(R.id.adapter_telnumber_item_name_tv);
            holder.number = (TextView) convertView.findViewById(R.id.adapter_telnumber_item_number_tv);

            convertView.setTag(holder);
        }else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getName());
        holder.number.setText(data.get(position).getNumber());

        return convertView;
    }
    class MyHolder{
        TextView name;
        TextView number;
    }
}
