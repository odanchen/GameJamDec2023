package com.totallynotacult.jam;

import com.totallynotacult.jam.entities.Entity;

public class Camera {

    float x;
    float y;

    float xTo;
    float yTo;

    float width;

    float height;
    Entity following;

    public Camera(Entity following, int width, int height) {
        this.following = following;
        this.width = width;
        this.height = height;
        x = following.getX();
        y = following.getY();
        xTo = x;
        yTo = y;

    }

    public void camFollow() {
        xTo = following.getX();
        yTo = following.getY();
        x += (xTo - x) / 25;
        y += (yTo - y) / 25;

        //x = clamp(x,width/2-width/2,width-width/2);
        //y = clamp(y,height-height/2,height/2 );
    }

    public float clamp(float v, float min, float max) {
        if (v > max) v = max;
        if (v < min) v = min;
        return v;
    }

    public void switchRoom() {
        x = following.getX();
        y = following.getY();
        xTo = x;
        yTo = y;
    }
}
