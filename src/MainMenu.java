import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import appearance.Appearance;
// this class responsible for showing main menu and SignInAs menu
public class MainMenu {
    Scanner input = new Scanner(System.in);
    private final Templates TEMPLATE;
    public MainMenu(Templates TEMPLATE) {
        this.TEMPLATE = TEMPLATE;
    }
    // this function use for showing main menu
    public void mainMenu() throws IOException {

        System.out.println(Appearance.BLUE + "[ WELCOME TO AIRLINE RESERVATION SYSTEM ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + """
            1. Sign in
            2. Sign up
            Choose : \s""" + Appearance.RESET_STYLE);

        String choice = input.next();
        label :
        while (true) {
            switch (TEMPLATE.availableInput(choice)) {
                case "1" :
                    signInAs();
                    break label;
                case "2" :
                    var registerMenu = new RegisterMenu
                            (new Users(new RandomAccessFile("user.dat", "rw")));
                    registerMenu.passengerSignUp();
                    break label;
                default:
                    System.out.println(Appearance.RED + "Wrong command! Try again :" + Appearance.RESET_COLOR);
                    choice = input.next();
            }
        }
    }
    // this function use for showing a menu which give you two option => sign in as admin or sign in as passenger
    private void signInAs() throws IOException {

        System.out.println(Appearance.BLUE + "[ SIGN IN PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + """
            1. Sign in as Admin
            2. Sign in as Passenger
            Choose :\s""" );

        String choice = input.next();
        var registerMenu = new RegisterMenu(
                new Users(new RandomAccessFile("user.dat", "rw")));
        label :
        while (true) {
            switch (TEMPLATE.availableInput(choice)) {
                case "1":
                    registerMenu.adminSighIn();
                    break label;
                case "2":
                    registerMenu.passengerSighIn();
                    break label;
                default:
                    System.out.println(Appearance.RED +
                            "Wrong command! Try again :" + Appearance.RESET_STYLE + Appearance.RESET_COLOR);
                    choice = input.next();
            }
        }
    }
}
