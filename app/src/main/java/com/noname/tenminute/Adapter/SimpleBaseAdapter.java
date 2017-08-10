package com.noname.tenminute.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by PJC on 2017-08-10.
 */

public class SimpleBaseAdapter<T> extends BaseAdapter {

    public ArrayList<T> mList;
    public Context mContext;
    public int mLayoutId;

    public SimpleBaseAdapter(Context context, int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
        mList = new ArrayList<>();
    }

    public void addItem(T item) {
        mList.add(item);
    }

    public void addArray(ArrayList<T> array) {
        for(int i=0; i<array.size(); i++) {
            mList.add(array.get(i));
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, viewGroup, false);
        }
        return convertView;
    }
}
