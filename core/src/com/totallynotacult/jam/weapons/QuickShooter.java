package com.totallynotacult.jam.weapons;

import com.totallynotacult.jam.entities.EntityManager;
import com.totallynotacult.jam.entities.ShootingEntity;

public abstract class QuickShooter extends Weapon {
    public QuickShooter(ShootingEntity owner) {
        super(owner);
    }

    public void quickShoot(float angle, EntityManager manager, boolean isFriendly) {
        timeSinceShot = shootDelay;
        super.shoot(angle, manager, isFriendly);
    }
}
