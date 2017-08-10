package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.idunnololz.widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MissionActivity extends AppCompatActivity {
    ScrollView scrollView;
    private AnimatedExpandableListView listView1;
    private AnimatedExpandableListView listView2;
    private ExampleAdapter adapter1;
    private ExampleAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        


        List<GroupItem> items1 = new ArrayList<GroupItem>();
        List<GroupItem> items2 = new ArrayList<GroupItem>();

        /****************processingMissionList Start********************/
        GroupItem processingMission = new GroupItem();
        processingMission.title = "Processing Mission";

        ChildItem runningMission = new ChildItem();
        runningMission.title = "Run 3km with your mebers!";
        processingMission.items.add(runningMission);

        ChildItem invitationMission = new ChildItem();
        invitationMission.title = "Invite those who is from KOREA!";
        processingMission.items.add(invitationMission);

        ChildItem quizMission = new ChildItem();
        quizMission.title = "Let's solve the quiz!";
        processingMission.items.add(quizMission);
        /****************processingMissionList End********************/

        /****************processedMissionList Start********************/
        GroupItem processedMission = new GroupItem();
        processedMission.title = "Processed Mission";

        for(int i = 0; i < 5; i++) {
            ChildItem child = new ChildItem();
            child.title = "Awesome item" + i;
            child.hint = "Too awesome";

            processedMission.items.add(child);
        }
        /****************processedMissionList End********************/

        items1.add(processingMission);
        items2.add(processedMission);

        adapter1 = new ExampleAdapter(this);
        adapter1.setData(items1);

        adapter2 = new ExampleAdapter(this);
        adapter2.setData(items2);

//        listView1 = (AnimatedExpandableListView) findViewById(R.id.processingMissionList);
//        listView1.setAdapter(adapter1);
//
//        listView2 = (AnimatedExpandableListView) findViewById(R.id.processedMissionList);
//        listView2.setAdapter(adapter2);

        listView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                if(listView1.isGroupExpanded(groupPosition)) {
                    listView1.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView1.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        listView2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                if(listView2.isGroupExpanded(groupPosition)) {
                    listView2.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView2.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });
    }

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
        TextView title;
        TextView hint;
    }

    private static class GroupHolder {
        TextView title;
    }

    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
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
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.title.setText(item.title);
            holder.hint.setText(item.hint);

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
            if(convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_items, parent, false);
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
