package com.noname.tenminute.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noname.tenminute.R;

import java.util.ArrayList;

/**
 * Created by PJC on 2017-08-08.
 */

public class SettingAdapter extends BaseAdapter {

    private ArrayList<SettingModel> mList;
    public Context mContext;

    public SettingAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mList.add(new SettingModel(R.drawable.setting_icon_1, "상점"));
        mList.add(new SettingModel(R.drawable.setting_icon_2, "이상형 설정"));
        mList.add(new SettingModel(R.drawable.setting_icon_3, "설정"));
        mList.add(new SettingModel(R.drawable.setting_icon_4, "앱 사용법"));
        mList.add(new SettingModel(R.drawable.setting_icon_5, "FAQ"));
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
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_setting, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindObject(mList.get(i));
        return convertView;
    }

    public class ViewHolder {
        ImageView imvImage;
        TextView tvText;

        public ViewHolder(View v) {
            imvImage = (ImageView) v.findViewById(R.id.imv_image);
            tvText = (TextView) v.findViewById(R.id.tv_text);
        }

        public void bindObject(SettingModel model) {
            imvImage.setBackgroundResource(model.resImage);
            tvText.setText(model.settingText);
        }
    }

    public static class SettingModel {
        public int resImage;
        public String settingText;

        public SettingModel(int resImage, String settingText) {
            this.resImage = resImage;
            this.settingText = settingText;
        }
    }
}
