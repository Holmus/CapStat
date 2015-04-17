package capstat.model;

public class User {

    private String name;
    private String nickname;
    private String hashedPassword;
    private ChalmersAge age;
    private ELORanking ranking;

    public User(String nickname, String name, String hashedPassword, ChalmersAge age, ELORanking ranking) {
        this.nickname = nickname;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.age = new ChalmersAge(age);
        this.ranking = new ELORanking(ranking);
    }

    public User(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.hashedPassword = user.getHashedPassword();
        this.age = user.getAge();
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

    public ChalmersAge getAge() {
        return new ChalmersAge(this.age);
    }

    public ELORanking getRanking() {
        return new ELORanking(this.ranking);
    }

}
