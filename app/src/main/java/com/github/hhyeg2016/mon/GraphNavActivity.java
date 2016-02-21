package com.github.hhyeg2016.mon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class GraphNavActivity extends AppCompatActivity {

    ImageButton graph1;
    ImageButton graph2;
    ImageButton graph3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        graph1 = (ImageButton) findViewById(R.id.graph1);
        graph2 = (ImageButton) findViewById(R.id.graph2);
        graph3 = (ImageButton) findViewById(R.id.graph3);

        graph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphNavActivity.this, ChartTextDataActivity.class);
                startActivity(intent);
            }
        });

        graph2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphNavActivity.this, ChartPhoneDataActivity.class);
                startActivity(intent);
            }
        });

        graph3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphNavActivity.this, ChartAppDataActivity.class);
                startActivity(intent);
            }
        });



    }

}
