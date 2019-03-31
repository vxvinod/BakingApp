package com.example.a60010743.bakingpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.example.a60010743.bakingpro.Adapters.RecepieAdapter;

public class MainActivity extends AppCompatActivity {



//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView recepieGridView = (GridView) findViewById(R.id.baking_grid_view);
        recepieGridView.setAdapter(new RecepieAdapter(this, recepieNames));

        Intent intent = new Intent(this, RecepieSteps.class);
        startActivity(intent);
   //     BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public String[] recepieNames = {
            "Recepie-1", "Recepie-2", "Recepie-3",
            "Recepie-4", "Recepie-5", "Recepie-6",
            "Recepie-7", "Recepie-8", "Recepie-9",
            "Recepie-10", "Recepie-11", "Recepie-12"
    };

}
