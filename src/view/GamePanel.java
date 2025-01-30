package view;

import alert.Alert;
import main.Game;
import models.Player;
import utils.GameTimer;
import utils.ImageHandler;
import utils.InputHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GamePanel extends JPanel {
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;
    private BufferedImage background;
    private Player player1;
    private Player player2;
    private final String backgroundPath = "sprites/TropicalMap.png";
    private HealthBar healthBar1;
    private HealthBar healthBar2;
    private GameTimer gameTimer;
    private Game game;

    public GamePanel(Game game) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocusInWindow();
        requestFocus();
        this.game = game;

        // Initialize players
        player1 = new Player(1, new Point(100, 100)); // Starting position for player 1
        player2 = new Player(2, new Point(800, 100)); // Starting position for player 2

        healthBar1 = new HealthBar(player1, new Point(20, 20), 150, 20);
        healthBar2 = new HealthBar(player2, new Point(WIDTH - 170, 20), 150, 20);

        gameTimer = new GameTimer();

        // Add key listeners for players
        addKeyListener(new InputHandler(player1, 1, player2, game));
        addKeyListener(new InputHandler(player2, 2, player1, game));

        // Load background
        loadBackground();
    }

    private void loadBackground() {
        background = ImageHandler.resize(new File(backgroundPath), WIDTH, HEIGHT);
    }
    public void update() {
        // Update game objects
        player1.update(player2);
        player2.update(player1);

        //check if the player is dead
        if (player1.isDead()) {
            Alert.show("Player 2 wins", "Game Over", "INFORMATION_MESSAGE");
            game.stop();
            game.setWinner(player2);
            game.switchScreen("EndScreen");
        } else if (player2.isDead()) {
            Alert.show("Player 1 wins", "Game Over", "INFORMATION_MESSAGE");
            game.stop();
            game.setWinner(player1);
            game.switchScreen("EndScreen");
        }

        // Process user input
        // Update game timer
        gameTimer.getElapsedTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        player1.draw(g);
        player2.draw(g);

        // Health bars
        healthBar1.draw(g);
        healthBar2.draw(g);

        // Width and height of the rectangle
        int rectWidth = 100;
        int rectHeight = 20;

        int rectX = WIDTH / 2 - rectWidth / 2;
        int rectY = 20 - rectHeight / 2;

        g.setColor(Color.WHITE);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        g.setColor(Color.BLACK);
        g.drawRect(rectX, rectY, rectWidth, rectHeight);

        String timeString = "Time: " + gameTimer.getElapsedTime();
        g.drawString(timeString, rectX + 10, rectY + 15);
    }

    public String getTimerValue() {
        return gameTimer.getElapsedTime();
    }
}
