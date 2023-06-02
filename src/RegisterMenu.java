import appearance.Appearance;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
// this class has responsible for showing register menu => sign in and sign up
public class RegisterMenu {
    private Users users;
    Scanner input = new Scanner(System.in);
    public RegisterMenu(Users users) {
        this.users = users;
    }
    private void adminRegister() throws IOException {
        if (users.search(0,"samin",180) == -1){
            users.write(new User("samin", "samin228", "0"));
        }
    }

    // this function use for showing admin sign in menu and register admin
    public void adminSighIn() throws IOException {
        adminRegister();
        users.ifClose("user.dat");

        System.out.println(Appearance.BLUE + "[ ADMIN SIGN IN PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter your username :"  + Appearance.RESET_STYLE );
        String username = input.next();

        while (true) {
            if ((users.search(0, username,180) != -1) && (username.equals("samin"))) {
                break;
            } else {
                System.out.println(Appearance.RED + "Wrong username! Try again :" + Appearance.RESET_COLOR);
                username = input.next();
            }
        }

        System.out.println(Appearance.TEXT_ITALIC + "Enter your password :"  + Appearance.RESET_STYLE );
        String password = input.next();

        while (true){
            if (password.equals("samin228")){

                users.closeFile();

                var admin = new Admin(new Templates()
                        ,new Flights(new RandomAccessFile("flights.dat", "rw")),
                        new Tickets(new RandomAccessFile("tickets.dat", "rw")));
                admin.adminMenu();
                break;
            } else {
                System.out.println(Appearance.RED + "Wrong password! Try again :"+ Appearance.RESET_COLOR);
                password = input.next();
            }
        }
    }
    // this function use for showing passenger sign in menu and register passenger
    public void passengerSighIn() throws IOException {
        users.ifClose("user.dat");

        System.out.println(Appearance.BLUE + "[ PASSENGER SIGN IN PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter your username :");
        String username = input.next();

        while (true) {
            if (users.search(0, username, 180) == -1 || username.equals("samin")) {
                System.out.println(Appearance.RED +
                        "This username isn't exist. Try another one :" + Appearance.RESET_COLOR);
                username = input.next();
            } else break;
        }

        System.out.println("Enter your password :");
        String password = input.next();
        int position = users.search(0, username, 180);

        while (true){
            if (users.getSinglePartOfRecord(position).equals(password)){

                var passenger = new Passenger(username,
                        new Templates(),
                        new Users(new RandomAccessFile("user.dat", "rw")),
                        new Flights(new RandomAccessFile("flights.dat", "rw")),
                        new Tickets(new RandomAccessFile("tickets.dat", "rw")));

                passenger.passengerMenu();
                break;
            }
            else {
                System.out.println(Appearance.RED +
                        "Wrong password! Try again" + Appearance.RESET_COLOR + Appearance.RESET_STYLE);
                password = input.next();
            }
        }
    }
    // this function use for showing passenger sign up menu and register passenger
    public void passengerSignUp() throws IOException {
        users.ifClose("user.dat");

        System.out.println(Appearance.BLUE + "[ SIGN UP PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter your username :");
        String username = input.next();

        while (true) {
            if (users.search(0, username, 180) != -1 || username.equals("samin")) {
                System.out.println(Appearance.RED +
                        "This username is already exist. Try another one :" + Appearance.RESET_COLOR);
                username = input.next();
            } else break;
        }

        System.out.println("Enter your password :" + Appearance.RESET_STYLE);
        String password = input.next();

        users.write(new User(username, password,"0"));

        var passenger = new Passenger(username,
                new Templates(),
                new Users(new RandomAccessFile("user.dat", "rw")),
                new Flights(new RandomAccessFile("flights.dat", "rw")),
                new Tickets(new RandomAccessFile("tickets.dat", "rw")));

        passenger.passengerMenu();
    }
}
