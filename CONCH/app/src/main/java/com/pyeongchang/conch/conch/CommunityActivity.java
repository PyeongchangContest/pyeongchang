package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;
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
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.toIntExact;

public class CommunityActivity extends AppCompatActivity {
    private boolean lastItemVisibleFlag = false; // 화면에 리스트의 마지막 아이템이 보여지는지 체크

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private TorchCommunity community;
    private String communityName;
    private String runningMission;
    private String invitationMission;
    private String quizMission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communityName = getIntent().getStringExtra("communityName");

        getCommunity(communityName);

        String temp = communityName;


        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word01", "Word02");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("This is ?", rotatable);

        Button missionBtn = (Button) findViewById(R.id.missionBtn);
        missionBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, MissionActivity.class);
                startActivity(intent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));

        ListView listView = (ListView) findViewById(R.id.runner_listview);

        ArrayList<RunnerListviewItem> data = new ArrayList<>();
        RunnerListviewItem pyeongchang = new RunnerListviewItem(R.drawable.emblem, "PyeongChang", today);
        RunnerListviewItem tiger = new RunnerListviewItem(R.drawable.horang, "Tiger", today);
        RunnerListviewItem bear = new RunnerListviewItem(R.drawable.bear, "Bear", today);
        RunnerListviewItem torch = new RunnerListviewItem(R.drawable.torch, "Torch", today);

        data.add(pyeongchang);
        data.add(tiger);
        data.add(bear);
        data.add(torch);


        RunnerListviewAdapter adapter = new RunnerListviewAdapter(this, R.layout.runner_listview_item, data);
        listView.setAdapter(adapter);

        Button timelineBtn=(Button) findViewById(R.id.timelineBtn);
        timelineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
                intent.putExtra("communityName",communityName);
                startActivity(intent);
            }
        });
    }
    public void setCommunity(TorchCommunity community) {
        this.community = community;
    }

    public TorchCommunity creatCommunityObject(DataSnapshot dataSnapshot) {
        MissionItem racingMission = new MissionItem((String)dataSnapshot.child("racingMission").child("missionName").getValue(),((Long)dataSnapshot.child("racingMission").child("progress").getValue()).intValue());
        MissionItem invitationMission = new MissionItem((String)dataSnapshot.child("invitationMission").child("missionName").getValue(),((Long)dataSnapshot.child("invitationMission").child("progress").getValue()).intValue());
        MissionItem quizMission = new MissionItem((String)dataSnapshot.child("quiz").child("missionName").getValue(),((Long)dataSnapshot.child("quiz").child("progress").getValue()).intValue());

        List<String> userList = new ArrayList<String>();
        for(DataSnapshot singleDataSnapshot : dataSnapshot.child("userList").getChildren()) {
//            UserProperty user = new UserProperty(((Long)singleDataSnapshot.child("userLevel").getValue()).intValue(),((Long)singleDataSnapshot.child("userScore").getValue()).intValue());
//            userList.add(user);
        }

        TorchCommunity community = new TorchCommunity();
        community.setCommunityName((String)dataSnapshot.child("communityName").getValue());
        community.setCommunityRank(((Long) dataSnapshot.child("communityRank").getValue()).intValue());
        community.setCommunityScore(((Long) dataSnapshot.child("communityScore").getValue()).intValue());
        community.setMaxPeople(((Long)dataSnapshot.child("maxPeople").getValue()).intValue());
        community.setSecret((boolean)dataSnapshot.child("secret").getValue());
        community.setInvitationMission(invitationMission);
        community.setQuiz(quizMission);
        community.setRacingMission(racingMission);

        TorchCommunity temp = community;

        return community;
    }
    public void getCommunity(final String communityName) {
        mDatabase.child("Community").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleDataSnapshot : dataSnapshot.getChildren()) {
                    if(singleDataSnapshot.getKey().equals(communityName)) {
                        creatCommunityObject(singleDataSnapshot);
                        Object temp = singleDataSnapshot.child("communityName").getValue();
                        TorchCommunity temp2 = (TorchCommunity) temp;
//                        setCommunity(currentCommunity);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
