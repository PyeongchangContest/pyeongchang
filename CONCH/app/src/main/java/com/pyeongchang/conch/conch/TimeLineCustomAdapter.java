package com.pyeongchang.conch.conch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hyojung on 2017-07-12.
 * 리스트뷰 구현을 위한 어뎁터
 */

public class TimeLineCustomAdapter extends ArrayAdapter<Item> implements View.OnClickListener {

    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    private ListBtnClickListener listBtnClickListener;

    private ArrayList<Item> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView text2;
    }

    public TimeLineCustomAdapter(ArrayList<Item> data, Context context){
        super(context, R.layout.timeline_list_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Item dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        //생성자로부터 저장된 layout을 inflate하여 참조 획득
        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.timeline_list_item, parent, false);
            viewHolder.text2 = (TextView)convertView.findViewById(R.id.content);

            result=convertView;

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }
        //아이템 내 각 위젯에 데이터 반영
        viewHolder.text2.setText(dataModel.getContent());

        //타임라인 리스트의 버튼.
        Button commentBtn = (Button) convertView.findViewById(R.id.comment);
        commentBtn.setTag(position);
        commentBtn.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        //ListBtnClickListener(timeline activity)의 onlistBtnClick()함수 호출
        if(this.listBtnClickListener != null){
            this.listBtnClickListener.onListBtnClick((int)view.getTag());
        }

    }


}
