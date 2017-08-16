package com.pyeongchang.conch.conch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        TextView date;
        TextView name;
        TextView likeCount;
        TextView content;
    }
    //현재 몇개의 아이템을 가지고 있는지
    public int getCount(){
       return this.dataSet.size();
    }

    //어떤 아이템인지 알려주는 부분
    public Item getItem(int position){
        return this.dataSet.get(position);
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

        //생성자로부터 저장된 layout을 inflate하여 참조 획득
        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.timeline_list_item, parent, false);
            viewHolder.content= (TextView)convertView.findViewById(R.id.content);
            viewHolder.date= (TextView)convertView.findViewById(R.id.date);
            viewHolder.name= (TextView)convertView.findViewById(R.id.simpleProfile);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();

        }
        //아이템 내 각 위젯에 데이터 반영
        viewHolder.content.setText(dataModel.getContent());
        viewHolder.date.setText(dataModel.getDate());
        viewHolder.name.setText(dataModel.getName());



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
