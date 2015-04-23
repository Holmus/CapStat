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
public class Main implements MatchOverObserver, DuelObserver {

    private static Match match = (new GameFactory()).createDefaultMatch();

    public static void main(String[] args) {
        match.addMatchOverObserver(new Main());
        match.addDuelObserver(new Main());
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

//    static GameFactory factory = new GameFactory();
//    static Match testMatch = factory.createDefaultMatch();
//    static Scanner sc = new Scanner(System.in);
//
//    //ToDo: How to fetch users
//    static User player1 = new User("Holmus", "Jakob Holmgren", "foo", new
//            ChalmersAge(new Birthday(2015,04,22), new Admittance(2014, 1)),
//            new ELORanking(1500));
//    static User player2 = new User("Saser", "Christian Persson", "bar", new
//            ChalmersAge(new Birthday(2015,04,22), new Admittance(2014, 1)),
//            new ELORanking(1500));
//    public static void main(String [] args) {
//
//        System.out.println(testMatch);
//
//        String s;
//        testMatch.startMatch();
//        testMatch.setPlayer1(player1);
//        testMatch.setPlayer2(player2);
//        s = sc.nextLine();
//        while (!s.equals("q")) {
//            User currPlayer = testMatch.getPlayerWhoseTurnItIs();
//            switch (s) {
//                case "h":
//                    //ToDo: Duel functionality
//                    TurnPrinter(true, currPlayer);
//                    break;
//                case "m":
//                    TurnPrinter(false, currPlayer);
//                    break;
//            }
//            s = sc.nextLine();
//        }
//        System.exit(0);
//    }
//    //Method which Prints the outcome of the past turn, also showing who's turn it is
//    public static void TurnPrinter(boolean hit, User currPlayer){
//        char star = '*';
//        updateState(hit, currPlayer);
//        if(currPlayer.equals(player1)){
//            System.out.println(star + " P1: " + glassState() + " :P2");
//        }
//        if(currPlayer.equals(player2)){
//            System.out.println("P1: " + glassState() + " :P2 " + star);
//        }
//
//    }
//    //Method to update which glasses are currently active based on outcome of last throw
//    public static void updateState(boolean hit, User currPlayer){
//        if(hit == true){
//            testMatch.recordHit();
//            System.out.println(currPlayer.getName() + ": hit!");
//        } else if(hit == false){
//            testMatch.recordMiss();
//            System.out.println(currPlayer.getName() + ": miss!");
//        }
//
//    }
//    //method to organize active glasses as a String f.e "x o o o o o o"
//    public static String glassState(){
//        Match.Glass[] glasses = testMatch.getGlasses();
//        String returnString = "";
//        for(int i=0; i<glasses.length; i++){
//            if(glasses[i].isActive()){
//                returnString = returnString + "o";
//            } else if(glasses[i].isActive()){
//                returnString = returnString + "x";
//            }
//        }
//        return returnString;
//    }
}