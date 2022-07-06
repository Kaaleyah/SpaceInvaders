package screens;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class levelInterval extends JPanel{
    BorderLayout levelBorder;

    public levelInterval(int levelNum) {
        setOpaque(false);
        levelBorder = new BorderLayout();
        setLayout(levelBorder);

        JLabel levelInfo = new JLabel("  Level " + levelNum + " Complete");

        levelInfo.setFont(new Font("Jetbrains Mono", Font.PLAIN, 100));
        levelInfo.setForeground(new Color(255, 255, 255));
        levelInfo.setBackground(new Color(0, 0, 0, 50));

        add(levelInfo, BorderLayout.CENTER);
    }

}
