package com.noname.tenminute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.noname.tenminute.Activity.AwaitApprovalActivity;
import com.noname.tenminute.Activity.MainActivity;
import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.Database.TokenObject;
import com.noname.tenminute.HttpSerivce.User;
import com.noname.tenminute.Model.BaseModel;
import com.noname.tenminute.Model.LoginModel;
import com.noname.tenminute.Util.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void Login() {
        // CHECK INPUT
        if(!TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString())) {
            HttpUtil.api(User.class).login(etEmail.getText().toString(), etPassword.getText().toString(), new Callback<LoginModel>() {
                @Override
                public void success(LoginModel loginModel, Response response) {
                    if(loginModel.code == 1) {
                        TokenObject.getInstance().setToken(loginModel.token);
                        if(loginModel.profile.approval) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, AwaitApprovalActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "아이디 또는 패스워드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_signup)
    void goRegister() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
