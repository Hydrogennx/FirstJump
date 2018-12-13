package com.hydrogennx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//TODO figure out how to add custom JavaFX elements via SceneBuilder.

/**
 * Represents the object controlled by the player during the ActionPhase sessions.
 */
public class ControllableCharacter extends Group {

    public static final double FRAMES_TO_FULL_SPEED = 4;
    public static final double TOP_SPEED = 16;

    public double xSpeed = 0;
    public double ySpeed = 0;

    public double invulnerabilityFrames = 0;

    Player controllingPlayer;
    GameActionPane context;

    ImageView sprite;

    public ControllableCharacter() {

        Image imageToUse = new Image("file:res/coward.png");
        sprite = new ImageView(imageToUse);

        getChildren().add(sprite);

    }

    /**
     * A temporary method that will hard-code the movement of ControllableCharacter to certain keys.
     * Ideally, the player would be able to change keybinds however they please.
     */
    public void setDefaultMovement() {

        System.out.println("Default movement set!");

        setOnKeyPressed(event -> {

            System.out.println("Key pressed!");

            double speed = TOP_SPEED / FRAMES_TO_FULL_SPEED;
            switch(event.getCode()) {
                case W:
                    ySpeed -= speed;
                    break;
                case S:
                    ySpeed += speed;
                    break;
                case A:
                    xSpeed -= speed;
                    break;
                case D:
                    xSpeed += speed;
                    break;
                case ENTER:
                    System.out.println("Ow.");
                    controllingPlayer.registerDamage(0.1);
                    break;
            }
            


        });

        setOnKeyReleased(event -> {

            System.out.println("Key released!");

            double speed = TOP_SPEED / FRAMES_TO_FULL_SPEED;
            switch(event.getCode()) {
                case W:
                    ySpeed -= -speed;
                    break;
                case S:
                    ySpeed += -speed;
                    break;
                case A:
                    xSpeed -= -speed;
                    break;
                case D:
                    xSpeed += -speed;
                    break;
            }

        });

        requestFocus();

    }

    public void update(double time) {

        setLayoutX(getLayoutX() + xSpeed);
        setLayoutY(getLayoutY() + ySpeed);

        if (isInvulnerable()) {
            invulnerabilityFrames--;
        }

        keepSpriteInBounds();

    }

    private void keepSpriteInBounds() {

        if (getLayoutX() < 0) {
            setLayoutX(0);
        } else if (getLayoutX() > context.getWidth()) {
            setLayoutX(context.getWidth());
        }

        if (getLayoutY() < 0) {
            setLayoutY(0);
        } else if (getLayoutY() > context.getHeight()) {
            setLayoutY(context.getHeight());
        }

    }

    public void setContext(GameActionPane context) {

        this.context = context;

    }

    public void setControllingPlayer(Player controllingPlayer) {

        this.controllingPlayer = controllingPlayer;

    }

    /**
     * Resets this controllableCharacter to be indistinguishable from a newly created one.
     */
    public void reset() {

        //nothing needs to be done, you can't even move the character lol

    }

    public Player getPlayer() {

        return controllingPlayer;

    }

    public boolean isInvulnerable() {

        return invulnerabilityFrames > 0;

    }

    public void registerHit(double damage) {

        if (isInvulnerable()) return;

        getPlayer().registerDamage(damage);

        invulnerabilityFrames = 60;

    }

    public boolean isDead() {

        return getPlayer().getHealth() <= 0;

    }
}
