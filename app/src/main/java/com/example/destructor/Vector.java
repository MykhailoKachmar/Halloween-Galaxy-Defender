package com.example.destructor;

public class Vector {
    public float x,y;
    public Vector(float x, float y ){
        this.x = x;
        this.y = y;
    }

    public Vector change(float x,float y){
        this.x = x;
        this.y = y;
        return  this;
    }

    public Vector mov(Vector v)
    {
        Vector newV = this;
        newV.x *= v.x;
        newV.y *= v.y;
        return newV;
    }

    public float GetLength(){
        return (float) Math.sqrt(x*x + y*y);
    }

    public Vector getNormala(){
        float len = GetLength();
        if( len != 0.0f )
        {
            Vector newV = this;
            newV.x *= (1.0f / len);
            newV.y *= (1.0f / len);
            return newV;
        }
        return this;

    }

}
