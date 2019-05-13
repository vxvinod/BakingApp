package com.example.a60010743.bakingpro.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;
import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecepieDetailsFragments extends Fragment {


    private TextView mTextMessage;
    private TextView mDescription;
    private TextView mShortDesc;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private ImageView mImageView;
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

    Unbinder unbinder;
    public RecepieDetailsFragments() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recepie_detail_fragment_layout, container, false);
       // ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        componentListener = new RecepieDetailsFragments.ComponentListener();
        mTextMessage = (TextView) view.findViewById(R.id.message);
        mPlayerView = (PlayerView) view.findViewById(R.id.detail_video_view);
        mDescription = (TextView) view.findViewById(R.id.description);
        mShortDesc   = (TextView) view.findViewById(R.id.shortDesc);
        mImageView = (ImageView) view.findViewById(R.id.imageView);

        if(savedInstanceState!=null) {
            mRecepieItem = savedInstanceState.getString(getString(R.string.recepieItem));
            mNavigationIndex = savedInstanceState.getInt(getString(R.string.navigationIndex));
            mCurrentWindow = savedInstanceState.getInt(getString(R.string.currentWindow));
            mPlayBackPosition = savedInstanceState.getLong(getString(R.string.playBackposition));
            mPlayWhenReady = savedInstanceState.getBoolean(getString(R.string.playWhenReady));
        }

        BottomNavigationView mNavigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);

        // Fetch Recepie Details from DB and Set in UI.
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
        if(TextUtils.isEmpty(videoUrl)) {
            if(TextUtils.isEmpty(thumbnailUrl)) {
                return;
            } else {
                Uri uri = Uri.parse(mRecepieStepDetails.getThumbnailUrl());
                MediaSource mediaSource = buildMediaSource(uri);
                boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
                if (haveStartPosition) {
                    mPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
                }
                mPlayer.prepare(mediaSource, !haveStartPosition, false);
            }
        } else {
            Uri uri = Uri.parse(mRecepieStepDetails.getVideoUrl());
            MediaSource mediaSource = buildMediaSource(uri);
            boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
            if (haveStartPosition) {
                mPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
            }
            mPlayer.prepare(mediaSource, !haveStartPosition, false);
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
            if(mRecepieStepDetails != null) {
                videoUrl = mRecepieStepDetails.getVideoUrl().toString();
                thumbnailUrl = mRecepieStepDetails.getThumbnailUrl().toString();
            }
            if (videoUrl != null) {
                if (TextUtils.isEmpty(videoUrl)) {
                    if (TextUtils.isEmpty(thumbnailUrl)) {
                        return;
                    } else {
                        Uri uri = Uri.parse(mRecepieStepDetails.getThumbnailUrl());
                        Picasso.with(getContext()).load(uri).into(mImageView);
                    }
                } else {
                    Uri uri = Uri.parse(mRecepieStepDetails.getVideoUrl());
                    MediaSource mediaSource = buildMediaSource(uri);
                    boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
                    if (haveStartPosition) {
                        mPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
                    }
                    mPlayer.prepare(mediaSource, !haveStartPosition, false);
                }
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.exoPlayer))).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIFECYCLE", "START");
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayBackPosition = mPlayer.getContentPosition();
        }
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }


    private void releasePlayer() {
        if(mPlayer != null) {
            mPlayBackPosition = mPlayer.getContentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.removeListener(componentListener);
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void updateStartPosition() {
        if (mPlayer != null) {
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayBackPosition = Math.max(0, mPlayer.getContentPosition());
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
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putString(getString(R.string.recepieItem), mRecepieItem);
        outState.putInt(getString(R.string.navigationIndex), mNavigationIndex);
        outState.putInt(getString(R.string.currentWindow), mCurrentWindow);
        outState.putLong(getString(R.string.playBackposition),  mPlayBackPosition);
        outState.putBoolean(getString(R.string.playWhenReady), mPlayWhenReady);
    }
}
