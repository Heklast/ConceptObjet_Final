package game.events;

import game.RandomGenerator;

public final class EventFactory {

    public static final double VIRUS_PROBABILITY = 0.5;

    private EventFactory() {
    }

    public static RandomEvent createRandomEvent() {
        if (RandomGenerator.chance(VIRUS_PROBABILITY)) {
            return new Virus();
        }
        return new Meteorite();
    }
}
