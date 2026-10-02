package game.beings;
import game.Direction;
import java.util.Random;

public class LivingBeing {
	private int posX;
	private int posY;
	private String[] messages;
	private int energyPoints;
	private Direction lastDirectionTaken;
	private Color color;
        
        private int speed;
        private int strength;
        private int memory;
        
        
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
        }
}
