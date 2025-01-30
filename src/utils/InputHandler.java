package utils;

import main.Game;
import models.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private Player player;
    private Player player2;
    private int playerNumber;
    private Game game; // Add this

    public InputHandler(Player player, int playerNumber, Player player2, Game game) { // Modify this
        this.player = player;
        this.playerNumber = playerNumber;
        this.player2 = player2;
        this.game = game; // And this
    }

    public void keyPressed(KeyEvent e) {
        int speed = 5; // Adjust as needed

        if (player.getOuterBounds().intersects(player2.getOuterBounds())){
            System.out.println("Collision detected");
        }

        if (playerNumber == 1) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.moveUp(speed);
                    break;
                case KeyEvent.VK_S:
                    player.moveDown(speed);
                    break;
                case KeyEvent.VK_A:
                    player.moveLeft(speed);
                    break;
                case KeyEvent.VK_D:
                    player.moveRight(speed);
                    break;
                case KeyEvent.VK_R:
                    player.kick();
                    break;
                case KeyEvent.VK_F:
                    player.punch();
                    break;
                case KeyEvent.VK_C:
                    player.throwItem();
                    break;
                case KeyEvent.VK_X:
                    player.crouch();
                    break;
            }
        } else if (playerNumber == 2) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    player.moveUp(speed);
                    break;
                case KeyEvent.VK_DOWN:
                    player.moveDown(speed);
                    break;
                case KeyEvent.VK_LEFT:
                    player.moveLeft(speed);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveRight(speed);
                    break;
                case KeyEvent.VK_J:
                    player.punch();
                    break;
                case KeyEvent.VK_K:
                    player.kick();
                    break;
                case KeyEvent.VK_L:
                    player.throwItem();
                    break;
                case KeyEvent.VK_SLASH:
                    player.crouch();
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (playerNumber == 1) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                    player.stopMoving();
                    break;
            }
        } else if (playerNumber == 2) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                    player.stopMoving();
                    break;
            }
        }
    }
}