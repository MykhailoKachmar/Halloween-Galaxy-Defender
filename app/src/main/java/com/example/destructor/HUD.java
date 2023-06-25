package com.example.destructor;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

public class HUD {

    public HUD(Typeface font , Bitmap mapMains, Context context){
        this.life = 3;
        this.gameover = false;
        this.newlevel = false;
        this.restart = false;
        this.font = font;
        this.mapMains = mapMains;
        this.audio = new Audio(context);

    }

    private int points, level = 1, life;
    private final int maxlevel = 10;
    public int maxPoints;
    public boolean unlimitedHealth = false;

    public Bitmap mapMains;
    private final Typeface font;
    public boolean gameover, newlevel, restart, win;
    public Audio audio;


    public int hudSizeCof = 20;
    private final Paint textPaint = new Paint();

    public RectF textS_Stop, textS_Restart, textS_UnlimitedHealth;
    //points
    public void incScore(int plus){
        points += plus;

    }
    public int getPoints(){
        return points;
    }

    //Level
    public void incLevel(){
        if(level < maxlevel) {
            level++;
            audio.startNewLevel();
        }
        else {
            win = true;
            audio.startWin();
        }
    }
    public void resetLevel(){
        level = 1;
    }
    public int getLevel(){
        return  level;
    }

    //Life
    public void incLife(){
        if(life < 5) life++;
    }
    public void decLife(){
        if(life > 1 ) {

            if(!unlimitedHealth)life--;
            audio.startMiss();
        }
        else {
            audio.startGameOver();
            gameover = true;
        }
    }
    public void resetLife(){
        life = 3;
    }
    public int getLife(){
        return  life;
    }
    public void resetPoints(){
        points = 0;
    }

    public void draw(Canvas canvas, int WindowWidth, int WindowHeight, Ball ball){

        float TextSize = WindowHeight/(hudSizeCof*1.75f);

        textPaint.setColor(Color.MAGENTA);
        textPaint.setTextSize(TextSize);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(font);






        Paint mainPaint = new Paint();
        canvas.drawColor(Color.BLACK);
        Rect rect = new Rect();
        rect.contains(0,0,WindowWidth,WindowHeight);


        canvas.drawBitmap(mapMains, null, new RectF(0, 0, WindowWidth, WindowHeight), null);

        //Draw down hud
        mainPaint.setColor(Color.MAGENTA);
        canvas.drawRect(0, WindowHeight - (WindowHeight / hudSizeCof), WindowWidth, WindowHeight-WindowHeight/hudSizeCof - 4, mainPaint );
        //Draw Level
        canvas.drawText("Level : " + level + " / " + maxlevel, WindowWidth/hudSizeCof, WindowHeight - (WindowHeight/hudSizeCof)/2  + TextSize/2 , textPaint);

        Paint newPaint = new Paint(textPaint);
        newPaint.setTextSize(TextSize*1.5f);
        if(unlimitedHealth) newPaint.setColor(Color.GREEN);

        canvas.drawText("∞", WindowWidth/1.75f, WindowHeight - (WindowHeight/hudSizeCof)/2  + TextSize/2 , newPaint);
        textS_UnlimitedHealth = new RectF(WindowWidth/2.5f, WindowHeight - (WindowHeight/hudSizeCof)*1.1f,
                WindowWidth/1f, WindowHeight);




        //Draw up hud
        mainPaint.setColor(Color.MAGENTA);
        canvas.drawRect(0,WindowHeight/hudSizeCof,WindowWidth,WindowHeight/hudSizeCof + 4,mainPaint );




        //Button restart
        canvas.drawText("Restart", WindowWidth - WindowWidth/hudSizeCof - TextSize*4, (WindowHeight/(hudSizeCof))/2 + TextSize/2  , textPaint);
        textS_Restart = new RectF(WindowWidth - WindowWidth/hudSizeCof - TextSize*4, 0, WindowWidth - WindowWidth/hudSizeCof, WindowHeight/hudSizeCof );
        //Button stop
        canvas.drawText("Stop", WindowWidth - WindowWidth/hudSizeCof - TextSize*8, (WindowHeight/(hudSizeCof))/2 + TextSize/2  , textPaint);
        textS_Stop = new RectF (WindowWidth - WindowWidth/hudSizeCof - TextSize*8, 0, WindowWidth - WindowWidth/hudSizeCof - TextSize*5, WindowHeight/hudSizeCof );

        //Draw Score
        canvas.drawText("Score : " + points, WindowWidth/hudSizeCof, (WindowHeight/(hudSizeCof))/2 + TextSize/2  , textPaint);




        //Health bar
        for(int i = 0; i < life; i++){

            Paint color = ball.GetPaint(WindowWidth - (ball.radius *3) * i - ball.radius - WindowWidth/20,WindowHeight - WindowHeight/25 + ball.radius*1.5f,
                    WindowWidth - (ball.radius *3) * i + ball.radius - WindowWidth/20,WindowHeight - WindowHeight/25 + ball.radius*2*1.5f);
            canvas.drawCircle(WindowWidth - (ball.radius *3) * i - WindowWidth/20, WindowHeight - WindowHeight/25 + ball.radius*1.5f , ball.radius, color);


        }



    }





}
