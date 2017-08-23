package com.pyeongchang.conch.conch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hyojung on 2017-07-19.
 * 덧글을 위한 어댑터
 */

public class CommentCustomAdapter extends ArrayAdapter<Item>{
    private ArrayList<Item> dataSet;
    Context mContext;
    int position;


    private  static class ViewHolder{
        TextView name;
        TextView content;
        TextView date;
    }
    public CommentCustomAdapter(ArrayList<Item> data, Context context, int layout){
        super(context, R.layout.comment_list_item,data);
        this.dataSet = data;
        this.mContext = context;
        this.position = layout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Item commentData = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comment_list_item, parent, false);

            viewHolder.content = (TextView)convertView.findViewById(R.id.content);
            viewHolder.date = (TextView)convertView.findViewById(R.id.date);
            viewHolder.name = (TextView)convertView.findViewById(R.id.simpleProfile);

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();

        }
        viewHolder.content.setText(commentData.getContent());
        viewHolder.date.setText(commentData.getDate());
        viewHolder.name.setText(commentData.getName());

        return convertView;

    }
}

