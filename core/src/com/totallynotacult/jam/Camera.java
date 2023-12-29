package com.totallynotacult.jam;

import com.totallynotacult.jam.entities.Entity;
import com.totallynotacult.jam.entities.ShootingEntity;

import static java.lang.Math.*;

public class Camera {

    float x;
    float y;

    float xTo;
    float yTo;

    float width;

    float height;
    boolean switchingRoom = false;

    float roomSwitchHorX = 0;
    float roomSwitchVerY = 0;
    boolean switchingHor = false;


    ShootingEntity following;
    DungeonScreen screen;
    public Camera(ShootingEntity following, int width, int height, DungeonScreen screen) {
        this.following = following;
        this.width = width;
        this.height = height;
        x = following.getX();
        y = following.getY();
        xTo = x;
        yTo = y;
        this.screen = screen;

    }

    public void camFollow() {
        x += (xTo - x) / 12;
        y += (yTo - y) / 12;
        if (!switchingRoom) {
            xTo = (float) (following.getX() + 10 * cos(following.getAimAngle()));
            yTo = (float) (following.getY() + 10 * sin(following.getAimAngle()));
            roomSwitchHorX = following.getX();
            roomSwitchVerY = following.getY();

        } else {

            xTo = roomSwitchHorX;
            yTo = roomSwitchVerY;

            if (switchingHor) {
                if (abs(x) >= abs(roomSwitchHorX)-100) {
                    x = 180 + (-roomSwitchHorX);
                    switchingRoom = false;
                    screen.changeRoom(0, (int) (roomSwitchHorX/abs(roomSwitchHorX)));
                }
            }
            else {
                if (abs(y) >= abs(roomSwitchVerY)-100) {
                    y = 180 +(-roomSwitchVerY);
                    switchingRoom = false;

                    screen.changeRoom( (int) (-roomSwitchVerY/abs(roomSwitchVerY)), 0);

                }
            }
        }

    }

    public float clamp(float v, float min, float max) {
        if (v > max) v = max;
        if (v < min) v = min;
        return v;
    }

    public void switchRoom(boolean hor, boolean right) {
        int dis = 500;
        switchingHor = false;
        if (hor) {
            if (right) {
                roomSwitchHorX = 180+dis;

            } else {
                roomSwitchHorX = 180-dis;
            }
            switchingHor = true;
        }
        else {
            if (right) {
                roomSwitchVerY = 180+dis;
            } else {
                roomSwitchVerY = 180-dis;
            }
        }

        switchingRoom = true;
    }
}
