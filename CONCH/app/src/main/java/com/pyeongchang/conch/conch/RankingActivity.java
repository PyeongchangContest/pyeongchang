package com.pyeongchang.conch.conch;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private ArrayList<TorchCommunity> torchCommunityArrayList;
    private LinearLayout rankMyCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //탭 설정
        TabHost tabHost = (TabHost) findViewById(R.id.rankingTab);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1").setContent(R.id.tab1).setIndicator(getString(R.string.tab1));
        tabHost.addTab(spec1);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2").setContent(R.id.tab2).setIndicator(getString(R.string.tab2));
        tabHost.addTab(spec2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int index=1;
        torchCommunityArrayList=((MainActivity)MainActivity.mContext).getCommunityList();
        for (TorchCommunity torchCommunity:torchCommunityArrayList) {
            updateMyCommunityList(torchCommunity,index++);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void updateMyCommunityList(TorchCommunity torchCommunity, int index) {
        rankMyCommunity= (LinearLayout) findViewById(R.id.rank_myCommunity);
        LinearLayout addMyCommunity=new LinearLayout(this);

        TextView addCommunityNumber=new TextView(this);
        TextView addCommunityName=new TextView(this);
        TextView addCommunityScore=new TextView(this);

        addCommunityNumber.setText(String.valueOf(index));
        addCommunityNumber.setTextSize(24);
        addCommunityNumber.setGravity(Gravity.CENTER_VERTICAL);
        addCommunityNumber.setTextColor(Color.DKGRAY);

        addCommunityName.setText(torchCommunity.getCommunityName());
        addCommunityName.setTextSize(24);
        addCommunityName.setGravity(Gravity.CENTER_VERTICAL);
        addCommunityName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        addCommunityName.setTextColor(Color.BLUE);

        addCommunityScore.setText(String.valueOf(torchCommunity.getCommunityScore())+"p");
        addCommunityScore.setTextSize(24);
        addCommunityScore.setGravity(Gravity.CENTER_VERTICAL);
        addCommunityScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        addCommunityScore.setTextColor(R.color.comColor2);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        int numberWidth= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int nameWidth= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        p.setMargins(0,5,0,5);
        LinearLayout.LayoutParams numberParam=new LinearLayout.LayoutParams(numberWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams nameParam=new LinearLayout.LayoutParams(nameWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams scoreParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        addMyCommunity.addView(addCommunityNumber,numberParam);
        addMyCommunity.addView(addCommunityName,nameParam);
        addMyCommunity.addView(addCommunityScore,scoreParam);

        addMyCommunity.setBackgroundResource(R.color.comColor4);
        rankMyCommunity.addView(addMyCommunity,p);
    }
}
