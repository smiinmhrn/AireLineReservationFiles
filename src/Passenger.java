import appearance.Appearance;
import java.io.IOException;
import java.util.Scanner;
// this class is handling passenger menu and all passenger options in passenger menu
public class Passenger {
    Scanner input = new Scanner(System.in);
    private final String USERNAME;
    private final Templates TEMPLATE;
    private Users users;
    private Flights flights;
    private Tickets tickets;
    public Passenger(String USERNAME, Templates TEMPLATE, Users users, Flights flights, Tickets tickets) {
        this.USERNAME = USERNAME;
        this.TEMPLATE = TEMPLATE;
        this.users = users;
        this.flights = flights;
        this.tickets = tickets;
    }

    // this function is for showing passenger menu
    public void passengerMenu() throws IOException {

        System.out.println(Appearance.BLUE + "[ PASSENGER MENU OPTIONS ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + """
            1. Change password
            2. Search flight ticket
            3. Booking ticket
            4. ticket cancellation
            5. Booked ticket
            6. Add Charge
            0. Sign out
            Choose :\s""" + Appearance.RESET_STYLE);

        String choice = input.next();
        label :
        while (true) {
            switch (TEMPLATE.availableInput(choice)) {
                case "1":
                    changePass();
                    break label;
                case "2":
                    search();
                    break label;
                case "3":
                    bookingTicket();
                    break label;
                case "4":
                    ticketCancellation();
                    break label;
                case "5":
                    bookedTickets(1);
                    break label;
                case "6":
                    addCharge(1);
                    break label;
                case "0":
                    tickets.closeFile();
                    flights.closeFile();
                    users.closeFile();

                    var mainMenu = new MainMenu(new Templates());
                    mainMenu.mainMenu();
                    break label;
                default:
                    System.out.println(Appearance.RED + "Wrong command! Try again :" + Appearance.RESET_COLOR);
                    choice = input.next();
            }
        }
    }
    // this function use for changing passenger password
    private void changePass () throws IOException {
        users.ifClose("user.dat");

        System.out.println(Appearance.BLUE + "[ CHANGE PASSWORD PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter your new password :" + Appearance.TEXT_ITALIC);
        String password = input.next();

        users.changePass(USERNAME, password);

        System.out.println(Appearance.GREEN + "Password change successfully" + Appearance.RESET_COLOR);
        if (TEMPLATE.backToMenu("Passenger", "Changing").equals("1")) passengerMenu();
        else changePass();
    }
    // this function and next function use for searching airlines in 7 different ways
    private void search() throws IOException {

        System.out.println(Appearance.BLUE + "[ SEARCHING PANEL ]" + Appearance.RESET_COLOR);
        flights.ifClose("flights.dat");
        flights.randomAccessFile.seek(0);

        flights.printAllAirline();
        System.out.println(
                Appearance.TEXT_ITALIC + "type what you want to search and type -1 for skip :" + Appearance.RESET_STYLE);

        System.out.println("Flight ID :");
        String flightId = input.next().toUpperCase();

        System.out.println("origin :");
        String origin = TEMPLATE.templateStringStyle(input.next());

        System.out.println("destination :");
        String destination = TEMPLATE.templateStringStyle(input.next());

        System.out.println("date -> -1 for skip 0 for searching");
        String choice1 = input.next();
        String date;

        label:
        while (true) {
            switch (choice1) {
                case "-1" :
                    date = "-1";
                    break label;
                case "0" :
                    date = TEMPLATE.dateTemplate();
                    break label;
                default:
                    System.out.println(Appearance.RED + "wrong command! try again" + Appearance.RESET_COLOR);
                    choice1 = input.next();
            }
        }

        System.out.println("time -> -1 for skip 0 for searching");
        String choice2 = input.next();
        String time;
        label :
        while (true) {
            switch (choice2) {
                case "-1" :
                    time = "-1";
                    break label;
                case "0" :
                    time = TEMPLATE.timeTemplate();
                    break label;
                default:
                    System.out.println(Appearance.RED + "wrong command! try again" + Appearance.RESET_COLOR);
                    choice2 = input.next();
            }
        }

        System.out.println("price :");
        String price = TEMPLATE.availableInput(input.next());

        System.out.println("seat :");
        String seat = TEMPLATE.availableInput(input.next());

        flights.randomAccessFile.seek(0);
        int searchResult = 0;

        if (flightId.equals("-1") && origin.equals("-1") && destination.equals("-1") && date.equals("-1")
                && time.equals("-1") && price.equals("-1") && seat.equals("-1"))
            System.out.println(Appearance.GREEN + "Search without results !" + Appearance.RESET_COLOR);
        else {
            while (flights.randomAccessFile.getFilePointer() != flights.randomAccessFile.length()){

                String fileFlightId = flights.read();
                String fileOrigin = flights.read();
                String fileDestination = flights.read();
                String fileDate = flights.read();
                String fileTime = flights.read();
                String filePrice = flights.read();
                String fileSeat = flights.read();

                if ((fileFlightId.equals(flightId) || flightId.equals("-1")) &&
                        (fileOrigin.equals(origin) || origin.equals("-1")) &&
                        (fileDestination.equals(destination) || destination.equals("-1")) &&
                        (fileDate.equals(date) || date.equals("-1")) &&
                        (fileTime.equals(time) || time.equals("-1")) &&
                        (filePrice.equals(price) || price.equals("-1")) &&
                        (fileSeat.equals(seat) || seat.equals("-1"))) {

                    searchResult = 1;
                    long pointer = flights.randomAccessFile.getFilePointer();
                    flights.randomAccessFile.seek((pointer - 420));
                    flights.printSingleAirline();
                    flights.randomAccessFile.seek(pointer);
                }
            }
            if (searchResult == 0)
                System.out.println(Appearance.GREEN + "Search without results !" + Appearance.RESET_COLOR);
        }

        if (TEMPLATE.backToMenu("Passenger", "Searching").equals("1")) passengerMenu();
        else search();
    }
    // this function use for booking tickets
    private void bookingTicket() throws IOException {
        flights.ifClose("flights.dat");

        flights.printAllAirline();

        System.out.println(Appearance.BLUE + "[ BOOKING TICKET PANEL ]" + Appearance.RESET_COLOR);
        System.out.println(Appearance.TEXT_ITALIC + "Enter the Flight id which you want to buy :");
        String flightId = input.next().toUpperCase();

        int result;
        while (true) {
            result = flights.search(0,flightId,420);
            if (result != -1) break;
            else {
                System.out.println(Appearance.RED +
                        "This Flight id dos not exist! Try again :" + Appearance.RESET_COLOR);
                flightId = input.next().toUpperCase();
            }
        }

        int process = 1 ;
        if (flights.availableSeat((result + 300))) {
            process = 0 ;
            System.out.println(Appearance.RED +
                    "This airline is full ! try again with another airline" + Appearance.RESET_COLOR);
        }
        String shouldPay = flights.getSinglePartOfRecord((result + 240));

        if (process != 0) {
            while (true) {
                if (users.isEnough(shouldPay, USERNAME)) break;
                else {
                    System.out.println(Appearance.RED +
                            "Not enough money for buying! Charge your account" + Appearance.RESET_COLOR);
                    addCharge(0);
                }
            }
            tickets.ifClose("tickets.dat");
            String ticketId = tickets.creatTicketId(USERNAME, flightId);

            tickets.write(new Ticket(USERNAME, flightId, ticketId));

            users.ifClose("user.dat");
            users.decreaseCharge(USERNAME, shouldPay);

            flights.decreaseSeatsSeats((result + 300));

            System.out.println(Appearance.GREEN + "Booking ticket done successfully! Your ticket id is : " + ticketId
                    + Appearance.RESET_STYLE + Appearance.RESET_COLOR);

            System.out.println("Your ticket information is :");
            flights.randomAccessFile.seek((result - 60));
            flights.printSingleAirline();
        }

        if (TEMPLATE.backToMenu("passenger", "booking").equals("1")) passengerMenu();
        else bookingTicket();
    }
    // this function use for increase account charge
    private void addCharge(int nextStep) throws IOException {
        users.ifClose("user.dat");

        System.out.println(Appearance.BLUE + "[ ADD CHARGE PANEL ]" + Appearance.RESET_COLOR);
        System.out.println("Your total charge is : " + users.getSinglePartOfRecord(
                (users.search(0,USERNAME,180) + 60)));

        System.out.println(Appearance.TEXT_ITALIC + "How much do you want to charge :" + Appearance.RESET_STYLE);
        String chargeAmount = TEMPLATE.availableInput(input.next());

        users.increaseCharge(USERNAME,chargeAmount);

        if (nextStep == 1){
            if(TEMPLATE.backToMenu("Passenger", "Charging").equals("1")) passengerMenu();
            else addCharge(nextStep);
        }
    }
    // this function use for showing all booking tickets
    private boolean bookedTickets(int nextStep) throws IOException {
        tickets.ifClose("tickets.dat");

        if (nextStep == 1) System.out.println(Appearance.BLUE + "[ BOOKED TICKETS PANEL ]" + Appearance.RESET_COLOR);

        if (tickets.haveEverBooked(USERNAME)) {
            System.out.println(Appearance.TEXT_ITALIC + "Your Tickets are :");
            tickets.printAllUserTickets(USERNAME);

            if (nextStep == 1){
                if(TEMPLATE.backToMenu("Passenger", "Showing ticket library").equals("1")) passengerMenu();
                else bookedTickets(nextStep);
            }
            return true;
        } else System.out.println("You have not purchased any tickets yet !" + Appearance.RESET_STYLE);

        if (nextStep == 1){
            if(TEMPLATE.backToMenu("Passenger", "Showing ticket library").equals("1")) passengerMenu();
            else bookedTickets(nextStep);
        }
        return false;
    }
    // this function use for cancelling tickets
    private void ticketCancellation() throws IOException {
        tickets.ifClose("tickets.dat");

        System.out.println(Appearance.BLUE + "[ TICKET CANCELLATION PANEL ]" + Appearance.RESET_COLOR);
        if (bookedTickets(0)) {

            System.out.println(Appearance.TEXT_ITALIC + "Enter the Ticket Id to cancel :" + Appearance.RESET_STYLE);
            String ticketId = input.next();

            while (true){
                if (tickets.search(120,ticketId,180) == -1){
                    System.out.println(Appearance.RED +
                            "This Ticket id dos not exist! Try again :" + Appearance.RESET_COLOR);
                    ticketId = input.next();
                } else break;
            }

            int position = tickets.search(120,ticketId,180);
            String flightId = tickets.getSinglePartOfRecord((position - 60));

            flights.ifClose("flights.dat");
            int flightPos = flights.search(0, flightId, 420);
            flights.increaseSeats((flightPos + 360));
            String price = flights.getSinglePartOfRecord((flightPos + 300));

            users.ifClose("user.dat");
            users.increaseCharge(USERNAME, price);

            tickets.remove((position - 120), 180, "tickets.dat");

            System.out.println(Appearance.GREEN + "Ticket cancel successfully !" + Appearance.RESET_COLOR);
        }

        if (TEMPLATE.backToMenu("Passenger", "Cancelling").equals("1")) passengerMenu();
        else ticketCancellation();
    }
}
