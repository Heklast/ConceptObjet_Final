package game.beings.masters;

import game.beings.Color;
import game.beings.LivingBeing;

public interface Master {

    Color getColor();
    void addMessage(String message);
    int getNumberOfMessages();

    // Called when a live trooper finishes its move in its own safe zone.
    default void receiveMessages(LivingBeing trooper) {
        if (trooper.isDead() || trooper.getColor() != getColor()) {
            return;
        }

        for (String message : trooper.getMessages()) {
            addMessage(message);
        }
    }

    default int getScore() {
        return getNumberOfMessages();
    }
}