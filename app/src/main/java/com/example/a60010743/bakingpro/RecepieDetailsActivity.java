package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a60010743.bakingpro.Fragments.RecepieDetailsFragments;
import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;
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


public class RecepieDetailsActivity extends AppCompatActivity implements
        RecepieStepsFragments.OnStepClickListener{

    private boolean mTwoPane = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_details);
            RecepieDetailsFragments recepieDetailsFragments = new RecepieDetailsFragments();
            recepieDetailsFragments.setRecepieItem(getIntent().getStringExtra("recepieItem"));
            recepieDetailsFragments.setNavigationIndex(getIntent().getIntExtra("navigationIndex",0));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recepie_details_fragment_container, recepieDetailsFragments)
                    .commit();
        }


      //  mRecepieStepDetails = getIntent().getParcelableExtra("recStpDetails");


    @Override
    public void onStepClicked(int navigationIndex) {
        Toast.makeText(this, "Position clicked ="+ navigationIndex, Toast.LENGTH_SHORT).show();
    }
}
