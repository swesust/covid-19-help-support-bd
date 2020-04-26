package com.example.covid19shahajjo.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.models.YouTubeVideos;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Covid19StoryActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView storyRecyclerView;
    private Covid19StoryActivity covid19StoryActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_story);
        covid19StoryActivity=this;
        setUserPreferableTitle();


        storyRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_video);
        storyRecyclerView.setHasFixedSize(true);
        storyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("video");

        YouTubeVideos youTubeVideos=new YouTubeVideos(covid19StoryActivity,null,null);


        youTubeVideos.getYoutubeVideoData(databaseReference,storyRecyclerView);



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String title = getResources().getString(R.string.covid_story_title_bd);
            setTitle(title);
        }else{
            setTitle("Covid-19 Story");
        }
    }
}
