package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by bumsu on 2017-08-23.
 */

public class Member extends AppCompatActivity {

    EditText NAME,PASS,PASSSIGN,ID;
    Spinner COUNTRY;
    String Tname, Tpass, Tpasssign, Tid, Tcountry;
    String array_spinner[];
    private ArrayList<String> alreadyRegisteredId=new ArrayList();
    DatabaseReference databaseUsers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        NAME = (EditText) findViewById(R.id.name);
        PASS = (EditText) findViewById(R.id.password);
        PASSSIGN = (EditText) findViewById(R.id.passsign);
        ID = (EditText) findViewById(R.id.id);
        COUNTRY = (Spinner) findViewById(R.id.spCountry);

        array_spinner = new String[1];
        array_spinner[0] = "South Korea";

        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        checkIdInFirebase();

        Spinner citizenship = (Spinner)findViewById(R.id.spCountry);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        citizenship.setAdapter(adapter);
    }

    public void join(View view){

        Tname = NAME.getText().toString();
        Tpass = PASS.getText().toString();
        Tpasssign = PASSSIGN.getText().toString();
        Tid = ID.getText().toString();
        Tcountry = COUNTRY.getSelectedItem().toString();

        if (Tname.length() <= 0) {
            Toast.makeText(getApplicationContext(), "이름을 정확하게 입력하세요.", Toast.LENGTH_SHORT).show();
        }else if (Tpass.length() <6) {
            Toast.makeText(getApplicationContext(), "비밀번호를 6자리 이상 입력하세요.", Toast.LENGTH_SHORT).show();
        } else if (!Tpasssign.equals(Tpass)) {
            Toast.makeText(getApplicationContext(), "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
        } else if (Tid.length() < 5 ){
            Toast.makeText(getApplicationContext(), "아이디는 5글자 이상입니다.", Toast.LENGTH_SHORT).show();
        } else if(!isValidId(Tid)){
            Toast.makeText(getApplicationContext(), "이미 가입된 아이디입니다.", Toast.LENGTH_SHORT).show();
        }else if (Tcountry.length() <= 0) {
            Toast.makeText(getApplicationContext(), "국가를 선택해 주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            User User = new User(Tname, Tid, Tpass, Tcountry);
            databaseUsers.child(Tid).setValue(User);

            Toast.makeText(getApplication(), Tname + "님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(getApplication(), Login.class);
            login.putExtra("splash", "splash");
            startActivity(login);
            finish();
       }
    }
    private boolean isValidId(String givenId){
        boolean result=true;
        for (String target : alreadyRegisteredId){
            if (target.equals(givenId))
                result=false;
        }
        return result;
    }
    private void checkIdInFirebase(){

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index=0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        alreadyRegisteredId.add(index++,postSnapshot.getKey());
                        Log.i(index-1+"번째",alreadyRegisteredId.get(index-1));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}