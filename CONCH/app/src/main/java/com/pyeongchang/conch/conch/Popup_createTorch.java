package com.pyeongchang.conch.conch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class Popup_createTorch extends AppCompatActivity {

    private SeekBar sb;
    private int value=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
//        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount= 0.7f;
//        getWindow().setAttributes(layoutParams);
//        setContentView(R.layout.activity_popup_create_torch);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_create_torch);

        sb=(SeekBar) findViewById(R.id.create_maxPeople);
        sb.setProgress(value);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                printValue(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }
    public void printValue(int value){
        TextView textView=(TextView)findViewById(R.id.create_maxPeopleValue);
        textView.setText(String.valueOf(value));
    }
}
