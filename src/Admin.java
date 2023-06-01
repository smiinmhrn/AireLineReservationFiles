import appearance.Appearance;

import java.io.IOException;
import java.util.Scanner;
// this class is handling admin menu and all admins options in admin menu
public class Admin {
    Scanner input = new Scanner(System.in);
    private final Templates TEMPLATE;
    private final Flights FLIGHTS;
    private final Tickets TICKETS;
    public Admin(Templates TEMPLATE, Flights flights, Tickets tickets) {
        this.TEMPLATE = TEMPLATE;
        this.FLIGHTS = flights;
        this.TICKETS = tickets;
    }
    // this function is for showing admin menu
    public void adminMenu() throws IOException {
        System.out.println(Appearance.BLUE + "[ ADMIN MENU OPTIONS ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + """
            1. Add
            2. Update
            3. Remove
            4. Flight schedules
            0. Sign out
            Choose :\s""" +  Appearance.RESET_STYLE);

        String choice = input.next();
        label :
        while (true) {
            switch (TEMPLATE.availableInput(choice)) {
                case "1" :
                    add();
                    break label;
                case "2" :
                    update();
                    break label;
                case "3" :
                    remove();
                    break label;
                case "4" :
                    showingFlightSchedules();
                    break label;
                case "0" :
                    FLIGHTS.closeFile();
                    TICKETS.closeFile();

                    var mainMenu = new MainMenu(new Templates());
                    mainMenu.mainMenu();
                    break label;
                default :
                    System.out.println(Appearance.RED + "Wrong command! Try again :" + Appearance.RESET_COLOR);
                    choice = input.next();
            }
        }
    }
    // this function is for adding new airline
    private void add() throws IOException {
        FLIGHTS.ifClose("flights.dat");

        System.out.println(Appearance.BLUE + "[ ADDING PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter Flight id :");
        String flightId = input.next().toUpperCase();

        while (true) {
            if (FLIGHTS.search(0,flightId,420) != -1) {
                System.out.println(Appearance.RED +
                        "This flight id is existed! Try again with another flight id :" + Appearance.RESET_COLOR);
                flightId = input.next().toUpperCase();
            } else break;
        }

        System.out.println("Enter Origin :");
        String origin = TEMPLATE.templateStringStyle(input.next());

        System.out.println("Enter Destination :");
        String destination = TEMPLATE.templateStringStyle(input.next());

        while (true) {
            if (origin.equals(destination)) {
                System.out.println(Appearance.RED +
                        "Origin and Destination cant be the same! Try again with another destination :"
                        + Appearance.RESET_COLOR);
                destination = TEMPLATE.templateStringStyle(input.next());
            } else break;
        }

        System.out.println("Date :");
        String date = TEMPLATE.dateTemplate();

        System.out.println("Time :");
        String time = TEMPLATE.timeTemplate();

        System.out.println("Enter Price :");
        String price = TEMPLATE.availableInput(input.next());

        System.out.println("Enter Seats :" + Appearance.RESET_STYLE);
        String seats = TEMPLATE.availableInput(input.next());

        FLIGHTS.write(new Flight(flightId, origin, destination, date, time, price, seats));
        System.out.println(Appearance.GREEN + "Adding new airline successfully !" + Appearance.RESET_COLOR);

        if (TEMPLATE.backToMenu("Admin", "Adding").equals("1")) adminMenu();
        else add();
    }
    // this function is for removing single airline
    private void remove() throws IOException {
        FLIGHTS.ifClose("flights.dat");
        TICKETS.ifClose("tickets.dat");

        System.out.println(Appearance.BLUE + "[ REMOVE PANEL ]" + Appearance.RESET_COLOR);
        FLIGHTS.printAllAirline();

        System.out.println(Appearance.TEXT_ITALIC +
                "Type the Flight Id that you want to remove :" + Appearance.RESET_STYLE);
        String flightId = existsFlightId(input.next().toUpperCase());

        int process = 1;
        if (TICKETS.search(60, flightId, 180) != -1){
            System.out.println(Appearance.GREEN +
                    "This airline have been booked and you can not remove it !" + Appearance.RESET_COLOR);
            process = 0;
        }
        if (process != 0){
            FLIGHTS.remove(FLIGHTS.search(0,flightId,420),420,"flights.dat");
            System.out.println(Appearance.GREEN + "Removing airline successfully !" + Appearance.RESET_COLOR);
        }

        if (TEMPLATE.backToMenu("Admin", "Removing").equals("1")) adminMenu();
        else remove();
    }
    /**
     * @param id => this function is for checking if the id is exists in airline or not
     * @return => and return an available id
     */
    private String existsFlightId(String id) throws IOException {
        FLIGHTS.ifClose("flights.dat");
        while (true) {
            if (FLIGHTS.search(0,id,420) == -1) {
                System.out.println(Appearance.RED +
                        "This Flight id dos not exist! Try again :" + Appearance.RESET_COLOR);
                id = input.next().toUpperCase();
            } else break;
        } return id;
    }
    // this function and next function are for updating an airline
    private void update() throws IOException {
        FLIGHTS.ifClose("flights.dat");
        TICKETS.ifClose("tickets.dat");

        System.out.println(Appearance.BLUE + "[ UPDATING PANEL ]" + Appearance.RESET_COLOR);
        FLIGHTS.printAllAirline();

        System.out.println(Appearance.TEXT_ITALIC + "Type the Flight id you want to update :" + Appearance.RESET_STYLE);

        String flight = existsFlightId(input.next().toUpperCase());

        int process = 1;
        if (TICKETS.search(60, flight, 180) != -1){
            System.out.println(Appearance.GREEN +
                    "This airline have been booked and you can not update it !" + Appearance.RESET_COLOR);
            process = 0;
        }
        if (process != 0){
            updateInProgress((FLIGHTS.search(0,flight,420) - 60));
            System.out.println(Appearance.GREEN + "updating airline successfully !" + Appearance.RESET_COLOR);
        }

        if (TEMPLATE.backToMenu("Admin", "Updating").equals("1")) adminMenu();
        else {
            FLIGHTS.randomAccessFile.seek(0);
            update();
        }
    }
    /**
     * @param result => in previous function admin search for a flight id which he/she wanted to update and search
     *               for this id and save the index of it . this function use this index to show that single airline
     *               and update that special airline
     */
    private void updateInProgress(int result) throws IOException {
        FLIGHTS.ifClose("flights.dat");

        FLIGHTS.randomAccessFile.seek(result);
        FLIGHTS.printSingleAirline();

        System.out.println(Appearance.TEXT_ITALIC + """
            Which statement you want to change :
            1. FlightId   2. Origin   3. Distinction   4. Date   5. Time   6. Price   7. Seats\s""" );

        String choice = input.next();
        label :
        while (true) {
            switch (TEMPLATE.availableInput(choice)) {

                case "1" :
                    System.out.println("Enter your new Flight Id :");
                    String id = input.next().toUpperCase();

                    while (true) {
                        if (FLIGHTS.search(0,id,420) != -1) {
                            System.out.println(Appearance.RED +
                                    "This Flight id dos exist! Try again :" + Appearance.RESET_COLOR);
                            id = input.next().toUpperCase();
                        } else break;
                    }
                    FLIGHTS.update(result,id);
                    break label;

                case "2" :
                    System.out.println("Enter your new Origin :");
                    String origin = TEMPLATE.templateStringStyle(input.next());
                    FLIGHTS.update((result + 60),origin);

                    break label;

                case "3" :
                    System.out.println("Enter your new Destination :");
                    String destination = TEMPLATE.templateStringStyle(input.next());
                    FLIGHTS.update((result + 120),destination);

                    break label;

                case "4" :
                    FLIGHTS.update((result + 180),TEMPLATE.dateTemplate());
                    break label;

                case "5" :
                    FLIGHTS.update((result + 240),TEMPLATE.timeTemplate());
                    break label;

                case "6" :
                    System.out.println("Enter your new Price :");
                    FLIGHTS.update((result + 300),TEMPLATE.availableInput(input.next()));
                    break label;

                case "7" :
                    System.out.println("Enter your new Seats :");
                    FLIGHTS.update((result + 360),TEMPLATE.availableInput(input.next()));
                    break label;

                default :
                    System.out.println(Appearance.RED + "Wrong command! Try again :" + Appearance.RESET_COLOR);
                    choice = input.next();
            }
        }

        System.out.println(Appearance.GREEN + "Updating airline successfully !" + Appearance.RESET_COLOR);
        System.out.println("""
            1. Keep updating this airline
            2. Skip\s""" );

        String secondChoice = input.next();
        label :
        while (true) {
            switch (TEMPLATE.availableInput(secondChoice)) {
                case "1":
                    updateInProgress(result);
                    break label ;
                case "2" :
                    break label;
                default :
                    System.out.println(Appearance.RED +
                            "Wrong command! Try again :"+ Appearance.RESET_COLOR + Appearance.RESET_STYLE);
                    secondChoice = input.next();
            }
        }
    }
    // this function is for showing all airlines
    private void showingFlightSchedules() throws IOException {
        FLIGHTS.ifClose("flights.dat");

        System.out.println(Appearance.BLUE + " [ AIRLINE SCHEDULES ] " + Appearance.RESET_COLOR);
        FLIGHTS.printAllAirline();

        if (TEMPLATE.backToMenu("Admin", "Showing airline list").equals("1")) adminMenu();
        else {
            FLIGHTS.randomAccessFile.seek(0);
            showingFlightSchedules();
        }
    }
}
