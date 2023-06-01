import java.io.IOException;
import java.io.RandomAccessFile;
public class Start {
    public void StartProgram() throws IOException {
        var defaultFlights = new DefaultFlights
                (new Flights(new RandomAccessFile("flights.dat", "rw")));
        defaultFlights.add();

        var mainMenu = new MainMenu(new Templates());
        mainMenu.mainMenu();
    }
}
