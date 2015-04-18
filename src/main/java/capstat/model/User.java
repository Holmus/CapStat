package capstat.model;

public class User {

    private String name;
    private String nickname;
    private String hashedPassword;
    private ChalmersAge chalmersAge;
    private ELORanking ranking;

    public User(String nickname, String name, String hashedPassword, ChalmersAge chalmersAge, ELORanking ranking) {
        this.nickname = nickname;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.chalmersAge = new ChalmersAge(chalmersAge);
        this.ranking = new ELORanking(ranking);
    }

    public User(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.hashedPassword = user.getHashedPassword();
        this.chalmersAge = user.getChalmersAge();
        this.ranking = user.getRanking();
    }

    public String getName() {
        return this.name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public ChalmersAge getChalmersAge() {
        return new ChalmersAge(this.chalmerAge);
    }

    public ELORanking getRanking() {
        return new ELORanking(this.ranking);
    }

}
