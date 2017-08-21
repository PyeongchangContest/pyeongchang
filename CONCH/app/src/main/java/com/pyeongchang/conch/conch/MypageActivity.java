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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity {
    ArrayList<TorchCommunity> torchCommunityArrayList;


    private LinearLayout mypageScrollLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addCommunityListInMypage(TorchCommunity addTorchCommunity, int index){
        TextView addTextView=new TextView(this);
        addTextView.setText(addTorchCommunity.getCommunityName());
        addTextView.setTextSize(30);
        addTextView.setTextColor(Color.WHITE);
        addTextView.setGravity(Gravity.CENTER);
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        Log.i("(테스트)커뮤니티 크기: ",String.valueOf(index));
        switch (index) {
            case 1:
                addTextView.setBackgroundResource(R.color.comColor1);
                Log.i("(테스트)1 ",String.valueOf(index));
                break;
            case 2:
                addTextView.setBackgroundResource(R.color.comColor2);
                Log.i("(테스트)2",String.valueOf(index));
                break;
            case 3:
                addTextView.setBackgroundResource(R.color.comColor3);
                Log.i("(테스트)3 ",String.valueOf(index));
                break;
            case 4:
                addTextView.setBackgroundResource(R.color.comColor4);
                Log.i("(테스트)4 ",String.valueOf(index));
                break;
            default:
                addTextView.setBackgroundResource(R.color.comColor5);
                Log.i("(테스트)5 ",String.valueOf(index));
        }
        mypageScrollLayout.addView(addTextView,p);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int index=1;
        mypageScrollLayout= (LinearLayout) findViewById(R.id.mypage_communityList);

        torchCommunityArrayList=((MainActivity)MainActivity.mContext).getCommunityList();
        for (TorchCommunity torchCommunity:torchCommunityArrayList) {
            addCommunityListInMypage(torchCommunity,index++);
        }
    }
}
