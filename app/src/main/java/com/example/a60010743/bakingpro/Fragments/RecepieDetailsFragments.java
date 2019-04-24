package com.example.a60010743.bakingpro.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.RecepieDetailsActivity;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.json.JSONException;

import java.util.List;

public class RecepieDetailsFragments extends Fragment {

    private TextView mTextMessage;
    private TextView mDescription;
    private TextView mShortDesc;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private boolean mPlayWhenReady = false;
    private int mCurrentWindow = 0;
    private long mPlayBackPosition = 0;
    private RecepieStepDetails mRecepieStepDetails;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private ComponentListener componentListener;
    private RecepieViewModel mRecepieViewModel;
    private int mNavigationIndex;
    private List<RecepieStepDetails> mRecepieStepDetailsList;
    private String mRecepieItem;


    public RecepieDetailsFragments() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recepie_detail_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        componentListener = new RecepieDetailsFragments.ComponentListener();



        mTextMessage = (TextView) view.findViewById(R.id.message);
        mPlayerView = (PlayerView) view.findViewById(R.id.detail_video_view);
        mDescription = (TextView) view.findViewById(R.id.description);
        mShortDesc   = (TextView) view.findViewById(R.id.shortDesc);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //String mRecepieItem = getIntent().getStringExtra("recepieItem");
        //final int mNavigationIndex = getIntent().getIntExtra("navigationIndex",0);
        Log.d("Test", "recepieITem--"+mRecepieItem);
        Log.d("Test", "NavigationIndex----"+mNavigationIndex);
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        mRecepieViewModel.getRecepieSteps(mRecepieItem).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    mRecepieStepDetailsList = JsonParseUtils.parseRecSteps(s);
                    mRecepieStepDetails = mRecepieStepDetailsList.get(mNavigationIndex);
                    mDescription.setText(mRecepieStepDetails.getDesc());
                    mShortDesc.setText(mRecepieStepDetails.getShortDesc());
                    String videoUrl = mRecepieStepDetails.getVideoUrl();
                    String thumbnailUrl = mRecepieStepDetails.getThumbnailUrl();
                    setUpPlayer(videoUrl, thumbnailUrl);
                    //displayIngredients();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }

    public void setRecepieItem(String recepieItem) {
        mRecepieItem = recepieItem;
    }

    public void setNavigationIndex(int index) {
        mNavigationIndex = index;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_previous:
                    if(mNavigationIndex == 0) return true;
                    mNavigationIndex = mNavigationIndex - 1;
                    mRecepieStepDetails = mRecepieStepDetailsList.get(mNavigationIndex);
                    mDescription.setText(mRecepieStepDetails.getDesc());
                    mShortDesc.setText(mRecepieStepDetails.getShortDesc());
                    setUpPlayer(mRecepieStepDetails.getVideoUrl(), mRecepieStepDetails.getThumbnailUrl());
                    return true;
                case R.id.navigation_dashboard:
                    //onBackPressed();
                    return true;
                case R.id.navigation_next:
                    if(mNavigationIndex == mRecepieStepDetailsList.size()) return true;
                    mNavigationIndex = mNavigationIndex + 1;
                    mRecepieStepDetails = mRecepieStepDetailsList.get(mNavigationIndex);
                    mDescription.setText(mRecepieStepDetails.getDesc());
                    mShortDesc.setText(mRecepieStepDetails.getShortDesc());
                    setUpPlayer(mRecepieStepDetails.getVideoUrl(), mRecepieStepDetails.getThumbnailUrl());
                    return true;
            }
            return false;
        }
    };
    public void setUpPlayer(String videoUrl, String thumbnailUrl) {
        if (videoUrl.isEmpty() || videoUrl.equals("") || videoUrl == null) {
            if (thumbnailUrl.isEmpty() || thumbnailUrl.equals("") || thumbnailUrl == null) {

                return;
            } else {
                Log.d("Test", "IndideThumbnail" + thumbnailUrl.toString());
                Uri uri = Uri.parse(mRecepieStepDetails.getThumbnailUrl());
                MediaSource mediaSource = buildMediaSource(uri);
                mPlayer.prepare(mediaSource, true, false);
            }
        } else {
            Log.d("Test", "IndideVideo" + videoUrl.toString());
            Uri uri = Uri.parse(mRecepieStepDetails.getVideoUrl());
            MediaSource mediaSource = buildMediaSource(uri);
            mPlayer.prepare(mediaSource, true, false);
        }
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            String videoUrl = null;
            String thumbnailUrl = null;
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);


            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
            mPlayer.addListener(componentListener);
            mPlayerView.setPlayer(mPlayer);

            mPlayer.setPlayWhenReady(mPlayWhenReady);
            mPlayer.seekTo(mCurrentWindow, mPlayBackPosition);

            //Uri uri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
            if(mRecepieStepDetails != null) {
                videoUrl = mRecepieStepDetails.getVideoUrl().toString();
                thumbnailUrl = mRecepieStepDetails.getThumbnailUrl().toString();
            }

            if (videoUrl != null) {

                if (videoUrl.isEmpty() || videoUrl.equals("") || videoUrl == null) {
                    if (thumbnailUrl.isEmpty() || thumbnailUrl.equals("") || thumbnailUrl == null) {

                        return;
                    } else {
                        Log.d("Test", "IndideThumbnail" + thumbnailUrl.toString());
                        Uri uri = Uri.parse(mRecepieStepDetails.getThumbnailUrl());
                        MediaSource mediaSource = buildMediaSource(uri);
                        mPlayer.prepare(mediaSource, true, false);
                    }
                } else {
                    Log.d("Test", "IndideVideo" + videoUrl.toString());
                    Uri uri = Uri.parse(mRecepieStepDetails.getVideoUrl());
                    MediaSource mediaSource = buildMediaSource(uri);
                    mPlayer.prepare(mediaSource, true, false);
                }
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        initializePlayer();
    }

    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if(mPlayer != null) {
            mPlayBackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.removeListener(componentListener);
            mPlayer.release();
            mPlayer = null;
        }
    }

    private class ComponentListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "Player.STATE_IDLE";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "Player.STATE_BUFFERING";
                    break;
                case Player.STATE_READY:
                    stateString = "Player.STATE_READY";
                    break;
                case Player.STATE_ENDED:
                    stateString = "Player.STATE_ENDED";
                    break;
                default:
                    stateString = "UNKNOWN_STATE";
                    break;

            }
            Log.d("Player", "Changed state to" + stateString +
                    "playWhenReady" + playWhenReady);
        }
    }
}
