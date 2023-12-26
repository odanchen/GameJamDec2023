package com.totallynotacult.jam;

public class PlayerCharacter {
    private double xCor;
    private double yCor;
    private int health;
    private int maxHealth;
    private double speed;

    public PlayerCharacter() {
        xCor = 200;
        yCor = 200;
        health = 6;
        maxHealth = 6;
        speed = 15;
    }

    public void move(int horizontalDir, int verticalDir, float deltaTime) {
        if (horizontalDir != 0 && verticalDir != 0) {
            xCor += speed / Math.sqrt(2) * horizontalDir;
            yCor += speed / Math.sqrt(2) * verticalDir;
        } else {
            xCor += speed * horizontalDir;
            yCor += speed * verticalDir;
        }
    }

    public double xCor() {return xCor;}
    public double yCor() {return yCor;}
}
