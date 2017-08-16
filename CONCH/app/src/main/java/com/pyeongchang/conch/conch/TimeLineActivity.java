package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;



public class TimeLineActivity extends Activity implements TimeLineCustomAdapter.ListBtnClickListener{

    TextView text;
    TimeLineCustomAdapter cAdapter;
    final ArrayList<Item> timeLinelist = new ArrayList<>();
    ListView lv;
    int count = 0;
    int likeCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lv = (ListView)findViewById(R.id.timeline_list_view);
        newWrite();



    }
    //onNewIntent를 사용해서 타임라인을 누적!



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

    public void newWrite(){
        Intent intent = getIntent();
        String post = intent.getStringExtra("newWrite");
        String currentData = DateFormat.getDateTimeInstance().format(new Date());

        count++;
        timeLinelist.add(new Item(currentData, likeCount, "1", post));

        cAdapter= new TimeLineCustomAdapter(timeLinelist, this);
        lv.setAdapter(cAdapter);


    }

}
