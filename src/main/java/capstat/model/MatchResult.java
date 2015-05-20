package capstat.model;

/**
 * Created by jibbs on 20/05/15.
 */
public class MatchResult {
	private int id;
	private String p1, p2;
	private int p1Score, p2Score;
	private String spectator;
	private int year, month, day, hour, minute, second;

	public MatchResult(int id,
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
	                            int second) {

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


	public int getId() {
		return this.id;
	}

	public String getP1() {
		return this.p1;
	}

	public String getP2() {
		return this.p2;
	}

	public int getP1Score() {
		return this.p1Score;
	}

	public int getP2Score() {
		return this.p2Score;
	}

	public String getSpectator() {
		return this.spectator;
	}

	public int getYear() {
		return this.year;
	}

	public int getMonth() {
		return this.month;
	}

	public int getDay() {
		return this.day;
	}

	public int getHour() {
		return this.hour;
	}

	public int getMinute() {
		return this.minute;
	}

	public int getSecond() {
		return this.second;
	}
}
