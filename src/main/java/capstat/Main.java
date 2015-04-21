package capstat;

import capstat.model.Match;
import capstat.model.RankedMatch;
import capstat.model.User;
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
public class Main{

    static GameFactory factory = new GameFactory();
    static Match testMatch = factory.createDefaultMatch();
    static Scanner sc = new Scanner(System.in);

    //ToDo: How to fetch users
    static User player1 = new User();
    static User player2 = new User();
    public static void main(String [] args) {

        String s;
        testMatch.startMatch();
        testMatch.setPlayer1(player1);
        testMatch.setPlayer2(player2);
        do {
            s = sc.nextLine();
        } while (!s.equals("q")) {
            User currPlayer = testMatch.getPlayerNextUp();
            switch (s) {
                case "h":
                    //ToDo: Duel functionality
                    TurnPrinter(true, currPlayer);
                    break;
                case "m":
                    TurnPrinter(false, currPlayer);
                    break;
            }
            s = sc.nextLine();
        }
        System.exit(0);
    }
    //Method which Prints the outcome of the past turn, also showing who's turn it is
    public String TurnPrinter(boolean hit, User currPlayer){
        char star = '*';
        updateState(hit, currPlayer);
        if(currPlayer.equals(player1)){
            System.out.println(star + " P1: " glassState() + " :P2");
        }
        if(currPlayer.equals(player2)){
            System.out.println("P1: " + glassState() + " :P2 " + star);
        }

    }
    //Method to update which glasses are currently active based on outcome of last throw
    public void updateState(boolean hit, User currPlayer){
        if(hit == true){
            testMatch.recordHit();
            System.out.println(currPlayer.getName() + ": hit!");
        } else if(hit == false){
            testMatch.recordMiss();
            System.out.println(currPlayer.getName() + ": miss!");
        }

    }
    //method to organize active glasses as a String f.e "x o o o o o o"
    public String glassState(){
        Match.Glass[] glasses = testMatch.getGlasses();
        String returnString = "";
        for(int i=0; i<glasses.length; i++){
            if(glasses[i].glassIsActive()){
                returnString = returnString + "o";
            } else if(glasses[i].glassIsActive()){
                returnString = returnString + "x";
            }
        }
        return returnString;
    }
}