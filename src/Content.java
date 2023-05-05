/**
 * Class for file contents
 */
public class Content {
    /**
     * Player name from file
     */
    private String name;
    /**
     * Steps from file
     */
    private int steps;

    /**
     * Constructor for Content class
     * @param name player name
     * @param steps steps
     */
    public Content(String name, int steps) {
        this.name = name;
        this.steps = steps;
    }

    /**
     * Show file contents
     */
    public void showData() {
        if (name.length() > 15) {
            name = name.substring(0, 15); // 15 char
        } else {
            name = String.format("%-15s", name);
        }
        String n = String.format("%-10s", name);
        String s = String.format("%3d", steps);
        System.out.println(n+s);
    }
}
