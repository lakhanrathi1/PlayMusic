package com.example.lakhan.musicapp;

/**
 * Created by pawankumar on 30/05/17.
 */

public class SongInfo {
    private String Songname;
    private String Artistname;
    private String SongUrl;
    private String fav;
    int most;
    int _id;

    public SongInfo() {
    }

    public SongInfo(String songname, String artistname, String songUrl,String f,int m) {
        Songname = songname;
        fav = f;
        Artistname = artistname;
        SongUrl = songUrl;
        most =m;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    public String getFav(){return fav;}

    public void setFav(String f){fav = f;}

    public String getSongname() {
        return Songname;
    }

    public void setSongname(String songname) {
        Songname = songname;
    }

    public String getArtistname() {
        return Artistname;
    }

    public void setArtistname(String artistname) {
        Artistname = artistname;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
    }




    public int getmost(){return most;}

    public void setmost(int m){most = m;}


}
