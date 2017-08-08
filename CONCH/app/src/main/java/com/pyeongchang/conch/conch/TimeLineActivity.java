package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TimeLineActivity extends Activity implements TimeLineCustomAdapter.ListBtnClickListener{

    TextView text;
    TimeLineCustomAdapter[] cAdapter;
    final ArrayList<Item> timeLinelist = new ArrayList<>();
    ListView lv;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lv = (ListView)findViewById(R.id.timeline_list_view);
        newWirte();

        //parcelable 기반 꾸러미로 사용자가 입력한 정보 받아오기


    }
    //onNewIntent를 사용해서 타임라인을 누적!


    @Override
    protected void onResume() {
        super.onResume();
        newWirte();
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }
    @Override
    public void onListBtnClick(int position) {
        Intent intent = new Intent(this, CommentAddActivity.class);
        startActivity(intent);
        onStop();
    }

    public void newWirte(){
        Intent intent = getIntent();
        String post = intent.getStringExtra("newWrite");

        count++;
        timeLinelist.add(new Item(post));

        cAdapter[count] = new TimeLineCustomAdapter(timeLinelist, this);
        cAdapter[count].add(new Item(post));
        lv.setAdapter(cAdapter[count]);


    }

}
