package com.example.covid19shahajjo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.R;


public class HomeMenuAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private String menus[];
    private ImageView menuIcon;
    private TextView menuText;

    public HomeMenuAdapter(@NonNull Context context, int resource, String[] menus) {
        super(context, resource, menus);
        this.context = context;
        this.resource = resource;
        this.menus = menus;
    }

    @Override
    public int getCount(){
        return this.menus.length;
    }

    @Override
    public String getItem(int index){
        return this.menus[index];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(this.resource, parent, false);
        }

        String menu = getItem(position);
        menuIcon = (ImageView) row.findViewById(R.id.home_menu_item_icon);
        menuText = (TextView) row.findViewById(R.id.home_menu_item_text);
        menuIcon.setImageResource(getDrawable(position));
        menuText.setText(menu);
        return row;
    }

    private LayoutInflater getLayoutInflater(){
        return (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private int getDrawable(int position){
        switch (position){
            case 0: return R.drawable.ic_help_contact;
            case 1: return R.drawable.ic_hospital;
            case 2: return R.drawable.ic_test_center;
            case 3: return R.drawable.ic_statistics;
            case 4: return R.drawable.ic_settings;
            case 5: return R.drawable.ic_about;
            default: return -1;
        }
    }
}
