package com.raven.component;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MP3Player {

    private Player player;
    private FileInputStream fileInputStream;
    private String filePath;
    private int totalDuration;
    private boolean isPlaying = false;

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public int getCurrentPosition() {
        return player == null ? 0 : player.getPosition() / 1000;
    }

    public void loadFile(String filePath) throws IOException, InvalidDataException, UnsupportedTagException {
        this.filePath = filePath;
        Mp3File mp3File = new Mp3File(filePath);
        totalDuration = (int) mp3File.getLengthInSeconds();
    }

    public void play() throws FileNotFoundException, JavaLayerException {
        if (player != null) {
            player.close();
        }
        fileInputStream = new FileInputStream(filePath);
        player = new Player(fileInputStream);
        isPlaying = true;

        new Thread(() -> {
            try {
                player.play();
                isPlaying = false;
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }).start();
    }

    public void pause() {
        if (player != null) {
            player.close();
            isPlaying = false;
        }
    }

    public void close() {
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
