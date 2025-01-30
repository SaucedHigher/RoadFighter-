package models;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Item {
    Point position;
    private String direction;
    private int speed = 5;
    private boolean shouldRemove = false;
    private long lifeTime = 2000;
    private long creationTime;
    private int initialY;
    private int number;

    private double gravity = 40;

    public Item(Point startPosition, String direction, int number) {
        this.position = startPosition;
        this.direction = direction;
        this.creationTime = System.currentTimeMillis();
        this.initialY = startPosition.y;
        this.number = number;
        startRemovalTimer();
    }

    private void startRemovalTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                shouldRemove = true;
            }
        }, lifeTime-1000);
    }

    public boolean shouldRemove() {
        return shouldRemove;
    }

    public void update() {
        lifeTime -= System.currentTimeMillis() - creationTime;
        creationTime = System.currentTimeMillis();

        double time = (2000 - lifeTime) / 1000.0; // convert to seconds

        if (direction.equals("LEFT") || direction.equals("DOWN")) {
            position.x -= speed;
        } else if (direction.equals("RIGHT")|| direction.equals("UP")) {
            position.x += speed;
        }

        // Update the value of gravity based on the current lifetime
        if (time <= 1) {
            gravity = 40 * (1 - time); // Linearly decrease from 40 to 0
        } else {
            gravity = 40 * (time - 1); // Linearly increase from 0 to 40
        }

        // Make the item move in a curve -- projectile motion
        position.y = initialY - (int)(speed * time - 0.5 * gravity * time * time);
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        if (number == 2){
            g.setColor(Color.DARK_GRAY);
        }
        int size = (int)(10 * ((double)lifeTime / 1000));
        g.fillOval(position.x, position.y, size, size);
    }
}