import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
    public static void main(String[] args) throws IOException {
        var f = new DefaultFlights(new Flights(new RandomAccessFile("flights.dat", "rw"),"flights.dat"));
        f.add();
        var m = new MainMenu(new Templates());
        m.mainMenu();
    }
}