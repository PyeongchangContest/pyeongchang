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

    private  static class ViewHolder{
        TextView content;
    }
    public CommentCustomAdapter(ArrayList<Item> data, Context context){
        super(context, R.layout.comment_list_item,data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Item commentData = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if(convertView == null){

            viewHolder = new CommentCustomAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.comment_list_item, parent, false);

            viewHolder.content = (TextView)convertView.findViewById(R.id.content);

            result=convertView;

            convertView.setTag(viewHolder);
        } else{
            viewHolder = (CommentCustomAdapter.ViewHolder)convertView.getTag();
            result = convertView;
        }
        viewHolder.content.setText(commentData.getContent());

        return convertView;

    }
}

