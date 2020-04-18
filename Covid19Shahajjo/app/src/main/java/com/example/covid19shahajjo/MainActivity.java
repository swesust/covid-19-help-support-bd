package com.example.covid19shahajjo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.covid19shahajjo.responses.HelpCenter;

import static com.example.covid19shahajjo.R.id.text;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        text.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), HelpCenter.class);
                startActivity(intent);
            }
        });
    }
}
