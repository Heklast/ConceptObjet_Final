package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RandomEventTest {

    @Test
    void creatingAVirusIncrementsBothCounters() {
        int eventsBefore = RandomEvent.getInstanceCount();
        int virusesBefore = Virus.getInstanceCount();
        int meteoritesBefore = Meteorite.getInstanceCount();

        new Virus();

        assertEquals(eventsBefore + 1, RandomEvent.getInstanceCount());
        assertEquals(virusesBefore + 1, Virus.getInstanceCount());
        assertEquals(meteoritesBefore, Meteorite.getInstanceCount());
    }
}
