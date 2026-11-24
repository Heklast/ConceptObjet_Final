/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import game.beings.Elf;
import game.beings.Goblin;
import game.beings.Human;
import game.beings.LivingBeing;
import game.beings.Orch;


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
        for (String e : LivingBeing.drainEvents()) System.out.println("  "+e);
        System.out.println("Elves: "+Elf.getCount()+" | Humans: "+Human.getCount()
                +" | Goblins: "+Goblin.getCount()+" | Orcs: "+Orch.getCount());
        System.out.println("Beings: "+LivingBeing.getCount()
                +" | exhausted: "+LivingBeing.getExhaustedCount());
    }
}
