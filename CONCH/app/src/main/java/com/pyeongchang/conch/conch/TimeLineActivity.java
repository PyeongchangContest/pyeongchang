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
    TimeLineCustomAdapter cAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ListView lv = (ListView)findViewById(R.id.timeline_list_view);

        Intent intent = getIntent();
        String post = intent.getStringExtra("newWrite");

        final ArrayList<Item> timeLinelist = new ArrayList<>();
        timeLinelist.add(new Item(post));


        cAdapter = new TimeLineCustomAdapter(timeLinelist, this);
        lv.setAdapter(cAdapter);

    }


    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }
    @Override
    public void onListBtnClick(int position){
        Intent intent = new Intent(this, CommentAddActivity.class);
        startActivity(intent);
    }

}
