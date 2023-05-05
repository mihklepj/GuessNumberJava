import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * All game logics
 */
public class Model {
    /**
     * Minimum random number inclusive
     */
    private final int MINIMUM = 1;
    /**
     * Maximum random number inclusive
     */
    private final int MAXIMUM = 100;
    /**
     * Scoreboard filename
     */
    private final String filename = "scoreboard.txt";
    /**
     * User input from commandline
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Computer random number 1-100 inclusive.
     */
    private int pcNumber;
    /**
     * Steps used to guess number.
     */
    private int steps;
    /**
     * Game over (boolean)
     */
    private boolean gameOver;
    /**
     * Scoreboard file contents in List
     */
    private List<Content> scoreboard = new ArrayList<>();

    /**
     * Show game menu
     */
    public void showMenu() {
        System.out.println("1. Play");
        System.out.println("2. Scoreboard");
        System.out.println("3. Exit");
        System.out.print("Enter 1, 2 or 3: ");
        int choice = scanner.nextInt();
        // System.out.println(choice); // TEST
        switch (choice) {
            case 1:
                setupGame();
                letsPlay();
                //System.out.println("PLAY"); // TEST
            case 2:
                // System.out.println("SCORE");  // TEST
                showScoreboard();
                showMenu();
                break;
            case 3:
                System.out.println("Sayonara!");
                System.exit(0);
            default: // Wrong int chosen
                showMenu(); // Show menu
        }
    }

    /**
     * Set up the game
     */
    private void setupGame() {
        pcNumber = ThreadLocalRandom.current().nextInt(MINIMUM, MAXIMUM+1);
        steps = 0;
        gameOver = false;
    }

    /**
     * Ask to enter number
     */
    private void ask() {
        System.out.printf("Enter number between %d - %d: ", MINIMUM, MAXIMUM);
        int userNumber = scanner.nextInt();
        steps += 1;
        if (userNumber > pcNumber && userNumber != 10000) {
            System.out.println("Smaller");
        } else if (userNumber < pcNumber && userNumber != 10000) {
            System.out.println("Bigger");
        } else if (userNumber == pcNumber && userNumber != 10000) {
            System.out.printf("You guessed the number in %d steps.%n", steps);
            gameOver = true;
        } else if (userNumber == 10000) {
            System.out.printf("Backdoor used. The number is %d.%n", pcNumber);

        }
    }

    private void letsPlay() {
        while (!gameOver) {
            ask();
        }
        askName();
        showMenu();
    }
    private void askName() {
        System.out.print("Enter your name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name;
        try {
            name = br.readLine();
            if(name.strip().length()>1) {
                writeToFile(name);
            } else {
                System.out.println("Name is too short!");
                askName();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write name andt steps to file
     * @param name player name
     */
    private void writeToFile(String name) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            String line = name + ";" + steps;
            bw.write(line); // Write line to file
            bw.newLine(); //
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show scoreboard to console
     */
    private void showScoreboard() {
        readFromFile();
        System.out.println();
        for(Content c : scoreboard) {
            c.showData();
        }
        System.out.println();
    }

    /**
     * Read data from scoreboard file
     */
    private void readFromFile() {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            scoreboard = new ArrayList<>();
            for(String line; (line = br.readLine()) != null;) {
                String name = line.split(";")[0]; // Name from file line
                int steps = Integer.parseInt(line.split(";")[1]); // Steps from file line
                scoreboard.add(new Content(name, steps));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
