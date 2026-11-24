package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import game.GameMap;
import game.Square;
import game.SquareTypes;
import java.util.List;
import org.junit.jupiter.api.Test;

class MeteoriteTest {

    private int countRocks(GameMap map) {
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
        GameMap map = new GameMap(16, 10);
        Meteorite meteorite = new Meteorite();

        meteorite.apply(map, List.of());

        assertEquals(Meteorite.NUMBER_OF_ROCKS, countRocks(map));
        assertEquals(Meteorite.NUMBER_OF_ROCKS + " rocks fell", meteorite.describeEffect());
    }

    @Test
    void rocksNeverFallInASafeZone() {
        for (int i = 0; i < 50; i++) {
            GameMap map = new GameMap(16, 10);

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
}
