package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class WritingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close:
                finish();
                break;
            case R.id.post:
                EditText editId = (EditText)findViewById(R.id.newWriting);
                String newWrite = editId.getText().toString();

                Intent intent;
                intent = new Intent();
                intent.putExtra("newWrite", newWrite);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}
