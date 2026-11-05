package game;
import game.beings.LivingBeing;

public class Game {
	private LivingBeing[] characters;
	private Map map;
        
        public Game(){
            this(16, 10, 5);
        }
        
        public Game(int sizeX,int sizeY, int nbCharactersByTeam){
            Map carte = new Map(sizeX,sizeY);
            this.map=carte;
        }
        
        
        public void main(){
            System.out.println("test");
            map.display();
        }
}
