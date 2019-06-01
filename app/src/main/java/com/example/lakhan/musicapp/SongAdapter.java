package com.example.lakhan.musicapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawankumar on 30/05/17.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> implements AdapterView.OnItemLongClickListener {

    private List<SongInfo> _songs = new ArrayList<SongInfo>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private AdapterView.OnItemLongClickListener mlong;
    private DatabaseHandler db;


    public SongAdapter(Context context, List<SongInfo> songs) {
        this.context = context;
        this._songs = songs;
        db = new DatabaseHandler(context);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    public interface OnItemClickListener {
        void onItemClick(Button b, View view, SongInfo obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }




    @Override
    public SongHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.row_songs,viewGroup,false);
        return new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final SongHolder songHolder, final int i) {
            final SongInfo s = _songs.get(i);
            songHolder.tvSongName.setText(_songs.get(i).getSongname());
            songHolder.tvSongArtist.setText(_songs.get(i).getArtistname());
            songHolder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                       // Toast.makeText(context, _songs.get(i).getSongUrl(),Toast.LENGTH_LONG).show();

                        db.most(_songs.get(i).getSongUrl());

                        mOnItemClickListener.onItemClick(songHolder.btnAction,v, s, i);
                    }
                }
            });



        songHolder.tvSongName.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {



                db.update(_songs.get(i).getSongUrl());

                Toast.makeText(context, _songs.get(i).getSongname()+" Added to Favourite List",Toast.LENGTH_LONG).show();


//                Toast.makeText(context, _songs.get(i).getSongUrl(),Toast.LENGTH_LONG).show();


                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return _songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView tvSongName,tvSongArtist;
        Button btnAction;
        public SongHolder(View itemView) {
            super(itemView);
            tvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            tvSongArtist = (TextView) itemView.findViewById(R.id.tvArtistName);
            btnAction = (Button) itemView.findViewById(R.id.btnPlay);
        }
    }
}
