package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.noname.tenminute.Activity.SignupActivity;
import com.noname.tenminute.R;

import java.io.File;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;

/**
 * Created by PJC on 2017-08-16.
 */

public class SignupPhotoFragment extends BaseFragment {

    @BindViews({
            R.id.imv_upload_img, R.id.imv_upload_img2, R.id.imv_upload_img3,
            R.id.imv_upload_img4, R.id.imv_upload_img5, R.id.imv_upload_img6})
    List<ImageView> imvUploadList;

    ImageView imvLastClicked = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup_photo, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.btn_next)
    void btnNext() {
        ((SignupActivity) getActivity()).changeScreen(getArguments().getInt("FNUM")+1);
    }

    @OnClick({R.id.imv_upload_img, R.id.imv_upload_img2, R.id.imv_upload_img3,
            R.id.imv_upload_img4, R.id.imv_upload_img5, R.id.imv_upload_img6})
    void selectImage(View v) {
        imvLastClicked = (ImageView) v;
        EasyImage.openGallery(getActivity(), EasyImageConfig.REQ_TAKE_PICTURE);
    }

    public void imageCallback(File file) {
        int idx = 0;
        if(imvLastClicked != null) {
            for(int i=0; i<imvUploadList.size(); i++) {
                if(imvLastClicked == imvUploadList.get(i)) {
                    idx = i;
                    break;
                }
            }
            ((SignupActivity) getActivity()).getRegisterObject().imageList[idx] = file;
            Glide.with(this).load(file.getAbsolutePath()).apply(RequestOptions.circleCropTransform()).into(imvLastClicked);
        }
    }
}
