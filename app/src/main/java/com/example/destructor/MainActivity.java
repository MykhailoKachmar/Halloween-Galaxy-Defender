package com.example.destructor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;



public class MainActivity extends AppCompatActivity {

    /*
    public MyDbManager myDbManager;
    public MyDbHelper dbHelper;
    TableLayout tvOut;*/

    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void startGame(View view){
        gameView = new GameView(this);
        setContentView(gameView);
    }



    /*
    public void tableView(View view){
        setContentView(R.layout.score_table);
        myDbManager  = new MyDbManager(this);
        tvOut = findViewById(R.id.tvOut);

    }*/



    @Override
    protected void onPause() {

        if (gameView != null && gameView.hud != null && gameView.hud.audio != null){
                gameView.hud.audio.pauseAll();}
        super.onPause();
    }



}