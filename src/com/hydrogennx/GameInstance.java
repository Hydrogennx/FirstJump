package com.hydrogennx;

import com.hydrogennx.javafx.ActionPhase;
import com.hydrogennx.javafx.ScreenFramework;
import com.hydrogennx.javafx.TurnPhase;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

/**
 * An abstract class. This holds all relevant information about the current game.
 * This class is never used directly -- instead, a LocalPracticeInstance, ClientManager or HostManager should be created.
 */
public abstract class GameInstance {

    GameManager gameManager;
    GameState gameState;

    public GameInstance(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //TODO create a ClientManager and a HostManager when we are ready to begin netcode.

    public abstract void queueAttack(List<AttackSequence> attacksToDefendAgainst);

    public void updateScreen() {
        switch (gameState) {
            case TURN:
                gameManager.screenFramework.graphicsManager.setScreen(ScreenFramework.TURN_PHASE_ID);
                break;
            case ACTION:
                gameManager.screenFramework.graphicsManager.setScreen(ScreenFramework.ACTION_PHASE_ID);
                break;
            default:
                return; //should not happen
        }
    }

    protected void changeGameState(GameState gameState) {
        this.gameState = gameState;
        gameManager.updateScreen();
    }

    public void update(double time) {

        if (gameState == GameState.ACTION) {

            //TODO get and update the ActionPhase.
            ActionPhase actionPhase = (ActionPhase) gameManager.screenFramework.graphicsManager.getController(ScreenFramework.ACTION_PHASE_ID);

            actionPhase.update(time);

        }

    }

}
