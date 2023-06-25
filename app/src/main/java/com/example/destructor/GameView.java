package com.example.destructor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.graphics.RectF;
import androidx.core.content.res.ResourcesCompat;

import java.util.Random;



public class GameView extends View {
    Context context;

    int brickOffsetTop, brickOffsetLeft, brickPadding;
    Handler handler;
    final long dt = 120;
    Runnable runnable;

    Paddle paddle;
    Ball ball;
    Brick[] bricks;
    HUD hud;
    Levels level;




    RectF walls = new RectF();
    RectF padTouchZone = new RectF();
    Vector setSpeed = new Vector(0,0);
    int dWidth, dHeight;

    float curColDistSq = 1000;
    int curColIndex;
    int counterDrawBricks;
    float touchMoveX , oldtouchMoveX;
    float cofSpeedX,cofSpeedY;
    Random random;
    int numBricks = 0;
    int maxBricksBroke = 0;
    boolean start = false;
    int points;
    boolean win;




    public GameView (Context context) {
        super(context);
        this.context = context;

        //bitmap and font for HUD
        Bitmap mapMain = BitmapFactory.decodeResource(getResources(), R.drawable.mainscreen);
        Typeface customTypeface = ResourcesCompat.getFont(context, R.font.beattech);
        //init HUD
        hud = new HUD(customTypeface, mapMain,context);
        level = new Levels();
        handler = new Handler();
        /*runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };*/

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        walls.set(0,dHeight/hud.hudSizeCof,dWidth,dHeight-dHeight/hud.hudSizeCof);
        random = new Random();

        cofSpeedX = dWidth/1080.f;
        cofSpeedY = dHeight/2400.f;

        padTouchZone.set(0,dHeight/2, dWidth, dHeight - dHeight/hud.hudSizeCof);

        brickOffsetTop = dHeight/20;
        brickOffsetLeft = dWidth/40;
        brickPadding = brickOffsetLeft/5;
        //Create all objects
        createObjects();

        hud.maxPoints = maxBricksBroke*10;
    }


    private  void createBricks(){

        float brickWidth = (dWidth - brickOffsetLeft*2) / 13 - brickPadding;
        float brickHeight = (dHeight*0.4f - brickOffsetTop) / 10;


        bricks = new Brick[150];
        maxBricksBroke = 0;
        numBricks = 0;
        //level.Level(12);
        level.Level(hud.getLevel());





        for(int column = 0; column < 13; column++){
            for(int row = 0; row < 10; row++){
                if(level.image[row][column] > 0){
                    float brickX = (column*(brickWidth+brickPadding*1.5f))+brickOffsetLeft+brickWidth/2 + brickPadding/2;
                    float brickY = (row*(brickHeight+brickPadding*1.5f))+brickOffsetTop*3;
                    //float brickX = (column*(brickWidth))+brickOffsetLeft+brickWidth/2 ;
                    //float brickY = (row*(brickHeight))+brickOffsetTop*3;

                    bricks[numBricks] = new Brick(new Vector(brickX,brickY), brickWidth, brickHeight, level.image[row][column],
                            1);
                    numBricks++;
                    if(level.image[row][column] != 11) maxBricksBroke++;

                }


            }
        }
    }
    private void createObjects(){
        int PaddleHalfWidth = dWidth/8;
        int PaddleHalfHeight = PaddleHalfWidth/10;
        int BallRadius = dHeight/90;


        Vector ballDir = new Vector(dWidth/150, dHeight/200);;

        paddle = new Paddle(new Vector(dWidth/2, dHeight*(float)0.85), PaddleHalfWidth, PaddleHalfHeight);
        ball = new Ball(new Vector(paddle.pos.x, paddle.pos.y - PaddleHalfHeight - BallRadius), ballDir, BallRadius, (cofSpeedY+cofSpeedX)/2 );

        createBricks();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        paddle.Update(event,walls,padTouchZone);

        //Check push on screen
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(hud.textS_Stop.contains(event.getX(),event.getY())){
                hud.gameover = true;
            } else if (hud.textS_Restart.contains(event.getX(),event.getY())) {
                hud.restart = true;
            }
            if(hud.textS_UnlimitedHealth.contains(event.getX(),event.getY())) {
                if(!hud.unlimitedHealth) hud.unlimitedHealth =true;
                else hud.unlimitedHealth= false;
            }


            if(padTouchZone.contains(event.getX(),event.getY())) start = true;

        }


