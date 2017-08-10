package com.example.cloudretrieve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James Ooi on 9/8/2017.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    private ArrayList<Person> data;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
        TextView txtCity;
    }

    public PersonAdapter(ArrayList<Person> data, Context context) {
        super(context, R.layout.list_item, data);
        this.data = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder.txtName = (TextView)convertView.findViewById(R.id.item_name);
            viewHolder.txtCity = (TextView)convertView.findViewById(R.id.item_city);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtName.setText(person.getName());
        viewHolder.txtCity.setText(person.getCity());

        return convertView;
    }
}
