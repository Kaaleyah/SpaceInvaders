package screens;

import com.ainur.global;
import enemy.*;
import player.bullet;
import player.spaceShip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class gameScreen extends JPanel implements ActionListener {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;

    shield wall;
    public spaceShip ship;
    minionFleet enemies;
    int level = 1;

    public gameScreen() {
        setOpaque(false);
        setFocusable(true);
        requestFocusInWindow();

        levelSetUp();

        Timer t = new Timer(1, this);
        //t.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        enemies.paintComponents(g);
        ship.paintComponents(g);
        wall.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enemies.actionPerformed(e);
        ship.actionPerformed(e);
        wall.actionPerformed(e);

        hitAliens();
        hitShip();
        hitBarricades();

        repaint();
        revalidate();
    }

    public void levelUp() {
        level++;
    }

    public void levelSetUp() {
        if (level == 1) {
            enemies = new minionFleet(4,  level);

            wall = new shield(4);
        }
        else if (level == 2) {
            enemies = new minionFleet(6,  level);
            wall = new shield(4);
        }
        else if (level == 3) {
            enemies = new minionFleet(6, level);
            wall = new shield(6);
        }

        ship = new spaceShip();

        add(ship);
        add(enemies);
        add(wall);

    }

    public int getLevel() {
        return level;
    }

    public void hitShip() {
        for (int i = 0; i < enemies.getList().size(); i++) {
            minion currentMinion = enemies.getMinion(i);
            LinkedList<bullet> currentMinionMagList = currentMinion.getMag().getList();

            for (int j = 0; j < currentMinionMagList.size(); j++) {
                Rectangle shipBounds = ship.getBounds();
                bullet currentBullet = currentMinionMagList.get(j);
                Rectangle bullBound = currentBullet.getBounds(10, 10);

                if (bullBound.intersects(shipBounds)) {
                    currentMinion.getMag().removeBullet(currentBullet);

                    ship.decreaseHeart();

                    repaint();
                    revalidate();
                }
            }
        }
    }

    public boolean isShipDead() {
        return ship.isDead();
    }

    public void hitAliens() {
        for (int i = 0; i < ship.mag.getList().size(); i++) {
            bullet currentBullet = ship.mag.getList().get(i);
            Rectangle bulletB = currentBullet.getBounds(10, 10);

            for (int j = 0; j < enemies.getList().size(); j++) {
                minion currentMinion = enemies.getList().get(j);
                Rectangle alienB = currentMinion.getBounds();

                if (bulletB.intersects(alienB)) {
                    currentMinion.decreaseLive();
                    ship.mag.removeBullet(currentBullet);
                }

                if (currentMinion.isDead()) {
                    enemies.removeAlien(currentMinion);
                }
            }
        }
    }

    public void hitBarricades(){
        for (int i = 0; i< wall.getList().size(); i++){
            Rectangle barricadeBounds = wall.getIndexedElement(i).getBounds();

            for (int j = 0; j < ship.mag.getList().size(); j++) {
                bullet currentBullet = ship.mag.getList().get(j);
                Rectangle bulletB = currentBullet.getBounds(10, 10);

                if (bulletB.intersects(barricadeBounds)){
                    ship.mag.removeBullet(currentBullet);
                }
            }
        }
    }

    public boolean win() {
        if (enemies.getList().size() == 0) {
            return true;
        }
        return false;
    };
}
