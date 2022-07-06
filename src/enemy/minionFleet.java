package enemy;

import com.ainur.global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class minionFleet extends JPanel implements ActionListener {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;
    static Random rand = new Random();

    protected LinkedList<minion> aliens = new LinkedList<minion>();
    int numOfMinions;
    int levelNum = 1;
    int shootDelay = 0;
    int maxDelay = 40;

    minion dummyMinion;

    public minionFleet(int num, int level) {
        this.levelNum = level;

        this.numOfMinions = num;

        addFleet();
    }

    public void selectShoot() {
        int luckyOne = 1;
        int remainingMinions = aliens.size();


        if (remainingMinions > 0 && shootDelay > maxDelay) {
            luckyOne = rand.nextInt(remainingMinions);

            aliens.get(luckyOne).shoot();

            shootDelay = 0;

            if (remainingMinions > aliens.size()) {
                maxDelay += 20;
            }
        }
        else {
            shootDelay++;
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < aliens.size(); i++) {
            dummyMinion = aliens.get(i);

            dummyMinion.actionPerformed(e);

            selectShoot();
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        for (int i = 0; i < aliens.size(); i++) {
            dummyMinion = aliens.get(i);

            dummyMinion.paintComponents(g);
        }
    }

    public void addFleet() {
        double distance = screenWidth / numOfMinions;
        double initPosition = 100;

        for (int i = 0; i < screenWidth - 100; i+= distance) {
            aliens.add( new minion(initPosition + i,100, levelNum));
        }
    }

    public LinkedList<minion> getList() {
        return aliens;
    }

    public minion getMinion(int n) {
        return aliens.get(n);
    }

    public void removeAlien(minion deadAlien) {
        aliens.remove(deadAlien);
    }
}
