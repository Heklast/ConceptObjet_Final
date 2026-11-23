package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.List;
import org.junit.jupiter.api.Test;

class RandomEventTest {

    @Test
    void eventsHaveTheirName() {
        assertEquals("Virus", new Virus().getName());
        assertEquals("Meteorite", new Meteorite().getName());
    }

    @Test
    void eventsAreRandomEvents() {
        assertInstanceOf(RandomEvent.class, new Virus());
        assertInstanceOf(RandomEvent.class, new Meteorite());
    }

    @Test
    void eventsCanBeHandledThroughTheAbstractType() {
        List<RandomEvent> events = List.of(new Virus(), new Meteorite());

        assertEquals("Virus", events.get(0).getName());
        assertEquals("Meteorite", events.get(1).getName());
    }

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

    @Test
    void creatingAMeteoriteIncrementsBothCounters() {
        int eventsBefore = RandomEvent.getInstanceCount();
        int virusesBefore = Virus.getInstanceCount();
        int meteoritesBefore = Meteorite.getInstanceCount();

        new Meteorite();

        assertEquals(eventsBefore + 1, RandomEvent.getInstanceCount());
        assertEquals(virusesBefore, Virus.getInstanceCount());
        assertEquals(meteoritesBefore + 1, Meteorite.getInstanceCount());
    }
}
