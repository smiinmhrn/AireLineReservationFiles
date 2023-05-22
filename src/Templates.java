import java.util.Scanner;
import appearance.Appearance;
// this class has responsible for creating templates forms
public class Templates {
    Scanner input = new Scanner(System.in);
    // check if inputs are numbers not anything else
    public String availableInput(String choice){

        while (true){
            try {
                Long.parseLong(choice);
                break;
            } catch (Exception e) {
                System.out.println( Appearance.RED + "Pleas enter a available \"number\" :" + Appearance.RESET_COLOR);
                choice = input.next();
            }
        }
        return choice;
    }
    // creating string style => avery letter of a word should be lowercase except first letter
    public String templateStringStyle(String inputSearch) {
        return inputSearch.substring(0,1).toUpperCase() + inputSearch.substring(1).toLowerCase();
    }
    // creating time template and return a time in its template
    public String timeTemplate() {

        System.out.println("Enter Hour :");
        String hour = availableInput(input.next());

        while (true) {
            if (!(Integer.parseInt(hour) >= 1 && Integer.parseInt(hour) <= 24)) {
                System.out.println(Appearance.RED + "Enter available hour. Try again :" + Appearance.RESET_COLOR);
                hour = availableInput(input.next());
            } else break;
        }
        if (Integer.parseInt(hour) >= 1 && Integer.parseInt(hour) < 10) hour = "0" + hour;

        System.out.println("Enter Minute :");
        String minute = availableInput(input.next());

        while (true) {
            if (!(Integer.parseInt(minute) >= 0 && Integer.parseInt(minute) < 60)) {
                System.out.println(Appearance.RED + "Enter available minute. Try again :" + Appearance.RESET_COLOR);
                minute = availableInput(input.next());
            }else break;
        }

        if (Integer.parseInt(minute) == 0) minute = "0";
        if ((Integer.parseInt(minute) >= 0 && Integer.parseInt(minute) < 10) || (minute.equals("0"))) minute = "0" + minute;

        return hour + ":" + minute;
    }
    // creating date template and return a date in its template
    public String dateTemplate() {

        System.out.println("Enter Year :");
        String year = availableInput(input.next());

        while (true) {
            if (Integer.parseInt(year) <= 1000) {
                System.out.println(Appearance.RED + "Enter available Year. Try again :" + Appearance.RESET_COLOR);
                year = availableInput(input.next());
            } else break;
        }

        System.out.println("Enter Month :");
        String month = availableInput(input.next());

        while (true){
            if (!(Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12)) {
                System.out.println(Appearance.RED + "Enter available Month. Try again :" + Appearance.RESET_COLOR);
                month = availableInput(input.next());
            } else break;
        }

        if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) < 10) month = "0" + month;

        System.out.println("Enter Day :");
        String day = availableInput(input.next());

        while (true) {
            if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 6) {
                if (!(Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 31)) {

                    System.out.println(Appearance.RED + "Enter available Day. Try again :" + Appearance.RESET_COLOR);
                    day = availableInput(input.next());
                } else break;
            }

            if (Integer.parseInt(month) >= 7 && Integer.parseInt(month) <= 11) {
                if (!(Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 30)) {

                    System.out.println(Appearance.RED + "Enter available Day. Try again :" + Appearance.RESET_COLOR);
                    day = availableInput(input.next());
                } else break;
            }

            if (Integer.parseInt(month) == 12) {
                if (Integer.parseInt(day) > 29) {

                    System.out.println(Appearance.RED + "Enter available Day. Try again :" + Appearance.RESET_COLOR);
                    day = availableInput(input.next());
                } else break;
            }
        }

        if (Integer.parseInt(day) >= 1 && Integer.parseInt(day) < 10) day = "0" + day;
        return year + "-" + month + "-" + day;
    }
    // creating a template menu for moving to another menu
    public String backToMenu(String user, String action) {

        System.out.println(Appearance.TEXT_ITALIC + "1. Back to " + user + " menu");
        System.out.println("2. Continue " + action + Appearance.RESET_STYLE);
        String choice = input.next();

        while (true) {
            choice = availableInput(choice);

            if(!(choice.equals("1") || choice.equals("2"))) {
                System.out.println(Appearance.RED + "Wrong command! Try again :" + Appearance.RESET_COLOR);
                choice = input.next();
            } else break;
        }
        return choice;
    }
}

