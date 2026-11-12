package game;

import game.beings.Elf;
import game.beings.Goblin;
import game.beings.Human;
import game.beings.LivingBeing;
import game.beings.Orch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Game {

    private ArrayList<LivingBeing> characters;
    private Set<String> messages;
    private Map map;

    public Game() {
        this(16, 10, 5);
    }

    public Game(int sizeX, int sizeY, int nbCharactersByTeam) {
        Map carte = new Map(sizeX, sizeY);
        this.map = carte;
        
        
        characters = new ArrayList<>(); 
        for (int i = 0; i < nbCharactersByTeam; i++) {
            Elf elf =new Elf((int) Math.ceil(i/3),sizeX-2+(i%2));
            characters.add(elf);
            map.moveCharacter(elf, elf.getPosX(), elf.getPosY());
            Goblin goblin = new Goblin((int)Math.ceil(i/3),1+(i%2));
            characters.add(goblin);
            map.moveCharacter(goblin, goblin.getPosX(), goblin.getPosY());
            Human human = new Human(sizeY-2+(i%2),sizeX-2+(i%2));
            characters.add(human);
            map.moveCharacter(human, human.getPosX(), human.getPosY());
            Orch orch = new Orch(sizeY-2,(int)Math.ceil(i+1/3));
            characters.add(orch);
            map.moveCharacter(orch, orch.getPosX(), orch.getPosY());
        }
        
        messages = new HashSet<>();

        messages.addAll(List.of(
                "The autumn wind howls. The Bard won't stop playing minor chords.",
                "The sobs of autumn violins are actually a banshee. Roll initiative.",
                "My heart is wounded with monotonous languor. Apply 2d4 healing potion.",
                "The autumn leaves are falling. The Paladin failed his DEX save.",
                "The tea is cold, the dungeon is dark, and the Rogue stole the spoon.",
                "The owl hoots twice. The Goblin thinks it's a mating call.",
                "The dragon is asleep. Please stop breathing so loudly.",
                "The secret door is disguised as a normal door. It is just a door.",
                "The Wizard used Fireball indoors again. The carpet is ruined.",
                "The tavern keeper's apron is greasy. Repeat, do not order the stew.",
                "The mimic pretends to be a chest. Jokes on him, it's empty.",
                "The Necromancer is throwing a party. Bring your own shovel.",
                "The Ranger lost his bow in the leaves. Operation Metal Detector begins.",
                "The Cleric is out of spell slots. Prepare for dramatic weeping.",
                "The Barbarian tried to read the code. He broke the parchment.",
                "The potion of invisibility works. We lost the potion of invisibility.",
                "The autumn rain falls. The Kobold refuses to step in puddles.",
                "The secret handshake was forgotten. We are now using finger guns.",
                "The Druid turned into a pumpkin. Do not bake the Druid.",
                "The pigeon carrying the message was eaten by a Manticore.",
                "The Warlock’s patron is on hold. Please stay on the line.",
                "The trap door is loose. The Fighter is now in the cellar.",
                "The map is upside down. We have been invading a bakery.",
                "The Beholder needs eye drops. Requesting immediate assistance.",
                "The autumn leaves rustle. It’s just the Bard sneaking out of paying.",
                "The skeleton dropped his femur. He doesn’t have a leg to stand on.",
                "The cursed sword keeps humming 80s pop songs. Kill me now.",
                "The Rogue rolled a 1 on stealth. He is currently wearing tap shoes.",
                "The dungeon master is smiling. I repeat, the dungeon master is smiling.",
                "The gargoyle sneezed. The disguise is blown.",
                "The Gelatinous Cube smells like strawberries today. Do not eat it.",
                "The Monk has achieved inner peace. He won't clean his room.",
                "The sacred artifact is being used as a paperweight.",
                "The autumn fog rises. The Sorcerer melted his own boots.",
                "The goblin password is 'password'. We overthought this.",
                "The Paladin’s armor is too shiny. We are blinding ourselves.",
                "The invisible stalker bumped into a lamp. Send help.",
                "The tavern ran out of ale. The real apocalypse has begun.",
                "The loot consists of three copper coins and a damp sock.",
                "The raven delivered the message, but it demands cheese first."
        ));
        
        this.distributeMessages();
    }
    
    

    public void startGame() {
        int nbTurns = 0;
        Scanner sc = new Scanner(System.in);
        
        while(nbTurns<50){
            System.out.println("Press 'Enter' for next turn");
            sc.nextLine();
            System.out.println("Turn n°"+nbTurns);
            this.newTurn();
            this.map.display();
            nbTurns++;
        }
    }
    
    
    private void distributeMessages(){
        Iterator<String> messagesIterator = messages.iterator();
        int i =0;
        while(messagesIterator.hasNext()){
            characters.get(i %characters.size()).addMessage(messagesIterator.next());
            i++;
        }
    }
    
    private void newTurn(){
        
        for(LivingBeing character: characters){
            character.move();
        }
    }
}
