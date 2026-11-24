package game;

import java.util.Random;

/** Single source of pseudo-random numbers for the whole simulation (seedable). */
public final class RandomGenerator {
    private static RandomGenerator instance;
    private final Random random;

    private RandomGenerator(long seed) {
        this.random = new Random(seed);
    }

    public static RandomGenerator getInstance() {
        if (instance == null) {
            instance = new RandomGenerator(System.nanoTime());
        }
        return instance;
    }

    /** Re-seed (reproducible runs, tests). */
    public static void setSeed(long seed) {
        instance = new RandomGenerator(seed);
    }

    public int nextInt(int bound)      { return random.nextInt(bound); }
    public double nextDouble()         { return random.nextDouble(); }
    public boolean nextBoolean()       { return random.nextBoolean(); }
    public double nextGaussian()       { return random.nextGaussian(); }
    public Random asRandom()           { return random; }
}
