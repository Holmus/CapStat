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

    GameFactory factory = new GameFactory();
    Match test = factory.createDefaultMatch();
    Scanner sc = new Scanner(System.in);
    String s = sc.nextLine();

    public static void main(String [] args){


        test.startMatch();
        User player1 = test.setPlayer1();
        User player2 = test.setPlayer2();
        while(!s.equals("q")){
            User curr = test.getPlayerNextUp();
            switch(s){
                case h: updateState(true,curr);
                        TurnPrinter(true,curr);
                        break;
                case m: updateState(false, curr);
                        TurnPrinter(false, curr);
                        break;
            }

        }
        System.exit(0);
    }
    //TODO: glassState
    public String TurnPrinter(boolean hit, User currPlayer){
        char star = '*';

        if(currPlayer.equals(player 1)){
            System.out.println(star + glassState());
        }
        if(currPlayer.equals(player 2)){
            System.out.println(glassState() + star);
        }

    }
    public void updateState(boolean hit, User currPlayer){
        if(hit == true){
            test.recordHit();
            System.out.println(currPlayer.getName() + ": hit!");
        } else if(hit == false){
            test.recordMiss();
            System.out.println(currPlayer.getName() + ": miss!");
        }

    }
}