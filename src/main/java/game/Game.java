package game;

import game.beings.Color;
import game.beings.Elf;
import game.beings.Goblin;
import game.beings.Human;
import game.beings.LivingBeing;
import game.beings.Orch;
import game.beings.Roles;
import game.beings.masters.Master;
import game.beings.masters.MasterElf;
import game.beings.masters.MasterGoblin;
import game.beings.masters.MasterHuman;
import game.beings.masters.MasterOrch;
import game.events.EventFactory;
import game.events.RandomEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {

    public static final int MAX_TURNS = 50;
    public static final int NUMBER_OF_OBSTACLES = 10;
    private static final int EARLIEST_EVENT_TURN = 10;
    private static final int LATEST_EVENT_TURN = 40;
    private static final int DELAY_BETWEEN_TURNS_MS = 1000;

    private final EnumMap<Color, Master> masters = new EnumMap<>(Color.class);
    private final EnumMap<Color, Deque<String>> undealtMessages = new EnumMap<>(Color.class);
    
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
        
        registerMaster(MasterElf.getInstance());
        registerMaster(MasterGoblin.getInstance());
        registerMaster(MasterHuman.getInstance());
        registerMaster(MasterOrch.getInstance());

        map.placeRandomRocks(NUMBER_OF_OBSTACLES);

        messages = new LinkedHashSet<>();

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
        int turnsPlayed = playTurns(true);
        display.displayEnd(turnsPlayed);
        printResult();
    }

    // Batch checks use the same turns and events as the animated console game.
    int playTurns(boolean showTurns) {
        int turnsPlayed = 0;

        for (int turn = 0; turn < MAX_TURNS; turn++) {
            boolean complete = newTurn();
            turnsPlayed++;

            if (!complete && turn == eventTurn) {
                RandomEvent event = EventFactory.createRandomEvent();
                event.apply(map, characters);
                if (showTurns) display.displayEvent(turn, event);
            }

            if (showTurns) {
                display.displayTurn(turn, map);
                for (Master master : masters.values()) {
                    System.out.printf("%s Master: %d/%d%n",
                            master.getColor(), master.getScore(), messages.size());
                }
            } else {
                LivingBeing.drainEvents();
            }

            if (complete) break;
            if (showTurns && turnsPlayed < MAX_TURNS) display.pause();
        }

        return turnsPlayed;
    }
    
    private void printResult() {
        int best = masters.values().stream()
                .mapToInt(Master::getScore)
                .max()
                .orElseThrow();

        List<String> leaders = new ArrayList<>();
        for (Master master : masters.values()) {
            if (master.getScore() == best) {
                leaders.add(master.getColor().toString());
            }
        }

        String label = leaders.size() == 1 ? "Winner: " : "Draw: ";
        System.out.println(label + String.join(", ", leaders)
                + " (" + best + "/" + messages.size() + ")");
    }

    public List<LivingBeing> getCharacters() {
        return List.copyOf(characters);
    }

    public GameMap getMap() {
        return map;
    }

    private List<Square> getTeamZone(Color color, int nbCharactersByTeam) {
        List<Square> zone = map.getSafeZoneSquares(color);
        int maxTroopers = zone.size() - 1;

        if (nbCharactersByTeam < 1 || nbCharactersByTeam > maxTroopers) {
            throw new IllegalArgumentException(
                "Troopers per team must be between 1 and " + maxTroopers
            );
        }

        RandomGenerator.shuffle(zone);
        return zone;
    }
    
    private <T extends LivingBeing & Master> void registerMaster(T master) {
        map.placeInOwnSafeZone(master);
        master.clearMessages();
        masters.put(master.getColor(), master);
    }

    private void addCharacter(LivingBeing character, Roles role) {
        character.setRole(role);
        character.setMap(map);
        characters.add(character);
        map.moveCharacter(character, character.getPosX(), character.getPosY());
    }
    
    
    private void distributeMessages() {
        List<String> deck = new ArrayList<>(messages);
        RandomGenerator.shuffle(deck);
        List<Master> teams = new ArrayList<>(masters.values());
        RandomGenerator.shuffle(teams);
        for (int i = 0; i < teams.size(); i++) {
            Master master = teams.get(i);
            Color color = master.getColor();
            List<LivingBeing> team = characters.stream()
                    .filter(trooper -> trooper.getColor() == color)
                    .collect(Collectors.toList());
            for (LivingBeing trooper : team) trooper.setTeamMaster(master);

            // Overlapping hands keep several copies in circulation. Each team must still
            // learn the missing messages from encounters and deliver all 40 to its Master.
            int count = Config.STARTING_TEAM_MESSAGES;
            // Goblins move only one tile; Humans cannot take diagonal shortcuts.
            if (color == Color.GREEN || color == Color.BLUE) {
                count += Config.SHORT_RANGE_EXTRA_MESSAGES;
            }
            int start = i * deck.size() / teams.size();
            Deque<String> hand = new ArrayDeque<>();
            for (int j = 0; j < Math.min(count, deck.size()); j++) {
                hand.addLast(deck.get((start + j) % deck.size()));
            }
            // Deal in rounds, leaving one slot free for an encounter. Smaller rosters
            // can pick up the rest of their original hand on later visits home.
            int maxCapacity = team.stream().mapToInt(LivingBeing::getMessageCapacity).max().getAsInt();
            for (int slot = 1; slot < maxCapacity && !hand.isEmpty(); slot++) {
                for (LivingBeing trooper : team) {
                    if (hand.isEmpty()) break;
                    if (slot < trooper.getMessageCapacity()) trooper.addMessage(hand.removeFirst());
                }
            }
            if (!hand.isEmpty()) undealtMessages.put(color, hand);
        }
    }
    
    private boolean newTurn() {
        RandomGenerator.shuffle(characters);

        for (LivingBeing character : characters) {
            if (character.isDead()) continue;

            character.move();

            if (!character.isDead() && map.isInOwnSafeZone(character)) {
                Master master = masters.get(character.getColor());
                master.receiveMessages(character);

                if (master.getScore() == messages.size()) {
                    return true;
                }
                collectUndealtMessages(character);
            }
        }
        return false;
    }

    private void collectUndealtMessages(LivingBeing trooper) {
        Deque<String> reserve = undealtMessages.get(trooper.getColor());
        if (reserve == null) return;
        // The Master has just archived this hand. It is safe to carry the next one.
        trooper.clearMessages();
        while (!reserve.isEmpty() && trooper.getNumberOfMessages() < trooper.getMessageCapacity()) {
            trooper.addMessage(reserve.removeFirst());
        }
        if (reserve.isEmpty()) undealtMessages.remove(trooper.getColor());
    }

}
