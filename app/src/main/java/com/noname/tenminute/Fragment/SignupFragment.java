package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.Adapter.SpinnerDropdownAdapter;
import com.noname.tenminute.Dialog.PickBodyTypeDialog;
import com.noname.tenminute.Dialog.PickInterestDialog;
import com.noname.tenminute.HttpSerivce.Define;
import com.noname.tenminute.HttpSerivce.User;
import com.noname.tenminute.Model.BaseModel;
import com.noname.tenminute.Model.LikeThingModel;
import com.noname.tenminute.Model.RegionModel;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.HttpUtil;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by PJC on 2017-08-16.
 */

public class SignupFragment extends BaseFragment {

    @BindView(R.id.et_email)
    EditText etUserName;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_job)
    EditText etJob;

    @BindView(R.id.et_job_place)
    EditText etJobPlace;

    @BindView(R.id.spinner_height)
    Spinner spinnerHeight;

    @BindView(R.id.spinner_region)
    Spinner spinnerRegion;

    @BindView(R.id.spinner_body_type)
    TextView tvBodyType;

    @BindView(R.id.spiner_birth_year)
    Spinner spinnerYear;

    @BindView(R.id.spiner_birth_month)
    Spinner spinnerMonth;

    @BindView(R.id.spiner_birth_day)
    Spinner spinnerDay;

    @BindView(R.id.spinner_drink)
    Spinner spinnerDrink;

    @BindView(R.id.spinner_smoke)
    Spinner spinnerSmoke;

    int bodyType = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, rootView);

        SpinnerDropdownAdapter spinnerDropdownAdapter = new SpinnerDropdownAdapter(getActivity());
        for(int i=150; i<190; i++) {
            spinnerDropdownAdapter.addItem(String.valueOf(i));
        }
        spinnerHeight.setAdapter(spinnerDropdownAdapter);

        SpinnerDropdownAdapter yearAdapter = new SpinnerDropdownAdapter(getActivity());
        for(int i=1988; i<1998; i++) {
            yearAdapter.addItem(String.valueOf(i));
        }
        spinnerYear.setAdapter(yearAdapter);

        SpinnerDropdownAdapter monthAdapter = new SpinnerDropdownAdapter(getActivity());
        for(int i=1; i<=12; i++) {
            monthAdapter.addItem(String.valueOf(i));
        }
        spinnerMonth.setAdapter(monthAdapter);

        SpinnerDropdownAdapter dayAdapter = new SpinnerDropdownAdapter(getActivity());
        for(int i=1; i<=30; i++) {
            dayAdapter.addItem(String.valueOf(i));
        }
        spinnerDay.setAdapter(dayAdapter);

        // Attach Drink Adapter
        SpinnerDropdownAdapter drinkAdapter = new SpinnerDropdownAdapter(getActivity());
        drinkAdapter.addItem("전혀안함");
        drinkAdapter.addItem("어쩔 수 없을 때");
        drinkAdapter.addItem("가끔");
        drinkAdapter.addItem("자주");
        spinnerDrink.setAdapter(drinkAdapter);

        SpinnerDropdownAdapter smokeAdapter = new SpinnerDropdownAdapter(getActivity());
        smokeAdapter.addItem("안함");
        smokeAdapter.addItem("흡연함");
        spinnerSmoke.setAdapter(smokeAdapter);

        getRegionList();
        return rootView;
    }

    @OnClick(R.id.spinner_body_type)
    void btnBodyType() {
        PickBodyTypeDialog pickBodyTypeDialog = new PickBodyTypeDialog();
        pickBodyTypeDialog.setPickBodyTypeInterface(new PickBodyTypeDialog.PickBodyTypeInterface() {
            @Override
            public void selectItem(String item, int position) {
                tvBodyType.setText(item);
                bodyType = position;
            }
        });
        pickBodyTypeDialog.show(getFragmentManager(), "SHOW");
    }

    @OnClick(R.id.btn_like)
    void btnLike() {
    }

    @OnClick(R.id.btn_next)
    void btnNext() {
        PickInterestDialog pickInterestDialog = new PickInterestDialog();
        pickInterestDialog.setInterface(new PickInterestDialog.PickInterestInterface() {
            @Override
            public void onFinish() {
                HttpUtil.api(User.class).check_username(etUserName.getText().toString(), new Callback<BaseModel>() {
                    @Override
                    public void success(BaseModel baseModel, Response response) {
                        if(baseModel.code == 1) {
                            ((SignupActivity) getActivity()).changeScreen(getArguments().getInt("FNUM")+1);
                            ((SignupActivity) getActivity()).getRegisterObject().userId = etUserName.getText().toString();
                            ((SignupActivity) getActivity()).getRegisterObject().userPassword = etPassword.getText().toString();
                            ((SignupActivity) getActivity()).getRegisterObject().height = Integer.parseInt((String)spinnerHeight.getSelectedItem());
                            ((SignupActivity) getActivity()).getRegisterObject().isDrink = spinnerDrink.getSelectedItemPosition();
                            ((SignupActivity) getActivity()).getRegisterObject().bodyType = bodyType;
                            ((SignupActivity) getActivity()).getRegisterObject().bloodGroup = 0;
                            ((SignupActivity) getActivity()).getRegisterObject().work = etJob.getText().toString();
                            ((SignupActivity) getActivity()).getRegisterObject().workPlace = etJobPlace.getText().toString();
                            ((SignupActivity) getActivity()).getRegisterObject().sex = true;
                            ((SignupActivity) getActivity()).getRegisterObject().region = 0;
                        } else {
                            Toast.makeText(getActivity(), "유저아이디가 중복됩니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        Log.d("url", error.getUrl());
                    }
                });
            }
        });
        pickInterestDialog.show(getFragmentManager(), "SHOW");
    }

    void getRegionList() {
        HttpUtil.api(Define.class).getRegion(new Callback<RegionModel>() {
            @Override
            public void success(RegionModel regionModel, Response response) {
                if(regionModel.code == 1) {
                    SpinnerDropdownAdapter regionAdapter = new SpinnerDropdownAdapter(getActivity());
                    for(int i=0; i<regionModel.dataList.size(); i++) {
                        regionAdapter.addItem(regionModel.dataList.get(i).regionName);
                        Log.d("region", regionModel.dataList.get(i).regionName);
                    }
                    spinnerRegion.setAdapter(regionAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}
