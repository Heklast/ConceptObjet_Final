package game.beings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.masters.MasterElf;
import org.junit.jupiter.api.Test;

class LivingBeingTest {

    @Test
    void beingStartsAliveWithFullEnergy() {
        Elf elf = new Elf(5, 5);

        assertFalse(elf.isDead());
        assertEquals(LivingBeing.DEFAULT_MAX_ENERGY_POINTS, elf.getMaxEnergyPoints());
        assertEquals(elf.getMaxEnergyPoints(), elf.getEnergyPoints());
    }

    @Test
    void eachSpeciesHasItsTeamColor() {
        assertEquals(Color.PURPLE, new Elf(0, 0).getColor());
        assertEquals(Color.BLUE, new Human(0, 0).getColor());
        assertEquals(Color.GREEN, new Goblin(0, 0).getColor());
        assertEquals(Color.ORANGE, new Orch(0, 0).getColor());
    }

    @Test
    void masterHasTheColorOfItsSpecies() {
        assertEquals(Color.PURPLE, new MasterElf(0, 0).getColor());
    }

    @Test
    void loseEnergyPointsDecreasesEnergy() {
        Elf elf = new Elf(5, 5);

        elf.loseEnergyPoints(30);

        assertEquals(elf.getMaxEnergyPoints() - 30, elf.getEnergyPoints());
        assertFalse(elf.isDead());
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

    @Test
    void loseEnergyPointsRejectsNegativeAmount() {
        Elf elf = new Elf(5, 5);

        assertThrows(IllegalArgumentException.class, () -> elf.loseEnergyPoints(-1));
    }

    @Test
    void dieLosesAllMessages() {
        Orch orch = new Orch(5, 5);
        orch.addMessage("first");
        orch.addMessage("second");

        orch.die();

        assertTrue(orch.isDead());
        assertEquals(0, orch.getEnergyPoints());
        assertEquals(0, orch.getNumberOfMessages());
    }
}
