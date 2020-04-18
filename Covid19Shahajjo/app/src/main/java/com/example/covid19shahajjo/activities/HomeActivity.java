package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.adapters.HomeMenuAdapter;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView menuView;
    private Enums.Language userLang;
    private HomeMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        checkPreconditions();
        layoutComponentMapping();
    }

    private void checkPreconditions(){
        Enums.Language language =  SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.NONE){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else{
            language = language;
        }
    }

    private void layoutComponentMapping(){
        menuView = (ListView) findViewById(R.id.home_menu_list);
        String[] menus = userPreferableMenu();
        adapter = new HomeMenuAdapter(this, R.layout.layout_home_menu_item, menus);
        menuView.setAdapter(adapter);
        menuView.setOnItemClickListener(this);
    }

    private String[] userPreferableMenu(){
        String menus[];
        if(userLang == Enums.Language.EN){
            menus = getResources().getStringArray(R.array.menu_en);
        }else{
            menus = getResources().getStringArray(R.array.menu_bd);
        }
       return menus;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentOnClickAction(position);
    }

    private void intentOnClickAction(int position){

    }
}
