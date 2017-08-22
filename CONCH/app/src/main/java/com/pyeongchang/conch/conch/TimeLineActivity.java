package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeLineActivity extends Activity {

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
        Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();

        //게시글 얻어오기
        cAdapter= new TimeLineCustomAdapter(timeLinelist, this);
        //리스트 뷰에 어뎁터 등록
        lv.setAdapter(cAdapter);



    }
    //리스트뷰 재구성
    @Override
    protected void onResume() {
        super.onResume();
        cAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 0) {
            String response = data.getStringExtra("newWrite");
            //날짜 시간
            String currentData = DateFormat.getDateTimeInstance().format(new Date());

            timeLinelist.add(new Item(currentData, likeCount, "1", response));
            cAdapter.notifyDataSetChanged();
        }

    }
    //onNewIntent를 사용해서 타임라인을 누적!



    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivityForResult(intent,0);

    }


}
