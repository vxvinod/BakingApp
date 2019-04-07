package com.example.a60010743.bakingpro;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a60010743.bakingpro.Adapters.RecepieStepsAdapter;

public class RecepieStepsActivity extends FragmentActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_steps);
        RecyclerView resStpRecyclerView = (RecyclerView) findViewById(R.id.recepie_steps_recycler_view);
       // resStpRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        resStpRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecepieStepsAdapter(this, recepieSteps);
        resStpRecyclerView.setAdapter(mAdapter);

    }
    public String[] recepieSteps = {
            "Recepie-1", "Recepie-2", "Recepie-3",
            "Recepie-4", "Recepie-5", "Recepie-6",
            "Recepie-7", "Recepie-8", "Recepie-9",
            "Recepie-10", "Recepie-11", "Recepie-12"
    };

}
