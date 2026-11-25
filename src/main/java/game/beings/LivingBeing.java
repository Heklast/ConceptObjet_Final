package game.beings;

import game.Config;
import game.Direction;
import game.GameMap;
import game.RandomGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LivingBeing {

    // Number of beings created / exhausted (static, for the display)
    private static int count = 0;
    private static int exhaustedCount = 0;
    /** Message transfers / fights of the current step, shown by the display (cleared by it). */
    private static final List<String> events = new ArrayList<>();

    private int posX;
    private int posY;
    private ArrayList<String> messages;
    private Direction lastDirectionTaken;
    private final Color color;
    private Roles role;
    private Skills skills;
    private GameMap map;

    // Energy
    private int energyPoints;
    private int maxEnergyPoints;
    private boolean returningHome = false;
    private boolean exhausted = false;

    public LivingBeing(int initialY, int initialX, Color color) {
        this(initialY, initialX, color, Roles.WARRIOR);
    }

    public LivingBeing(int initialY, int initialX, Color color, Roles role) {
        posX = initialX;
        posY = initialY;
        messages = new ArrayList<>();
        lastDirectionTaken = Direction.SE;
        this.color = color;
        applyRole(role);
        count++;
    }

    //role, skills and max EP
    private void applyRole(Roles role) {
        this.role = role;
        this.skills = role.getSkills();
        this.maxEnergyPoints = (int) Math.round(Config.BASE_MAX_EP
                * (1 + Config.STRENGTH_EP_BONUS * (skills.getStrength() - 1)));
        this.energyPoints = maxEnergyPoints;
    }

    public void setRole(Roles role) { applyRole(role); }

    public void setMap(GameMap map) { this.map = map; }

    //this one is only called by GameMap.moveCharacter
    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // ---------------------------------------------------------------- energy

    public int getEnergyPoints() { return energyPoints; }

    public int getMaxEnergyPoints() { return maxEnergyPoints; }

    //if EP below 20% of the maximum, the being will head back to the SafeZone
    public boolean isLowEnergy() {
        return energyPoints < Config.LOW_EP_RATIO * maxEnergyPoints;
    }

    public boolean isExhausted() { return exhausted; }

    //exhausted (EP == 0) and dead (killed by an event) are the same state
    public boolean isDead() { return exhausted; }

    public int getNumberOfMessages() { return messages.size(); }

    //direct EP loss (events), if 0 the being dies
    public void loseEnergyPoints(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount (" + amount + ") must not be negative");
        }
        loseEnergy(amount);
    }

    public void die() {
        energyPoints = 0;
        becomeObstacle();
    }

    protected void spendEnergy(int tiles) {
        loseEnergy(tiles * Config.EP_PER_TILE);
    }

    //blocked by an obstacle so the being loses the EP of the tiles it had left to walk
    protected void spendEnergyForBlockedPath(int tilesLeft) {
        if (!isInOwnSafeZone()) {
            spendEnergy(tilesLeft);
        }
    }

    //direct EP loss (fights), if 0 the being becomes an obstacle
    protected void loseEnergy(int ep) {
        energyPoints = Math.max(0, energyPoints - ep);
        if (energyPoints == 0) becomeObstacle();
    }

    protected void recharge() {
        energyPoints = Math.min(maxEnergyPoints, energyPoints + Config.RECHARGE_PER_STEP);
    }

    //EP == 0: the being stops moving, loses its messages and acts as an obstacle
    protected void becomeObstacle() {
        if (exhausted) return;
        exhausted = true;
        messages.clear();
        exhaustedCount++;
        //the being stays on its square, which stays occupied so it is an obstacle for everybody
    }

    // ------------------------------------------------------------------ move

    /**
     * One turn. Polymorphic: masters override it to stay still
     * The walking itself (obstacles, encounters) needs the Map and is still to be plugged in
     */
    public void move() {
        if (isDead() || map == null) return;

        boolean inSafeZone = isInOwnSafeZone();
        if (inSafeZone) {
            recharge();
            //stay until enough EP to avoid going back and forth
            if (energyPoints < Config.LEAVE_SAFEZONE_RATIO * maxEnergyPoints) return;
            returningHome = false;
        } else if (isLowEnergy()) {
            returningHome = true;
        }

        Direction nextDirection = returningHome ? directionToSafeZone() : randomDirection();
        int distance = randomDistance();

        walk(nextDirection, distance);

        if (energyPoints == 0) becomeObstacle();
    }
    
    protected boolean canMoveInDirection(Direction direction) {
        return true;
    }

    //last direction slightly rotated (-1, 0 or +1 mostly)
    private Direction randomDirection() {
        int modifier = (int) Math.round(RandomGenerator.nextGaussian());
        return lastDirectionTaken.getModifiedValue(modifier);
    }

    //1 to (1 + speed) tiles; higher speed makes long moves more probable
    protected int randomDistance() {
        double p = RandomGenerator.nextDouble();
        int distance = 1;
        if (p < 0.6 + skills.getSpeed() * 0.1) distance++;
        if (skills.getSpeed() >= 2 && p < 0.2 + skills.getSpeed() * 0.1) distance++;
        return distance;
    }

    /**
     * Walks tile by tile. Stops in front of an obstacle/being (then loses the EP of the
     * tiles it had left) and turns so that the next move is not blocked again.
     * EP is only spent on tiles outside the own SafeZone.
     */
    private void walk(Direction direction, int distance) {
        //go home so sidestep (+-45, +-90 degrees) if the straight way is blocked
    	if (!canMoveInDirection(direction)) {
    	    int turn = RandomGenerator.nextBoolean() ? 1 : -1;
    	    direction = direction.getModifiedValue(turn);
    	}
        if (returningHome) {
            int[] turns = {0, 1, -1, 2, -2};
            for (int t : turns) {
                Direction d = direction.getModifiedValue(t);
                if (canMoveInDirection(d)
                        && map.isFreeFor(this, posX + d.getDx(), posY + d.getDy())) {
                    direction = d;
                    break;
                }
            }
        }
        lastDirectionTaken = direction;
        for (int step = 0; step < distance && !isExhausted(); step++) {
            int nx = posX + direction.getDx();
            int ny = posY + direction.getDy();
            if (!map.isFreeFor(this, nx, ny)) {
                //possible encounter
                if (map.isInBounds(nx, ny)) {
                    LivingBeing other = map.getSquare(nx, ny).getOccupedBy();
                    if (other != null && !other.isExhausted() && !exhausted) {
                        encounter(other);
                    }
                }
                spendEnergyForBlockedPath(distance - step);
                boolean clockwise = RandomGenerator.nextBoolean();
                lastDirectionTaken = direction.getModifiedValue(clockwise ? 1 : -1);
                return;
            }
            map.moveCharacter(this, nx, ny);
            if (isInOwnSafeZone()) {
                if (returningHome) return;
            } else {
                spendEnergy(1);
            }
        }
    }

    private boolean isInOwnSafeZone() {
        return map.isInOwnSafeZone(this);
    }

    private Direction directionToSafeZone() {
        return map.directionToSafeZone(this);
    }


    //encounters

    //same race?
    public boolean isSameRace(LivingBeing other) { return false; }

    //same alliance (Good / Bad)?
    public boolean isAlly(LivingBeing other) { return false; }

    /**
     * Meeting with another being. Same race: union of knowledge. Same alliance: random
     * exchange. Otherwise: fight (the loser also pays more EP). Never inside a SafeZone.
     */
    public void encounter(LivingBeing other) {
        if (map.isInOwnSafeZone(this) || map.isInOwnSafeZone(other)) return;
        if (isSameRace(other)) {
            transfer(this, other, Integer.MAX_VALUE);
            transfer(other, this, Integer.MAX_VALUE);
        } else if (isAlly(other)) {
            transfer(this, other, 1 + RandomGenerator.nextInt(Config.MAX_MESSAGES_PER_ENCOUNTER));
            transfer(other, this, 1 + RandomGenerator.nextInt(Config.MAX_MESSAGES_PER_ENCOUNTER));
        } else {
            fight(other);
        }
    }

    /**roll d6 + strength for each side; the winner takes messages it lacks from the loser. */
    private void fight(LivingBeing other) {
        int mine = 1 + RandomGenerator.nextInt(6) + skills.getStrength();
        int theirs = 1 + RandomGenerator.nextInt(6) + other.skills.getStrength();
        if (mine == theirs) {
            events.add(label() + " vs " + other.label() + ": draw");
            return;
        }
        LivingBeing winner = mine > theirs ? this : other;
        LivingBeing loser = winner == this ? other : this;
        int moved = transfer(loser, winner, 1 + RandomGenerator.nextInt(Config.MAX_MESSAGES_PER_ENCOUNTER), true);
        events.add(winner.label() + " beats " + loser.label() + " (" + moved + " msg stolen)");
        winner.loseEnergy(Config.FIGHT_EP_COST_WINNER);
        loser.loseEnergy(Config.FIGHT_EP_COST_LOSER);
    }

    private static int transfer(LivingBeing from, LivingBeing to, int max) {
        return transfer(from, to, max, false);
    }

    //copies (or moves if `steal`) up to `max` messages `to` does not have, within its capacity
    private static int transfer(LivingBeing from, LivingBeing to, int max, boolean steal) {
        List<String> candidates = new ArrayList<>();
        for (String m : from.messages) {
            if (!to.messages.contains(m)) candidates.add(m);
        }
        Collections.shuffle(candidates, RandomGenerator.asRandom());
        int moved = 0;
        for (String m : candidates) {
            if (moved >= max || to.messages.size() >= to.getMessageCapacity()) break;
            to.messages.add(m);
            if (steal) from.messages.remove(m);
            moved++;
        }
        if (moved > 0 && !steal) {
            events.add(from.label() + " -> " + to.label() + " : " + moved + " msg");
        }
        return moved;
    }

    private String label() {
        return getClass().getSimpleName() + "@" + posX + "," + posY;
    }

    public List<String> getMessages() { return messages; }

    //Events of the last step; the display prints then clears them
    public static List<String> drainEvents() {
        List<String> copy = new ArrayList<>(events);
        events.clear();
        return copy;
    }

    //messages

    //Memory skill limits how many messages can be carried
    public int getMessageCapacity() {
        return Config.MESSAGES_PER_MEMORY * skills.getMemory();
    }

    public void addMessage(String message) {
        if (!messages.contains(message)) {
            this.messages.add(message);
        }
    }

    //getters

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public Skills getSkills() { return skills; }

    public Roles getRole() { return role; }

    public Color getColor() { return color; }

    public static int getCount() { return count; }

    public static int getExhaustedCount() { return exhaustedCount; }
}
