package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton btnBengali, btnEnglish;
    private RadioGroup btnGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUserPreferableTitle();
        layoutComponentsMapping();
        setUserSelectedLanguage();
    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String title = getResources().getString(R.string.settings_title_bd);
            setTitle(title);
        }else{
            setTitle("Settings");
        }
    }

    private void layoutComponentsMapping(){
        btnGroup = (RadioGroup) findViewById(R.id.settings_lang_group);
        btnBengali =  (RadioButton) findViewById(R.id.settings_lang_bengali);
        btnEnglish = (RadioButton) findViewById(R.id.settings_lang_english);
    }

    private void setUserSelectedLanguage(){
        Enums.Language userLanguage = SharedStorge.getUserLanguage(this);
        if(userLanguage == Enums.Language.BD){
            btnBengali.setChecked(true);
        }
        else if(userLanguage == Enums.Language.EN){
            btnEnglish.setChecked(true);
        }
    }

    public void onSave(View view){
        int checkedId = btnGroup.getCheckedRadioButtonId();
        if(checkedId == btnBengali.getId()){
            saveLanguage(Enums.Language.BD);
        }
        else{
            saveLanguage(Enums.Language.EN);
        }
    }

    private void saveLanguage(Enums.Language language){
        boolean result = SharedStorge.setUserLanguage(this, language);
        if(result){
            redirectHomePage();
        }else{
            saveError();
        }
    }


    private void saveError(){
        Toast.makeText(this, "Sorry, there is some problem occurred", Toast.LENGTH_SHORT).show();
    }

    public void redirectHomePage(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
