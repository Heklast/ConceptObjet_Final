package game;

import game.beings.Color;
import game.beings.LivingBeing;

public class Map {
    private int sizeX;
    private int sizeY;
    private Square[][] squares;
        
    public Map(int sizeX,int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        squares = new Square[sizeY][sizeX];
        
        for(int i=0;i<sizeY;i++){
            for(int j=0;j<sizeX;j++){
                Color color = Color.RESET;
                
                if(i<3 && j<3){
                    color=Color.GREEN;
                }
                if(i>=sizeY-3 && j<3){
                    color = Color.ORANGE;
                }
                if(i<3 && j>=sizeX-3){
                    color = Color.PURPLE;
                }
                if(i>=sizeY-3 && j>=sizeX-3){
                    color = Color.BLUE;
                }
                
                Square square = new Square(i,j,color,this);
                squares[i][j]=square;
                
            }
        }
     
    }
    
    public void display(){
        System.out.println("Map:");
        for (Square[] square1 : squares) {
            for (Square square : square1) {
                square.display();
            }
            System.out.println("");
        }
    }
    
    public void moveCharacter(LivingBeing character,int newPosX,int newPosY){
        int oldX = character.getPosX();
        int oldY = character.getPosY();
        
        squares[oldY][oldX].setOccupedBy(null);
        squares[newPosY][newPosX].setOccupedBy(character);
    }
       
        
}
