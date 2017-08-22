package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeLineActivity extends Activity implements AdapterView.OnItemClickListener {

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


        //게시글 얻어오기
        cAdapter= new TimeLineCustomAdapter(timeLinelist, this, R.layout.timeline_list_item);
        //리스트 뷰에 어뎁터 등록
        lv.setAdapter(cAdapter);
        lv.setOnItemClickListener(this);

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
            if(data != null) {
                String response = data.getStringExtra("newWrite");
                //날짜 시간
                String currentData = DateFormat.getDateTimeInstance().format(new Date());

                timeLinelist.add(new Item(currentData, likeCount, "1", response));
                cAdapter.notifyDataSetChanged();
            }
            else
                cAdapter.notifyDataSetChanged();
        }

    }
    //onNewIntent를 사용해서 타임라인을 누적!

    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivityForResult(intent,0);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), CommentAddActivity.class);
        intent.putExtra("name",timeLinelist.get(i).name);
        intent.putExtra("content",timeLinelist.get(i).content);

        startActivity(intent);

    }
}
