package view;

import models.Player;

import java.awt.*;

public class HealthBar {
    private Player player;
    private Point position;
    private int width;
    private int height;

    public HealthBar(Player player, Point position, int width, int height) {
        this.player = player;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(position.x, position.y, width, height);

        g.setColor(Color.ORANGE);
        if (player.getPlayerNumber() == 1){
            g.setColor(Color.BLUE);
        }
        int healthWidth = (int) (width * ((double) player.getHealth() / 100));
        g.fillRect(position.x, position.y, healthWidth, height);

        g.setColor(Color.BLACK);
        g.drawRect(position.x, position.y, width, height);
    }
}
