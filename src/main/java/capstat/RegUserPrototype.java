package capstat;

import java.util.List;
import java.util.Scanner;

import capstat.model.*;

public class RegUserPrototype {

    public static void main(String[] args) {
        CapStat cs = CapStat.getInstance();

        Scanner sc = new Scanner(System.in);
        String cmd;
        System.out.print("> ");
        while (sc.hasNextLine()) {
            cmd = sc.nextLine();
            switch (cmd) {
                case "register":
                    System.out.print("Nickname: ");
                    String nickname = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    System.out.print("Birthday (YYYY-MM-DD): ");
                    String birthdayString = sc.nextLine();
                    if (!birthdayString.matches("\\d{4}-\\d{2}-\\d{2}")) System.exit(1);
                    String[] birthdaySplits = birthdayString.split("-");
                    int[] birthdayElements = new int[birthdaySplits.length];
                    for (int i = 0; i < birthdayElements.length; i++) {
                        birthdayElements[i] = Integer.parseInt(birthdaySplits[i]);
                    }
                    Birthday birthday = new Birthday(birthdayElements[0], birthdayElements[1], birthdayElements[2]);

                    System.out.print("Admittance (YYYY-P): ");
                    String admittanceString = sc.nextLine();
                    if (!admittanceString.matches("\\d{4}-\\d{1}")) System.exit(1);
                    String[] admittanceSplits = admittanceString.split("-");
                    int[] admittanceElements = new int[admittanceSplits.length];
                    for (int i = 0; i < admittanceElements.length; i++) {
                        admittanceElements[i] = Integer.parseInt(admittanceSplits[i]);
                    }
                    Admittance admittance = new Admittance(admittanceElements[0], admittanceElements[1]);

                    cs.registerUser(nickname, name, password, birthday, admittance);
                    break;
                case "list":
                    cs.printUsers();
                    break;
                case "quit":
                    System.exit(0);
                    break;
            }
            System.out.print("> ");
        }
    }

}
