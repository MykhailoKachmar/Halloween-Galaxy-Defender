package com.example.destructor;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

public class PaintColor {

    public PaintColor(int type, RectF rect){
        switch (type){
            case 1:
            {
                this.color2 = Color.argb(255,20,20,20);
                this.color1 = Color.argb(255,80,80,80);

                break;}
            case 2:
            {
                this.color1 = Color.argb(255,255,0,0);
                this.color2 = Color.argb(255,102,0,0);

                break;}
            case 3:
            {
                this.color1 = Color.argb(255,0,255,0);
                this.color2 = Color.argb(255,0,102,0);

                break;}
            case 4:
            {
                this.color1 = Color.argb(255,204,0,204);
                this.color2 = Color.argb(255,102,0,102);

                break;}

            case 5:
            {
                this.color1 = Color.argb(255,255,255,0);
                this.color2 = Color.argb(255,102,102,0);

                break;}

            case 6:
            {
                this.color1 = Color.argb(255,127,0,255);
                this.color2 = Color.argb(255,51,0,102);

                break;}
            case 7:
            {
                this.color1 = Color.argb(255,254,151,29);
                this.color2 = Color.argb(255,102,40,10);

                break;}
            case 8:
            {
                this.color1 = Color.argb(200,0,0,255);
                this.color2 = Color.argb(200,0,0,102);

                break;}
            case 9:
            {
                this.color1 = Color.argb(200,0,255,255);
                this.color2 = Color.argb(200,0,102,102);

                break;}
            case 10:
            {
                this.color1 = Color.argb(255,255,255,255);
                this.color2 = Color.argb(255,104,104,104);

                break;}
            case 11:
            {
                this.color1 = Color.argb(255,169,169,169);
                this.color2 = Color.argb(255,75,75,75);

                break;}

        }

        Shader s1 = new LinearGradient(rect.left, rect.top,
                rect.right, rect.bottom, color1, color2, Shader.TileMode.CLAMP);


        this.out = new Paint();
        this.out.setShader(s1);

        Shader s2 = new LinearGradient(rect.left, rect.top,
                rect.right, rect.bottom, color2, color1, Shader.TileMode.MIRROR);

        this.in = new Paint();
        this.in.setShader(s2);




    }

    public int color1, color2;

    public Paint out, in;



}
