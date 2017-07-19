package com.pyeongchang.conch.conch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentAddActivity extends AppCompatActivity {

    final ArrayList<Item> commentList = new ArrayList<>();
    CommentCustomAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_add);



        ListView commentView = (ListView) findViewById(R.id.comment_list_view);
        commentAdapter = new CommentCustomAdapter(commentList,this);
        commentView.setAdapter(commentAdapter);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment_close:
                finish();
                break;
            case R.id.comment_add_button:
                EditText commentId = (EditText)findViewById(R.id.comment_add_button);
                String comment = commentId.getText().toString();

                commentList.add(new Item(comment));
                break;
        }

    }
}
