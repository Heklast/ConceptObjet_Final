package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.Elf;
import game.beings.Goblin;
import org.junit.jupiter.api.Test;

class MapTest {

    private static final int SIZE_X = 16;
    private static final int SIZE_Y = 10;

    @Test
    void mapKeepsItsSize() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertEquals(SIZE_X, map.getSizeX());
        assertEquals(SIZE_Y, map.getSizeY());
    }

    @Test
    void getSquareRejectsCoordinatesOutsideTheMap() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertThrows(IllegalArgumentException.class, () -> map.getSquare(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> map.getSquare(0, SIZE_Y));
        assertThrows(IllegalArgumentException.class, () -> map.getSquare(SIZE_X, 0));
    }

    @Test
    void cornersAreSafeZonesAndCenterIsNot() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertTrue(map.getSquare(0, 0).isSafeZone());
        assertTrue(map.getSquare(SIZE_X - 1, SIZE_Y - 1).isSafeZone());
        assertFalse(map.getSquare(8, 5).isSafeZone());
    }

    @Test
    void elfIsSafeOnlyInItsOwnCorner() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertTrue(map.isInOwnSafeZone(new Elf(0, SIZE_X - 1)));
        assertFalse(map.isInOwnSafeZone(new Elf(5, 8)));
        assertFalse(map.isInOwnSafeZone(new Elf(0, 0)));
    }

    @Test
    void goblinIsSafeInTheTopLeftCorner() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertTrue(map.isInOwnSafeZone(new Goblin(0, 0)));
    }

    @Test
    void placeRockTurnsAFreeSquareIntoARock() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Square square = map.getSquare(8, 5);

        square.placeRock();

        assertEquals(SquareTypes.ROCK, square.getType());
        assertFalse(square.isFree());
    }

    @Test
    void placeRockRefusesAnOccupiedSquare() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Elf elf = new Elf(5, 8);
        map.moveCharacter(elf, 8, 5);

        assertThrows(IllegalStateException.class, () -> map.getSquare(8, 5).placeRock());
    }
}
