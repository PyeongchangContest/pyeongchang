package com.pyeongchang.conch.conch;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupMail extends AppCompatActivity {
    private LinearLayout mailBoxLayout;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private User user;
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
    private void drawMail(final String contents, int type){
        mailBoxLayout = (LinearLayout) findViewById(R.id.mailbox_layout);
        ImageButton closeButton=new ImageButton(this);
        int textWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,280, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams textParam=new LinearLayout.LayoutParams(textWidth,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams closeBtnParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout addMailInfo=new LinearLayout(this);

        switch (type){
            case MAIL_TO_INFORM:
                TextView textInfo = new TextView(this);

                textInfo.setText(contents);
                textInfo.setTextSize(13);
                textInfo.setGravity(Gravity.CENTER_VERTICAL);
                textInfo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textInfo.setTextColor(R.color.Ivory);
                addMailInfo.addView(textInfo, textParam);
                addMailInfo.setBackgroundResource(R.color.comColor4);
                break;
            case MAIL_TO_INVITE:

                Button buttonInfo = new Button(this);

                buttonInfo.setText(contents);
                buttonInfo.setTextSize(13);
                buttonInfo.setTypeface(null, Typeface.BOLD);
                buttonInfo.setGravity(Gravity.CENTER_VERTICAL);
                buttonInfo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                buttonInfo.setTextColor(R.color.DarkRed);
                addMailInfo.addView(buttonInfo, textParam);
                addMailInfo.setBackgroundResource(R.color.PapayaWhip);
                buttonInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PopupMail.this);
                        builder.setMessage("해당 커뮤니티에 참석하시겠습니까?");
                        builder.setTitle("메시지").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                String communityName=contents.substring(5);
                                enterCommunity(communityName);
                                Toast.makeText(PopupMail.this, communityName+"에 오신 것을 환영합니다.", Toast.LENGTH_SHORT).show();
                                mailBoxLayout.removeView(addMailInfo);
                                deleteMessage(contents);
                                finish();
                                ((MainActivity)MainActivity.mContext).loadMyCommunityList();
                            }
                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("커뮤니티 참가");
                        alert.show();
                    }
                });
                break;
        }
        closeButton.setBackgroundResource(R.drawable.btn_back);


        addMailInfo.addView(closeButton,closeBtnParam);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PopupMail.this);
                builder.setMessage("이 메시지를 삭제하시겠습니까?");
                builder.setTitle("메시지").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteMessage(contents);
                        Toast.makeText(PopupMail.this, "메시지 삭제 완료", Toast.LENGTH_SHORT).show();
                        mailBoxLayout.removeView(addMailInfo);
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("메시지 삭제");
                alert.show();
            }
        });
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        p.setMargins(0,5,0,5);
        mailBoxLayout.addView(addMailInfo,p);

    }

    private void enterCommunity(final String communityName) {
        user.getCommunityList().add(communityName);


        mDatabase.child("Community").child(communityName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TorchCommunity chosenCommunity = dataSnapshot.getValue(TorchCommunity.class);
                int population=chosenCommunity.getUserList().size();
                mDatabase.child("Community").child(communityName).child("userList").child(String.valueOf(population)).setValue(user.getId());
                mDatabase.child("Users").child(user.getId()).child("communityList").child(String.valueOf(user.getCommunityList().size()-1)).setValue(communityName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteMessage(final String contents) {
        mDatabase.child("Users").child(user.getId()).child("msgList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getValue(String.class).equals(contents)){
                        Log.e("(삭제 메시지 발견)",contents);
                        mDatabase.child("Users").child(user.getId()).child("msgList").child(String.valueOf(user.getMsgList().indexOf(contents))).removeValue();
                        user.getMsgList().remove(contents);
                        Log.e("(삭제 완료)",contents);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user=((MainActivity)MainActivity.mContext).getUser();
        for (String index:user.getMsgList()){
            if (index.startsWith("초대")){
                drawMail(index,MAIL_TO_INVITE);
            }else
                drawMail(index,MAIL_TO_INFORM);
        }
    }
}
