package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeLineActivity extends Activity implements AdapterView.OnItemClickListener {

    TextView text;
    TimeLineCustomAdapter cAdapter;
    final ArrayList<Item> timeLinelist = new ArrayList<>();
    ListView lv;
    int count = 0;
    String communityName;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lv = (ListView) findViewById(R.id.timeline_list_view);

        Intent intent = getIntent();
        communityName = intent.getStringExtra("commnityName");

        //게시글 얻어오기
        cAdapter = new TimeLineCustomAdapter(timeLinelist, this, R.layout.timeline_list_item);
        //리스트 뷰에 어뎁터 등록
        lv.setAdapter(cAdapter);
        lv.setOnItemClickListener(this);

        databaseReference.child("timeLine").child(communityName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeLinelist.clear();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {

                    String str = fileSnapshot.child("content").getValue(String.class);
                    String name = fileSnapshot.child("name").getValue(String.class);
                    String date = fileSnapshot.child("date").getValue(String.class);
                    timeLinelist.add(new Item(date, 1, name, str));
                }
                count = timeLinelist.size();
                cAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });

    }

    //리스트뷰 재구성
    @Override
    protected void onResume() {
        super.onResume();
        cAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {

                final String response = data.getStringExtra("newWrite");
                //날짜 시간
                final String currentData = DateFormat.getDateTimeInstance().format(new Date());
                //사용자 이미지

                //firebase 저장
                Item itemData = new Item(currentData, 1, "username", response);
                databaseReference.child("timeLine").child(communityName).child(String.valueOf(count)).setValue(itemData);

                //timeLinelist.add(new Item(currentData, 1, "username", response));
                cAdapter.notifyDataSetChanged();
            } else
                cAdapter.notifyDataSetChanged();
        }

    }
    //onNewIntent를 사용해서 타임라인을 누적!

    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), CommentAddActivity.class);
        intent.putExtra("name", timeLinelist.get(i).name);
        intent.putExtra("content", timeLinelist.get(i).content);
        intent.putExtra("count",i);
        intent.putExtra("communityName",communityName);

        startActivity(intent);

    }
    public int getCount(){
        return this.count;
    }
}
