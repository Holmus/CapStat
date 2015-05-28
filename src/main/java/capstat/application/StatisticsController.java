package capstat.application;

import capstat.model.user.User;

/**
 * Created by Jakob on 21/05/15.
 */
public class StatisticsController {
    //ToDo: Call for actual values in corresponding Calculator-class.

    Double[] test = new Double[]{
      4.3, 3.5, 7.8, 6.3, 8.0
    };
    Double[] test2 = new Double[]{
            2.1, 0.3, 8.1, 4.2, 10.8, 9.23, 8.56
    };
    public Double[] getData(String dataType, User currentUser) {
        if(dataType.equals("Accuracy")) {
            return test;
        } else {
            return test2;
        }
    }
}
