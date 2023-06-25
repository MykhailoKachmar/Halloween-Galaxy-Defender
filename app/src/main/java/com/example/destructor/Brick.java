package com.example.destructor;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.graphics.Canvas;
import android.graphics.RectF;

import androidx.compose.ui.geometry.Rect;

public class Brick {

    public Brick(Vector pos, float width, float height, int color, int health){
        this.rect = new RectF(pos.x - width/2, pos.y  - height/2, pos.x + width/2, pos.y + height/2);
        this.color = color;
        this.health = health;
        diag = (float)Math.sqrt(width*width+height*height);
        if(color == 11){
            isUnbreakable = true;
        }
        this.destroyed = false;

    }
    public int color;
    private boolean isTouched = false;
    private int countTimeChange = 0;
    public RectF rect;
    public float diag;
    public int health;
    boolean isUnbreakable = false;
    boolean destroyed = true;
    boolean destroyingProces = false;

    public void changeColor(int color){
        color = color;
    }

    public void setDestroyed(){ destroyed = false;}

    public boolean CheckBallCollision( Ball ball){
        boolean collHappend = false;
        if(!destroyed && RectF.intersects(rect, ball.GetRect())) {


            for(int i = 0; i < 359; i += 15){
                float x = (float) (24*Math.cos(i) + ball.pos.x);
                float y = (float) (24*Math.sin(i) + ball.pos.y);
                //Vector pos = new Vector(x,y);
                if(rect.contains(x,y)) {
                    collHappend = true;
                    break;
                }
            }

        /*Vector temp = new Vector(ballRect.right - rect.left, ball.pos.y - rect.centerY());
        float distancesToLeft = temp.GetLength();
        temp = new Vector(ball.pos.x - rect.centerX(), ballRect.bottom - rect.top);
        float distancesToTop = temp.GetLength();
        temp = new Vector(ballRect.right - rect.left, ball.pos.y - rect.centerY());
        float distancesToRight = temp.GetLength();
        temp = new Vector(ball.pos.x - rect.centerX(), ballRect.top - rect.bottom);
        float distancesToBottom = temp.GetLength();



        RectF ballRect = ball.GetRect();
        if(rect.contains(ballRect.left, ballRect.left)){
            ;
        }
            if ((ball.pos.x + ball.radius > rect.left) && (ball.pos.x - ball.radius < rect.right)
                    && (ball.pos.y + ball.radius > rect.top) && (ball.pos.y - ball.radius < rect.bottom)) {
                //ball.pos.x = rect.left - ball.radius;
                collHappend = true;
            } else if ((ball.pos.y + ball.radius > rect.top) && (ball.pos.y - ball.radius < rect.bottom)
                    && (ball.pos.x + ball.radius > rect.left) && (ball.pos.x - ball.radius < rect.right)) {
                //ball.pos.y = rect.top - ball.radius;
                collHappend = true;
            }

        float minY = min(distancesToTop, distancesToBottom);
        float minX = min(distancesToLeft, distancesToRight);
        if(minX < minY){
            if(minX < (ball.radius + rect.width()/2)){
                collHappend = true;
                if(minX == distancesToLeft){
                   ball.pos.x = rect.left - ball.radius;
                }else ball.pos.x = rect.right + ball.radius;
            }
        } else {
            if (minY < (ball.radius + rect.height()/2)) {
                collHappend = true;
                if (minY == distancesToTop) {
                    ball.pos.y = rect.top - ball.radius;
                } else ball.pos.y = rect.bottom + ball.radius;
            }
        }*/
        }

        return !destroyed && collHappend;
    }

    public void ExecuteBallCollision( Ball ball){
        assert CheckBallCollision(ball);
        Vector ballpos = ball.getPos();

        isTouched = true;

        RectF ballRect = ball.GetRect();
        float NearestX = max(rect.centerX(), min(ball.pos.x, rect.centerX() + rect.width()));
        float NearestY = max(rect.centerY(), min(ball.pos.y, rect.centerY() + rect.height()));
        Vector dist = new Vector(ball.pos.x - NearestX, ball.pos.y - NearestY);

        if (dist.x > dist.y){
            ball.pos.x += dist.x;
        }else ball.pos.y += dist.y;


        if((ball.getVel().x < 0) && ((ballpos.x - getCenter().x) < 0)){
            ball.invertY();

        } else if (ballpos.x >= rect.left && ballpos.x <= rect.right) {
            ball.invertY();

        } else  {
            ball.invertX();
        }



        /*if(rect.contains(ball.pos.x + ball.radius, ball.pos.y)){
                ball.invertX();
            }else if(rect.contains(ball.pos.x - ball.radius, ball.pos.y)){
                ball.invertX();
            }else if(rect.contains(ball.pos.x , ball.pos.y + ball.radius)){
                ball.invertY();
            }else if(rect.contains(ball.pos.x , ball.pos.y - ball.radius)){
                ball.invertY();
            }


        if((ball.pos.x + ball.radius > rect.left) && (ball.pos.y + ball.radius > rect.top) && (ball.pos.y - ball.radius < rect.bottom)){
            ball.pos.x = rect.left - ball.radius;
        }else if((ball.pos.x - ball.radius < rect.right) && (ball.pos.y + ball.radius > rect.top) && (ball.pos.y - ball.radius < rect.bottom)){
            ball.pos.x = rect.right  - ball.radius;
        }else if((ball.pos.y + ball.radius > rect.top) && (ball.pos.x + ball.radius > rect.left) && (ball.pos.x - ball.radius < rect.right)){
            ball.pos.y = rect.top - ball.radius;
        }else if((ball.pos.x - ball.radius < rect.bottom) && (ball.pos.x + ball.radius > rect.left) && (ball.pos.x - ball.radius < rect.right)){
            ball.pos.y = rect.bottom - ball.radius;
        }*/



















    }

    public void reduceHealth(){
        if(health > 0) health--;

        if(health < 1 && !isUnbreakable)
        {
            destroyed = true;
            destroyingProces = true;
        }
    }

    public Vector getCenter(){
       return new Vector(rect.centerX(), rect.centerY());
    }

    public  void Draw(Canvas canvas){


        RectF outRect = new RectF(rect.left+rect.width()/20, rect.top+rect.height()/20,
                rect.right - rect.width()/20, rect.bottom - rect.height()/20);

        PaintColor color = new PaintColor(this.color, rect);
        if(!isTouched && !destroyingProces){
            color.out.setAlpha(255);
            color.in.setAlpha(255);
        }else {
            color.out.setAlpha(255-countTimeChange*18);
            color.in.setAlpha(255-countTimeChange*18);
            if(countTimeChange > 10 ){
                countTimeChange =0;
                isTouched = false;
                destroyingProces = false;
            }
            else countTimeChange++;

        }




        canvas.drawRoundRect(rect, rect.width()/10,rect.height()/10,color.out);
        canvas.drawRoundRect(outRect, outRect.width() / 10  ,outRect.height() / 10,color.in);


    }









}
