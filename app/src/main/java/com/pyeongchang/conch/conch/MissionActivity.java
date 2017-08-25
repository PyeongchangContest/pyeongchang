package com.pyeongchang.conch.conch;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idunnololz.widgets.AnimatedExpandableListView;
import com.idunnololz.widgets.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 *
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
public class MissionActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        communityName = getIntent().getStringExtra("communityName");

        adapter = new ExampleAdapter(getBaseContext());
        listView = (AnimatedExpandableListView) findViewById(R.id.listView);

        adapter2 = new ExampleAdapter(getBaseContext());
        listView2 = (AnimatedExpandableListView) findViewById(R.id.listView2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot communityRef = dataSnapshot.child("Community").child(communityName);

                processingMissions = new GroupItem();
                processingMissions.title = "Processing Missions";

                ChildItem racingMission = new ChildItem();
                racingMission.title = "Run " + communityRef.child("racingMission").child("missionName").getValue(String.class) + " with your members";
                processingMissions.items.add(racingMission);

                ChildItem invitationMission = new ChildItem();
                invitationMission.title = "Invite a " + communityRef.child("invitationMission").child("missionName").getValue(String.class) + " person";
                processingMissions.items.add(invitationMission);

                ChildItem quizMission = new ChildItem();
                quizMission.title = communityRef.child("quiz").child("missionName").getValue(String.class);
                processingMissions.items.add(quizMission);

                items.add(processingMissions);
                adapter.setData(items);
                listView.setAdapter(adapter);

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

                //completedMissionListView Start
                completedMissions = new GroupItem();
                completedMissions.title = "Completed Missions";

                DataSnapshot completedMissionRef = communityRef.child("completedMission");
                if (((Long)completedMissionRef.getChildrenCount()).intValue() != 0) {

                    for(DataSnapshot singleDataSnapshot : completedMissionRef.getChildren()) {
                        ChildItem completedList = new ChildItem();
                        completedList.title = singleDataSnapshot.getValue(String.class);
                        completedMissions.items.add(completedList);
                    }

                    items2.add(completedMissions);
                    adapter2.setData(items2);
                    listView2.setAdapter(adapter2);

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
    }

    private static class ChildHolder {
        TextView title;
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
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

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

}

