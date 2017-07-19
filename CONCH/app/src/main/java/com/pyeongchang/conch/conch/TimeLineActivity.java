package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TimeLineActivity extends Activity{

    TextView text;
    CustomAdapter cAdapter;
    PostDBAdapter postDB;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ListView lv = (ListView)findViewById(R.id.listView1);

        Intent intent = getIntent();
        String post = intent.getStringExtra("newWrite");

        final ArrayList<Item> list = new ArrayList<Item>();
        list.add(new Item(post));

        cAdapter = new CustomAdapter(list, this);
        lv.setAdapter(cAdapter);

        postDB = new PostDBAdapter(this);
        postDB.open();
        ArrayList<String> array_list = (ArrayList) postDB.getAllPost();
        TextView tv = findViewById(R.id.content);
        tv.setText(array_list.get(0));
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, WritingActivity.class);
        startActivity(intent);
    }
}
