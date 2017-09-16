package com.noname.tenminute.Fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by PJC on 2017-07-30.
 */

public class BaseFragment extends Fragment {

    protected void showToast(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}
