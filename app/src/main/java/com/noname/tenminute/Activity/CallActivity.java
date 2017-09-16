package com.noname.tenminute.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.noname.tenminute.R;

import org.webrtc.PeerConnectionFactory;
import org.webrtc.VideoCapturerAndroid;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        PeerConnectionFactory.initializeAndroidGlobals(
                this,
                true,
                false,
                true,
                true);

        // Returns the number of camera devices
        VideoCapturerAndroid.getDeviceCount();

        // Returns the front face device name
        VideoCapturerAndroid.getNameOfFrontFacingDevice();
        // Returns the back facing device name
        VideoCapturerAndroid.getNameOfBackFacingDevice();

        // Creates a VideoCapturerAndroid instance for the device name
        VideoCapturerAndroid.create("FACE");
    }
}
