package group6.Ozhich.varC2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Field extends JPanel {

    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    private boolean paused;

    private int computerScore = 0;
    private int playerScore = 0;

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
    }

    public void addBall() {
        if (balls.size() == 0) {
            balls.add(new BouncingBall(this));
        }
    }

    public synchronized void reset() {
        balls.clear();
        computerScore = 0;
        playerScore = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        if (computerScore == 10) {
            paused = true;
            canvas.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            canvas.drawString("Победил компьютер!", 300, 400);
        }
        if (playerScore == 10) {
            paused = true;
            canvas.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            canvas.drawString("Победил человек!", 350, 400);
        }
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
            if ((ball.getHit()).equals("Down")) {
                computerScore++;
                ball.NoneHit();
            } else if ((ball.getHit()).equals("Up")) {
                playerScore++;
                ball.NoneHit();
            }
        }
        canvas.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        canvas.setColor(Color.BLACK);
        canvas.drawString(String.valueOf(playerScore) + " : " + String.valueOf(computerScore), 20, 375);

    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void canMove(BouncingBall ball) throws InterruptedException {
        if (paused) {
            wait();
        }
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }
}
