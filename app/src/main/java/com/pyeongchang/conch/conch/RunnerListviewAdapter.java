package com.pyeongchang.conch.conch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Gayeon on 2017-08-03.
 */

public class RunnerListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<RunnerListviewItem> data;
    private int layout;

    public RunnerListviewAdapter(Context context, int layout, ArrayList<RunnerListviewItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        RunnerListviewItem listviewItem = data.get(position);

        ImageView profile = (ImageView) convertView.findViewById(R.id.profile);
        profile.setImageResource(listviewItem.getProfile());

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(listviewItem.getName());

        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(listviewItem.getDate());

        return convertView;
    }
}
