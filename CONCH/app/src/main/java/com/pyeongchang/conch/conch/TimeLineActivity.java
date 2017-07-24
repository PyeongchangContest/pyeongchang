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
    final ArrayList<Item> timeLinelist = new ArrayList<>();
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        lv = (ListView)findViewById(R.id.timeline_list_view);



        //3주차 ppt보고 보안 activity간에 정보 교환하는거
        //함수를 만들어서 넘겨버리기
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

     //   timeLinelist.add(new Item(post));

      //  cAdapter = new TimeLineCustomAdapter(timeLinelist, this);
        cAdapter.add(new Item(post));
        lv.setAdapter(cAdapter);

    }

}
