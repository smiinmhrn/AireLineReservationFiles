public class User implements CanCustomize{
    private final String USERNAME;
    private String password;
    private String charge;
    public User(String USERNAME, String password, String charge) {
        this.USERNAME = USERNAME;
        this.password = password;
        this.charge = charge;
    }
    // for customizing string for writing in file
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
