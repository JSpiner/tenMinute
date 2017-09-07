package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.noname.tenminute.Adapter.SettingAdapter;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.ViewUtil;

import org.angmarch.views.NiceSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PJC on 2017-08-08.
 */

public class SettingFragment extends BaseFragment {

    @BindView(R.id.imv_profile)
    ImageView imvProfile;

    @BindView(R.id.listview)
    ListView listView;

    SettingAdapter settingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, rootView);

        Glide.with(this).load(R.drawable.ex_user_profile2).apply(RequestOptions.circleCropTransform()).into(imvProfile);

        settingAdapter = new SettingAdapter(getActivity());
        listView.setAdapter(settingAdapter);
        ViewUtil.setListViewHeightBasedOnChildren(listView);

        return rootView;
    }
}
