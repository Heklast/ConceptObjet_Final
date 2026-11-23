package game.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.Map;
import game.beings.Elf;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirusTest {

    private Map map;
    private Virus virus;

    @BeforeEach
    void setUp() {
        map = new Map(16, 10);
        virus = new Virus();
    }

    private Elf elfWithEnergy(int initialY, int initialX, int energyPoints) {
        Elf elf = new Elf(initialY, initialX);
        elf.loseEnergyPoints(elf.getMaxEnergyPoints() - energyPoints);
        elf.addMessage("secret");
        return elf;
    }

    @Test
    void weakBeingOutsideItsSafeZoneDies() {
        Elf elf = elfWithEnergy(5, 8, 20);

        virus.apply(map, List.of(elf));

        assertTrue(elf.isDead());
        assertEquals(0, elf.getNumberOfMessages());
        assertEquals("1 character died", virus.describeEffect());
    }

    @Test
    void strongBeingOutsideItsSafeZoneSurvives() {
        Elf elf = elfWithEnergy(5, 8, 80);

        virus.apply(map, List.of(elf));

        assertFalse(elf.isDead());
        assertEquals(1, elf.getNumberOfMessages());
    }

    @Test
    void weakBeingInsideItsOwnSafeZoneSurvives() {
        Elf elf = elfWithEnergy(0, 15, 10);

        virus.apply(map, List.of(elf));

        assertFalse(elf.isDead());
    }

    @Test
    void weakBeingInAnotherTeamSafeZoneDies() {
        Elf elf = elfWithEnergy(0, 0, 10);

        virus.apply(map, List.of(elf));

        assertTrue(elf.isDead());
    }
}
