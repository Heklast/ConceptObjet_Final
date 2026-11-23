package game.beings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void reachingZeroEnergyKillsTheBeing() {
        Elf elf = new Elf(5, 5);
        elf.addMessage("hello");

        elf.loseEnergyPoints(elf.getMaxEnergyPoints() + 50);

        assertEquals(0, elf.getEnergyPoints());
        assertTrue(elf.isDead());
        assertEquals(0, elf.getNumberOfMessages());
    }
}
