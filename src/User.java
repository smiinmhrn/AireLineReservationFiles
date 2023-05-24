public class User implements CanCustomize{
    private final String USERNAME; // 0-29
    private String password; // 30-60
    private String charge; // 60-90
    public User(String USERNAME, String password, String charge) {
        this.USERNAME = USERNAME;
        this.password = password;
        this.charge = charge;
    }
    @Override
    public String customize(String str){
        while (str.length() < 30)
            str += " ";
        return str.substring(0,30);
    }
    @Override
    public String toString() {
        return customize(USERNAME) + customize(password) + customize(charge);
    }
}
