package edu.zhuoxin.feicui.phonesafe.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 *
 */

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> data;

    public GuidePagerAdapter(List<View> data) {
        this.data = data;
    }

    /**返回viewPager要显示的页面的数量*/
    @Override
    public int getCount() {
        return data.size();
    }
    /**判断视图是否来自于View对象*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    /**添加下一个即将显示的view*/
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //将数据源中的View对象，添加到容器中（如果显示，如何显示）
        container.addView(data.get(position));
        return data.get(position);
    }
    /**销毁上一个View*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
