/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;


public class ConsoleDisplay {
    
    private int delayMs;
    private Game game;

    public ConsoleDisplay(Game game,int delayMs){
        this.game = game;
        this.delayMs=delayMs;
    }
    
    public void displayTurn(int turn,Map map){
        System.out.println("Turn n°"+turn);
        map.display();
    }
}
