package capstat;

import capstat.model.*;
import capstat.utils.GameFactory;
import java.util.Scanner;

/** ToDo: Add functionality for when game is over
 *  ToDo: Add functionality to determine when a duel is won
 *  ToDo: Test current functionality
 *  ToDo: How to fetch users

 */

/**
 * @author Holmus
 */

/**
 * Rough Prototype-class to demonstrate how recording a match would work out if done by terminal
 */

//Method to start the match, register result and quit
public class PlaymatchPrototype implements MatchOverObserver, DuelObserver {

    private static Match match = (new GameFactory()).createDefaultMatch();

    public static void main(String[] args) {
        match.addMatchOverObserver(new PlaymatchPrototype());
        match.addDuelObserver(new PlaymatchPrototype());
        match.setPlayer1(new User("Holmus", "", "", new ChalmersAge(new
                Birthday(1234, 56, 78), new Admittance(1111, 1)),
                new ELORanking(13124)));
        match.setPlayer2(new User("Saser", "", "", new ChalmersAge(new
                Birthday(1234, 56, 78), new Admittance(1111, 1)), new
                ELORanking(1222)));
        System.out.println(match);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String next = sc.next().toLowerCase();
            if (next.equals("q")) {
                System.out.println("Quitting…");
                System.exit(0);
            }
            if (next.equals("h")) {
                match.recordHit();
            } else if (next.equals("m")) {
                match.recordMiss();
            } else {
                System.out.println("H: Hit \nM: Miss\nQ: Quit");
            }
            System.out.println(match);
        }
    }

    @Override
    public void matchOver() {
        try {
            System.out.println(match);
            System.out.println("Winner is " + match.getWinner().getNickname() + "!");
            System.exit(0);
        } catch (Match.MatchNotOverException e) {
            System.out.println("Error! Match should be over, but can't get a " +
                    "winner from the match");
            e.printStackTrace();
        }
    }

    @Override
    public void duelStarted() {
        System.out.println("Duel!");
    }

    @Override
    public void duelEnded() {
        System.out.println("Duel ended, " + match.getPlayerWhoseTurnItIs()
                .getNickname() + " lost :_(");
    }
}