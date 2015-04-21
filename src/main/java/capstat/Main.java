package capstat;

import capstat.model.Match;
import capstat.model.RankedMatch;
import capstat.model.User;
import capstat.utils.GameFactory;
import java.util.Scanner;

/**
 * @author Holmus
 */

public class Main{

    static GameFactory factory = new GameFactory();
    static Match testMatch = factory.createDefaultMatch();
    static Scanner sc = new Scanner(System.in);
    static User player1 = new User();
    static User player2 = new User();
    public static void main(String [] args){

        String s;
        testMatch.startMatch();
        testMatch.setPlayer1(player1);
        testMatch.setPlayer2(player2);
        do {
            s = sc.nextLine();
        } while(!s.equals("q")){
            User currPlayer = testMatch.getPlayerNextUp();
            switch(s){
                case "h": TurnPrinter(true, currPlayer);
                          break;
                case "m": TurnPrinter(false, currPlayer);
                          break;
            }
            s = sc.nextLine();
        }
        System.exit(0);
    }
    //TODO: glassState
    public String TurnPrinter(boolean hit, User currPlayer){
        char star = '*';
        updateState(hit, currPlayer);
        if(currPlayer.equals(player1)){
            System.out.println(star + glassState());
        }
        if(currPlayer.equals(player2)){
            System.out.println(glassState() + star);
        }

    }
    public void updateState(boolean hit, User currPlayer){
        if(hit == true){
            testMatch.recordHit();
            System.out.println(currPlayer.getName() + ": hit!");
        } else if(hit == false){
            testMatch.recordMiss();
            System.out.println(currPlayer.getName() + ": miss!");
        }

    }
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