        return true;
    }

    @Override
    protected void onDraw (Canvas canvas){


        if (hud.newlevel) {
            hud.incLevel();
            start = false;
            createObjects();
            counterDrawBricks = 0;
            hud.maxPoints = hud.getPoints() + maxBricksBroke * 10;
            hud.newlevel = false;
        } else if (hud.restart) {
            start = false;
            hud.resetLevel();
            hud.resetLife();
            hud.resetPoints();
            counterDrawBricks = 0;
            hud.maxPoints = maxBricksBroke * 10;
            createObjects();


            hud.restart = false;
        }

        if (hud.getPoints() >= hud.maxPoints) hud.newlevel = true;
        //Drawing hud elements
        hud.draw(canvas, dWidth, dHeight, ball);


        //Holding ball on paddle
        if (start && counterDrawBricks >= numBricks) {
            UpdateModels(10);
        } else {

            ball.SetDirection(setSpeed.change((0.5f + (random.nextInt(4) - 2) )* cofSpeedX, -0.5f * cofSpeedY + (random.nextInt(2) + 2) * cofSpeedY * -1));
            ball.pos.x = paddle.pos.x;
            ball.pos.y = paddle.pos.y - paddle.halfHeight - ball.radius;
        }


        //Drawing objects
        ball.Draw(canvas);
        paddle.Draw(canvas);


        //Draw bricks
        if (counterDrawBricks < numBricks) {
            for (int i = 0; i < counterDrawBricks; i++) {
                if (!bricks[i].destroyed || bricks[i].destroyingProces) {
                    bricks[i].Draw(canvas);
                }
            }
            counterDrawBricks++;
        } else {
            for (int i = 0; i < numBricks; i++) {
                if (!bricks[i].destroyed || bricks[i].destroyingProces) {
                    bricks[i].Draw(canvas);
                }
            }
        }





        /*
        if((ballX >= dWidth - ballRadius+5) || ballX <= 0){
            velocity.setX(velocity.getX() * -1);
        }
        if(ballY - ballRadius <= 0){
            velocity.setY(velocity.getY() * -1);
        }
        if(ballY + ballRadius > dHeight -20){// + paddle.getHeight()+200) {
            //ballX = 1 + random.nextInt(dWidth - ballRadius - 1);
            //ballY = dHeight / 4;
            ballX = paddleX + paddle.getWidth()/2.f;
            ballY = paddleY - 50;
            if (mpMiss != null) {
                mpMiss.start();
            }
            start = false;
            life--;
            if (life == 0) {
                gameOver = true;
                launchGameOver();
            }
        }


        if(((ballX) >= paddleX)
                && (ballX <= paddleX + paddle.getWidth())
                && (ballY + ballRadius >= paddleY)
                && (ballY + ballRadius <= paddleY + paddle.getHeight())){
            if(mpHit != null){
                mpHit.start();
            }
            velocity.setX(velocity.getX() + velocity.getX()/100);
            velocity.setY(velocity.getY() + velocity.getY()/100);


            velocity.setY(velocity.getY()* -1);

        }
        canvas.drawBitmap(paddle, paddleX, paddleY, null);*/
        /*
        //CollisionDetection
        for (int i = 0;  i < numBricks; i++){
            if(bricks[i].getVisibility()){
                float xd = bricks[i].x - ballX;
                float yd = bricks[i].y - ballY;
                float wd = bricks[i].width/2 + ballRadius;
                if(xd*xd + yd*yd <= wd*wd + 50 ){
                    if (mpBreak != null) {
                        mpBreak.start();
                    }
                    if(ballX > bricks[i].x + bricks[i].width/2 || ballX < bricks[i].x - bricks[i].width/2) velocity.setX(velocity.getX() * -1);

                    if(yd < bricks[i].y + bricks[i].height/2 || yd > bricks[i].y - bricks[i].height/2) velocity.setY(velocity.getY() * -1);


                    bricks[i].reduceHealth();
                    if(bricks[i].health < 1){
                        points += 10;
                        brokenBricks++;
                        bricks[i].setInvisible();
                    }


                    if(brokenBricks == numBricks){
                        launchGameOver();
                    }
                    }
            }
        }
        if(brokenBricks == numBricks){
            gameOver = true;
        }*/


        if(!hud.gameover && !hud.win) {

            invalidate();
            //handler.postDelayed(runnable, dt);
        }
        else {
            launchGameOver();
            points = hud.getPoints();
            win = hud.win;


        }

    }
    private void UpdateModels(long dt){
        boolean paddlecolHap = paddle.DoBallCollision(ball);
        ball.update(dt);

        hud.audio.startTheme();


        //Check paddle speed
        touchMoveX = oldtouchMoveX - paddle.pos.x;
        paddle.speed = touchMoveX/dt;
        oldtouchMoveX = paddle.pos.x;

        boolean collisionHappened = false;
        for( int i = 0; i < numBricks; i++ )
        {
            if( bricks[i].CheckBallCollision( ball ))
            {

                float newColDistSq = (ball.pos.x - bricks[i].getCenter().x)*(ball.pos.x - bricks[i].getCenter().x) -
                        (ball.pos.y - bricks[i].getCenter().y)*(ball.pos.y - bricks[i].getCenter().y);
                if( collisionHappened )
                {
                    if( newColDistSq < curColDistSq)
                    {
                        curColDistSq = newColDistSq;
                        curColIndex = i;
                    }
                    else if(newColDistSq - curColDistSq < brickPadding){
                        curColDistSq = newColDistSq;
                        curColIndex = i;
                    }
                }
                else
                {
                    curColDistSq = newColDistSq;
                    curColIndex = i;
                    collisionHappened = true;
                }
            }
        }

        if( collisionHappened )
        {

            paddle.resetCoolDown();
            bricks[curColIndex].ExecuteBallCollision( ball );
            bricks[curColIndex].reduceHealth();
            if(bricks[curColIndex].destroyed) hud.incScore(10);

            hud.audio.startBreak();
        }

        if( paddlecolHap )
        {
            hud.audio.startHit();
        }

        int ballWallColResult = ball.DoWallcolision(walls);
        if( ballWallColResult == 1 )
        {

            if( !RectF.intersects(paddle.GetRect(), ball.GetRect()))
            {
                paddle.resetCoolDown();
            }
            hud.audio.startHit();


        }
        else if( ballWallColResult == 2 )
        {
            hud.decLife();
            start = false;
        }


    }


    private void launchGameOver() {
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, GameOver.class);
        intent.putExtra("Score:", hud.getPoints());
        intent.putExtra("winGame",hud.win);
        context.startActivity(intent);
        ((Activity)context).finish();
    }


}

