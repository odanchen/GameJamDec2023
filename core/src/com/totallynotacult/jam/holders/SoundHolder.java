package com.totallynotacult.jam.holders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public enum SoundHolder {
    TIMESTOP_SOUND("timeStopSoundEffect.mp3"),
    ZAWARUDO("zawarudo.mp3"),
    GUN("gun.wav"),
    SHOTGUN("shotgun.wav"),
    AMMO_PICKUP("ammo_pickup.wav"),
    DAMAGE("enemy_wounded.wav");
    private final Sound sound;

    public Sound getSound() {
        return sound;
    }

    public void dispose() {
        for (var value : values()) value.sound.dispose();
    }

    private SoundHolder(String s) {
        sound = Gdx.audio.newSound(Gdx.files.internal(s));
    }
}
