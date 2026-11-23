package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.LivingBeing;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void gameCreatesFourTeamsOfCharacters() {
        Game game = new Game(16, 10, 5);

        assertEquals(20, game.getCharacters().size());
    }

    @Test
    void everyCharacterStartsOnItsOwnSquare() {
        Game game = new Game(16, 10, 5);
        Set<String> positions = new HashSet<>();

        for (LivingBeing character : game.getCharacters()) {
            positions.add(character.getPosX() + "," + character.getPosY());
        }

        assertEquals(game.getCharacters().size(), positions.size());
    }

    @Test
    void everyCharacterStartsInItsOwnSafeZone() {
        Game game = new Game(16, 10, 5);

        for (LivingBeing character : game.getCharacters()) {
            assertTrue(game.getMap().isInOwnSafeZone(character));
        }
    }

    @Test
    void everyCharacterIsVisibleOnTheMap() {
        Game game = new Game(16, 10, 5);
        Map map = game.getMap();

        for (LivingBeing character : game.getCharacters()) {
            assertEquals(character, map.getSquare(character.getPosX(), character.getPosY()).getOccupedBy());
        }
    }

    @Test
    void gamePlacesTheObstacles() {
        Game game = new Game(16, 10, 5);
        Map map = game.getMap();
        int rocks = 0;

        for (int y = 0; y < map.getSizeY(); y++) {
            for (int x = 0; x < map.getSizeX(); x++) {
                if (map.getSquare(x, y).getType() == SquareTypes.ROCK) {
                    rocks++;
                }
            }
        }

        assertEquals(Game.NUMBER_OF_OBSTACLES, rocks);
    }

    @Test
    void gameRefusesMoreCharactersThanASafeZoneCanHold() {
        assertThrows(IllegalArgumentException.class, () -> new Game(16, 10, 10));
    }

    @Test
    void charactersListCannotBeModifiedFromOutside() {
        Game game = new Game(16, 10, 5);
        List<LivingBeing> characters = game.getCharacters();

        assertThrows(UnsupportedOperationException.class, () -> characters.clear());
    }
}
