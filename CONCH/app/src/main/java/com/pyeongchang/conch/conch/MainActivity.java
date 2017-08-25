package com.pyeongchang.conch.conch;


import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
    private static final int LOCATION_PERMISSION_IN_PROJECT = 1;
    private DrawerLayout dlDrawer;
    private LinearLayout llDrawer;
    private PopupWindow mPopupWindow;
    private ConstraintLayout mConstraintLayout;
    private ArrayList<TorchCommunity> communityList = new ArrayList<>();
    private List<String> loadCommunityList=new ArrayList<>();
    private TextView infoTorchRank;
    private TextView infoTorchName;
    private TextView infoTorchScore;
    private TextView infoTorchState;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private TextView infoSumOfTorch;
    private TextView runningDistance;
    private ArrayList<String> alreadyRegistered=new ArrayList<>();


    private User user;



    private Button sendTorchBtn;
    BroadcastReceiver broadcastReceiver;
    // GPSTracker class
    private GpsInfo gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바 수정
        getSupportActionBar().setTitle("PyeonChang2018");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff339999));

        //유저 로딩
        final Intent intent = getIntent();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user= dataSnapshot.child("Users").child(intent.getExtras().getString("user_id")).getValue(User.class);
                loadMyCommunityList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        user = new User(
//                intent.getExtras().getString("user_name"),
//                intent.getExtras().getString("user_id"),
//                intent.getExtras().getString("user_country"));


        mContext = this;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_IN_PROJECT);

                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다

            }
        }
        CarouselFragment cf = CarouselFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_body, cf);
        fragmentTransaction.commit();
    }

    public void popupTorch() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.activity_popup_create_torch, (ViewGroup) findViewById(R.id.popup_layout));
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.layout_body);
        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton closeButton = customView.findViewById(R.id.ib_close);
        final Button createTorchBtn = customView.findViewById(R.id.create_TorchBtn);
        final TextView textView = customView.findViewById(R.id.create_maxPeopleValue);
        final SeekBar sb;
        int value = 20;
        sb = customView.findViewById(R.id.create_maxPeople);
        sb.setProgress(value);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });
        createTorchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //성화정보들

                TextView torchName = customView.findViewById(R.id.create_torchName);
                TextView torchMaxPeople = customView.findViewById(R.id.create_maxPeopleValue);
                ToggleButton isSecretCommunity = customView.findViewById(R.id.create_isSecret);
                String tName = torchName.getText().toString();
                if (tName.equals("")) {
                    Toast.makeText(MainActivity.this, "성화 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if (isValidCommunityName(tName)){
                    Toast.makeText(MainActivity.this, "이미 생성된 이름입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    int tMaxPeople = Integer.parseInt(torchMaxPeople.getText().toString());
                    boolean isSecret = isSecretCommunity.isChecked();
                    //성화 커뮤니티 객체 추가
                    /*****이곳에 DB에 저장하는 것을 추가해야함. communityList도 로그인 정보를 받아와서 해당 user의 정보에 추가해야한다고 생각됨**********/
                    /**커뮤니티 생성하자마자 미션 3개를 배정해야 함!! -> 미션 배정하는 메소드 필요**/
                    Calendar calendar = Calendar.getInstance();
                    java.util.Date date = calendar.getTime();
                    String today = (new SimpleDateFormat("yyyy-MM-dd").format(date));

                    TorchCommunity addTorchCommunity = new TorchCommunity(user.getUserName(), today,tName,tMaxPeople,isSecret,user.getId());
                    /******************************************
                     addTorchCommunity.runner를 현재 로그인되어있는 사용자로 set해주는 부분
                     addTorchCommunity.route에 현재 로그인되어있는 사용자의 nation을 add해주는 부분
                     **********************************************************/
                    generateMission(addTorchCommunity);
                    communityList.add(addTorchCommunity); // 추후 수정 대상으로 고려 필요

                    mDatabase.child("Users").child(user.getId()).child("communityList").child(String.valueOf(user.getCommunityList().size())).setValue(tName);
                    user.getCommunityList().add(tName);
                    CarouselFragment carouselFragment = (CarouselFragment) getFragmentManager().findFragmentById(R.id.layout_body);
                    carouselFragment.createNewTorch(tName);

                    countAllCommunityAndUpdateCommunityRank();

                    int index=carouselFragment.getTorchList().size()-1;
                    carouselFragment.getmCarouselView().scrollToChild(index);
                    carouselFragment.getmCarouselView().notifyDataSetChanged();

                    mPopupWindow.dismiss();
                }
            }
        });

        mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER, 0, 0);

    }

    private boolean isValidCommunityName(final String name) {
        boolean registered = false;
        for (String index:alreadyRegistered){
            if (index.equals(name)){
                registered=true;
            }
        }
        return registered;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sliderBtn) {
            llDrawer = (LinearLayout) findViewById(R.id.sliderMenu);
            dlDrawer = (DrawerLayout) findViewById(R.id.activity_main);
            dlDrawer.openDrawer(llDrawer);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeTorchInfo(String name, int score, int rank) {
        infoTorchName.setText(name);
        infoTorchScore.setText(String.valueOf(score));
        infoTorchRank.setText(String.valueOf(rank));
    }

    public void visibilityOfTorchInfo(boolean visible) {
        if (visible) {
            infoTorchState.setVisibility(View.INVISIBLE);
            infoTorchName.setVisibility(View.VISIBLE);
            infoTorchScore.setVisibility(View.VISIBLE);
            infoTorchRank.setVisibility(View.VISIBLE);
            infoSumOfTorch.setVisibility(View.VISIBLE);
        } else {
            infoTorchState.setVisibility(View.VISIBLE);
            infoTorchName.setVisibility(View.INVISIBLE);
            infoTorchScore.setVisibility(View.INVISIBLE);
            infoTorchRank.setVisibility(View.INVISIBLE);
            infoSumOfTorch.setVisibility(View.INVISIBLE);
        }
    }

    public void changeDistanceText(String distance) {
        runningDistance.setText(distance);
    }


    public void generateMission(final TorchCommunity torchCommunity) {
        mDatabase.child("Mission").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object runningMissionContent = dataSnapshot.child("Racing").child(String.valueOf(torchCommunity.getRacingLevel())).getValue();
                MissionItem runningMission = new MissionItem(runningMissionContent.toString());
                torchCommunity.setRacingMission(runningMission);

                Random random = new Random();
                int invitationMissionNum = ((Long)dataSnapshot.child("Invitation").getChildrenCount()).intValue();
                Object invitationMissionContent = (String) dataSnapshot.child("Invitation").child(String.valueOf(random.nextInt(invitationMissionNum))).getValue();
                MissionItem invitationMission = new MissionItem(invitationMissionContent.toString());
                torchCommunity.setInvitationMission(invitationMission);

                int quizMissionNum = ((Long)dataSnapshot.child("Quiz").getChildrenCount()).intValue();
                Object quizMissionContent = (String) dataSnapshot.child("Quiz").child(String.valueOf(random.nextInt(quizMissionNum))).getValue();
                MissionItem quizMission = new MissionItem(quizMissionContent.toString());
                torchCommunity.setQuiz(quizMission);

                writeNewCommunity(torchCommunity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    public void writeNewCommunity(TorchCommunity torchCommunity) {
        mDatabase.child("Community").child(torchCommunity.getCommunityName()).setValue(torchCommunity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_IN_PROJECT:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
// 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
// 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ImageButton profileBtn = (ImageButton) findViewById(R.id.profileBtn);//프로필 버튼
        ImageButton mailboxBtn = (ImageButton) findViewById(R.id.mailboxBtn);//메일함 버튼
        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);//세팅 버튼
        ImageButton rankingBtn = (ImageButton) findViewById(R.id.rankingBtn);//랭킹 버튼
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);//프로그레스바 버튼

        final RelativeLayout runFinishedLayout = (RelativeLayout) findViewById(R.id.layout_finished);//3km 뛴 후 레이아웃
        final RelativeLayout runProgressedLayout = (RelativeLayout) findViewById(R.id.layout_walking);//3km 뛰기 전 레이아웃

        infoTorchName = (TextView) findViewById(R.id.main_torchName);//성화 이름
        infoTorchScore = (TextView) findViewById(R.id.main_torchScore);//성화 점수
        infoTorchRank = (TextView) findViewById(R.id.main_torchRank);//성화 랭킹
        infoTorchState=(TextView)findViewById(R.id.main_torchInform);//성화생성정보
        infoSumOfTorch = (TextView) findViewById(R.id.main_sumOfTorch);//총 성화 갯수

        runningDistance = (TextView) findViewById(R.id.progressText_now);//뛴 거리


        profileBtn.setOnClickListener(new View.OnClickListener() {//프로필 버튼 클릭시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });
        mailboxBtn.setOnClickListener(new View.OnClickListener() {//메일 버튼 클릭시
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PopupMail.class));
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {//설정 버튼 클릭시
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PopupSetting.class));
            }
        });
        rankingBtn.setOnClickListener(new View.OnClickListener() {//랭킹 버튼 클릭시
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
            }
        });


        // 유저 정보 생성
        sendTorchBtn = (Button) findViewById(R.id.sendButton);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.pyeongchang.conch.conch.SEND_DISTANCE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                float distance = intent.getFloatExtra("distance", 0);
                float a=intent.getFloatExtra("currentDistance",0);
                changeDistanceText(String.valueOf(distance));
                addDistanceToCommunityDistance(a);
                progressBar.setProgress((int) distance);
                if (progressBar.getProgress() >= 3000) {
                    runProgressedLayout.setVisibility(View.GONE);
                    runFinishedLayout.setVisibility(View.VISIBLE);
                    gps = null;
                    unregisterReceiver(broadcastReceiver);
                }
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        gps = new GpsInfo(MainActivity.this);


        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        sendTorchBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, InviteActivity.class));
            }
        });
        countAllCommunityAndUpdateCommunityRank();

    }


    public TorchCommunity updateCommunityRankingInFirebase(final TorchCommunity torchCommunity){
        final String targetCommunityName=torchCommunity.getCommunityName();

        mDatabase.child("Community").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.getKey().equals(targetCommunityName)){
                            postSnapshot.getValue(TorchCommunity.class);
                            int rank=Integer.parseInt(postSnapshot.child("communityRank").getValue().toString());
                            int score=Integer.parseInt(postSnapshot.child("communityScore").getValue().toString());
                            Log.e("(테스트)","계산된 랭킹 : "+rank);
                            torchCommunity.setCommunityRank(rank);
                            torchCommunity.setCommunityScore(score);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return torchCommunity;
    }
    private void addDistanceToCommunityDistance(float a) {
        final float currentDistance = a;

        //사용자가 속한 커뮤니티 모두에 해당 거리를 더해주고 해당 값을 db에 다시 저장
        mDatabase.child("Community").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i=0; i < loadCommunityList.size(); i++) {
                    String a=loadCommunityList.get(i);
                    DataSnapshot communityDataSnapshot = dataSnapshot.child(a);

                    float totalDistance = communityDataSnapshot.child("totalDistance").getValue(Float.class);
                    totalDistance += currentDistance;
                    int score=communityDataSnapshot.child("communityScore").getValue(Integer.class);
                    mDatabase.child("Community").child(loadCommunityList.get(i)).child("totalDistance").setValue(totalDistance);
                    mDatabase.child("Community").child(loadCommunityList.get(i)).child("communityScore").setValue(score+((int)totalDistance));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        countAllCommunityAndUpdateCommunityRank();
        unregisterReceiver(broadcastReceiver);
    }

    public ArrayList<TorchCommunity> getCommunityArrayList() {
        return communityList;
    }

    public void showToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void countAllCommunityAndUpdateCommunityRank(){
        Query topCommunityScoreQuery=mDatabase.child("Community").orderByChild("communityScore");
        topCommunityScoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index=0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.i("대상",postSnapshot.getKey());
                        alreadyRegistered.add(index++,postSnapshot.getKey());
                        Log.i(index-1+"번째",alreadyRegistered.get(index-1));
                    }

                    infoSumOfTorch.setText("/"+index);

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String topCommunityName = postSnapshot.getKey();
                        mDatabase.child("Community").child(topCommunityName).child("communityRank").setValue(index--);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (int i=0;i<communityList.size();i++){
            communityList.set(i, updateCommunityRankingInFirebase(communityList.get(i)));
            Log.e("(테스트)",i+"번째 이름 : "+communityList.get(i).getCommunityName());
            Log.e("(테스트)",i+"번째 점수 : "+communityList.get(i).getCommunityRank());
        }
    }

    public void loadMyCommunityList(){
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadCommunityList=user.getCommunityList();
                loadCommunityFromFirebase();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadCommunityFromFirebase(){
        mDatabase.child("Community").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        for (String target : loadCommunityList){
                            if (postSnapshot.getKey().equals(target)){
                                TorchCommunity loadedCommunity=postSnapshot.getValue(TorchCommunity.class);

                                communityList.add(loadedCommunity); // 추후 수정 대상으로 고려 필요

                                CarouselFragment carouselFragment = (CarouselFragment) getFragmentManager().findFragmentById(R.id.layout_body);
                                carouselFragment.createNewTorch(loadedCommunity.getCommunityName());

                                countAllCommunityAndUpdateCommunityRank();
                                int index=carouselFragment.getTorchList().size()-1;
                                carouselFragment.getmCarouselView().scrollToChild(index);
                                carouselFragment.getmCarouselView().notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
//    getter
    public User getUser() {
        return user;
    }
    public ArrayList<TorchCommunity> getCommunityList() {
        return communityList;
    }
}
