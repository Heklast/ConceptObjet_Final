package game.beings;

/**
 * Immutable set of the three skills of a being.
 * Rule: every skill is 1 or 2, and exactly ONE of them is 2.
 */
public final class Skills {
    private final int strength;
    private final int speed;
    private final int memory;

    public Skills(int strength, int speed, int memory) {
        if (!isValid(strength) || !isValid(speed) || !isValid(memory)) {
            throw new IllegalArgumentException("Each skill must be 1 or 2");
        }
        if ((strength + speed + memory) != 4) {
            throw new IllegalArgumentException("Exactly one skill must be 2 and the others 1");
        }
        this.strength = strength;
        this.speed = speed;
        this.memory = memory;
    }

    private static boolean isValid(int v) {
        return v == 1 || v == 2;
    }

    public static Skills strong() { return new Skills(2, 1, 1); }
    public static Skills fast()   { return new Skills(1, 2, 1); }
    public static Skills wise()   { return new Skills(1, 1, 2); }

    public int getStrength() { return strength; }
    public int getSpeed()    { return speed; }
    public int getMemory()   { return memory; }

    @Override
    public String toString() {
        return "STR" + strength + "/SPD" + speed + "/MEM" + memory;
    }
}
