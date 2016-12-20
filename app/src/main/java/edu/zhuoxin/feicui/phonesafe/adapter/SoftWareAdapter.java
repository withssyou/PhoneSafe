package edu.zhuoxin.feicui.phonesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import edu.zhuoxin.feicui.phonesafe.R;
import edu.zhuoxin.feicui.phonesafe.base.BaseBaseAdapter;
import edu.zhuoxin.feicui.phonesafe.entity.SoftWareInfo;

/**
 * Created by Administrator on 2016/12/20.
 */

public class SoftWareAdapter extends BaseBaseAdapter<SoftWareInfo> {

    public SoftWareAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SoftHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.adapter_software_item,null);
        }
        return convertView;
    }
    class SoftHolder{

    }
}
