package com.pyeongchang.conch.conch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bumsu on 2017-08-24.
 */

public class InviteActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;

    ListView listViewUsers;

    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        listViewUsers = (ListView) findViewById((R.id.listViewUsers));


        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int index, long l) {
//                User User = userList.get(i);
//                Intent intent = getIntent();
//                String communityName = intent.getStringExtra("communityName");
//                User.addInviteList(communityName);

                // 수정중
                AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);
                builder.setMessage("초대하시겠습니까?");
                builder.setTitle("초대").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        inviteOtherUser(index);
                        finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("초대 알림창");
                alert.show();

            }

        });
    }

    private void inviteOtherUser(int index){



        Toast.makeText(InviteActivity.this, userList.get(index).getUserName()+"님이 초대되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super .onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

        userList = new ArrayList<>();

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    userList.add(user);
                }
                InviteHelpActivity adapter = new InviteHelpActivity(InviteActivity.this, userList);
                listViewUsers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
