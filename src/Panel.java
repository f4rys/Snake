import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Panel extends JPanel implements ActionListener {

    private static final int SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT;
    private static final int UNIT_SIZE;
    private static final int DELAY;
    private static final int GAME_UNITS;

    private int bodyParts = 6;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction = 'R';
    private boolean running;
    private boolean gameStarted = false;
    private Timer timer;
    private Random random;

    static {
        Properties prop = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SCREEN_WIDTH = Integer.parseInt(prop.getProperty("SCREEN_WIDTH"));
        SCREEN_HEIGHT = Integer.parseInt(prop.getProperty("SCREEN_HEIGHT"));
        UNIT_SIZE = Integer.parseInt(prop.getProperty("UNIT_SIZE"));
        DELAY = Integer.parseInt(prop.getProperty("DELAY"));
        GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    }

    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];

    public Panel() {
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
    }

    private void resetGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        running = true;
        newApple();
        for (int i = 0; i < bodyParts; i++) {
            x[i] = SCREEN_WIDTH / 2 - i * UNIT_SIZE;
            y[i] = SCREEN_HEIGHT / 2;
        }
        timer.restart();
    }

    private void showStartScreen(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Pixeled", Font.BOLD, UNIT_SIZE*2));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String welcome = "SNAKE";
        g.drawString(welcome, getWidth() / 2 - metrics.stringWidth(welcome) / 2, getHeight() / 2 - metrics.getHeight() / 2);
    
        g.setFont(new Font("Pixeled", Font.PLAIN, UNIT_SIZE));
        metrics = getFontMetrics(g.getFont());
        String startMsg = "PRESS SPACE TO START";
        g.drawString(startMsg, getWidth() / 2 - metrics.stringWidth(startMsg) / 2, getHeight() / 2 + metrics.getHeight() * 2);
    }
    
    private void startGame() {
        if (running) return;
        newApple();
        running = true;
        gameStarted = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
            showStartScreen(g);
        } else if (running) {
            drawRunning(g);
        } else {
            drawGameOver(g);
        }
    }
    
    private void drawRunning(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        g.setColor(new Color 	(204,204,204));
        g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        for (int i = 0; i < bodyParts; i++) {
            g.setColor(new Color(156,155,155));
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Pixeled", Font.BOLD, UNIT_SIZE/2));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String score = "SCORE: " + applesEaten;
        g.drawString(score, getWidth() / 2 - metrics.stringWidth(score) / 2, g.getFont().getSize() + (UNIT_SIZE/2));
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Pixeled", Font.BOLD, UNIT_SIZE + UNIT_SIZE/2));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        String gameOver = "GAME OVER";
        g.drawString(gameOver, getWidth() / 2 - metrics1.stringWidth(gameOver) / 2, getHeight() / 2 - metrics1.getHeight() / 2);

        g.setFont(new Font("Pixeled", Font.BOLD, UNIT_SIZE/2));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        String score = "SCORE: " + applesEaten;
        String restartText = "PRESS SPACE TO RESTART";
        
        g.drawString(score, getWidth() / 2 - metrics2.stringWidth(score) / 2, getHeight() / 2 + metrics2.getHeight() / 2 );
        g.drawString(restartText, getWidth() / 2 - metrics2.stringWidth(restartText) / 2, getHeight() / 2 + 2 * metrics2.getHeight() / 2 + UNIT_SIZE);
    }

    private void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    private void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 'R') {
                direction = 'L';
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 'L') {
                direction = 'R';
            } else if (e.getKeyCode() == KeyEvent.VK_UP && direction != 'D') {
                direction = 'U';
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 'U') {
                direction = 'D';
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!gameStarted) startGame();
                else resetGame();
            }
        }
    }
}