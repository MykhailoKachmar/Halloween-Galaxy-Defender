package com.example.destructor;
import android.content.Context;
import android.media.MediaPlayer;


public class Audio {



    public Audio(Context context){

        this.mpHit = MediaPlayer.create(context, R.raw.hit);
        this.mpHit.setVolume(50,50);
        this.mpMiss = MediaPlayer.create(context, R.raw.lose);
        this.mpMiss.setVolume(50,50);
        this.mpBreak = MediaPlayer.create(context, R.raw.piu);
        this.mpBreak.setVolume(50,50);
        this.mpTheme = MediaPlayer.create(context, R.raw.starwarstheme);
        this.mpTheme.setVolume(20,20);
        this.mpNewLevel = MediaPlayer.create(context, R.raw.nextlevel);
        this.mpNewLevel.setVolume(50,50);
        this.mpGameOver = MediaPlayer.create(context, R.raw.gameover);
        this.mpGameOver.setVolume(50,50);
        this.mpWin = MediaPlayer.create(context, R.raw.wins);
        this.mpWin.setVolume(50,50);
    }

    public MediaPlayer mpHit ,mpMiss ,mpBreak , mpTheme ,mpNewLevel ,mpGameOver ,mpWin;


    public void startHit(){
        if (mpHit.isPlaying())
        {
            mpHit.seekTo(0);
        }
        else
        {
            mpHit.start();
        }
    }
    public void startMiss(){
        if (mpMiss.isPlaying())
        {
            mpMiss.seekTo(0);
        }
        else
        {
            mpMiss.start();
        }
    }
    public void startBreak(){
        if (mpBreak.isPlaying())
        {
            mpBreak.seekTo(0);
        }
        else
        {
            mpBreak.start();
        }
    }
    public void startTheme(){
        if (!mpTheme.isPlaying())
        {
            mpTheme.start();
        }
    }
    public void startNewLevel(){
        if(mpNewLevel == null) return;
        pauseTheme();
        if (mpNewLevel.isPlaying())
        {
            mpNewLevel.seekTo(0);
        }
        else
        {
            mpNewLevel.start();
        }
    }
    public void startGameOver(){

        if(mpGameOver == null) return;

        pauseTheme();

        if (mpGameOver.isPlaying())
        {
            mpGameOver.seekTo(0);
        }
        else
        {
            mpGameOver.start();
        }
    }
    public void startWin(){
        if(mpWin == null) return;

        pauseTheme();


        if (mpWin.isPlaying())
        {
            mpWin.seekTo(0);
        }
        else
        {
            mpWin.start();
        }
    }


    public void pauseTheme(){
        if(mpTheme != null && mpTheme.isPlaying()){
            mpTheme.pause();
        }
    }
    public void pauseAll(){
        if(mpHit != null && mpHit.isPlaying()){
            mpHit.pause();
        }
        if(mpMiss != null && mpMiss.isPlaying()){
            mpMiss.pause();
        }
        if(mpBreak != null && mpBreak.isPlaying()){
            mpBreak.pause();
        }
        /*if(mpNewLevel != null && mpNewLevel.isPlaying()){
            mpNewLevel.pause();
        }
        if(mpGameOver != null && mpGameOver.isPlaying()){
            mpGameOver.pause();
        }
        if(mpWin != null && mpWin.isPlaying()){
            mpWin.pause();
        }*/
        if(mpTheme != null && mpTheme.isPlaying()){
            mpTheme.pause();
        }
    }
}
