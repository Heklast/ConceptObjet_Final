package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import game.Map;
import game.Square;
import game.SquareTypes;
import game.beings.Elf;
import game.beings.LivingBeing;
import java.util.List;
import org.junit.jupiter.api.Test;

class MeteoriteTest {

    private int countRocks(Map map) {
        int rocks = 0;
        for (int y = 0; y < map.getSizeY(); y++) {
            for (int x = 0; x < map.getSizeX(); x++) {
                if (map.getSquare(x, y).getType() == SquareTypes.ROCK) {
                    rocks++;
                }
            }
        }
        return rocks;
    }

    @Test
    void meteoritePlacesTheExpectedNumberOfRocks() {
        Map map = new Map(16, 10);

        new Meteorite().apply(map, List.of());

        assertEquals(Meteorite.NUMBER_OF_ROCKS, countRocks(map));
    }

    @Test
    void rocksNeverFallInASafeZone() {
        for (int i = 0; i < 50; i++) {
            Map map = new Map(16, 10);

            new Meteorite().apply(map, List.of());

            for (int y = 0; y < map.getSizeY(); y++) {
                for (int x = 0; x < map.getSizeX(); x++) {
                    Square square = map.getSquare(x, y);
                    if (square.isSafeZone()) {
                        assertEquals(SquareTypes.GRASS, square.getType());
                    }
                }
            }
        }
    }

    @Test
    void rocksNeverFallOnACharacter() {
        Map map = new Map(7, 6);
        Elf first = new Elf(0, 3);
        Elf second = new Elf(1, 3);
        map.moveCharacter(first, 3, 0);
        map.moveCharacter(second, 3, 1);
        List<LivingBeing> characters = List.of(first, second);

        new Meteorite().apply(map, characters);

        assertEquals(SquareTypes.GRASS, map.getSquare(3, 0).getType());
        assertEquals(SquareTypes.GRASS, map.getSquare(3, 1).getType());
        assertEquals(4, countRocks(map));
    }

    @Test
    void meteoriteOnAMapWithoutFreeSquaresDoesNothing() {
        Map map = new Map(6, 6);
        Meteorite meteorite = new Meteorite();

        meteorite.apply(map, List.of());

        assertEquals(0, countRocks(map));
        assertEquals("0 rocks fell", meteorite.describeEffect());
    }

    @Test
    void describeEffectCountsTheRocks() {
        Meteorite meteorite = new Meteorite();

        meteorite.apply(new Map(16, 10), List.of());

        assertEquals(Meteorite.NUMBER_OF_ROCKS + " rocks fell", meteorite.describeEffect());
    }

    @Test
    void secondMeteoriteAddsNewRocks() {
        Map map = new Map(16, 10);

        new Meteorite().apply(map, List.of());
        new Meteorite().apply(map, List.of());

        assertEquals(2 * Meteorite.NUMBER_OF_ROCKS, countRocks(map));
    }
}
