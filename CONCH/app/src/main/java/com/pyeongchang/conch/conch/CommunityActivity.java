package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Gayeon on 2017-07-24.
 */

public class CommunityActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        Button missionBtn = (Button) findViewById(R.id.missionBtn);
        Button timelineBtn = (Button) findViewById(R.id.timelineBtn);

        missionBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToMission = new Intent(getApplicationContext(), MissionActivity.class);
                startActivity(intentToMission);
            }
        });

        timelineBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToTimeline = new Intent(getApplicationContext(), TimeLineActivity.class);
                startActivity(intentToTimeline);
            }
        });
    }
}
