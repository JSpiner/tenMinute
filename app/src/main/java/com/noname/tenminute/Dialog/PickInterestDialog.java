package com.noname.tenminute.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.HttpSerivce.Define;
import com.noname.tenminute.Model.LikeThingModel;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by PJC on 2017-08-14.
 */

public class PickInterestDialog extends BaseDialog {

    @BindView(R.id.flexbox)
    FlexboxLayout flexboxLayout;

    PickInterestInterface pickBodyTypeInterface;
    SignupActivity.RegisterParams registerParams;

    public interface PickInterestInterface {
        void onFinish();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_pick_interest, container, false);
        ButterKnife.bind(this, rootView);

        registerParams = ((SignupActivity)getActivity()).getRegisterObject();

        getInterest();

        return rootView;
    }

    public void setInterface(PickInterestInterface pickBodyTypeInterface) {
        this.pickBodyTypeInterface = pickBodyTypeInterface;
    }

    @OnClick(R.id.btn_ok)
    void ok() {
        registerParams.selectInterest.clear();
        for(int i=0; i<flexboxLayout.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) flexboxLayout.getChildAt(i);
            registerParams.selectInterest.add(Integer.parseInt((String)checkBox.getTag()));
        }
        dismiss();
        pickBodyTypeInterface.onFinish();
    }

    private void getInterest() {
        HttpUtil.api(Define.class).getLike(new Callback<LikeThingModel>() {
            @Override
            public void success(LikeThingModel likeThingModel, Response response) {
                if(likeThingModel.code == 1) {
                    for(int i=0; i<likeThingModel.dataList.size(); i++) {
                        CheckBox flexChild = new CheckBox(getActivity());
                        flexChild.setText(likeThingModel.dataList.get(i).name);
                        flexChild.setBackgroundResource(R.drawable.radio_button_sex_selector);
                        flexChild.setButtonDrawable(null);
                        flexChild.setTag(likeThingModel.dataList.get(i).id);
                        flexChild.setPadding(50, 25, 50, 25);

                        /*
                        for(int j=0; i<registerParams.selectInterest.size(); i++) {
                            if(likeThingModel.dataList.get(i).id.equals(String.valueOf(registerParams.selectInterest.get(j)))) {
                                //flexChild.setChecked(true);
                            }
                        } */
                        flexboxLayout.addView(flexChild);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
