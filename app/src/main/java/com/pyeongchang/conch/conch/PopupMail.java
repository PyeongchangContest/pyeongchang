package com.pyeongchang.conch.conch;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class PopupMail extends AppCompatActivity {
    private LinearLayout mailBoxLayout;
    private static final int MAIL_TO_INFORM=0;
    private static final int MAIL_TO_INVITE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_mail);

    }
    public void onClick(View view){
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void drawMail(String contents, int type){
        mailBoxLayout = (LinearLayout) findViewById(R.id.mailbox_layout);

        LinearLayout.LayoutParams nameParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout addMailInfo=new LinearLayout(this);

        switch (type){
            case MAIL_TO_INFORM:
                TextView textInfo = new TextView(this);

                textInfo.setText(contents);
                textInfo.setTextSize(24);
                textInfo.setGravity(Gravity.CENTER_VERTICAL);
                textInfo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textInfo.setTextColor(Color.BLUE);

                addMailInfo.addView(textInfo, nameParam);
                break;
            case MAIL_TO_INVITE:

                Button buttonInfo = new Button(this);

                buttonInfo.setText(contents);
                buttonInfo.setTextSize(24);
                buttonInfo.setGravity(Gravity.CENTER_VERTICAL);
                buttonInfo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                buttonInfo.setTextColor(Color.BLUE);

                addMailInfo.addView(buttonInfo, nameParam);
                break;
        }

        addMailInfo.setBackgroundResource(R.color.comColor4);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        p.setMargins(0,5,0,5);
        mailBoxLayout.addView(addMailInfo,p);

    }
}
