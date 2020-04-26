package com.example.covid19shahajjo.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.activities.Covid19StoryActivity;
import com.example.covid19shahajjo.activities.youtubeFullScreenActivity;
import com.example.covid19shahajjo.models.YouTubeVideos;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Covid19StoryActivity covid19StoryActivity;
    private ArrayList<YouTubePlayer> youTubePlayerArrayList = new ArrayList<>();
    private RecyclerView storyRecyclerView;
    private boolean isPlaying=false;
    private float playerCurrentSecondPlayed=0;
    List<YouTubeVideos>youTubeVideosList;


    public VideoAdapter(List<YouTubeVideos> youTubeVideosList, Covid19StoryActivity covid19StoryActivity, RecyclerView storyRecyclerView) {
        this.youTubeVideosList = youTubeVideosList;
        this.covid19StoryActivity=covid19StoryActivity;
        this.storyRecyclerView=storyRecyclerView;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,parent,false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        holder.tileTextView.setText(youTubeVideosList.get(position).getTitle());


        //full screen youtube player listener
        holder.youTubePlayerView.getPlayerUiController().setFullScreenButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(covid19StoryActivity, youtubeFullScreenActivity.class);
                myIntent.putExtra("url", youTubeVideosList.get(position).getUrl()); //Optional parameters
                myIntent.putExtra("playerCurrentSecondPlayed", playerCurrentSecondPlayed); //Optional parameters
                covid19StoryActivity.startActivity(myIntent);
            }
        });


        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener(){


            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                covid19StoryActivity.getLifecycle().addObserver(holder.youTubePlayerView);
                youTubePlayer.cueVideo(youTubeVideosList.get(position).getUrl(),0);
                youTubePlayerArrayList.add(youTubePlayer);


                //scrolling listener
                storyRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(isPlaying==true) pauseAllPlayer();
                        isPlaying=false;


                    }
                });


            }



            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {


                if(state.toString().equals("PLAYING") || state.toString().equals("BUFFERING") || state.toString().equals("UNKNOWN")){
                    if(isPlaying==true){
                        pauseAllPlayer();
                        youTubePlayer.play();
                    }
                   isPlaying=true;

                }else isPlaying=false;//ENDED,PAUSED,VIDEO_CUED,UNSTARTED
            }

            @Override
            public void onCurrentSecond(YouTubePlayer youTubePlayer, float second) {
                super.onCurrentSecond(youTubePlayer, second);
                playerCurrentSecondPlayed=second;
            }
        });


    }



    @Override
    public int getItemCount() {
        return youTubeVideosList.size();
    }

    private void pauseAllPlayer(){
        for(int i=0;i<youTubePlayerArrayList.size();i++){
            youTubePlayerArrayList.get(i).pause();
        }
    }



    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tileTextView;


        //youtube player
        YouTubePlayerView youTubePlayerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tileTextView =(TextView) itemView.findViewById(R.id.YoutubeVideoTitleId);


            youTubePlayerView =(YouTubePlayerView)itemView.findViewById(R.id.youtube_player_view);

        }


    }
}
