package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.beings.Color;
import game.beings.Goblin;
import game.beings.LivingBeing;
import game.beings.masters.Master;
import game.beings.masters.MasterElf;
import game.beings.masters.MasterGoblin;
import game.beings.masters.MasterHuman;
import game.beings.masters.MasterOrch;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeamBalanceTest {
    private final Master[] masters = {
        MasterOrch.getInstance(), MasterElf.getInstance(),
        MasterHuman.getInstance(), MasterGoblin.getInstance()
    };

    @Test
    void everyDefaultMessageIsInPlayAndMustBeDelivered() {
        RandomGenerator.setSeed(1);
        Game game = new Game();
        Set<String> deck = new HashSet<>();
        EnumMap<Color, Set<String>> hands = new EnumMap<>(Color.class);
        for (LivingBeing trooper : game.getCharacters()) {
            assertTrue(trooper.getNumberOfMessages() < trooper.getMessageCapacity());
            Set<String> team = hands.computeIfAbsent(trooper.getColor(), color -> new HashSet<>());
            for (String message : trooper.getMessages()) {
                assertTrue(team.add(message), "Starting hands within a team must be distinct");
                deck.add(message);
            }
        }
        assertEquals(40, deck.size());
        for (Master master : masters) {
            assertEquals(0, master.getScore());
            int expected = master.getColor() == Color.GREEN || master.getColor() == Color.BLUE ? 30 : 29;
            assertEquals(expected, hands.get(master.getColor()).size());
        }
    }

    @Test
    void smallRostersCanCollectMoreThanOneLoadWithoutExceedingMemory() {
        RandomGenerator.setSeed(1);
        Game game = new Game(16, 10, 1);
        for (LivingBeing trooper : game.getCharacters()) {
            assertTrue(trooper.getNumberOfMessages() <= trooper.getMessageCapacity());
        }
        game.playTurns(false);
        assertTrue(Arrays.stream(masters).anyMatch(master -> master.getScore() > Config.MESSAGES_PER_MEMORY));
        for (LivingBeing trooper : game.getCharacters()) {
            assertTrue(trooper.getNumberOfMessages() <= trooper.getMessageCapacity());
        }
    }

    @Test
    void aFullTrooperKeepsUndeliveredMessagesWhenLearningANewOne() {
        GameMap map = new GameMap(16, 10);
        MasterGoblin master = MasterGoblin.getInstance();
        master.clearMessages();
        master.addMessage("archived");
        Goblin sender = new Goblin(4, 5);
        Goblin receiver = new Goblin(4, 6);
        sender.setMap(map);
        receiver.setMap(map);
        receiver.setTeamMaster(master);
        receiver.addMessage("archived");
        for (int i = 1; i < receiver.getMessageCapacity(); i++) receiver.addMessage("pending " + i);
        sender.addMessage("new");
        sender.encounter(receiver);
        assertTrue(receiver.knowsMessage("new"));
        assertFalse(receiver.knowsMessage("archived"));
        for (int i = 1; i < receiver.getMessageCapacity(); i++) assertTrue(receiver.knowsMessage("pending " + i));
        assertEquals(receiver.getMessageCapacity(), receiver.getNumberOfMessages());
        assertEquals(1, master.getScore());
    }

    @Test
    void compareThreeHundredSeededGames() {
        int games = 300;
        int[] wins = new int[masters.length];
        int draws = 0;
        int early = 0;
        int shortest = Game.MAX_TURNS;
        for (int seed = 0; seed < games; seed++) {
            RandomGenerator.setSeed(seed);
            int turns = new Game().playTurns(false);
            int best = Arrays.stream(masters).mapToInt(Master::getScore).max().getAsInt();
            if (turns < Game.MAX_TURNS) {
                assertEquals(40, best);
                early++;
                shortest = Math.min(shortest, turns);
            }
            int leaders = 0;
            int winner = 0;
            for (int i = 0; i < masters.length; i++) {
                if (masters[i].getScore() == best) {
                    leaders++;
                    winner = i;
                }
            }
            if (leaders == 1) wins[winner]++;
            else draws++;
        }
        System.out.printf("Seeds 0-299: Orcs %d, Elves %d, Humans %d, Goblins %d, draws %d.%n",
                wins[0], wins[1], wins[2], wins[3], draws);
        System.out.printf("Finished before turn %d: %d/%d. Earliest finish: turn %d.%n",
                Game.MAX_TURNS, early, games, shortest);
        assertTrue(early > 0, "A full 40-message win should occur before the turn limit");
        int difference = Arrays.stream(wins).max().getAsInt() - Arrays.stream(wins).min().getAsInt();
        assertTrue(difference <= games / 10, "Win rates in this seeded batch should be within ten percentage points");
    }
}
