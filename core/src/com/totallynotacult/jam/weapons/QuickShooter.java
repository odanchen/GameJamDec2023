package com.totallynotacult.jam.weapons;

import com.totallynotacult.jam.entities.EntityManager;

public abstract class QuickShooter extends Weapon {
    public void quickShoot(float xCor, float yCor, float angle, EntityManager manager, boolean isFriendly) {
        timeSinceShot = shootDelay;
        super.shoot(xCor, yCor, angle, manager, isFriendly);
    }
}
