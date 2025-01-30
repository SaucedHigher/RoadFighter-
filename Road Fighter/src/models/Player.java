package models;

import alert.Alert;
import view.EndScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class Player {
    private Point position;
    private int health;
    private BufferedImage currentImage;
    private String direction;
    private final String spritePath;
    private final int playerNumber;
    private BufferedImage[] sprites;
    private Rectangle bounds;
    public boolean isInAction = false;
    public boolean isCrouching = false;
    public boolean isThrowing = false;
    private long lastActionTime;
    private List<Item> items;

    public Player(int playerNumber, Point startPosition) {
        this.playerNumber = playerNumber;
        this.position = startPosition;
        this.health = 100;
        this.spritePath = "sprites/Player" + playerNumber;
        this.direction = getRandomDirection();
        this.sprites = new BufferedImage[10];
        bounds = new Rectangle(position.x + 74, position.y + 68, 57, 70);
        loadSprites();
        currentImage = sprites[1];
        items = new ArrayList<>();
    }

    private String getRandomDirection() {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        return directions[new Random().nextInt(directions.length)];
    }

    public Rectangle getBounds() {
        // update the bounds to the current position
        bounds.setLocation(position.x + 74, position.y + 68);
        //if the user is crouching then the bounds should be updated and the height should be reduced
        if (currentImage == sprites[0]) {
            //the x position moves down

            bounds.setLocation(position.x + 74, position.y + 68 + 38);
            bounds.setSize(57, 70 - 35);
        }
        //if the user is punching or kicking then the bounds should be updated and the width should be increased
        //The width should be increased towards the direction the user is facing
        else if (currentImage == sprites[2] || currentImage == sprites[3] || currentImage == sprites[4] || currentImage == sprites[5]) {
            if (direction.equals("LEFT") || direction.equals("DOWN")) {
                bounds.setLocation(position.x + 74 - 20, position.y + 68); // shift the box to the left
                bounds.setSize(57 + 20, 70); // increase the width
            } else if (direction.equals("RIGHT") || direction.equals("UP")) {
                bounds.setSize(57 + 20, 70); // increase the width
            }
        }
        //the width should return to normal after the user has finished punching or kicking
        else {
            bounds.setSize(57, 70);
        }
        return bounds;
    }

    private void loadSprites() {
        String[] spriteNames = {
                "Crouch",
                "IDLE",
                "KickLeft",
                "KickRight",
                "PunchLeft",
                "PunchRight",
                "Special",
                "WalkDown",
                "WalkLeft",
                "WalkRight",
                "WalkUp"
        };

        sprites = new BufferedImage[spriteNames.length];

        try {
            for (int i = 0; i < spriteNames.length; i++) {
                sprites[i] = ImageIO.read(new File(spritePath + "/Player" + playerNumber + spriteNames[i] + ".png"));
            }
        } catch (IOException e) {
            Alert.show("Error loading player sprites", "Error", "ERROR_MESSAGE");
            System.out.println(e.getMessage() + "\n In LoadSprites");
        }
    }

    public void update(Player player) {
        // Update the player's state (movement, actions, etc.)
        for (Item item : items) {
            item.update();
        }
        checkItemCollision(player);
    }

    private void checkItemCollision(Player player) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (player.getBounds().intersects(new Rectangle(item.position.x, item.position.y, 10, 10))) {
                player.reduceHealth(20);
                iterator.remove();
            }
        }
        //check if the user is in action and intersects with the other user
        if (isInAction && getBounds().intersects(player.getBounds())) {
            if (currentImage == sprites[2] || currentImage == sprites[3]) {
                player.reduceHealth(10);
            } else if (currentImage == sprites[4] || currentImage == sprites[5]) {
                player.reduceHealth(20);
            }
            isInAction = false;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(currentImage, position.x, position.y, null);
        if (isDead()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g.setColor(Color.RED);
        Rectangle bounds = getBounds();
        //g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        //draw the outer bounds
        g.setColor(Color.BLUE);
        Rectangle outerBounds = getOuterBounds();
        //g.drawRect(outerBounds.x, outerBounds.y, outerBounds.width, outerBounds.height);
        if (!isDead()) {
            items.removeIf(Item::shouldRemove);
            for (Item item : items) {
                item.draw(g);
            }
        }

    }

    public void moveUp(int speed) {
        position.y -= speed;
        currentImage = sprites[10];
        direction = "UP";
    }

    public void moveDown(int speed) {
        position.y += speed;
        currentImage = sprites[7];
        direction = "DOWN";
    }

    public void moveLeft(int speed) {
        position.x -= speed;
        currentImage = sprites[8];
        direction = "LEFT";
    }

    public void moveRight(int speed) {
        position.x += speed;
        currentImage = sprites[9];
        direction = "RIGHT";
    }

    public void stopMoving() {
        // Ignore the stop moving action
    }

    public void kick() {

        if (isCoolingDown()) {
            return;
        }

        if (direction.equals("UP")) {
            currentImage = sprites[3];
        } else if (direction.equals("DOWN")) {
            currentImage = sprites[2];
        } else {
            currentImage = direction.equals("RIGHT") ? sprites[3] : sprites[2];
        }
        isInAction = true;
        lastActionTime = System.currentTimeMillis();
        resetImageAfterDelay();
    }

    public void punch() {
        if (isCoolingDown()) {
            return;
        }
        if (direction.equals("UP")) {
            currentImage = sprites[5];
        } else if (direction.equals("DOWN")) {
            currentImage = sprites[4];
        } else {
            currentImage = direction.equals("RIGHT") ? sprites[5] : sprites[4];
        }
        isInAction = true;
        lastActionTime = System.currentTimeMillis();
        resetImageAfterDelay();
    }

    public void throwItem() {
        if (isCoolingDown()) {
            return;
        }
        currentImage = sprites[6];
        isThrowing = true;
        items.add(new Item(new Point(position.x + 78, position.y + 72), direction, playerNumber));
        lastActionTime = System.currentTimeMillis();
        resetImageAfterDelay();
    }

    public void crouch() {
        currentImage = sprites[0];
        isCrouching = true;
        resetImageAfterDelay();
    }

    private void resetImageAfterDelay() {
        if (isThrowing) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    currentImage = sprites[1];
                    isInAction = false;
                    isCrouching = false;
                    isThrowing = false;
                }
            }, 3990);
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    currentImage = sprites[1];
                    isInAction = false;
                    isCrouching = false;
                }
            }, 1990);
        }
    }

    public Rectangle getOuterBounds() {
        return new Rectangle(position.x + 70, position.y + 64, 65, 78);
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int i) {
        health -= i;
        if (health < 0) {
            Alert.show("Player " + playerNumber + " has lost", "main.Game Over", "INFORMATION_MESSAGE");
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isCoolingDown() {
        long actionCoolDown = 2000;
        long throwItemCoolDown = 4000;

        // Check if enough time has passed since the last action
        if (isThrowing) {
            return System.currentTimeMillis() <= lastActionTime + throwItemCoolDown;
        } else {
            return System.currentTimeMillis() <= lastActionTime + actionCoolDown;
        }
    }

    public void setMiddlePosition() {
        position.x = 400;
        position.y = 250;
    }
}
