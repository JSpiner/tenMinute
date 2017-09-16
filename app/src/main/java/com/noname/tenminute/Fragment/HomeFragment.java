package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tenminute.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PJC on 2017-07-30.
 */

public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.imv_call)
    public void onCallButton() {

    }

    @OnClick(R.id.btn_set_if)
    public void onConditionButton() {

    }
}
