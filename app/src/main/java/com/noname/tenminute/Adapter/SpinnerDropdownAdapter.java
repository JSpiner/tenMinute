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

public class SpinnerDropdownAdapter extends BaseAdapter {

    private ArrayList<String> mList;
    private Context context;

    public SpinnerDropdownAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void setArray(ArrayList<String> array) {
        mList = array;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_default_normal, viewGroup, false);
        }
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(mList.get(i));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_default_dropdown, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(mList.get(position));

        return convertView;
    }
}
