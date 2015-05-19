package capstat.infrastructure;

/**
 * Created by jibbs on 19/05/15.
 */
public class MatchBlueprint {
	public final int id;
	public final String p1, p2;
	public final int p1Score, p2Score;
	public final String spectator;
	public final int year, month, day, hour, minute, second;

	public MatchBlueprint(int id,
	                      String p1,
	                      String p2,
	                      int p1Score,
	                      int p2Score,
	                      String spectator,
	                      int year,
	                      int month,
	                      int day,
	                      int hour,
	                      int minute,
	                      int second ) {

		this.id = id;
		this.p1 = p1;
		this.p2 = p2;
		this.p1Score = p1Score;
		this.p2Score = p2Score;
		this.spectator = spectator;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
}
