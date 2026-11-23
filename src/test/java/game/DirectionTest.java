package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void rotatingClockwiseMovesToTheNextDirection() {
        assertEquals(Direction.NE, Direction.N.getModifiedValue(1));
        assertEquals(Direction.S, Direction.SE.getModifiedValue(1));
    }

    @Test
    void rotatingCounterClockwiseFromNorthGivesNorthWest() {
        assertEquals(Direction.NO, Direction.N.getModifiedValue(-1));
        assertEquals(Direction.O, Direction.N.getModifiedValue(-2));
        assertEquals(Direction.SO, Direction.N.getModifiedValue(-3));
    }

    @Test
    void rotatingClockwiseFromNorthWestWrapsToNorth() {
        assertEquals(Direction.N, Direction.NO.getModifiedValue(1));
    }

    @Test
    void zeroRotationKeepsTheDirection() {
        for (Direction direction : Direction.values()) {
            assertEquals(direction, direction.getModifiedValue(0));
        }
    }

    @Test
    void fullTurnInBothWaysKeepsTheDirection() {
        for (Direction direction : Direction.values()) {
            assertEquals(direction, direction.getModifiedValue(8));
            assertEquals(direction, direction.getModifiedValue(-8));
        }
    }

    @Test
    void largeRotationsAreEquivalentToSmallOnes() {
        assertEquals(Direction.S.getModifiedValue(-1), Direction.S.getModifiedValue(-9));
        assertEquals(Direction.S.getModifiedValue(3), Direction.S.getModifiedValue(11));
    }
}
