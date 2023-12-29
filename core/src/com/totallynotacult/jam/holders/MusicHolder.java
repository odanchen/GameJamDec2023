package com.totallynotacult.jam.holders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum MusicHolder {
    THEME("GameMusic.mp3");

    private Music music;

    public Music getMusic() {
        return music;
    }

    private MusicHolder(String s) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal(s));
    }
}

