package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void rotatingCounterClockwiseFromNorthGivesNorthWest() {
        assertEquals(Direction.NO, Direction.N.getModifiedValue(-1));
        assertEquals(Direction.O, Direction.N.getModifiedValue(-2));
        assertEquals(Direction.N, Direction.NO.getModifiedValue(1));
    }
}
