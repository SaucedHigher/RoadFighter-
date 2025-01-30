package utils;

import alert.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private final String scoreFilePath = "scores.csv";

    public boolean saveScore(String initials, String playerNumber, String timeTaken) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFilePath, true))) {
            writer.write(initials + "," + playerNumber + "," + timeTaken);
            writer.newLine();
            //close the writer
            writer.close();
            return true;
        } catch (IOException e) {
            Alert.show("An error occurred while saving the score.","Error", "ERROR_MESSAGE");
        }
        return false;
    }

    public Object[][] loadScores() {
        List<Object[]> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                scores.add(new Object[]{parts[0] + " " + parts[1], parts[2]});
            }
        } catch (IOException e) {
            Alert.show("An error occurred while loading the scores.", "Error", "ERROR_MESSAGE");
        }
        return scores.toArray(new Object[0][]);
    }

    public void checkScoreFile() {
        try {
            Path path = Paths.get(scoreFilePath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            Alert.show("An error occurred while creating the score file.", "Error","ERROR_MESSAGE");
        }
    }
}