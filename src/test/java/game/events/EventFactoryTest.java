package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.RandomGenerator;
import org.junit.jupiter.api.Test;

class EventFactoryTest {

    @Test
    void createRandomEventNeverReturnsNull() {
        for (int i = 0; i < 100; i++) {
            assertNotNull(EventFactory.createRandomEvent());
        }
    }

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

    @Test
    void sameSeedCreatesSameEvent() {
        RandomGenerator.setSeed(7);
        String first = EventFactory.createRandomEvent().getName();

        RandomGenerator.setSeed(7);
        assertEquals(first, EventFactory.createRandomEvent().getName());
    }
}
