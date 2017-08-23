package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentAddActivity extends AppCompatActivity {

    final ArrayList<Item> commentList = new ArrayList<>();
    CommentCustomAdapter commentAdapter;
    ListView commentView;
    int count;
    int commentCount = 0;
    String communityName;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_add);

        //게시글 받아오기
        TextView name = (TextView)findViewById(R.id.comment_name);
        TextView content = (TextView)findViewById(R.id.comment_content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        content.setText(intent.getStringExtra("content"));
        communityName = intent.getStringExtra("communityName");
        count = intent.getIntExtra("count",0);

        commentView = (ListView) findViewById(R.id.comment_list_view);

        commentAdapter = new CommentCustomAdapter(commentList,this, R.layout.comment_list_item);
        commentView.setAdapter(commentAdapter);

        findViewById(R.id.comment_add_button).setOnClickListener(clickListener);

        databaseReference.child("timeLine").child(communityName).child(String.valueOf(count)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    String str = fileSnapshot.child("content").getValue(String.class);
                    String name = fileSnapshot.child("name").getValue(String.class);
                    String date = fileSnapshot.child("date").getValue(String.class);
                    if(str != null && name != null && date != null)
                          commentList.add(new Item(date,1,name,str));
                }
                commentCount = commentList.size();
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG: ", "Failed to read value", databaseError.toException());
            }
        });

    }
    private Button.OnClickListener clickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            EditText comment_content = (EditText)findViewById(R.id.comment_enter);
            ImageButton profile_img = (ImageButton)findViewById(R.id.comment_img);

            //사용자 이미지 받아오기
            String comment = comment_content.getText().toString();
            String commentData = DateFormat.getDateTimeInstance().format(new Date());

            //데이터베이스에 저장
           Item itemData = new Item(commentData,1,"username",comment);
            databaseReference.child("timeLine").child(communityName).child(String.valueOf(count)).child(String.valueOf(commentCount)).setValue(itemData);

           // commentList.add(new Item(commentData,1, "1", comment));
            commentAdapter.notifyDataSetChanged();
            comment_content.setText("");

        }
    };



}
