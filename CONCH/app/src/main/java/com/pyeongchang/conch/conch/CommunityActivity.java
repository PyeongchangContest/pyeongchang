package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommunityActivity extends AppCompatActivity{
    private boolean lastItemVisibleFlag = false; // 화면에 리스트의 마지막 아이템이 보여지는지 체크
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mTempRef = mDatabase.child("Community");
    private String communityName;
    private String runningMission;
    private String invitationMission;
    private String quizMission;
    private RotatingTextWrapper rotatingTextWrapper;
    private Rotatable rotatable;
    private ListView listView;
    private ArrayList<String> runnerList = new ArrayList<String>();
    private ArrayList<String> dateList = new ArrayList<String>();
    private ArrayList<RunnerListviewItem> runnerListView = new ArrayList<RunnerListviewItem>();
    private RunnerListviewAdapter adapter;
    private boolean loaded = false;
    private int oldRunnerListSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communityName = getIntent().getStringExtra("communityName");

        rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        rotatable = new Rotatable(Color.parseColor("#FFA036"), 3000, "Hello", "Hello", "Hello");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent( " ", rotatable);

        listView = (ListView) findViewById(R.id.runner_listview);
        adapter = new RunnerListviewAdapter(this, R.layout.runner_listview_item, runnerListView);
        listView.setAdapter(adapter);

        Button missionBtn = (Button) findViewById(R.id.missionBtn);
        missionBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, MissionActivity.class);
                startActivity(intent);
            }
        });

        Button timelineBtn=(Button) findViewById(R.id.timelineBtn);
        timelineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
                intent.putExtra("commnityName",communityName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String communityName = getIntent().getStringExtra("communityName");
                DataSnapshot communityDataSnapshot = dataSnapshot.child("Community").child(communityName);

                setRotatingText(communityDataSnapshot);

                if(!isLoaded()) { //커뮤니티에 처음으로 들어왔을 때
                    loadRunnerList(dataSnapshot, communityDataSnapshot);
                } else if (isLoaded() && runnerListSizeIsChanged(communityDataSnapshot)) { //다른 액티비티에서 다시 커뮤니티로 돌아왔을 경우
                    resetRunnerList();
                    loadRunnerList(dataSnapshot, communityDataSnapshot);
                }

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
            });
    }

    public void setRotatingText(DataSnapshot communityDataSnapshot) {
        String racingMission = "Run" + communityDataSnapshot.child("racingMission").child("missionName").getValue(String.class) + "Km with your members";
        String invitattionMission = "Invite a" + communityDataSnapshot.child("invitationMission").child("missionName").getValue(String.class) + "person";
        String quizMission = communityDataSnapshot.child("quiz").child("missionName").getValue(String.class);

        rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        rotatable.setText(racingMission, invitattionMission, quizMission);

        rotatingTextWrapper.setContent(" ", rotatable);
    }

    public void resetRunnerList() {
        runnerList = null;
        dateList = null;
        runnerListView = null;
    }

    public void loadRunnerList(DataSnapshot dataSnapshot, DataSnapshot communityDataSnapshot) {
        setOldRunnerListSize(((Long)communityDataSnapshot.child("userList").getChildrenCount()).intValue());

        for(DataSnapshot singleDataSnapshot : communityDataSnapshot.child("userList").getChildren()) {
            runnerList.add(singleDataSnapshot.getValue(String.class));
        }

        for(DataSnapshot singleDataSnapshot : communityDataSnapshot.child("date").getChildren()) {
            dateList.add(singleDataSnapshot.getValue(String.class));
        }

        dateList.add("2017-08-24"); /////////temp code!!!!!!!!!!

        for (int i=0; i < runnerList.size(); i++) {
            int photo = 0;
            for (DataSnapshot singleDataSnapshot : dataSnapshot.child("Users").getChildren()) {
                if (singleDataSnapshot.getKey().equals(runnerList.get(i))) {
                    photo = ((Long)singleDataSnapshot.child("photo").getValue()).intValue();
                    break;
                }
            }
            runnerListView.add(new RunnerListviewItem(photo, runnerList.get(i), dateList.get(i)));
        }
        adapter.notifyDataSetChanged();
        loaded = true;
    }

    public int getOldRunnerListSize() {
        return oldRunnerListSize;
    }

    public void setOldRunnerListSize(int oldRunnerListSize) {
        this.oldRunnerListSize = oldRunnerListSize;
    }

    public boolean runnerListSizeIsChanged(DataSnapshot communityDataSnapshot) {
        return this.getOldRunnerListSize() != ((Long)communityDataSnapshot.child("userList").getChildrenCount()).intValue();
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
