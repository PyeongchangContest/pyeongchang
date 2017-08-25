package com.pyeongchang.conch.conch;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idunnololz.widgets.AnimatedExpandableListView;
import com.idunnololz.widgets.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 *
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
public class MissionActivity extends AppCompatActivity {
    static final int RACING_MISSION = 0;
    static final int INVITATION_MISSION = 1;
    static final int QUIZ_MISSION = 2;

    private List<GroupItem> items = new ArrayList<GroupItem>();
    private List<GroupItem> items2 = new ArrayList<GroupItem>();
    private String communityName;
    private GroupItem processingMissions;
    private GroupItem completedMissions;

    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;

    private AnimatedExpandableListView listView2;
    private ExampleAdapter adapter2;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        communityName = getIntent().getStringExtra("communityName");

        adapter = new ExampleAdapter(getBaseContext());
        listView = (AnimatedExpandableListView) findViewById(R.id.listView);

        items2 = new ArrayList<GroupItem>();
        adapter2 = new ExampleAdapter(getBaseContext());
        listView2 = (AnimatedExpandableListView) findViewById(R.id.listView2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final DataSnapshot communityRef = dataSnapshot.child("Community").child(communityName);

                processingMissions = new GroupItem();
                processingMissions.title = "Processing Missions";

                completedMissions = new GroupItem();
                completedMissions.title = "Completed Missions";

                if (!isLoaded()) {
                    loadProcessingMissionList(communityRef);
                    loadCompletedMissionList(communityRef);

                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();

                    setLoaded(true);
                } else {
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                }

                listView.setOnGroupClickListener(new OnGroupClickListener() {

                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (listView.isGroupExpanded(groupPosition)) {
                            listView.collapseGroupWithAnimation(groupPosition);
                        } else {
                            listView.expandGroupWithAnimation(groupPosition);
                        }
                        return true;
                    }

                });

                listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                        //quiz를 눌렀을 경우 다이얼로그 띄우기
                        if (childPosition == QUIZ_MISSION) {
                            popUpTheQuiz(dataSnapshot);
                        }
                        return false;
                    }
                });



                listView2.setOnGroupClickListener(new OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                        if(listView2.isGroupExpanded(groupPosition)) {
                            listView2.collapseGroupWithAnimation(groupPosition);
                        } else {
                            listView2.expandGroupWithAnimation(groupPosition);
                        }
                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void resetCompletedMissionList() {
        items = new ArrayList<GroupItem>();
        items2 = new ArrayList<GroupItem>();

        setLoaded(false);
    }
    private void loadCompletedMissionList(DataSnapshot communityRef) {

        DataSnapshot completedMissionRef = communityRef.child("completedMission");
        if (((Long)completedMissionRef.getChildrenCount()).intValue() != 0) {

            for(DataSnapshot singleDataSnapshot : completedMissionRef.getChildren()) {
                ChildItem completedList = new ChildItem();
                completedList.title = singleDataSnapshot.getValue(String.class);
                completedList.currentProgress = "0"; //Temp!!!!!!!!!!!!!!!!! DELETE
                completedMissions.items.add(completedList);
            }

            items2.add(completedMissions);
            adapter2.setData(items2);
            listView2.setAdapter(adapter2);
        }
    }

    private void loadProcessingMissionList(DataSnapshot communityRef) {
        ChildItem racingMission = new ChildItem();
        racingMission.title = "Run " + communityRef.child("racingMission").child("missionName").getValue(String.class) + " with your members";
        racingMission.currentProgress = "13%"; //Temp!!!!!!!!DELETE
        processingMissions.items.add(racingMission);

        ChildItem invitationMission = new ChildItem();
        invitationMission.title = "Invite a " + communityRef.child("invitationMission").child("missionName").getValue(String.class) + " person";
        invitationMission.currentProgress = "15%"; //Temp!!!!!!!!DELETE
        processingMissions.items.add(invitationMission);

        ChildItem quizMission = new ChildItem();
        quizMission.title = "Let's solve the QUIZ! (Click me)";
        quizMission.currentProgress = "0"; //Temp!!!!!!!!DELETE
        processingMissions.items.add(quizMission);

        items.add(processingMissions);
        adapter.setData(items);
        listView.setAdapter(adapter);
    }

    public void addCompletedMission(DataSnapshot dataSnapshot, String missionContent, int mission) {
        DataSnapshot communityRef = dataSnapshot.child("Community").child(communityName);
        DataSnapshot missionRef = dataSnapshot.child("Mission");

        if (mission == RACING_MISSION) {
            mDatabase.child("Community").child(communityName).child("completedMission").child(Long.toString(communityRef.child("completedMission").getChildrenCount())).setValue(missionContent);
        } else if (mission ==  INVITATION_MISSION) {
            mDatabase.child("Community").child(communityName).child("completedMission").child(Long.toString(communityRef.child("completedMission").getChildrenCount())).setValue(missionContent);
        } else {
            mDatabase.child("Community").child(communityName).child("completedMission").child(Long.toString(communityRef.child("completedMission").getChildrenCount()))
                    .setValue("Let's solve the quiz : \n" + missionRef.child("Quiz").child(missionContent).getValue(String.class));
        }
    }

    public void popUpTheQuiz(final DataSnapshot dataSnapshot) {
        AlertDialog.Builder quizPopUp = new AlertDialog.Builder(MissionActivity.this);

        final String quizNum = dataSnapshot.child("Community").child(communityName).child("quiz").child("missionName").getValue(String.class);
        String quiz = dataSnapshot.child("Mission").child("Quiz").child(quizNum).getValue(String.class);

        final EditText editText = new EditText(MissionActivity.this);
        quizPopUp.setView(editText);

        quizPopUp.setTitle("QUIZ!");
        quizPopUp.setMessage(quiz)
            .setCancelable(false)
            .setNegativeButton("Cancle",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String answer = editText.getText().toString();
                            if (isCorrect(dataSnapshot, quizNum, answer)) {
                                noticeCorrect();
                                getScore(dataSnapshot, communityName);
                                addCompletedMission(dataSnapshot, quizNum, QUIZ_MISSION);
                                changeMission(dataSnapshot, QUIZ_MISSION);
                                resetCompletedMissionList();
                                loadCompletedMissionList(dataSnapshot.child("Community").child(communityName));
                            } else {
                                noticeWrong();
                            }
                        }
                    });

        quizPopUp.show();
    }

    public boolean isCorrect(DataSnapshot dataSnapshot, String quizNum, String answer) {
        return dataSnapshot.child("Mission").child("Answer").child(quizNum).getValue().equals(answer);
    }

    public void noticeCorrect() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MissionActivity.this);
        dialog.setTitle("Correct!");
        dialog.setMessage("You are correct! Congratulate! you get 50 score")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        dialog.show();
    }

    public void getScore(DataSnapshot dataSnapshot, String communityName) {
        int score = ((Long) dataSnapshot.child("Community").child(communityName).child("communityScore").getValue()).intValue();
        score += 500;

        mDatabase.child("Community").child(communityName).child("communityScore").setValue(score);
    }

    public void changeMission(DataSnapshot dataSnapshot, int mission) {
        Random random = new Random();

        int quizMissionNum = ((Long)dataSnapshot.child("Mission").child("Quiz").getChildrenCount()).intValue();
        Object quizMissionContent = (String) dataSnapshot.child("Mission").child("Quiz").child(String.valueOf(random.nextInt(quizMissionNum))).getKey();

        mDatabase.child("Community").child(communityName).child("quiz").child("missionName").setValue(quizMissionContent);
    }

    public void noticeWrong() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MissionActivity.this);
        dialog.setTitle("Wrong...");
        dialog.setMessage("You are wrong.. TT Chanllenge again");

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String currentProgress;
    }

    private static class ChildHolder {
        TextView title;
        TextView currentProgress;
    }

    private static class GroupHolder {
        TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.childTitle);
                holder.currentProgress = (TextView) convertView.findViewById(R.id.currentProgress);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            if ((!item.currentProgress.equals("0")) && (!item.currentProgress.equals("100"))) {
                holder.currentProgress.setText(item.currentProgress);
            } else {
                holder.currentProgress.setText(" ");
            }


            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}

