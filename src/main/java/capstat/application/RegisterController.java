package capstat.application;

import capstat.model.user.UserLedger;

import java.time.LocalDate;
import java.time.Year;

/**
 * Created by Jakob on 20/05/15.
 *
 * Class to control Registration in the Model-layer.
 */
public class RegisterController {
    UserLedger ul = UserLedger.getInstance();

    /**
     * Method to register a new user in the Database using the model
     * @param username the users desired username
     * @param fullName the users full name
     * @param password the users password (hashed, once created)
     * @param birth the users birthday
     * @param year the year the user attended Uni
     * @param i the reading period the user attended uni
     */
    public void registerNewUser(String username, String fullName, String password, LocalDate birth, Year year, int i) {
        ul.registerNewUser(username, fullName, password, birth, year, i);
    }

}
