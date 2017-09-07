package com.noname.tenminute.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noname.tenminute.Activity.AwaitApprovalActivity;
import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.HttpSerivce.User;
import com.noname.tenminute.Model.BaseModel;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.HttpUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by PJC on 2017-08-16.
 */

public class SignupAgreeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup_agree, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btn_next)
    void btnNext() {
        final SignupActivity.RegisterParams registerParams = ((SignupActivity)getActivity()).getRegisterObject();
        HttpUtil.api(User.class).signup_default(
                registerParams.userId,
                registerParams.userPassword,
                true,
                150,
                1,
                "dfdfd",
                "dkfjkdlfjdlkf",
                new Callback<BaseModel>() {
                    @Override
                    public void success(BaseModel baseModel, Response response) {
                        Log.d("message", baseModel.message);
                        if(baseModel.code == 1) {

                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SAVE", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", registerParams.userId);
                            editor.commit();

                            Intent intent = new Intent(getActivity(), AwaitApprovalActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }
}
