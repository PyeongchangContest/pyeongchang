package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommunityActivity extends AppCompatActivity {
    private boolean lastItemVisibleFlag = false; // 화면에 리스트의 마지막 아이템이 보여지는지 체크
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mTempRef = mDatabase.child("Community");
    private String communityName;
    private String runningMission;
    private String invitationMission;
    private String quizMission;
    private RotatingTextWrapper rotatingTextWrapper;
    private Rotatable rotatable;
    private TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communityName = getIntent().getStringExtra("communityName");

        temp = (TextView) findViewById(R.id.temp);

        rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        rotatable = new Rotatable(Color.parseColor("#FFA036"), 3000, "Hello", "Hello", "Hello");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent( " ", rotatable);

        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));

//        ListView listView = (ListView) findViewById(R.id.runner_listview);
//
//        ArrayList<RunnerListviewItem> data = new ArrayList<>();
//        RunnerListviewItem pyeongchang = new RunnerListviewItem(R.drawable.emblem, "PyeongChang", today);
//        RunnerListviewItem tiger = new RunnerListviewItem(R.drawable.horang, "Tiger", today);
//        RunnerListviewItem bear = new RunnerListviewItem(R.drawable.bear, "Bear", today);
//        RunnerListviewItem torch = new RunnerListviewItem(R.drawable.torch, "Torch", today);
//
//        data.add(pyeongchang);
//        data.add(tiger);
//        data.add(bear);
//        data.add(torch);
//
//
//        RunnerListviewAdapter adapter = new RunnerListviewAdapter(this, R.layout.runner_listview_item, data);
//        listView.setAdapter(adapter);

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

        mTempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String communityName = getIntent().getStringExtra("communityName");
                DataSnapshot communityDataSnapshot = dataSnapshot.child(communityName);
                String text = communityDataSnapshot.child("runner").getValue(String.class);
                temp.setText(text);

                String racingMission = communityDataSnapshot.child("racingMission").child("missionName").getValue(String.class);
                String invitattionMission = communityDataSnapshot.child("invitationMission").child("missionName").getValue(String.class);
                String quizMission = communityDataSnapshot.child("quiz").child("missionName").getValue(String.class);

                rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
                rotatingTextWrapper.setSize(35);

                rotatable.setText(racingMission, invitattionMission, quizMission);
//                rotatable = new Rotatable(Color.parseColor("#FFA036"), 3000, racingMission, invitattionMission, quizMission);
//                rotatable.setSize(35);
//                rotatable.setAnimationDuration(500);

                rotatingTextWrapper.setContent(" ", rotatable);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        getCommunityInfo(communityName);
    }

    public void getCommunityInfo(final String communityName) {
        mDatabase.child("Community").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()) {
                    if(singleDataSnapshot.getKey().equals(communityName)) {
                        TorchCommunity community = creatCommunityObject(singleDataSnapshot);
                    }
                }

                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public TorchCommunity creatCommunityObject(DataSnapshot dataSnapshot) {
        MissionItem racingMission = new MissionItem((String)dataSnapshot.child("racingMission").child("missionName").getValue(),((Long)dataSnapshot.child("racingMission").child("progress").getValue()).intValue());
        MissionItem invitationMission = new MissionItem((String)dataSnapshot.child("invitationMission").child("missionName").getValue(),((Long)dataSnapshot.child("invitationMission").child("progress").getValue()).intValue());
        MissionItem quizMission = new MissionItem((String)dataSnapshot.child("quiz").child("missionName").getValue(),((Long)dataSnapshot.child("quiz").child("progress").getValue()).intValue());

        ArrayList<String> userList = new ArrayList<String>();
        for(DataSnapshot singleDataSnapshot : dataSnapshot.child("userList").getChildren()) {
            userList.add((String) singleDataSnapshot.getValue());
        }

        ArrayList<String> route = new ArrayList<String>();
        for(DataSnapshot singleDataSnapshot : dataSnapshot.child("route").getChildren()) {
            route.add((String) singleDataSnapshot.getValue());
        }

        ArrayList<String> completedMissionList = new ArrayList<String>();
        if(dataSnapshot.hasChild("completedMissionList")) {
            for (DataSnapshot singleDataSnapshot : dataSnapshot.child("completedMissionList").getChildren()) {
                completedMissionList.add((String) singleDataSnapshot.getValue());
            }
        }

//        if(dataSnapshot.hasChild(""))
        TorchCommunity community = new TorchCommunity();
        community.setCommunityName((String)dataSnapshot.child("communityName").getValue());
        community.setCommunityRank(((Long) dataSnapshot.child("communityRank").getValue()).intValue());
        community.setCommunityScore(((Long) dataSnapshot.child("communityScore").getValue()).intValue());
        community.setRacingLevel(((Long) dataSnapshot.child("racingLevel").getValue()).intValue());
        community.setMaxPeople(((Long)dataSnapshot.child("maxPeople").getValue()).intValue());
        community.setSecret((boolean)dataSnapshot.child("secret").getValue());
        community.setRunner((String)dataSnapshot.child("runner").getValue());
        community.setInvitationMission(invitationMission);
        community.setQuiz(quizMission);
        community.setRacingMission(racingMission);
        community.setUserList(userList);
        community.setRoute(route);
        if(completedMissionList.size() != 0) {
            community.setCompletedMission(completedMissionList);
        }

        return community;
    }
}
