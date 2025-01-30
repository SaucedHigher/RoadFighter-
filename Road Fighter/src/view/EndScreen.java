package view;

import main.Game;
import models.Player;
import utils.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    private Player winner;
    private Game game;
    private JButton restartButton;
    private JButton quitButton;

    public EndScreen(Game game) {
        this.winner = game.getWinner();
        this.game = game;

        setLayout(null);

        // Initialize the restart button and add an action listener
        restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });

        // Initialize the quit button and add an action listener
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add the components to the panel
        add(restartButton);
        add(quitButton);
        restartButton.setBounds(450, 450, 100, 30);
        quitButton.setBounds(450, 500, 100, 30);
    }

    public void setWinner(Player winner) {
        this.winner = winner;
        winner.setMiddlePosition();
        collectPlayerInfo();
        repaint();
    }

    public void collectPlayerInfo() {
        // Ask for the player's initials
        String initials = JOptionPane.showInputDialog(this, "Enter your initials (max 3 letters):");
        while (initials == null || !initials.matches("^[a-zA-Z]{1,3}$")) {
            initials = JOptionPane.showInputDialog(this, "Invalid input. Please enter up to 3 alphabetical characters for your initials:");
        }

        // Ask for the player number
        String playerNumber = JOptionPane.showInputDialog(this, "Were you Player 1 or Player 2? (Enter P1 or P2):");
        while (playerNumber == null || !playerNumber.matches("P[12]")) {
            playerNumber = JOptionPane.showInputDialog(this, "Invalid input. Please enter 'P1' for Player 1 or 'P2' for Player 2:");
        }

        String gameTimeInSeconds = game.getTimerValue();

        // Save the player's initials, player number, and game time to a csv
        ScoreManager scoreManager = new ScoreManager();
        scoreManager.checkScoreFile();
        if (scoreManager.saveScore(initials, playerNumber, gameTimeInSeconds)) {
            JOptionPane.showMessageDialog(this, "Your score has been saved!");
        } else {
            JOptionPane.showMessageDialog(this, "Your score could not be saved. Please try again later.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String winnerText = winner.getPlayerNumber() + " won! Press Restart to play again or Quit to exit";
        int x = (getWidth() - g.getFontMetrics().stringWidth(winnerText)) / 2;
        g.drawString(winnerText, x, 200);
        winner.draw(g);
    }
}