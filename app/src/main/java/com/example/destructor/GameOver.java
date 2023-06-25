package com.example.destructor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class GameOver extends AppCompatActivity {


    TextView tvPoints;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        ImageView imageWin = findViewById(R.id.winner_img);
        TextView textWin = findViewById(R.id.winner_tx);;

        tvPoints = findViewById(R.id.tvPoints);
        int points = getIntent().getExtras().getInt("Score:");
        boolean WinGame = getIntent().getExtras().getBoolean("winGame");
        if(WinGame){
            imageWin.setVisibility(View.VISIBLE);
            textWin.setVisibility(View.VISIBLE);
        }
        tvPoints.setText("" + points);

    }

    public void exit(View view){
        finish();
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}

