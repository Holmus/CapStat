package capstat.model.user;

/**
 * A User is the root of an aggregate containing among other things a ranking
 * and a Chalmers age.
 * @author Christian Persson
 */
public class User {

    private String name;
    private String nickname;
    private String hashedPassword;
    private ChalmersAge chalmersAge;
    private ELORanking ranking;

    /**
     * Creates a new User instance.
     * @param nickname the nickname of the User, uniquely identifying it in the database
     * @param name the name of the User
     * @param hashedPassword the SHA-256 hash of the Users password
     * @param chalmersAge the ChalmersAge of the User
     * @param ranking the ELORanking of the User
     */
    public User(String nickname, String name, String hashedPassword, ChalmersAge chalmersAge, ELORanking ranking) {
        this.nickname = nickname;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.chalmersAge = new ChalmersAge(chalmersAge);
        this.ranking = new ELORanking(ranking);
    }

    /**
     * Creates a new User instance, as a copy of the given User instance.
     * @param user the User instance to create a copy of
     */
    public User(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.hashedPassword = user.getHashedPassword();
        this.chalmersAge = user.getChalmersAge();
        this.ranking = user.getRanking();
    }

    /**
     * Returns the nickname of the User.
     * @return the nickname of the User
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Returns the name of the User.
     * @return the name of the User
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the SHA-256 hash of the Users password.
     * @return the SHA-256 hash of the Users password
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     * Returns the ChalmersAge of the User.
     * @return the ChalmersAge of the User
     */
    public ChalmersAge getChalmersAge() {
        return new ChalmersAge(this.chalmersAge);
    }

    /**
     * Returns the ELORanking of the User.
     * @return the ELORanking of the User
     */
    public ELORanking getRanking() {
        return new ELORanking(this.ranking);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!this.nickname.equals(user.getNickname())) return false;
        if (!this.name.equals(user.getName())) return false;
        if (!this.hashedPassword.equals(user.getHashedPassword())) return false;
        if (!this.chalmersAge.equals(user.getChalmersAge())) return false;
        if (!this.ranking.equals(user.getRanking())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + nickname.hashCode();
        result = 31 * result + hashedPassword.hashCode();
        result = 31 * result + chalmersAge.hashCode();
        result = 31 * result + ranking.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", chalmersAge=" + chalmersAge +
                ", ranking=" + ranking +
                '}';
    }

    public void setRanking(final double ranking) {
        this.ranking = new ELORanking(ranking);
    }
}
