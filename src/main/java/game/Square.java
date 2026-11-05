package game;

import game.beings.Color;


public class Square {
    private int posX;
    private int posY;
    private Color owner;
    private SquareTypes type;
   
    
    public Square(int posX,int posY){
        this.posX=posX;
        this.posY=posY;
        this.type=SquareTypes.GRASS;
    }
}
