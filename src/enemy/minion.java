package enemy;

import com.ainur.global;
import player.bullet;
import player.shooter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class minion extends JPanel implements ActionListener {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;
    double X, Y;
    int level;
    int W = 100;
    int H = 150;
    double baseX, baseY;
    int lives;
    shooter mag = new shooter();

    boolean reverseMove = false;

    Image alienImage;

    public minion(double X, double Y, int level) {
        setOpaque(false);
        add(mag);

        this.level = level;

        if (level == 1) {
            alienImage = new ImageIcon("assets/img/MK1.png").getImage();
        }
        else if (level == 2) {
            alienImage = new ImageIcon("assets/img/MK2.png").getImage();
        }
        else if (level == 3) {
            alienImage = new ImageIcon("assets/img/boss.png").getImage();
        }

        this.X = X;
        this.Y = Y;
        this.baseX = X;
        this.baseY = Y;
        lives = 3;
    }

    public shooter getMag() {
        return mag;
    }

    public void decreaseLive() {
        lives -= 1;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D alien2D = (Graphics2D) g;

        alien2D.drawImage(alienImage, (int)X, (int)Y, W, H, null);

        mag.paintComponents(g);
    }

    public boolean isDead() {
        if (lives == 0) {
            return true;
        }
        return false;
    }

    public void shoot() {
        int angle = 0;

        if (level != 1) {
            Random rand = new Random();

            angle = rand.nextInt(60) - 30;
        }

        mag.reload(new bullet(X + W/2, Y + H, angle, true));
    }

    public void mover() {
        if (!reverseMove) {
            X += 1.5;

            if (X > baseX + Math.PI * 30) {
                reverseMove = true;
            }
        }
        else {
            X -= 1.5;

            if (X < baseX) {
                reverseMove = false;
            }
        }
        Y = baseY + (30 * Math.sin(X/20));
    }

    public Rectangle getBounds() {
        return new Rectangle((int)X, (int)Y, W, H);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mag.actionPerformed(e);

        repaint();
        setVisible(true);

        mover();
    }
}
