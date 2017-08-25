package com.pyeongchang.conch.conch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by bumsu on 2017-08-23.
 */

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;

    private TextView tvdetails;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private Button eloginButton;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            // Application code
                            try {
                                Log.d("tttttt",object.getString("id"));
                                String birthday="";
                                if(object.has("birthday")){
                                    birthday = object.getString("birthday"); // 01/31/1980 format
                                }

                                String fnm = object.getString("first_name");
                                String lnm = object.getString("last_name");
                                String mail = object.getString("email");
                                String gender = object.getString("gender");
                                String fid = object.getString("id");
                                String country = object.getString("country");
                                tvdetails.setText(fnm+" "+lnm+" \n"+mail+" \n"+gender+" \n"+fid+" \n"+country+" \n"+birthday);

                                User User = new User(fnm+lnm, fid, null, country);
                                databaseUsers.child(fid).setValue(User);
                                Toast.makeText(getApplication(), fnm + lnm + "님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainIntent.putExtra("user_id", fid);
//                                mainIntent.putExtra("user_name", fnm+lnm);
//                                mainIntent.putExtra("user_country", country);
                                LoginActivity.this.startActivity(mainIntent);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email, gender, country, birthday, location");
            request.setParameters(parameters);
            request.executeAsync();
            Profile profile = Profile.getCurrentProfile();
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            mainIntent.putExtra("user_id", profile.getId());
            LoginActivity.this.startActivity(mainIntent);
            finish();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);

        tvdetails = (TextView) findViewById(R.id.text);

        loginButton = (LoginButton) findViewById(R.id.btnfb);

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, callback);

        if(AccessToken.getCurrentAccessToken() != null) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(mainIntent);
        }

        eloginButton = (Button) findViewById(R.id.btnsie);
        eloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                LoginActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }
}