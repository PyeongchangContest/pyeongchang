package com.pyeongchang.conch.conch;


import android.app.FragmentTransaction;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dlDrawer;
    private LinearLayout llDrawer;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private ConstraintLayout mConstraintLayout;
    private ArrayList<ImageButton> torchList = new ArrayList<>();
    private ArrayList<TorchCommunity> communityList = new ArrayList<>();

    private TextView infoTorchRank;
    private TextView infoTorchName;
    private TextView infoTorchScore;

    final UserProperty userProperty = new UserProperty(2, 3500);//임시 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //액션바 수정
        getSupportActionBar().setTitle("PyeonChang2018");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff339999));
        //액션바 수정

        ImageButton profileBtn = (ImageButton) findViewById(R.id.profileBtn);//프로필 버튼
        ImageButton mailboxBtn = (ImageButton) findViewById(R.id.mailboxBtn);//메일함 버튼
        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);//세팅 버튼
        ImageButton rankingBtn = (ImageButton) findViewById(R.id.rankingBtn);//랭킹 버튼

        infoTorchName=(TextView)findViewById(R.id.main_torchName);//성화 이름
        infoTorchScore=(TextView)findViewById(R.id.main_torchScore);//성화 점수
        infoTorchRank =(TextView)findViewById(R.id.main_torchRank);//성화 랭킹

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

        CarouselFragment cf=CarouselFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_body, cf);
        fragmentTransaction.commit();
        // 유저 정보 생성


    }

    public void clickPlusBtn(View v) {
        mContext = getApplicationContext();
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.layout_body);

        if (userProperty.getUserLevel() == 1 || userProperty.getUserLevel() > torchList.size()) {
            popupTorch();
        }else{
            Toast.makeText(mContext, "성화를 추가하려면 레벨을 올리세요.", Toast.LENGTH_SHORT).show();
        }


    }

    public void createNewTorch(LinearLayout layout) {
        ImageButton addBtn = new ImageButton(this);
        addBtn.setImageResource(R.drawable.torch);

        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        addBtn.setLayoutParams(params);

        addBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addBtn.setBackgroundColor(Color.TRANSPARENT);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MissionActivity.class);
                startActivity(intent);
            }
        });
<<<<<<< HEAD
        
=======

        //객체 생성
//        View customView = getLayoutInflater().inflate(R.layout.activity_popup_create_torch, null);
//        TextView torchName = customView.findViewById(R.id.create_torchName);
//        TextView torchMaxPeople = customView.findViewById(R.id.create_maxPeopleValue);
//        ToggleButton isSecretCommunity = customView.findViewById(R.id.create_isSecret);
//        String tName=torchName.getText().toString();
//        int tMaxPeople=Integer.parseInt(torchMaxPeople.getText().toString());
//        boolean isSecret=isSecretCommunity.isChecked();
//        TorchCommunity addTorchCommunity = new TorchCommunity(userProperty,tName,tMaxPeople,isSecret);
//
//        communityList.add(addTorchCommunity);

        torchList.add(addBtn);
        layout.addView(addBtn);
        Toast.makeText(this, "성화 생성 완료!!", Toast.LENGTH_SHORT).show();
    }

    public void popupTorch() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customView= inflater.inflate(R.layout.activity_popup_create_torch, (ViewGroup) findViewById(R.id.popup_layout));
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
        final TextView textView=customView.findViewById(R.id.create_maxPeopleValue);
        final SeekBar sb;
        int value=20;
        sb=customView.findViewById(R.id.create_maxPeople);
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
                String tName=torchName.getText().toString();
                int tMaxPeople=Integer.parseInt(torchMaxPeople.getText().toString());
                boolean isSecret=isSecretCommunity.isChecked();
                //성화 커뮤니티 객체 추가
                TorchCommunity addTorchCommunity = new TorchCommunity(userProperty,tName,tMaxPeople,isSecret);
                communityList.add(addTorchCommunity);

                CarouselFragment carouselFragment=(CarouselFragment)getFragmentManager().findFragmentById(R.id.layout_body);
                carouselFragment.createNewTorch();

                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER, 0, 0);


>>>>>>> 2d9b3c6475a44dcec9d078330c8d639c3e6f0d50
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
            Toast.makeText(this, "메뉴 슬라이더", Toast.LENGTH_SHORT).show();
            llDrawer = (LinearLayout) findViewById(R.id.sliderMenu);
            dlDrawer = (DrawerLayout) findViewById(R.id.activity_main);
            dlDrawer.openDrawer(llDrawer);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeText(String name,int score,int rank){
        infoTorchName.setText(name);
        infoTorchScore.setText(String.valueOf(score));
        infoTorchRank.setText(String.valueOf(rank));

    }

    public ArrayList<TorchCommunity> getCommunityList() {
        return communityList;
    }
}
