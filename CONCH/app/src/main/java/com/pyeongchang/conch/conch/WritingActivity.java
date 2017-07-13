package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class WritingActivity extends AppCompatActivity {

    DBAdapter dbAdapter;
    PostDBAdapter postDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        postDB = new PostDBAdapter(this);
        postDB.open();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close:
                finish();
                break;
            case R.id.post:
                EditText editId = (EditText)findViewById(R.id.newWriting);
                String newWrite = editId.getText().toString();
                postDB.createPost("1", newWrite, "1");
                ArrayList<String> array_list = (ArrayList) postDB.getAllPost();
                Log.d("writingact", array_list.get(0));
                Intent intent = new Intent(this, TimeLineActivity.class);
                intent.putExtra("newWrite", newWrite);
                startActivity(intent);
                break;
        }
    }
}
