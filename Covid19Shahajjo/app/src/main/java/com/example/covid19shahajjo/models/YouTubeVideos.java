package com.example.covid19shahajjo.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19shahajjo.activities.Covid19StoryActivity;
import com.example.covid19shahajjo.adapters.VideoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class YouTubeVideos {

  private Vector<YouTubeVideos>youTubeVideos=new Vector<>();

    String title;
    String url;
    private Covid19StoryActivity covid19StoryActivity;

    public YouTubeVideos() {
    }

    public YouTubeVideos(String title, String url) {
        this.title=title;
        this.url=url;


    }

    public YouTubeVideos(Covid19StoryActivity covid19StoryActivity, String title, String url) {
        this.title=title;
        this.url=url;
        this.covid19StoryActivity=covid19StoryActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    ///Add Youtube video title and watch link to firebase realtime database

    public void addYoutubeVideoData(DatabaseReference databaseReference,String title,String url){

        String key1="title";
        String key2="url";

        String id= databaseReference.push().getKey();

        Map<String,String> map =new HashMap<>();
        map.put(key1,title);
        map.put(key2,url);

        databaseReference.child(id).setValue(map);

    }

    public void getYoutubeVideoData(DatabaseReference databaseReference, RecyclerView storyRecyclerView){


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                youTubeVideos.clear();

                for(DataSnapshot youtubeVideo : dataSnapshot.getChildren()){
                    YouTubeVideos tempdata =youtubeVideo.getValue(YouTubeVideos.class);
                    //Add data to vector data
                    youTubeVideos.add(new YouTubeVideos(tempdata.getTitle(),tempdata.getUrl()));

                }
                //set to recycler adapter
                VideoAdapter videoAdapter = new VideoAdapter(youTubeVideos,covid19StoryActivity,storyRecyclerView);
                storyRecyclerView.setAdapter(videoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteYoutubeVideoData(DatabaseReference databaseReference,String title){

        String key1="title";

        Query deleteQuery = databaseReference.orderByChild(key1).equalTo(title);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot deleteData: dataSnapshot.getChildren()) {
                    deleteData.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateYoutubeVideoData(DatabaseReference databaseReference,String presentTitle,String updateTitle,String updateUrl){

        String key1="title";
        String key2="url";


        Map<String,String> map =new HashMap<>();
        map.put(key1,updateTitle);
        map.put(key2,updateUrl);

        Query deleteQuery = databaseReference.orderByChild(key1).equalTo(presentTitle);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot updateData: dataSnapshot.getChildren()) {
                    updateData.getRef().setValue(map);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateYoutubeVideoTitleData(DatabaseReference databaseReference,String presentTitle,String updateTitle){

        String key1="title";
        String key2="url";





        Query deleteQuery = databaseReference.orderByChild(key1).equalTo(presentTitle);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot updateData: dataSnapshot.getChildren()) {
                    updateData.getRef().child(key1).setValue(updateTitle);
                //    mDatabaseRef.child("TABLE_NAME").child("orderStatus").setValue(2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateYoutubeVideoUrlData(DatabaseReference databaseReference,String presentTitle,String updateUrl){

        String key1="title";
        String key2="url";





        Query deleteQuery = databaseReference.orderByChild(key1).equalTo(presentTitle);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot updateData: dataSnapshot.getChildren()) {
                    updateData.getRef().child(key2).setValue(updateUrl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
