package com.example.destructor;

import static java.lang.Math.abs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class Paddle {

    public Paddle(Vector pos, float halfWidth, float halfHeight){
        this.pos = pos;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.exitXFactor = maximumExitRatio/ halfWidth;
        this.fixedZoneHalgWidth = halfWidth * fixedZoneWidthRatio;
        this.fixedZoneExitX = fixedZoneHalgWidth * exitXFactor;
    }
    public float halfWidth;
    float touchX;
    public float halfHeight;
    public Vector pos;
    public boolean isCooldown = false;
    public static Paint color = new Paint();;
    public static final float maximumExitRatio = 2.6f;
    public static final float fixedZoneWidthRatio = 0.2f;
    public float exitXFactor, fixedZoneHalgWidth, fixedZoneExitX;
    public float oldMoveX, oldPaddleX, dposX;

    public float speed = 0;

    public RectF GetRect(){
        RectF rectF = new RectF();
        rectF.set(pos.x - halfWidth, pos.y - halfHeight, pos.x + halfWidth, pos.y + halfHeight);
        return rectF;
    }
    public boolean DoBallCollision(Ball ball){
        if(!isCooldown){
            RectF rect = GetRect();
            if(RectF.intersects(rect, ball.GetRect()))
            {
                Vector ballV = new Vector(ball.vel.x, ball.vel.y).getNormala();
                Vector padV = new Vector(speed*2.2f, 0).getNormala();
                Vector ballPos = ball.getPos();
                if( (ball.getVel().x < 0) == ((ballPos.x - pos.x) < 0)
                        || (ballPos.x >= rect.left && ballPos.x <= rect.right))
                {
                    if (abs(speed) < 0.4){
                        ball.invertY();
                    }else {
                        Vector dir;
                        /*float xDifference = ballPos.x - pos.x;
                        if(abs(xDifference) < fixedZoneHalgWidth)
                        {
                            //if(xDifference < 0.0f) dir = new Vector(-fixedZoneExitX,-1.0f);
                            if(ballV.x > 0 && padV.x > 0 || ballV.x < 0 && padV.x < 0  ) dir = new Vector(fixedZoneExitX,-1.0f);
                            else dir = new Vector(-fixedZoneExitX,-1.0f);
                        }
                        else dir = new Vector(xDifference*exitXFactor,-1.0f);*/

                        if((ballV.x > 0 && padV.x >0) && (ballV.x < 0 && padV.x < 0 )) dir = new Vector(speed*0.8f+0.2f*ball.vel.x,-ball.vel.y);
                        else dir = new Vector(-(speed*0.8f+0.2f*ball.vel.x),-ball.vel.y);
                        ball.SetDirection(dir);
                    }


                }
                else ball.invertX();

                ball.incSpeed(0.005f);
                isCooldown = true;
                return true;
            }
        }
        return false;
    }
    private void DoWallCollision(float newPos, @NonNull RectF walls){
        if(newPos <= halfWidth) pos.x = halfWidth;
        else if(newPos >= walls.width() - halfWidth) pos.x = walls.width() - halfWidth;
        else pos.x = newPos;
    }

    public void Update(MotionEvent event, @NonNull RectF walls, RectF padTouchZone){
        touchX = event.getX();
        float touchY = event.getY();
        if((touchY <= padTouchZone.bottom) && (touchY >= padTouchZone.top)){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                oldMoveX = event.getX();
                oldPaddleX = pos.x;
            }
            if(action == MotionEvent.ACTION_MOVE){

                dposX = oldMoveX - touchX;
                float newX = oldPaddleX - dposX;
                DoWallCollision(newX, walls);

            }
        }
    }

    public void Draw(Canvas canvas){
        Shader shader = new LinearGradient(pos.x, pos.y - halfHeight,
                pos.x, pos.y + halfHeight, Color.CYAN,Color.BLACK, Shader.TileMode.CLAMP);
        color.setShader(shader);
        canvas.drawRoundRect(pos.x - halfWidth,pos.y - halfHeight,pos.x +halfWidth,
                pos.y + halfHeight, halfWidth/8, halfHeight/2, color);

    }

    public void resetCoolDown(){ isCooldown = false;}


}
