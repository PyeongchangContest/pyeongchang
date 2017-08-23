package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentAddActivity extends AppCompatActivity {

    final ArrayList<Item> commentList = new ArrayList<>();
    CommentCustomAdapter commentAdapter;
    ListView commentView;

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

        commentView = (ListView) findViewById(R.id.comment_list_view);

        commentAdapter = new CommentCustomAdapter(commentList,this);
        commentView.setAdapter(commentAdapter);

        findViewById(R.id.comment_add_button).setOnClickListener(clickListener);

    }
    private Button.OnClickListener clickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            EditText comment_content = (EditText)findViewById(R.id.comment_enter);
            String comment = comment_content.getText().toString();
            String commentData = DateFormat.getDateTimeInstance().format(new Date());

            commentList.add(new Item(commentData, "1", comment));
            commentAdapter.notifyDataSetChanged();
            comment_content.setText("");

        }
    };

}
