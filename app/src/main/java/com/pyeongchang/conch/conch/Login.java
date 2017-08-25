package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by bumsu on 2017-08-23.
 */

public class Login extends AppCompatActivity {

    EditText idText, passText;
    String Id, Pass;
    CheckBox chkAuto;

    FirebaseDatabase databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);
        chkAuto  = (CheckBox)findViewById(R.id.AutoLogin);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Boolean Auto = pref.getBoolean("chkAuto", false);
        if(Auto == true) {
            String AutoId = pref.getString("AutoId", "");
            String AutoPass = pref.getString("AutoPass", "");
            chkAuto.setChecked(Auto);
            idText.setText(AutoId);
            passText.setText(AutoPass);
            login(null);
        }
    }

    public void login(View v) {
        Id = idText.getText().toString();
        Pass = passText.getText().toString();
        chkAuto  = (CheckBox)findViewById(R.id.AutoLogin);
        databaseUsers = FirebaseDatabase.getInstance();
        databaseUsers.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean log = false;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (Id.equals(user.getId()) && Pass.equals(user.getPwd())) {
                        log = true;
                        Toast.makeText(getApplicationContext(), user.getUserName() + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                        if(chkAuto.isChecked() == true) {
                            SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putBoolean("chkAuto", chkAuto.isChecked());
                            editor.putString("AutoId", Id);
                            editor.putString("AutoPass", Pass);

                            editor.commit();
                        }
                        Intent main = new Intent(getApplication(), MainActivity.class);
                        main.putExtra("splash", "splash");
                        main.putExtra("user_id", Id);
                        main.putExtra("user_name", user.getUserName());
                        main.putExtra("user_country", user.getNation());
                        startActivity(main);
                        finish();
                    }
                }
                if(log == false) {
                    Toast.makeText(getApplicationContext(), "정확한 정보를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void member(View view){
        Intent member = new Intent(getApplication(), Member.class);
        member.putExtra("splash", "splash");
        startActivity(member);
    }
}