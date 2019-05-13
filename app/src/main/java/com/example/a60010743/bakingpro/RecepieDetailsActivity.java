package com.example.a60010743.bakingpro;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.a60010743.bakingpro.Fragments.RecepieDetailsFragments;
import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;

public class RecepieDetailsActivity extends AppCompatActivity implements
        RecepieStepsFragments.OnStepClickListener{

    private int mNavigationIndex;
    private String mRecepieItem;
    private RecepieDetailsFragments mRecepieStepsFragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_details);
        if (savedInstanceState != null) {
            mRecepieItem = savedInstanceState.getString("recepieItem");
            mNavigationIndex = savedInstanceState.getInt("navigationIndex");
            Log.d("SAVEINSTANCE", mRecepieItem);
        }
        if (savedInstanceState == null) {
            mNavigationIndex = getIntent().getIntExtra("navigationIndex", 0);
            mRecepieItem = getIntent().getStringExtra(getString(R.string.recepieItem));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            RecepieDetailsFragments recepieDetailsFragments = new RecepieDetailsFragments();
            recepieDetailsFragments.setRecepieItem(mRecepieItem);
            recepieDetailsFragments.setNavigationIndex(mNavigationIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.recepie_details_fragment_container, recepieDetailsFragments)
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        }

    @Override
    public void onStepClicked(int navigationIndex) {
        Toast.makeText(this, "Position clicked ="+ navigationIndex, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("navigationIndex", mNavigationIndex);
        outState.putString(getString(R.string.recepieItem), mRecepieItem);
    }
}
