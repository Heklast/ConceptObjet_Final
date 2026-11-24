package game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomGenerator {

    private static Random random = new Random();

    private RandomGenerator() {
    }

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public static int nextInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min (" + min + ") must not be greater than max (" + max + ")");
        }
        return min + random.nextInt(max - min + 1);
    }

    /** Random int in [0, bound). */
    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static double nextGaussian() {
        return random.nextGaussian();
    }

    public static Random asRandom() {
        return random;
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static boolean chance(double probability) {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("probability (" + probability + ") must be between 0 and 1");
        }
        return random.nextDouble() < probability;
    }

    public static void shuffle(List<?> list) {
        Collections.shuffle(list, random);
    }
}
