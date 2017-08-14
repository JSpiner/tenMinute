package com.noname.tenminute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.noname.tenminute.Dialog.PickInterestDialog;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        PickInterestDialog pickInterestDialog = new PickInterestDialog();
        pickInterestDialog.show(getSupportFragmentManager(), "DIALOG");
    }
}
