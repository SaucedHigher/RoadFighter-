package utils;

public class GameTimer {
    private long startTime;

    public GameTimer() {
        reset();
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    public String getElapsedTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int seconds = (int) ((elapsedTime / 100) % 30);
        int minutes = (int) ((elapsedTime / 100) / 30);
        return String.format("%02d:%02d", minutes, seconds);
    }
}