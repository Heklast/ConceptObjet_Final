package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.Elf;
import org.junit.jupiter.api.Test;

class MapTest {

    private static final int SIZE_X = 16;
    private static final int SIZE_Y = 10;

    @Test
    void elfIsSafeOnlyInItsOwnCorner() {
        Map map = new Map(SIZE_X, SIZE_Y);

        assertTrue(map.isInOwnSafeZone(new Elf(0, SIZE_X - 1)));
        assertFalse(map.isInOwnSafeZone(new Elf(5, 8)));
        assertFalse(map.isInOwnSafeZone(new Elf(0, 0)));
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
}
