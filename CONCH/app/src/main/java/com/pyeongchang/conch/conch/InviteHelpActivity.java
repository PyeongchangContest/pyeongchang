package com.pyeongchang.conch.conch;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bumsu on 2017-08-24.
 */

public class InviteHelpActivity extends  ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;

    public InviteHelpActivity(Activity context, List<User> userList) {
        super(context, R.layout.activity_invitehelp, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_invitehelp, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewNation = (TextView) listViewItem.findViewById(R.id.textViewNation);

        User user = userList.get(position);

        textViewName.setText(user.getUserName());
        textViewNation.setText(user.getNation());

        return listViewItem;
    }
}
