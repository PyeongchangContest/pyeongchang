package com.pyeongchang.conch.conch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class PopupMail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀바 삭제

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount=0.7f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_popup_mail);

    }
    public void onClick(View view){
        finish();
    }
}
