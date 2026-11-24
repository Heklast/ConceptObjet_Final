package game;

import game.beings.Color;
import game.beings.Elf;
import game.beings.Goblin;
import game.beings.Human;
import game.beings.LivingBeing;
import game.beings.Orch;
import game.beings.Roles;
import game.events.EventFactory;
import game.events.RandomEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game {

    public static final int MAX_TURNS = 50;
    public static final int NUMBER_OF_OBSTACLES = 10;
    private static final int EARLIEST_EVENT_TURN = 10;
    private static final int LATEST_EVENT_TURN = 40;
    private static final int DELAY_BETWEEN_TURNS_MS = 1000;

    private ArrayList<LivingBeing> characters;
    private Set<String> messages;
    private GameMap map;
    private ConsoleDisplay display;
    private final int eventTurn;

    public Game() {
        this(16, 10, 5);
    }

    public Game(int sizeX, int sizeY, int nbCharactersByTeam) {
        GameMap carte = new GameMap(sizeX, sizeY);
        this.map = carte;
        
        this.display = new ConsoleDisplay(this,DELAY_BETWEEN_TURNS_MS);

        this.eventTurn = RandomGenerator.nextInt(EARLIEST_EVENT_TURN, LATEST_EVENT_TURN);

        characters = new ArrayList<>();
        List<Square> elfZone = getTeamZone(Color.PURPLE, nbCharactersByTeam);
        List<Square> goblinZone = getTeamZone(Color.GREEN, nbCharactersByTeam);
        List<Square> humanZone = getTeamZone(Color.BLUE, nbCharactersByTeam);
        List<Square> orchZone = getTeamZone(Color.ORANGE, nbCharactersByTeam);
        for (int i = 0; i < nbCharactersByTeam; i++) {
            // skills come from the role (W,R,Wz,W,R...)
            Roles role = Roles.values()[i % 3];
            addCharacter(new Elf(elfZone.get(i).getY(), elfZone.get(i).getX()), role);
            addCharacter(new Goblin(goblinZone.get(i).getY(), goblinZone.get(i).getX()), role);
            addCharacter(new Human(humanZone.get(i).getY(), humanZone.get(i).getX()), role);
            addCharacter(new Orch(orchZone.get(i).getY(), orchZone.get(i).getX()), role);
        }

        map.placeRandomRocks(NUMBER_OF_OBSTACLES);

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
        for (int turn = 0; turn < MAX_TURNS; turn++) {
            newTurn();
            if (turn == eventTurn) {
                triggerRandomEvent(turn);
            }
            display.displayTurn(turn, map);
            display.pause();
        }
        display.displayEnd(MAX_TURNS);
    }

    public List<LivingBeing> getCharacters() {
        return List.copyOf(characters);
    }

    public GameMap getMap() {
        return map;
    }

    private List<Square> getTeamZone(Color color, int nbCharactersByTeam) {
        List<Square> zone = map.getSafeZoneSquares(color);
        if (nbCharactersByTeam < 0 || nbCharactersByTeam > zone.size()) {
            throw new IllegalArgumentException("nbCharactersByTeam (" + nbCharactersByTeam + ") must be between 0 and " + zone.size());
        }
        return zone;
    }

    private void addCharacter(LivingBeing character, Roles role) {
        character.setRole(role);
        character.setMap(map);
        characters.add(character);
        map.moveCharacter(character, character.getPosX(), character.getPosY());
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
        RandomGenerator.shuffle(characters);
        for(LivingBeing character: characters){
            if (!character.isDead()) {
                character.move();
            }
        }
    }

    private void triggerRandomEvent(int turn) {
        RandomEvent event = EventFactory.createRandomEvent();
        event.apply(map, characters);
        display.displayEvent(turn, event);
    }
}
