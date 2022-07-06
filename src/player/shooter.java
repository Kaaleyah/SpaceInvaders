package player;

import com.ainur.global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class shooter extends JPanel implements ActionListener {
    double screenHeight = global.screenSize.height;

    private LinkedList<bullet> bulletList = new LinkedList<bullet>();

    bullet bulletFrame;

    public shooter() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < bulletList.size(); i++) {
            bulletFrame = bulletList.get(i);

            bulletFrame.actionPerformed(e);
        }
    }

    @Override
    public void paintComponents(Graphics f) {
        super.paintComponents(f);

        for (int i = 0; i < bulletList.size(); i++) {
            bulletFrame = bulletList.get(i);

            if (bulletFrame.bY < 0 || bulletFrame.bY > screenHeight) {
                removeBullet(bulletFrame);
            }

            bulletFrame.paintComponents(f);
        }
    }

    public LinkedList<bullet> getList() {
        return bulletList;
    }

    public void reload(bullet newBullet) {
        bulletList.add(newBullet);
    }
    public void removeBullet(bullet oldBullet) {
        bulletList.remove(oldBullet);
    }
}