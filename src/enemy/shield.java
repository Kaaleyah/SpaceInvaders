package enemy;

import com.ainur.global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class shield extends JPanel {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;

    LinkedList<barricade> shieldList = new LinkedList<barricade>();

    barricade dummyBar;

    public shield(int numOfBars) {
        double initPosition = 200;
        double distance = screenWidth / numOfBars;

        for (int i = 0; i < numOfBars; i++) {
            shieldList.add(new barricade(initPosition + i * distance, 300));
        }
    }

    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        for (int i = 0; i < shieldList.size(); i++) {
            dummyBar = shieldList.get(i);

            dummyBar.paintComponents(g);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < shieldList.size(); i++) {
            dummyBar = shieldList.get(i);

            dummyBar.actionPerformed(e);
        }
    }

    public LinkedList<barricade> getList() {
        return shieldList;
    }

    public barricade getIndexedElement(int i) {
        return shieldList.get(i);
    }


    public class barricade extends JPanel {
        double X, Y;
        int W = 150;
        int H = 50;

        Image bar = new ImageIcon("assets/img/barricade.png").getImage();

        public barricade(double X, double Y) {
            this.X = X;
            this.Y = Y;
        }

        public void paintComponents(Graphics g) {
            super.paintComponents(g);

            Graphics2D bar2D = (Graphics2D) g;

            bar2D.drawImage(bar, (int)X, (int)Y, W, H, null);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
            setVisible(true);
        }

        public Rectangle getBounds() {
            return new Rectangle((int)X, (int)Y, W, H);
        }
    }
}
