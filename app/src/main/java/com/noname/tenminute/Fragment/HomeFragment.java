package com.noname.tenminute.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.noname.tenminute.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnectionFactory;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by PJC on 2017-07-30.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_home_waiting)
    protected TextView tvWaiting;

    private PublishSubject<Integer> subject = PublishSubject.create();
    private Disposable loadinDisposable = null;

    private boolean isCalling = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.imv_call)
    public void onCallButton() {
        if (isCalling) {
            stopLoadingText();
        } else {
            startLoadingText();
            startConnection();
        }

        isCalling = !isCalling;
    }

    @OnClick(R.id.btn_set_if)
    public void onConditionButton() {

    }

    private void startLoadingText() {
        tvWaiting.setVisibility(View.VISIBLE);
        loadinDisposable = subject.delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> {
                    tvWaiting.setText(
                            "통화 연결중" + repeat(".", count)
                    );

                    subject.onNext(
                            (++count) % 5
                    );
                });
        subject.onNext(0);
    }

    private void stopLoadingText() {
        showToast("통화연결이 취소되었습니다.");

        tvWaiting.setVisibility(View.INVISIBLE);
        if(loadinDisposable != null) {
            loadinDisposable.dispose();
        }
    }

    private String repeat(String text, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(text);
        }
        return builder.toString();
    }

    private void startConnection(){
        PeerConnectionFactory.initializeAndroidGlobals(
                getContext(),
                true,
                false,
                false,
                null
        );

        PeerConnectionFactory peerConnectionFactory = new PeerConnectionFactory();
        
    }
}
