package com.noname.tenminute.Fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.noname.tenminute.Model.IceCandidateModel;
import com.noname.tenminute.Model.JoinModel;
import com.noname.tenminute.Model.MatchModel;
import com.noname.tenminute.Model.OfferModel;
import com.noname.tenminute.Model.ProfileModel;
import com.noname.tenminute.R;
import com.noname.tenminute.Util.SockerIO;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.DisposableLambdaObserver;
import io.reactivex.subjects.PublishSubject;
import io.socket.emitter.Emitter;

/**
 * Created by PJC on 2017-07-30.
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.tv_home_waiting)
    protected TextView tvWaiting;

    //data TODO : 알아서 리펙토링해라 ㅅㄱ
    private ProfileModel profileModel;
    private String userName;
    private MatchModel matchModel;

    private PublishSubject<Integer> subject = PublishSubject.create();
    private Disposable loadinDisposable = null;

    private boolean isCalling = false;

    private PeerConnection peerConnection;

    public HomeFragment setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
        return this;
    }

    public HomeFragment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        init();
        return rootView;
    }

    private void init() {
        SockerIO.connect();

        initSocketEvents();
    }

    private void initSocketEvents() {
        SockerIO.on(SockerIO.EVENT_MATCHING, this::onMatching);
        SockerIO.on(SockerIO.EVENT_OFFER, this::onOffer);
        SockerIO.on(SockerIO.EVENT_ANSWER, this::onAnswer);
        SockerIO.on(SockerIO.EVENT_ICECANDIDATE, this::onIceCandidate);
    }

    private void onMatching(Object[] args) {
        Log.d(TAG, "onMatching " + args[0].toString());
        MatchModel matchModel = new Gson().fromJson(args[0].toString(), MatchModel.class);
        this.matchModel = matchModel;
        Log.d(TAG, new Gson().toJson(matchModel));
        switch (matchModel.requestType) {
            case "Offer":
                setOfferProcess();
                break;
            case "Answer":
                setAnswerProcess();
                break;
            default:
                new RuntimeException("에러임 ㅅㄱ" + matchModel.requestType);
        }
    }

    private void onOffer(Object[] args) {
        Log.d(TAG, "onOffer " + args[0].toString());
        OfferModel offerModel = new Gson().fromJson(args[0].toString(), OfferModel.class);
        setRemoteDescription(offerModel.sdp);
    }

    private void onAnswer(Object[] args) {
        Log.d(TAG, "onAnswer " + args[0].toString());
        OfferModel offerModel = new Gson().fromJson(args[0].toString(), OfferModel.class);
        setRemoteDescription(offerModel.sdp);
    }

    private void onIceCandidate(Object[] args) {
        Log.d(TAG, "onIceCandidate " + args[0].toString());
        IceCandidateModel iceCandidateModel = new Gson().fromJson(args[0].toString(), IceCandidateModel.class);
        peerConnection.addIceCandidate(iceCandidateModel.iceCandidate);
    }

    private void setOfferProcess() {
        createOffer();
    }

    private void setAnswerProcess() {
        //answer일 경우 대기함
    }

    private void startConnection() {
        PeerConnectionFactory.initializeAndroidGlobals(
                getContext(),
                true,
                false,
                false,
                null
        );

        PeerConnectionFactory peerConnectionFactory = new PeerConnectionFactory();

        AudioSource audioSource = peerConnectionFactory.createAudioSource(getAudioConstraints());
        AudioTrack localAudioTrack = peerConnectionFactory.createAudioTrack("audioPN", audioSource);
        if(audioSource == null || localAudioTrack == null){
            throw new NullPointerException("시발 오디오 ");
        }

        MediaStream mediaStream = peerConnectionFactory.createLocalMediaStream("localStreamPN");
        mediaStream.addTrack(localAudioTrack);

        peerConnection = peerConnectionFactory.createPeerConnection(
                getIceServerList(),
                getAudioConstraints(),
                new PeerConnection.Observer() {
                    @Override
                    public void onSignalingChange(PeerConnection.SignalingState signalingState) {
                        Log.d(TAG, "onSignalingChange " + signalingState);

                    }

                    @Override
                    public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                        Log.d(TAG, "onIceConnectionChange : " + iceConnectionState);
                    }

                    @Override
                    public void onIceConnectionReceivingChange(boolean b) {
                        Log.d(TAG, "onIceConnectionReceivingChange");

                    }

                    @Override
                    public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
                        Log.d(TAG, "onIceGatheringChange");

                    }

                    @Override
                    public void onIceCandidate(IceCandidate iceCandidate) {
                        Log.d(TAG, "onIceCandidate : " + new Gson().toJson(iceCandidate));
                        SockerIO.emit(
                                SockerIO.EVENT_ICECANDIDATE,
                                new IceCandidateModel(
                                        matchModel.username,
                                        iceCandidate
                                )
                        );
                    }

                    @Override
                    public void onAddStream(MediaStream mediaStream) {
                        Log.d(TAG, "onAddStream : " + mediaStream.label());
                        mediaStream.audioTracks.get(0);
                        AudioTrack audioTrack;
                        VideoTrack videoTrack;
                        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setSpeakerphoneOn(true);


                    }

                    @Override
                    public void onRemoveStream(MediaStream mediaStream) {
                        Log.d(TAG, "onRemoveStream");

                    }

                    @Override
                    public void onDataChannel(DataChannel dataChannel) {
                        Log.d(TAG, "onDataChannel");

                    }

                    @Override
                    public void onRenegotiationNeeded() {
                        Log.d(TAG, "onRenegotiationNeeded");

                    }
                }
        );
        peerConnection.addStream(mediaStream);

        join();

    }

    private void stopConnection() {
        SockerIO.disconnect();
        peerConnection.close();
    }

    private void createOffer() {
        Log.d(TAG, "createOffer");
        peerConnection.createOffer(
                new BaseSdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {
                        super.onCreateSuccess(sessionDescription);

                        Log.d(TAG, "sdp : " + new Gson().toJson(sessionDescription));

                        SockerIO.emit(
                                SockerIO.EVENT_OFFER,
                                new OfferModel(
                                        matchModel.username,
                                        sessionDescription
                                )
                        );
                        setLocalDescription(sessionDescription);
                    }

                    @Override
                    public void onSetSuccess() {
                        super.onSetSuccess();
                    }

                    @Override
                    public void onCreateFailure(String s) {
                        super.onCreateFailure(s);
                    }

                    @Override
                    public void onSetFailure(String s) {
                        super.onSetFailure(s);
                    }
                },
                defaultPcConstraints()
        );

    }

    private void setLocalDescription(SessionDescription sessionDescription) {
        Log.d(TAG, "setLocalDescription");
        peerConnection.setLocalDescription(
                new BaseSdpObserver() {
                    @Override
                    public void onSetSuccess() {
                        super.onSetSuccess();

                        switch (matchModel.requestType) {
                            case "Offer":
                                break;
                            case "Answer":
                                break;
                            default:
                                throw new RuntimeException("type 에러 ㅅㄱ" + matchModel.requestType);
                        }
                    }
                },
                sessionDescription
        );
    }

    private void setRemoteDescription(SessionDescription sessionDescription) {
        Log.d(TAG, "setRemoteDescription");
        peerConnection.setRemoteDescription(
                new BaseSdpObserver() {

                    @Override
                    public void onSetSuccess() {
                        super.onSetSuccess();

                        switch (matchModel.requestType) {
                            case "Offer":

                                break;
                            case "Answer":
                                createAnswer();
                                break;
                            default:
                                throw new RuntimeException("type 에러 ㅅㄱ" + matchModel.requestType);
                        }

                    }
                },
                sessionDescription
        );
    }

    private void join() {
        SockerIO.emit(
                SockerIO.EVENT_JOIN,
                new JoinModel(
                        userName,
                        profileModel.sex
                )
        );
    }

    private void createAnswer() {
        Log.d(TAG, "createAnswer");
        peerConnection.createAnswer(
                new BaseSdpObserver() {
                    @Override
                    public void onCreateSuccess(SessionDescription sessionDescription) {
                        super.onCreateSuccess(sessionDescription);

                        SockerIO.emit(
                                SockerIO.EVENT_ANSWER,
                                new OfferModel(
                                        matchModel.username,
                                        sessionDescription
                                )
                        );
                        setLocalDescription(sessionDescription);
                    }
                },
                defaultPcConstraints()
        );
    }

    public class BaseSdpObserver implements SdpObserver {

        private String CLASSNAME = "";//this.getClass().getEnclosingClass().getName();

        @CallSuper
        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
            Log.d(TAG, CLASSNAME + " onCreateSuccess");
            Log.d(TAG, CLASSNAME + " description " + sessionDescription.description);
            Log.d(TAG, CLASSNAME + " type " + sessionDescription.type);
        }

        @CallSuper
        @Override
        public void onSetSuccess() {
            Log.d(TAG, CLASSNAME + " onSetSuccess");
        }

        @CallSuper
        @Override
        public void onCreateFailure(String s) {
            Log.d(TAG, CLASSNAME + " onCreateFailure" + s);
        }

        @CallSuper
        @Override
        public void onSetFailure(String s) {
            Log.d(TAG, CLASSNAME + " onSetFailure" + s);
        }

    }

    private MediaConstraints getAudioConstraints() {
        return new MediaConstraints();
    }

    /*

            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turn:turn01.uswest.xirsys.com:80?transport=udp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            },
            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turn:turn01.uswest.xirsys.com:3478?transport=udp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            },
            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turn:turn01.uswest.xirsys.com:80?transport=tcp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            },
            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turn:turn01.uswest.xirsys.com:3478?transport=tcp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            },
            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turns:turn01.uswest.xirsys.com:443?transport=tcp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            },
            {
                "username": "142438b2-9b08-11e7-82e9-6397bf22c3d4",
                "url": "turns:turn01.uswest.xirsys.com:5349?transport=tcp",
                "credential": "1424393e-9b08-11e7-80c4-93bd5e1f0de0"
            }

     */
    private List<PeerConnection.IceServer> getIceServerList() {
        ArrayList<PeerConnection.IceServer> iceServerList = new ArrayList<>();
        iceServerList.add(new PeerConnection.IceServer("stun:turn01.uswest.xirsys.com"));
        iceServerList.add(new PeerConnection.IceServer("turn:turn01.uswest.xirsys.com:80?transport=udp", "142438b2-9b08-11e7-82e9-6397bf22c3d4", "1424393e-9b08-11e7-80c4-93bd5e1f0de0"));
        iceServerList.add(new PeerConnection.IceServer("turns:turn01.uswest.xirsys.com:5349?transport=tcp", "142438b2-9b08-11e7-82e9-6397bf22c3d4", "1424393e-9b08-11e7-80c4-93bd5e1f0de0"));

        return iceServerList;
    }


    @OnClick(R.id.imv_call)
    public void onCallButton() {
        if (isCalling) {
            stopLoadingText();
            stopConnection();
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
        if (loadinDisposable != null) {
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

    private static MediaConstraints defaultPcConstraints() {
        MediaConstraints pcConstraints = new MediaConstraints();
        pcConstraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));
        pcConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        pcConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "false"));
        return pcConstraints;
    }
}
