package view;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class CharacterSelection extends JPanel {
    private Game game;
    public CharacterSelection(Game game) {
        this.game = game;
        setLayout(null);

        String[] characters = {"CHARACTER 1", "CHARACTER 2", "CHARACTER 3"};
        JComboBox<String> characterDropdown = new JComboBox<>(characters);
        characterDropdown.setSelectedIndex(-1);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> game.switchScreen("StartScreen"));

        add(characterDropdown);
        add(backButton);
        characterDropdown.setBounds(400, 100, 200, 30);
        backButton.setBounds(400, 150, 200, 30);
    }
}