package com.ainur;

import screens.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Window extends JFrame implements ActionListener {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;

    FlowLayout bl;
    ImageIcon backImage;
    ImageIcon icon;
    JLabel backLabel;
    launchScreen launch = new launchScreen();
    gameOver gameFinished = new gameOver();
    gameScreen game;
    LinkedList<Integer> listofTimes = new LinkedList<>();
    int levelInterval = 0;
    int level = 0;
    Timer timerInterval;
    boolean gameOn = false;

    int levelIntervalDelay = 0;

    highScore highS;

    //Win Track
    screens.levelInterval breakLevel;

    //Music
    Clip musicClip;

    public BufferedImage loadImage() {
        BufferedImage baImage = null;
        try {
            baImage = ImageIO.read(new File("assets/img/space" + level + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baImage;
    }


    public Window() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super("Space Invaders");
        bl = new FlowLayout();
        setLayout(bl);
        icon = new ImageIcon("assets/img/ainula.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        launch.play.addActionListener(playListener);
        gameFinished.replay.addActionListener(playListener);
        this.requestFocusInWindow();

        //Music
        File musicFile = new File("assets/theme.wav");
        AudioInputStream musicTheme = AudioSystem.getAudioInputStream(musicFile);
        musicClip = AudioSystem.getClip();
        musicClip.open(musicTheme);

        global.sizeAdjuster(launch);
        global.sizeAdjuster(gameFinished);

        background(0);
        level = 0;

        add(launch);

        Timer t = new Timer(1, this);
        t.start();
    }

    public void background(int levelNum) {
        backImage = new ImageIcon("assets/img/space" + levelNum + ".png");
        backLabel = new JLabel(backImage);

        setContentPane(backLabel);
        setLayout(new FlowLayout());
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        repaint();
        revalidate();

        g.drawImage(loadImage(), 0, 0, (int)screenWidth, (int)screenHeight, null);
    }

    public void gameOverTracker() {
        if (game.isShipDead()) {
            finishGame();
        }
    }

    public void gameWinTrack() {
        if (game.win()) {
            gameOn = false;

            breakLevel = new levelInterval(game.getLevel());
            global.sizeAdjuster(breakLevel);
            game.levelUp();

            int level = game.getLevel();
            listofTimes.add(game.ship.getCurrentTime());

            remove(game);
            add(breakLevel);
            repaint();
            revalidate();

            if (level < 4) {
                timerInterval = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (levelInterval < 1) {
                            levelInterval++;
                        }
                        else {
                            gameOn = true;
                            remove(breakLevel);

                            background(game.getLevel());
                            add(game);
                            game.levelSetUp();

                            timerInterval.stop();
                            levelInterval = 0;
                        }
                    }
                });
                timerInterval.start();
            }
            else {
                remove(breakLevel);
                highS = new highScore(listofTimes);
                highS.play.addActionListener(playListener);
                global.sizeAdjuster(highS);
                add(highS);
            }

            repaint();
            revalidate();
        }
    }

    public void gameStart() {
        musicClip.start();
        remove(launch);
        game = new gameScreen();
        background(game.getLevel());
        global.sizeAdjuster(game);
        add(game);
        gameOn = true;

    }


    public void finishGame() {
        musicClip.stop();
        remove(game);
        add(gameFinished);

        repaint();
        revalidate();
    }


    //Action Listeners//

    ActionListener playListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            levelInterval = 0;
            listofTimes.clear();
            gameStart();

            repaint();
            revalidate();
        }
    };


    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameOn) {
            game.actionPerformed(e);

            gameOverTracker();
            gameWinTrack();
        }

    }
}
