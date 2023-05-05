/**
 * Constructor
 */
public class App {
    public App() {
        new Model().showMenu(); // Oneliner

        /*
         Model model = new Model(); // Multi
         model.showMenu();             // Line
        */
    }

    /**
     * App main method
     * @param args arguments List
     */
    public static void main(String[] args) {
        new App();
    }
}
