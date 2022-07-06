package player;

import com.ainur.global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class spaceShip extends JPanel implements ActionListener {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;
    int hearts = 6;
    int currentTime;

    stopWatch sW = new stopWatch();

    int W = 100;
    int H = 150;
    double X = screenWidth / 2.0 - W;
    double Y = (int)screenHeight - 300;
    double velX = 0;
    double velB = 0;
    double angle = 0;
    String direction = "default";

    //HUD Elements
    BorderLayout shipBorder;
    JPanel HUDPanel = new JPanel();

    double leftBound = 50;
    double rightBound = screenWidth - W - 20;

    public shooter mag = new shooter();

    Image spaceShipImage = new ImageIcon("assets/img/crimsonLight.png").getImage();

    public spaceShip() {
        shipBorder = new BorderLayout();
        setLayout(shipBorder);
        setOpaque(false);
        add(mag);

        Key();

        HUDCreate();
    }

    public spaceShip(String a) {
        X = -1000;
        setVisible(false);
        leftBound = -2000;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)X, (int)Y, W, H);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D ship2D = (Graphics2D) g;

        AffineTransform transform = new AffineTransform();

        transform.rotate(Math.toRadians(angle), X + W/2, Y + H/2);
        transform.translate((int)X, (int)Y);

        ship2D.drawImage(spaceShipImage, transform, null);

        mag.paintComponents(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (X <= leftBound) {
            X = 50;
        }
        if (X >= rightBound) {
            X = rightBound;
        }

        X += velX;

        HUDUpdate();
        sW.actionPerformed(e);

        repaint();
        revalidate();
        mag.actionPerformed(e);
    }

    public void dead() {
        setVisible(false);
        leftBound = -1000;
        rightBound = 3000;
        X = -500;
    }

    public void decreaseHeart() {
        hearts--;
    }

    public boolean isDead() {
        return hearts <= 0;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void Key() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "space");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "rotateLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "rotateLeftR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rotateRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rotateRightR");


        AbstractAction rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVelX(8);
            }
        };

        AbstractAction leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVelX(-8);
            }
        };

        AbstractAction releaseMove = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVelX(0);
            }
        };

        AbstractAction fire = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double bulX = X + 25;
                double bulY = Y;
                double bulAngle = angle;

                mag.reload(new bullet(bulX, bulY, bulAngle, false));
            }
        };

        AbstractAction rotateRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = 30;
                direction = "right";
            }
        };

        AbstractAction rotateLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = -30;
                direction = "left";
            }
        };

        AbstractAction releaseRotate = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = 0;
                direction = "default";
            }
        };

        am.put("right", rightAction);
        am.put("left", leftAction);
        am.put("rightR", releaseMove);
        am.put("leftR", releaseMove);
        am.put("space", fire);
        am.put("rotateRight", rotateRight);
        am.put("rotateLeft", rotateLeft);
        am.put("rotateRightR", releaseRotate);
        am.put("rotateLeftR", releaseRotate);
    }

    public void setVelX(double value){
        this.velX = value;
    }
    public void setVelB(double value){
        this.velB = value;
    }

    //Get HUD Elements

    public String getHeart() {
        return String.valueOf(hearts);
    }

    public String getAngle() {
        return String.valueOf((int)angle);
    }

    public String getVelocity() {
        return String.valueOf((int)velX);
    }

    JLabel angleText = new JLabel();
    JLabel dummyText = new JLabel();
    JLabel veloText = new JLabel();
    JLabel timeText = new JLabel();
    JLabel heartText = new JLabel();

    public void HUDCreate() {
        HUDPanel.setLayout(null);

        angleText.setFont(new Font("Jetbrains Mono", Font.PLAIN, 50));
        angleText.setForeground(Color.WHITE);

        dummyText.setFont(new Font("Jetbrains Mono", Font.PLAIN, 40));
        dummyText.setForeground(Color.white);

        veloText.setFont(new Font("Jetbrains Mono", Font.PLAIN, 50));
        veloText.setForeground(Color.WHITE);

        timeText.setFont(new Font("Jetbrains Mono", Font.PLAIN, 50));
        timeText.setForeground(Color.WHITE);

        heartText.setFont(new Font("Lato Black", Font.PLAIN, 50));
        heartText.setForeground(Color.MAGENTA);

        angleText.setBounds((int)screenWidth - 400,(int)screenHeight - 200,400,50);
        veloText.setBounds((int)screenWidth - 400,(int)screenHeight - 275,500,50);
        timeText.setBounds((int)screenWidth - 200,50,300,50);
        add(angleText);
        add(veloText);
        add(timeText);
        add(heartText);
        add(dummyText);
    }

    public void HUDUpdate() {

        heartText.setBounds((int)X-100, (int)Y,300,50);

        dummyText.setText("");
        angleText.setText("Angle:" + getAngle());
        heartText.setText("❤" + getHeart());
        veloText.setText("Velocity:" + getVelocity());
        timeText.setText(sW.getTime());

        currentTime = sW.getMSTime();
    }

    public class stopWatch implements ActionListener {
        private long start;
        private long end;
        long currentMS;

        public stopWatch() {
            start = System.currentTimeMillis();
        }

        public String getTime () {
            end = System.currentTimeMillis();
            currentMS = end - start;
            long currentMins = currentMS / 60000;
            long remainingMS = currentMS - currentMins * 60000;
            long currentSecs = remainingMS / 1000;

            String formattedTime = null;

            if (currentMins < 10) {
                formattedTime = "0" + currentMins;
            }
            else {
                formattedTime = "" + currentMins;
            }
            if (currentSecs < 10) {
                formattedTime += ":0" + currentSecs;
            }
            else {
                formattedTime += ":" + currentSecs;
            }

            return formattedTime;
        }

        public int getMSTime() {
            return (int) currentMS;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            getTime();
        }
    }

}