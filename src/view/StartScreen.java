package view;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    public StartScreen(Game game) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 0, 10)); // Grid layout with 4 rows, 1 column, and vertical gap of 10

        // Create buttons with the text as shown in the image
        JButton playButton = new JButton("Play");
        JButton optionsButton = new JButton("Options");
        JButton characterButton = new JButton("Select Character");
        JButton selectMapButton = new JButton("Select Map");
        JButton scoreboardButton = new JButton("Scoreboard");
        JButton exitButton = new JButton("Exit");

        // Set the font and alignment of the buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        playButton.setFont(buttonFont);
        optionsButton.setFont(buttonFont);
        scoreboardButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        characterButton.setFont(buttonFont);
        selectMapButton.setFont(buttonFont);

        // Center align the text in buttons
        playButton.setHorizontalAlignment(SwingConstants.CENTER);
        optionsButton.setHorizontalAlignment(SwingConstants.CENTER);
        scoreboardButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        selectMapButton.setHorizontalAlignment(SwingConstants.CENTER);
        characterButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Add action listeners for the buttons
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchScreen("GamePanel");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchScreen("Options");
            }
        });

        characterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchScreen("CharacterSelection");
            }
        });

        scoreboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchScreen("Scoreboard");
            }
        });

        selectMapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.switchScreen("MapSelection");
            }
        });

        // Add buttons to the panel
        buttonPanel.add(playButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(characterButton);
        buttonPanel.add(selectMapButton);
        buttonPanel.add(scoreboardButton);
        buttonPanel.add(exitButton);

        // Add the button panel to the main panel
        add(buttonPanel);

        buttonPanel.setPreferredSize(new Dimension(400, 400));
        buttonPanel.setBackground(Color.WHITE);

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String text = "Welcome to the Game! Press the button to Start";
        int x = (getWidth() - g.getFontMetrics().stringWidth(text)) / 2;
        g.drawString(text, x, 200);
    }
}