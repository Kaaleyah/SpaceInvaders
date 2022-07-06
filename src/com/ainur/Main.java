package com.ainur;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Window w = new Window();

        w.setSize(screenSize.width, screenSize.height);
        w.setVisible(true);
    }
}
