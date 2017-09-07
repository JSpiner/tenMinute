package com.noname.tenminute.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noname.tenminute.R;

import java.util.ArrayList;

/**
 * Created by PJC on 2017-09-07.
 */

public class DialogSelectAdapter extends BaseAdapter {

    public ArrayList<String> mList;
    private Context context;

    public DialogSelectAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void addItem(String item) {
        mList.add(item);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_select, viewGroup, false);
        }
        ((TextView) convertView.findViewById(R.id.tv_title)).setText(mList.get(i));
        return convertView;
    }
}
