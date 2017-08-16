package com.pyeongchang.conch.conch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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

        commentView = (ListView) findViewById(R.id.comment_list_view);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment_close:
                onPause();
                onStop();
                onDestroy();
                break;
            case R.id.comment_add_button:
                EditText commentId = (EditText)findViewById(R.id.comment_add_button);
                String comment = commentId.getText().toString();
                String commentData = DateFormat.getDateTimeInstance().format(new Date());

                commentList.add(new Item(commentData, "1", comment));
                commentAdapter = new CommentCustomAdapter(commentList,this);
                commentView.setAdapter(commentAdapter);
                break;
        }

    }
}
