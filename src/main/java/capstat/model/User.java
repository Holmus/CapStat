package capstat.model;

public class User {

    private String name;
    private String nickname;
    private String hash;
    private ChalmersAge age;
    private ELORanking ranking;

    public User(String name, String nickname, String hash, ChalmersAge age, ELORanking ranking) {
        this.name = name;
        this.nickname = nickname;
        this.hash = hash;
        this.age = new ChalmersAge(age);
        this.ranking = new ELORanking(ranking);
    }

    public User(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.hash = user.getHash();
        this.age = user.getAge();
        this.ranking = user.getRanking();
    }

    public String getName() {
        return this.name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getHash() {
        return this.hash;
    }

    public ChalmersAge getAge() {
        return new ChalmersAge(this.age);
    }

    public ELORanking getRanking() {
        return new ELORanking(this.ranking);
    }

}
