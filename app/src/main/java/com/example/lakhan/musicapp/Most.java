package com.example.lakhan.musicapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Most extends AppCompatActivity {

    private List<SongInfo> _songs = new ArrayList<SongInfo>();;
    RecyclerView recyclerView;
    SeekBar seekBar;
    SongAdapter songAdapter;
    MediaPlayer mediaPlayer;
    boolean value =true;
    private DatabaseHandler db;
    private Handler myHandler = new Handler();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        db = new DatabaseHandler(this);
        loadSongs();
        songAdapter = new SongAdapter(this,_songs);
        recyclerView.setAdapter(songAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Button b, View view, final SongInfo obj, int position) {
                if(b.getResources().equals(R.drawable.stop)){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Toast.makeText(getApplicationContext(),"OKK",Toast.LENGTH_SHORT).show();
                    b.setBackgroundResource(R.drawable.play);
                    b.setVisibility(View.INVISIBLE);
                    // b.setText("Play");
                }else {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (mediaPlayer!=null)
                                {
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                    mediaPlayer.release();

                                }
                                b.setVisibility(View.VISIBLE);
                                b.setBackgroundResource(R.drawable.stop);
                                value=true;
                                mediaPlayer = new MediaPlayer();
                                mediaPlayer.setDataSource(obj.getSongUrl());
                                mediaPlayer.prepareAsync();
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                        seekBar.setProgress(0);
                                        seekBar.setMax(mediaPlayer.getDuration());
                                        Log.d("Prog", "run: " + mediaPlayer.getDuration());
                                    }
                                });
                                //           b.setText("Stop");



                            }catch (Exception e){}
                        }

                    };
                    myHandler.postDelayed(runnable,100);

                }
            }
        });



        checkUserPermission();

        Thread t = new runThread();
        t.start();

    }
    public class runThread extends Thread {


        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Runwa", "run: " + 2);
                if (mediaPlayer != null) {
                    seekBar.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    });

                    Log.d("Runwa", "run: " + mediaPlayer.getCurrentPosition());
                }
            }
        }

    }

    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            }
        }
        // loadSongs();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // loadSongs();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }


    private void loadSongs() {

        Log.i("ok","2");
        _songs = db.getAllContactsMost();
        songAdapter = new SongAdapter(Most.this,_songs);

    }


}
