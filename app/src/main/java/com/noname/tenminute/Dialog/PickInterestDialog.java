package com.noname.tenminute.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tenminute.R;

/**
 * Created by PJC on 2017-08-14.
 */

public class PickInterestDialog extends BaseDialog {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_pick_interest, container, false);
        return rootView;
    }
}
