package screens;

import com.ainur.global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class highScore extends JPanel {
    double screenWidth = global.screenSize.width;
    double screenHeight = global.screenSize.height;

    boolean isPlay = false;
    int baseScore = 600000;
    int lostScore = 0;

    LinkedList times;
    JPanel buttonPanel;
    public JButton play;
    public JButton quit;


    public highScore(LinkedList times) {
        this.times = times;

        BorderLayout highBorder = new BorderLayout();
        setLayout(highBorder);

        nickNameField();
        title();
        buttonGroup();
    }

    public void nickNameField() {
        JPanel nickPanel = new JPanel();
        nickPanel.setBackground(new Color(0, 0, 0, 50));

        nickPanel.setLayout(new GridBagLayout());

        JButton submitNick = new JButton("Submit Score");
        JLabel enter = new JLabel("Enter your nickname: ");
        JTextField nickName = new JTextField(30);

        enter.setFont(new Font("Jetbrains Mono", Font.PLAIN, 30));
        enter.setForeground(Color.white);

        nickName.setFont(new Font("Jetbrains Mono", Font.PLAIN, 30));
        nickName.setBackground(new Color(0, 0, 0, 70));
        nickName.setForeground(Color.white);

        submitNick.setFont(new Font("Jetbrains Mono", Font.PLAIN, 30));

        nickPanel.add(enter);
        nickPanel.add(nickName);
        nickPanel.add(submitNick);

        submitNick.addActionListener(new ActionListener() {
            String nickNameText;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickName.getText() != "") {
                    nickNameText = nickName.getText();

                    for (int i = 0; i < times.size(); i++) {
                        int currentTime = (int) times.get(i);
                        baseScore -= currentTime;
                    }

                    remove(nickPanel);
                    scoreTable(nickNameText, baseScore);

                    revalidate();
                    repaint();

//                        PrintWriter addScore = new PrintWriter("scores.txt");
//
//                        Writer outNick = new BufferedWriter(new FileWriter("nicknames.txt", true));
//                        outNick.append(nickNameText);
//
//                        Writer outScore = new BufferedWriter(new FileWriter("scores.txt", true));
//                        outScore.append(String.valueOf(baseScore));
//
//                        outNick.close();
//                        outScore.close();
//
//                        remove(nickPanel);
//                        scoreTable();
//

                }

            }
        });

        add(nickPanel);
    }

    public void scoreTable(String newNick, int newScore) {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));

        String[] columnNames = {"Name", "Roll Number"};

        JTable scoreBox = new JTable(getScores(newNick, newScore), columnNames);

        scoreBox.setBackground(new Color(0, 0, 0, 0));
        scoreBox.setForeground(Color.white);
        scoreBox.setFocusable(false);
        scoreBox.setRowSelectionAllowed(false);

        scoreBox.setFont(new Font("Jetbrains Mono", Font.PLAIN, 30));

        scoreBox.setRowHeight((int) (screenHeight / 15));

        add(scoreBox, BorderLayout.CENTER);
    }


    public void title() {
        setOpaque(false);
        ImageIcon title = new ImageIcon("assets/img/congrats.png");
        JLabel titleLayout = new JLabel(title);

        add(titleLayout, BorderLayout.NORTH);
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

    public String[][] getScores(String newNick, int newScore) {
        File scoresFile = new File("assets/scores/scores.txt");
        File nickFile = new File("assets/scores/nicknames.txt");
        LinkedList<Integer> scores = new LinkedList<>();
        LinkedList<String> nickNames = new LinkedList<>();

        try {
            Scanner scScore = new Scanner(scoresFile);
            Scanner scNick = new Scanner(nickFile);

            while (scScore.hasNextLine()) {
                scores.add(Integer.valueOf((scScore.nextLine())));
            }
            while (scNick.hasNextLine()) {
                nickNames.add(scNick.nextLine());
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        nickNames.add(newNick);
        scores.add(newScore);

        //Sort
        int lastScore = scores.getLast();
        String lastNick = nickNames.getLast();

        for (int i = 0; i < scores.size() - 1; i++) {

            if (lastScore >= scores.get(i)) {
                scores.removeLast();
                nickNames.removeLast();

                scores.add(i, lastScore);
                nickNames.add(i, lastNick);
                break;
            }
        }

        //Update File
        try {
            PrintWriter updateScores = new PrintWriter("assets/scores/scores.txt");
            PrintWriter updateNickNames = new PrintWriter("assets/scores/nicknames.txt");

            for (int j = 0; j < scores.size(); j++) {
                updateScores.println(scores.get(j));
                updateNickNames.println(nickNames.get(j));
            }

            updateScores.close();
            updateNickNames.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String[][] combinedScores = new String[1000][2];


        for (int i = 0; i < nickNames.size(); i++) {
            combinedScores[i][0] = nickNames.get(i);
            combinedScores[i][1] = String.valueOf(scores.get(i));
        }

        return combinedScores;
    }

    public void clearButton(JButton b) {
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }
}
