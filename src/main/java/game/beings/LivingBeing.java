package game.beings;
import game.Direction;
import java.util.ArrayList;
import java.util.Random;

public class LivingBeing {
	public static final int DEFAULT_MAX_ENERGY_POINTS = 100;

	private int posX;
	private int posY;
	private ArrayList<String> messages;
	private int energyPoints;
	private final int maxEnergyPoints;
	private boolean dead;
	private Direction lastDirectionTaken;
	private final Color color;
        private Roles role;
        
        private int speed;
        private int strength;
        private int memory;
        
        public LivingBeing(int initialY, int initialX, Color color){
            posX=initialX;
            posY=initialY;
            messages = new ArrayList<>();
            lastDirectionTaken = Direction.SE;
            this.color = color;
            maxEnergyPoints = DEFAULT_MAX_ENERGY_POINTS;
            energyPoints = maxEnergyPoints;
        }
        
        public void move(){
            Random rand = new Random();
            
            int modifier = (int) Math.round(rand.nextGaussian());
            System.out.println(modifier);
            
            Direction nextDirection = lastDirectionTaken.getModifiedValue(modifier);
            
            boolean firstMovingCaseFree = true;
            
            if(!firstMovingCaseFree){
                boolean rotationHour = rand.nextBoolean();
                if(rotationHour){
                    nextDirection = nextDirection.getModifiedValue(-1);
                }
                else{
                    nextDirection = nextDirection.getModifiedValue(1);

                }
            }
            
            
            double distanceProbability  = rand.nextDouble();
            int distance = 1;
            if(distanceProbability<0.6+speed/10) distance++;
            if(distanceProbability<0.2+speed/10) distance++;

            System.out.println(nextDirection);
            System.out.println(distance);
        }
        
        public void addMessage(String message){
            this.messages.add(message);
        }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Color getColor() {
        return color;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public int getMaxEnergyPoints() {
        return maxEnergyPoints;
    }

    public int getNumberOfMessages() {
        return messages.size();
    }

    public boolean isDead() {
        return dead;
    }

    public void loseEnergyPoints(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount (" + amount + ") must not be negative");
        }
        energyPoints = Math.max(0, energyPoints - amount);
        if (energyPoints == 0) {
            die();
        }
    }

    public void die() {
        dead = true;
        energyPoints = 0;
        messages.clear();
    }
        
        
        
}
