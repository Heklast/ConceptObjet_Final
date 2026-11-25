package game.beings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.Direction;
import game.GameMap;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

class LivingBeingTest {

    @Test
    void eachSpeciesHasItsTeamColor() {
        assertEquals(Color.PURPLE, new Elf(0, 0).getColor());
        assertEquals(Color.BLUE, new Human(0, 0).getColor());
        assertEquals(Color.GREEN, new Goblin(0, 0).getColor());
        assertEquals(Color.ORANGE, new Orch(0, 0).getColor());
    }

    @Test
    void troopersUseTheirSpeciesDirections() {
        var diagonals = EnumSet.of(Direction.NE, Direction.SE, Direction.SO, Direction.NO);
        Elf elf = new Elf(5, 5);
        Human human = new Human(5, 5);
        Orch orch = new Orch(5, 5);
        Goblin goblin = new Goblin(5, 5);
        for (Direction direction : Direction.values()) {
            assertEquals(diagonals.contains(direction), elf.canMoveInDirection(direction));
            assertEquals(!diagonals.contains(direction), human.canMoveInDirection(direction));
            assertTrue(orch.canMoveInDirection(direction));
            assertTrue(goblin.canMoveInDirection(direction));
        }
    }

    @Test
    void goblinsMoveAtMostOneTileRegardlessOfRole() {
        Goblin goblin = new Goblin(5, 5);
        for (Roles role : Roles.values()) {
            goblin.setRole(role);
            assertEquals(1, goblin.randomDistance());
        }
    }

    @Test
    void returningElfStopsAtTheFirstSafeSquareAndRechargesNextTurn() {
        GameMap map = new GameMap(15, 15);
        Elf elf = new Elf(0, 11) {
            @Override
            protected int randomDistance() { return 3; }
        };
        map.moveCharacter(elf, 11, 0);
        elf.setMap(map);
        elf.loseEnergyPoints(elf.getMaxEnergyPoints() - 5);

        elf.move();
        assertEquals(12, elf.getPosX());
        assertEquals(1, elf.getPosY());
        assertEquals(5, elf.getEnergyPoints());

        elf.move();
        assertEquals(12, elf.getPosX());
        assertEquals(1, elf.getPosY());
        assertTrue(elf.getEnergyPoints() > 5);
    }

    @Test
    void reachingZeroEnergyKillsTheBeing() {
        Elf elf = new Elf(5, 5);
        elf.addMessage("hello");

        elf.loseEnergyPoints(elf.getMaxEnergyPoints() + 50);

        assertEquals(0, elf.getEnergyPoints());
        assertTrue(elf.isDead());
        assertEquals(0, elf.getNumberOfMessages());
    }
}
