package com.noname.tenminute.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.annotations.SerializedName;
import com.noname.tenminute.Dialog.PickInterestDialog;
import com.noname.tenminute.Fragment.SignupAdditionFragment;
import com.noname.tenminute.Fragment.SignupAgreeFragment;
import com.noname.tenminute.Fragment.SignupFragment;
import com.noname.tenminute.Fragment.SignupPhotoFragment;
import com.noname.tenminute.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SignupActivity extends AppCompatActivity {

    @BindViews({R.id.state_dot, R.id.state_dot2, R.id.state_dot3, R.id.state_dot4})
    List<ImageView> dotList;

    ArrayList<Fragment> fragmentList;
    int currentFragmentPosition = 0;

    int drawableCurrentDot[] = {
            R.drawable.step_1_ing,
            R.drawable.step_2_ing,
            R.drawable.step_3_ing,
            R.drawable.step_4_ing
    };
    int drawableWaitDot[] = {
            R.drawable.step_1_wait,
            R.drawable.step_2_wait,
            R.drawable.step_3_wait,
            R.drawable.step_4_wait
    };

    RegisterParams registerParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        registerParams = new RegisterParams();

        fragmentList = new ArrayList<>();
        addFragment(new SignupFragment(), 0);
        addFragment(new SignupPhotoFragment(), 1);
        addFragment(new SignupAdditionFragment(), 2);
        addFragment(new SignupAgreeFragment(), 3);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragmentList.get(0))
                .commit();
    }

    void addFragment(Fragment fragment, int num) {
        Bundle bundle = new Bundle();
        bundle.putInt("FNUM", num);
        fragment.setArguments(bundle);
        fragmentList.add(fragment);
    }

    public void changeScreen(int num) {
        currentFragmentPosition = num;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragmentList.get(num))
                .commit();
        changeDot(num);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                Log.d("image", "bbbb");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                ((SignupPhotoFragment)fragmentList.get(1)).imageCallback(imageFile);
            }

        });
    }

    @Override
    public void onBackPressed() {
        if(currentFragmentPosition <= 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragmentList.get(currentFragmentPosition-1))
                    .commit();
            currentFragmentPosition--;
            changeDot(currentFragmentPosition);
        }
    }

    private void changeDot(int currentPageNum) {
        if(currentPageNum > 0) {
            dotList.get(currentPageNum - 1).setBackgroundResource(R.drawable.step_complete);
        }
        dotList.get(currentPageNum).setBackgroundResource(drawableCurrentDot[currentPageNum]);

        Log.d("curr", currentPageNum+1  + "");
        if(currentPageNum+1 < 3) {
            dotList.get(currentPageNum+1).setBackgroundResource(drawableWaitDot[currentPageNum+1]);
        }
    }

    public RegisterParams getRegisterObject() {
        return registerParams;
    }

    public class RegisterParams {
        public ArrayList<Integer> selectInterest = new ArrayList<>();
        public String userId;
        public String userPassword;
        public String work;
        public String workPlace;
        public int bloodGroup;
        public int bodyType;
        public int height;
        public int region;
        public Boolean sex;
        public Boolean isDrink;

        // after register
        public File[] imageList = new File[6];

        public RegisterParams() {
            for(int i=0; i<6; i++)
                imageList[i] = null;
        }
    }
}
