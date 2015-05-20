package capstat.model;


/**
 * Created by jibbs on 20/05/15.
 */
public class MatchResultFactory {
	public static MatchResult createDummyMatchResult1(){

		int id = 0;
		String p1 = "DummyOne";
		String p2 = "DummyTwo";
		int p1Score = 3;
		int p2Score = 4;
		String spectator = "Guest";
		int year = 2015;
		int month = 05;
		int day = 19;
		int hour = 20;
		int minute = 53;
		int second = 32;

		return new MatchResult(id, p1, p2, p1Score, p2Score, spectator, year, month, day, hour, minute, second);


	}
}
