package group6.Ozhich.varC2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Field extends JPanel {

    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    private boolean paused;

    private long startPressedTime;

    private int mouseX;
    private int mouseY;

    private Timer repaintTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            repaint();
        }
    });

    public Field() {
        setBackground(Color.WHITE);
        repaintTimer.start();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPressedTime = System.nanoTime();
                mouseX = e.getX();
                mouseY = e.getY();
                pause();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resume();
                for (BouncingBall ball : balls) {
                    int ballX = (int) ball.getX();
                    int ballY = (int) ball.getY();
                    double distance = Math.pow(Math.pow((ballX - mouseX), 2) + Math.pow((ballY - mouseY), 2), 0.5);
                    if (distance <= 1.5 * ball.getRadius()) {
                        ball.setDirection(e.getX(), e.getY(), (int) ((System.nanoTime() - startPressedTime) / Math.pow(10, 7)));
                    }
                }
            }
        });
    }

    public void addBall() {
        balls.add(new BouncingBall(this));
    }

    public synchronized void reset() {
        balls.clear();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (BouncingBall ball : balls) {
            ball.paint(canvas);
        }

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

