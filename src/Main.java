import controller.Controller;
import java.util.Scanner;
import service.BarangService;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BarangService service = new BarangService();
        ConsoleView view = new ConsoleView(input);
        Controller app = new Controller(view, service);

        app.run();
        input.close();
    }
 
}