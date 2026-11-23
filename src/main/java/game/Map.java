package game;

import game.beings.Color;
import game.beings.LivingBeing;
import java.util.ArrayList;
import java.util.List;

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
                
                Square square = new Square(j,i,color,this);
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
    
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Square getSquare(int x, int y) {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) {
            throw new IllegalArgumentException("square (" + x + ", " + y + ") is outside the map");
        }
        return squares[y][x];
    }

    public boolean isInOwnSafeZone(LivingBeing being) {
        return getSquare(being.getPosX(), being.getPosY()).getOwner() == being.getColor();
    }

    public List<Square> getSafeZoneSquares(Color color) {
        List<Square> zone = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (square.getOwner() == color) {
                    zone.add(square);
                }
            }
        }
        return zone;
    }

    public List<Square> getFreeSquaresOutsideSafeZones() {
        List<Square> freeSquares = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (square.isFree() && !square.isSafeZone()) {
                    freeSquares.add(square);
                }
            }
        }
        return freeSquares;
    }

    public int placeRandomRocks(int numberOfRocks) {
        if (numberOfRocks < 0) {
            throw new IllegalArgumentException("numberOfRocks (" + numberOfRocks + ") must not be negative");
        }
        List<Square> candidates = getFreeSquaresOutsideSafeZones();
        int rocksPlaced = Math.min(numberOfRocks, candidates.size());
        for (int i = 0; i < rocksPlaced; i++) {
            int index = RandomGenerator.nextInt(0, candidates.size() - 1);
            candidates.remove(index).placeRock();
        }
        return rocksPlaced;
    }

    public void moveCharacter(LivingBeing character,int newPosX,int newPosY){
        Square target = getSquare(newPosX, newPosY);
        if (target.getOccupedBy() != character && !target.isFree()) {
            throw new IllegalStateException("square (" + newPosX + ", " + newPosY + ") is not free");
        }

        Square origin = getSquare(character.getPosX(), character.getPosY());
        if (origin.getOccupedBy() == character) {
            origin.setOccupedBy(null);
        }
        target.setOccupedBy(character);
        character.setPosition(newPosX, newPosY);
    }
       
        
}
