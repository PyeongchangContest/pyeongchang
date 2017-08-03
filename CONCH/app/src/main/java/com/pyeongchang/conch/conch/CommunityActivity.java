package com.gayeon.practice.myapplication2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.pyeongchang.conch.conch.R;
import com.pyeongchang.conch.conch.RunnerListviewAdapter;
import com.pyeongchang.conch.conch.RunnerListviewItem;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word01", "Word02");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("This is ?", rotatable);

        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyyMMdd").format(date));

        ListView listView = (ListView) findViewById(R.id.runner_listview);

        ArrayList<RunnerListviewItem> data = new ArrayList<>();
        RunnerListviewItem pyeongchang = new RunnerListviewItem(R.drawable.emblem, "PyeongChang", today);
        RunnerListviewItem tiger = new RunnerListviewItem(R.drawable.horang, "Tiger", today);
        RunnerListviewItem bear = new RunnerListviewItem(R.drawable.bear, "Bear", today);
        RunnerListviewItem torch = new RunnerListviewItem(R.drawable.torch, "Torch", today);

        data.add(pyeongchang);
        data.add(tiger);
        data.add(bear);
        data.add(torch);

        RunnerListviewAdapter adapter = new RunnerListviewAdapter(this, R.layout.runner_listview_item, data);
        listView.setAdapter(adapter);
    }
}
