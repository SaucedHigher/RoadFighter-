package view;

import main.Game;
import utils.ScoreManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Scoreboard extends JPanel {
    private Game game;
    public Scoreboard(Game game) {
        this.game = game;
        setLayout(null);

        JPanel tablePanel = new JPanel();
        String[] columnNames = {"User", "Time Spent"};

        ScoreManager scoreManager = new ScoreManager();
        Object[][] data = scoreManager.loadScores();

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JLabel title = new JLabel("Scoreboard");
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(title);
        tablePanel.add(scrollPane);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> game.switchScreen("StartScreen"));
        tablePanel.add(backButton);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.setBounds(100, 100, 800, 400);
        add(tablePanel);
    }
}