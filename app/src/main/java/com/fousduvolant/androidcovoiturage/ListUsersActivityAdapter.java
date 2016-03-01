package com.fousduvolant.androidcovoiturage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.UserList;

/**
 * Created by AREDON on 29/02/2016.
 */
public class ListUsersActivityAdapter extends ArrayAdapter<UserList> {

    private final Context context;
    private final ArrayList<UserList> itemsArrayList;

    public ListUsersActivityAdapter(Context context, ArrayList<UserList> usersArrayList) {

        super(context, R.layout.list_users_activity_layout_listview_item_row, usersArrayList);

        this.context = context;
        this.itemsArrayList = usersArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.list_users_activity_layout_listview_item_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView nameView = (TextView) rowView.findViewById(R.id.textViewName);
        TextView addressView = (TextView) rowView.findViewById(R.id.textViewAddress);

        // 4. Set the text for textView
        nameView.setText(itemsArrayList.get(position).getName());
        addressView.setText(itemsArrayList.get(position).getAddress());

        // 5. return rowView
        return rowView;
    }
}
