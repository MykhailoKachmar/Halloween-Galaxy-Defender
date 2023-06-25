package com.example.destructor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;


public class Ball{

    public Ball(Vector pos, Vector dir, float radius, float ballspeedcoff){
        this.pos = pos;
        this.radius = radius;
        this.speed = 2.1f * ballspeedcoff;
        SetDirection(dir);
    }
    public Vector pos;
    public Vector vel = new Vector(0,0);
    public float radius = 30;
    public float speed = 2.1f;
    private Paint color;


    public float getRadius() {
        return radius;
    }
    public Vector getPos() {
        return pos;
    }
    public Vector getVel() {
        return vel;
    }

    public void update(float dt){
        pos.x += vel.x*dt;
        pos.y += vel.y*dt;
    }
    public void invertX(){
        vel.x = vel.x*-1;
    }
    public void invertY(){
        vel.y = vel.y*-1;
    }
    public RectF GetRect(){
        RectF rectF = new RectF();
        rectF.set(pos.x - radius, pos.y - radius, pos.x + radius, pos.y + radius);
        return rectF;
    }
    public Paint GetPaint(float x0, float y0, float x1, float y1){
        Shader shaderBall = new LinearGradient(x0, y0, x1, y1,Color.MAGENTA,Color.BLACK, Shader.TileMode.CLAMP);
        color = new Paint();
        color.setShader(shaderBall);
        return color;
    }
    public int DoWallcolision(@NonNull RectF walls){
        int collisionResult = 0;
        RectF rect = GetRect();
        if( rect.left < walls.left )
        {
            pos.x += walls.left - rect.left;
            invertX();
            collisionResult = 1;
        }
        else if( rect.right > walls.right )
        {
            pos.x -= rect.right - walls.right;
            invertX();
            collisionResult = 1;
        }
        if( rect.top < walls.top )
        {
            pos.y += walls.top - rect.top;
            invertY();
            collisionResult = 1;
        }
        else if( rect.bottom > walls.bottom )
        {
            pos.y -= rect.bottom - walls.bottom;
            invertY();
            collisionResult = 2;
        }
        return collisionResult;
    }
    public void SetDirection(Vector dir){
        vel = dir.getNormala();

        vel.x *= speed;
        vel.y *= speed;


    }
    public void Draw(Canvas canvas){
        Shader shaderBall = new LinearGradient(pos.x - radius,
                pos.y - radius,pos.x + radius,
                pos.y + radius,Color.MAGENTA,Color.BLACK, Shader.TileMode.CLAMP);
        color = new Paint();
        color.setShader(shaderBall);
        canvas.drawCircle(pos.x, pos.y, radius, color);

    }

    public void incSpeed(float plus){
        speed += plus;
    }



}
