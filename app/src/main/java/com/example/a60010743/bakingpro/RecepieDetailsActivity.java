package com.example.a60010743.bakingpro;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.example.a60010743.bakingpro.Fragments.RecepieDetailsFragments;
import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;

public class RecepieDetailsActivity extends AppCompatActivity implements
        RecepieStepsFragments.OnStepClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);
        RecepieDetailsFragments recepieDetailsFragments = new RecepieDetailsFragments();
        recepieDetailsFragments.setRecepieItem(getIntent().getStringExtra(getString(R.string.recepieItem)));
        recepieDetailsFragments.setNavigationIndex(getIntent().
                getIntExtra(getString(R.string.navigationIndex),0));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recepie_details_fragment_container, recepieDetailsFragments)
                .commit();

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
}
