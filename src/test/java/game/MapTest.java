package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.Color;
import game.beings.Elf;
import game.beings.Goblin;
import java.util.List;
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
    void squaresKnowTheirCoordinates() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Square square = map.getSquare(12, 7);

        assertEquals(12, square.getX());
        assertEquals(7, square.getY());
    }

    @Test
    void eachSafeZoneHasNineSquaresOfItsColor() {
        Map map = new Map(SIZE_X, SIZE_Y);

        for (Color color : List.of(Color.GREEN, Color.PURPLE, Color.ORANGE, Color.BLUE)) {
            List<Square> zone = map.getSafeZoneSquares(color);
            assertEquals(9, zone.size());
            for (Square square : zone) {
                assertEquals(color, square.getOwner());
            }
        }
    }

    @Test
    void freeSquaresOutsideSafeZonesExcludeTheCorners() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertEquals(SIZE_X * SIZE_Y - 4 * 9, map.getFreeSquaresOutsideSafeZones().size());
    }

    @Test
    void placeRandomRocksPlacesTheRequestedNumber() {
        Map map = new Map(SIZE_X, SIZE_Y);
        int freeBefore = map.getFreeSquaresOutsideSafeZones().size();

        int placed = map.placeRandomRocks(10);

        assertEquals(10, placed);
        assertEquals(freeBefore - 10, map.getFreeSquaresOutsideSafeZones().size());
    }

    @Test
    void placeRandomRocksStopsWhenThereIsNoRoomLeft() {
        Map map = new Map(6, 6);

        assertEquals(0, map.placeRandomRocks(5));
    }

    @Test
    void placeRandomRocksRejectsNegativeNumber() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertThrows(IllegalArgumentException.class, () -> map.placeRandomRocks(-1));
    }

    @Test
    void moveCharacterUpdatesTheSquaresAndThePosition() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Elf elf = new Elf(5, 8);
        map.moveCharacter(elf, 8, 5);

        map.moveCharacter(elf, 9, 6);

        assertEquals(9, elf.getPosX());
        assertEquals(6, elf.getPosY());
        assertTrue(map.getSquare(8, 5).isFree());
        assertEquals(elf, map.getSquare(9, 6).getOccupedBy());
    }

    @Test
    void moveCharacterRefusesAnOccupiedSquare() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Elf first = new Elf(5, 8);
        Elf second = new Elf(5, 9);
        map.moveCharacter(first, 8, 5);
        map.moveCharacter(second, 9, 5);

        assertThrows(IllegalStateException.class, () -> map.moveCharacter(second, 8, 5));
        assertEquals(9, second.getPosX());
        assertEquals(second, map.getSquare(9, 5).getOccupedBy());
    }

    @Test
    void moveCharacterRefusesARock() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Elf elf = new Elf(5, 8);
        map.moveCharacter(elf, 8, 5);
        map.getSquare(9, 5).placeRock();

        assertThrows(IllegalStateException.class, () -> map.moveCharacter(elf, 9, 5));
    }

    @Test
    void placeRockRefusesAnOccupiedSquare() {
        Map map = new Map(SIZE_X, SIZE_Y);
        Elf elf = new Elf(5, 8);
        map.moveCharacter(elf, 8, 5);

        assertThrows(IllegalStateException.class, () -> map.getSquare(8, 5).placeRock());
    }
}
