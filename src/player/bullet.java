package player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class bullet extends JPanel implements ActionListener {
    double bX;
    double bY;
    int bW = 10;
    int bH = 40;
    double bAngle = 0;
    boolean isEnemy;

    Image bulletImage;
    Graphics2D bullet2D;

    public bullet(double X, double Y, double angle, boolean isEnemy) {
        setOpaque(false);
        this.isEnemy = isEnemy;

        if (isEnemy) {
            bulletImage = new ImageIcon("assets/img/alienProjectile.png").getImage();
        }
        else {
            bulletImage = new ImageIcon("assets/img/bull.png").getImage();
        }

        this.bX = X;
        this.bY = Y;
        this.bAngle = angle;
        repaint();
        revalidate();

        setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        AffineTransform transform = new AffineTransform();

        if (isEnemy) {
            transform.rotate(Math.toRadians(-bAngle), bX, bY);
        }
        else {
            transform.rotate(Math.toRadians(bAngle), bX, bY);
        }

        transform.translate((int)bX + bAngle, (int)bY);

        bullet2D = (Graphics2D) g;

        bullet2D.drawImage(bulletImage, transform, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int baseSpeed;
        double angleYSpeed = 0;
        double angleXSpeed = 0;

        if (isEnemy) {
            baseSpeed = +5;
            angleYSpeed = Math.cos(Math.toRadians(bAngle)) * 2 * baseSpeed;
            angleXSpeed = Math.sin(Math.toRadians(bAngle)) * 2 * baseSpeed;
        }
        else {
            baseSpeed = -8;
            angleYSpeed = Math.cos(Math.toRadians(bAngle)) * 2 * baseSpeed;
            angleXSpeed = -Math.sin(Math.toRadians(bAngle)) * 2 * baseSpeed;;
        }
        bX += angleXSpeed;
        bY += angleYSpeed;


        repaint();
        revalidate();
        setVisible(true);
    }

    public Rectangle getBounds(int width, int height) {
        return new Rectangle((int)bX, (int)bY, width, height);
    }
}
