package com.noname.tenminute.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.noname.tenminute.HttpSerivce.User;
import com.noname.tenminute.Model.AoorivalModel;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AwaitApprovalActivity extends AppCompatActivity {

    @BindView(R.id.imv_profile)
    ImageView imvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_await_approval);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.ex_user_profile2).apply(RequestOptions.circleCropTransform()).into(imvProfile);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("SAVE", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("username", null);
        HttpUtil.api(User.class).getApproval(str, new Callback<AoorivalModel>() {
            @Override
            public void success(AoorivalModel aoorivalModel, Response response) {
                if(aoorivalModel.code == 1) {
                    if(aoorivalModel.approval.equals("1")) {
                        Intent intent = new Intent(AwaitApprovalActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
