package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class gameOver extends JPanel {
    boolean isPlay = false;
    BorderLayout launchBorder;
    JPanel buttonPanel;
    public JButton replay;
    public JButton quit;


    public gameOver() {
        super();
        launchBorder = new BorderLayout();
        setLayout(launchBorder);

        title();

        buttonGroup();
    }

    public void title() {
        setOpaque(false);
        ImageIcon title = new ImageIcon("assets/img/gameOver.png");
        JLabel titleLayout = new JLabel(title);

        add(titleLayout, BorderLayout.NORTH);
    }

    public void buttonGroup() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        ImageIcon playImage = new ImageIcon("assets/img/play.png");
        ImageIcon quitImage = new ImageIcon("assets/img/quit.png");

        replay = new JButton();
        replay.setIcon(playImage);
        clearButton(replay);



        replay.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlay = true;
                repaint();
            }

        });

        quit = new JButton();
        quit.setIcon(quitImage);
        clearButton(quit);

        quit.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });

        buttonPanel.add(replay);
        buttonPanel.add(quit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void clearButton(JButton b) {
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }
}
