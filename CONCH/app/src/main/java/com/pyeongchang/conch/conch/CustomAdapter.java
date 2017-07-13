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

public class CustomAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView text2;
    }

    public CustomAdapter(ArrayList<Item> data, Context context){
        super(context, R.layout.timeline_list_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Item dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;
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
        viewHolder.text2.setText(dataModel.getContent());

        return convertView;
    }

}
