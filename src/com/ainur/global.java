package com.ainur;

import javax.swing.*;
import java.awt.*;

public class global{
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void sizeAdjuster(JPanel panel) {
        panel.setPreferredSize(new Dimension(screenSize.width, screenSize.height - 100));
    }
}
