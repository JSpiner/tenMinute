package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PJC on 2017-08-16.
 */

public class SignupFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btn_next)
    void btnNext() {
        ((SignupActivity) getActivity()).changeScreen(getArguments().getInt("FNUM")+1);
    }
}
