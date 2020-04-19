package com.example.covid19shahajjo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.models.Area;
import com.example.covid19shahajjo.models.AreaWithContacts;

import java.util.List;

public class AreaArrayAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List list;

    private TextView areaNameView;

    public AreaArrayAdapter(@NonNull Context context, int resource, List list) {
        this.list = list;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(this.resource, parent, false);
        }

        Area area = (Area) this.list.get(position);
        areaNameView = (TextView) row.findViewById(R.id.area_list_item_name);
        areaNameView.setText(area.Name);
        return row;
    }

    private LayoutInflater getLayoutInflater(){
        return (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
