package com.noname.tenminute.Activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.noname.tenminute.Dialog.PickInterestDialog;
import com.noname.tenminute.Fragment.SignupAgreeFragment;
import com.noname.tenminute.Fragment.SignupFragment;
import com.noname.tenminute.Fragment.SignupPhotoFragment;
import com.noname.tenminute.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        fragmentList = new ArrayList<>();
        addFragment(new SignupFragment(), 0);
        addFragment(new SignupPhotoFragment(), 1);
        addFragment(new SignupAgreeFragment(), 2);

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
        dotList.get(currentPageNum+1).setBackgroundResource(drawableWaitDot[currentPageNum+1]);
    }
}
