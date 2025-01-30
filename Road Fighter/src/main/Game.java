package main;

import models.Player;
import view.*;
import view.EndScreen;
import view.GamePanel;
import view.StartScreen;

import java.awt.*;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private boolean running = false;
    private GamePanel gamePanel;
    private StartScreen startScreen;
    private EndScreen endScreen;
    private CardLayout cardLayout;
    public Player winner;

    public Game() {
    // Initialize the game window
    setTitle("Road Fighter Project");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Initialize the panel
    gamePanel = new GamePanel(this);
    startScreen = new StartScreen(this);
    endScreen = new EndScreen(this);

    // Initialize the new screens
    Options optionsScreen = new Options();
    CharacterSelection characterSelectionScreen = new CharacterSelection(this);
    MapSelection mapSelectionScreen = new MapSelection();
    Scoreboard scoreboardScreen = new Scoreboard(this);

    cardLayout = new CardLayout();
    setLayout(cardLayout);

    // Add the screens to the card layout
    add(startScreen, "StartScreen");
    add(gamePanel, "GamePanel");
    add(endScreen, "EndScreen");
    add(optionsScreen, "Options");
    add(characterSelectionScreen, "CharacterSelection");
    add(mapSelectionScreen, "MapSelection");
    add(scoreboardScreen, "Scoreboard");

    cardLayout.show(this.getContentPane(), "StartScreen");

    pack();

    setLocationRelativeTo(null); // Center the window
    setVisible(true);
}

    public void switchScreen(String screenName) {
        cardLayout.show(this.getContentPane(), screenName);
        gamePanel.removeAll();
        if ("GamePanel".equals(screenName)) {
            gamePanel.requestFocusInWindow();
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                ticks++;
                gamePanel.update(); // Update game logic
                delta -= 1;
            }

            frames++;
            gamePanel.repaint(); // Render the game

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void restart() {
        Game game = new Game();
        gamePanel = new GamePanel(game);
        add(gamePanel, "GamePanel");
        cardLayout.show(this.getContentPane(), "GamePanel");
        gamePanel.requestFocusInWindow();
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
        endScreen.setWinner(winner);
    }

    public String getTimerValue() {
        return gamePanel.getTimerValue();
    }
}

