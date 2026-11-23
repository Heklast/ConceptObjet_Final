package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RandomGeneratorTest {

    @Test
    void nextIntStaysWithinBounds() {
        for (int i = 0; i < 1000; i++) {
            int value = RandomGenerator.nextInt(1, 3);
            assertTrue(value >= 1 && value <= 3);
        }
    }

    @Test
    void nextIntReachesBothBounds() {
        boolean minReached = false;
        boolean maxReached = false;
        for (int i = 0; i < 1000; i++) {
            int value = RandomGenerator.nextInt(1, 3);
            if (value == 1) {
                minReached = true;
            }
            if (value == 3) {
                maxReached = true;
            }
        }
        assertTrue(minReached);
        assertTrue(maxReached);
    }

    @Test
    void nextIntWithEqualBoundsReturnsThatValue() {
        assertEquals(5, RandomGenerator.nextInt(5, 5));
    }

    @Test
    void nextIntRejectsInvertedBounds() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerator.nextInt(3, 1));
    }

    @Test
    void nextDoubleIsBetweenZeroAndOne() {
        for (int i = 0; i < 1000; i++) {
            double value = RandomGenerator.nextDouble();
            assertTrue(value >= 0 && value < 1);
        }
    }

    @Test
    void chanceWithExtremeProbabilities() {
        for (int i = 0; i < 100; i++) {
            assertFalse(RandomGenerator.chance(0));
            assertTrue(RandomGenerator.chance(1));
        }
    }

    @Test
    void chanceRejectsInvalidProbability() {
        assertThrows(IllegalArgumentException.class, () -> RandomGenerator.chance(1.5));
        assertThrows(IllegalArgumentException.class, () -> RandomGenerator.chance(-0.1));
    }

    @Test
    void sameSeedGivesSameSequence() {
        RandomGenerator.setSeed(42);
        int first = RandomGenerator.nextInt(0, 1000);
        int second = RandomGenerator.nextInt(0, 1000);

        RandomGenerator.setSeed(42);
        assertEquals(first, RandomGenerator.nextInt(0, 1000));
        assertEquals(second, RandomGenerator.nextInt(0, 1000));
    }
}
