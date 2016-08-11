package com.ken.android.CloudMusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ken.android.CloudMusic.R;

import java.util.ArrayList;

/**
 * Created by axnshy on 16/5/26.
 */
public class DrawerListAdapter extends BaseAdapter {


    private static ArrayList<String> drawerlist;
    private static Context context;

    public DrawerListAdapter(Context context,ArrayList<String> drawerlist) {
        this.drawerlist = drawerlist;
        this.context=context;
    }

    @Override
    public int getCount() {
        return drawerlist.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (drawerlist == null) {
            return null;
        }
        String item = drawerlist.get(position);
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        convertView = layoutInflater.inflate(R.layout.drawer_listview_item, null);

        TextView itemText= (TextView) convertView.findViewById(R.id.drawerlist_title);
        itemText.setText(drawerlist.get(position));
        return convertView;
    }
}
