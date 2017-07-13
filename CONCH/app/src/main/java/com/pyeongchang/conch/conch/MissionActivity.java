package com.pyeongchang.conch.conch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

public class MissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);
        fc.initialize(30, 2000, Color.DKGRAY, 2);

        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.toggle(false);
            }
        });
    }
}
