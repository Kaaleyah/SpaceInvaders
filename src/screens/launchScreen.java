package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class launchScreen extends JPanel {
    boolean isPlay = false;
    BorderLayout launchBorder;
    JPanel buttonPanel;
    public JButton play;
    public JButton quit;


    public launchScreen() {
        super();
        launchBorder = new BorderLayout();
        setLayout(launchBorder);

        title();
        controls();
        buttonGroup();
    }

    public void title() {
        setOpaque(false);
        ImageIcon title = new ImageIcon("assets/img/title.png");
        JLabel titleLayout = new JLabel(title);

        add(titleLayout, BorderLayout.NORTH);
    }

    public void controls() {
        setOpaque(false);

        ImageIcon control = new ImageIcon("assets/img/controls.png");
        JLabel controlLayout = new JLabel(control);

        add(controlLayout, BorderLayout.EAST);
    }

    public void buttonGroup() {
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        ImageIcon playImage = new ImageIcon("assets/img/play.png");
        ImageIcon quitImage = new ImageIcon("assets/img/quit.png");

        play = new JButton();
        play.setIcon(playImage);
        clearButton(play);

        play.addActionListener( new ActionListener() {
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

        buttonPanel.add(play);
        buttonPanel.add(quit);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void clearButton(JButton b) {
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }
}
