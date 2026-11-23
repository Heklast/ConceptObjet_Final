package game.events;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventFactoryTest {

    @Test
    void createRandomEventProducesBothTypes() {
        boolean virusCreated = false;
        boolean meteoriteCreated = false;
        for (int i = 0; i < 1000; i++) {
            RandomEvent event = EventFactory.createRandomEvent();
            if (event instanceof Virus) {
                virusCreated = true;
            }
            if (event instanceof Meteorite) {
                meteoriteCreated = true;
            }
        }
        assertTrue(virusCreated);
        assertTrue(meteoriteCreated);
    }
}
