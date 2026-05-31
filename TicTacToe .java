import java.util.*; //importing utility classes
import java.io.*;

public class TicTacToe {

    //declaring global variables
    static Scanner scanner = new Scanner(System.in);
    static String[] board = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    static String player = "X";
    static String comp = "O";
    static String winner = null;
    static boolean gameRunning = true;
    static boolean isPvP = false;
    static boolean playAgain = true;
    static String player1Name = "Player X";
    static String player2Name = "Player O";
    static final String FILE_NAME = "tictactoe_results.txt";
    static int gameCounter = 0;

    //method to print board of tic tac toe game 
    public static void printBoard() {
        System.out.println();
        System.out.println(board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("-------------");
        System.out.println(board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("-------------");
        System.out.println(board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println();
    }

    //method to reset board to initial state for further games 
    public static void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }
    }

    //method to check the name of players
    public static boolean isValidName(String name) {
        if (name.matches("^[A-Za-z]+$")) { //regex pattern
            return true;
        } else {
            return false;
        }
    }

    //method to read valid name
    public static String readValidName(String prompt) {
        String name;
        while (true) {
            System.out.print(prompt);
            name = scanner.nextLine().trim();//trimming the whitespaces

            if (isValidName(name)) {
                return name;
            } else {
                System.out.println("Only alphabets and spaces allowed.");
            }
        }
    }

    //method for the toss
    public static String toss(String p1, String p2) {
        Random rand = new Random(); //using random method for the toss
        int coin = rand.nextInt(2);
        int guess = -1;

        while (true) {
            try {
                System.out.print(p1 + ", call the toss (0 or 1): ");
                guess = scanner.nextInt();
                if (guess == 0 || guess == 1) 
                break;
                else 
                {System.out.println("Enter only 0 or 1!");}

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Try again.");
                scanner.next();//clearing the input buffer
            }
        }
        if (guess == coin) {
            System.out.println(p1 + " wins the toss!");
            return p1;

        } else {
            System.out.println(p2 + " wins the toss!");
            return p2;
        }
    }

    //method for player's input 
    public static void playerInput(String symbol, String name) {

        try {//exception handling
            System.out.print(name + ", pick a position (1-9): ");
            int inp = scanner.nextInt();

            if (inp >= 1 && inp <= 9 && !board[inp - 1].equals("X") && !board[inp - 1].equals("O")) {
                board[inp - 1] = symbol;
                printBoard();

            } else {
                System.out.println("Invalid move. Try again.");
                playerInput(symbol, name);
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter a number only.");
            scanner.next();

            playerInput(symbol, name);//recursive call
        }
    }

    //method for computer's input
    public static int computerInput() {
        if (!board[4].equals("X") && !board[4].equals("O")){//default condition to occupy center first
            return 4;
        }
        for (int i = 0; i < 9; i++) {
            if (!board[i].equals("X") && !board[i].equals("O")) {//checking if the slot is empty
                String temp = board[i];
                board[i] = comp;
                if (checkWinCondition()) {
                    board[i] = temp;
                    return i;
                }
                board[i] = temp;
                board[i] = player;
                if (checkWinCondition()) {
                    board[i] = temp;
                    return i;
                }
                board[i] = temp;
            }
        }
        //setting priority to first occupy corners then sides
        int[] priority = {0, 2, 6, 8, 1, 3, 5, 7};
        for (int i = 0; i < priority.length; i++) {
            int pos = priority[i];
            if (!board[pos].equals("X") && !board[pos].equals("O")) return pos;
        }
        return -1;
    }
    
    //method to check win conditions using 2d array
    public static boolean checkWinCondition() {
        int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},//rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},//columns
            {0, 4, 8}, {2, 4, 6}//diagnols
        };

        for (int i = 0; i < winPositions.length; i++) {
            int[] pos = winPositions[i];
            if (board[pos[0]].equals(board[pos[1]]) && board[pos[1]].equals(board[pos[2]])) {
                winner = board[pos[0]];
                return true;
            }
        }
        return false;
    }

    //method to check win
    public static void checkWin(String name) {
        if (checkWinCondition()) { //calling method check win conditions
            System.out.println("Congratulations, " + name + "! You won the game!");
            writeResultToFile(name);//writing the result in the file
            gameRunning = false;
        }
    }

    //method to check if the game is tie
    public static void checkTie() {
        boolean isTie = true;
        for (int i = 0; i < 9; i++) {
            if (!board[i].equals("X") && !board[i].equals("O")) {//checks if the board is still empty
                isTie = false;
                break;
            }
        }
        if (isTie) {
            System.out.println("It's a tie!");
            writeResultToFile("Tie");//writing results in the file
            gameRunning = false;
        }
    }


    //writing results into the file 
    public static void writeResultToFile(String winnerName) {
        gameCounter++;
        //Handling exceptions
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write("Winner of Game " + gameCounter + " is: " + winnerName + "\n");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    //method to display results from the file 
    public static void displayResultsFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("-------- Game Results ----------");
            int counter = 1;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                counter++;
            }
            System.out.println("--------------------------------\n");
        } catch (FileNotFoundException e) {
            System.out.println("No game results found yet.");
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
    }

    //method for game
    public static void game() {
        if (!isPvP) { // player vs computer
            System.out.print("Enter your name: ");
            player1Name = readValidName("");

            String first = toss(player1Name, "Computer");
            gameRunning = true;
            printBoard();//printing board

            while (gameRunning) {
                if (first.equals(player1Name)) {
                    playerInput(player, player1Name);
                    checkWin(player1Name);
                    checkTie();

                    if (gameRunning) {
                        int move = computerInput();
                        board[move] = comp;
                        printBoard();
                        checkWin("Computer");
                        checkTie();
                    }

                } else {
                    int move = computerInput();
                    board[move] = comp;
                    printBoard();
                    checkWin("Computer");
                    checkTie();

                    if (gameRunning) {
                        playerInput(player, player1Name);
                        checkWin(player1Name);
                        checkTie();
                    }
                }
            }

        } else {//player vs player 
            System.out.print("Enter name for Player 1 (X): ");
            player1Name = readValidName("");

            System.out.print("Enter name for Player 2 (O): ");
            player2Name = readValidName("");

            String first = toss(player1Name, player2Name);
            String turn = first;
            gameRunning = true;
            printBoard();

            while (gameRunning) {
                if (turn.equals(player1Name)) {
                    playerInput("X", player1Name);
                    checkWin(player1Name);
                    checkTie();
                    turn = player2Name;

                } else {
                    playerInput("O", player2Name);
                    checkWin(player2Name);
                    checkTie();
                    turn = player1Name;
                }
            }
        }

        char response = 'n';
        boolean validInput = false;

        while (!validInput) {
            try {
                scanner.nextLine();
                System.out.print("Would you like to play again? (y/n): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("y") || input.equals("n")) {
                    response = input.charAt(0);
                    validInput = true;

                } else {
                    System.out.println("Invalid input! Please enter 'y' or 'n'.");
                }

            } catch (Exception e) {
                System.out.println("An error occurred while reading input! Please try again.");
                scanner.nextLine();
            }
        }

        if (response == 'y') {
            resetBoard();
        } else {
            playAgain = false;
        }
    }

    //main method
    public static void main(String[] args) {
        int choice = 0;
        do {//using a do-while loop to keep on dispalying menu until prompted exit 
            try {
                System.out.println("========== MAIN MENU ==========");
                System.out.println("1. Play Tic Tac Toe");
                System.out.println("2. Display Results");
                System.out.println("3. Exit");
                System.out.println("===============================");
                System.out.print("Please Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        int mode = 0;
                        boolean validInput = false;

                        while (!validInput) {
                            try {
                                System.out.println("========================");
                                System.out.println("Choose a Mode:");
                                System.out.println("========================");
                                System.out.println("1. Player vs Computer");
                                System.out.println("2. Player vs Player");
                                System.out.println("------------------------");
                                System.out.print("\nEnter your choice (1 or 2): ");
                                mode = Integer.parseInt(scanner.nextLine().trim());

                                if (mode == 1 || mode == 2) {
                                    validInput = true;
                                } else {
                                    System.out.println("Invalid choice. Please enter 1 or 2.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number (1 or 2).");
                            }
                        }

                        isPvP = (mode == 2);
                        playAgain = true;
                        resetBoard();

                        System.out.println("\t|===============================================|");
                        System.out.println("\t|         TIC TAC TOE by Momina & Hareem        |");
                        System.out.println("\t|-----------------------------------------------|");
                        System.out.println("\t| Version: 10.1.01 | Level: Impossible          |");
                        System.out.println("\t|===============================================|\n"); 

                        while (playAgain) {
                            game();
                        }

                        System.out.println("\t--------------------------------------");
                        System.out.println("\tThanks for playing! See you next time!");
                        System.out.println("\t--------------------------------------");
                        break;

                    case 2:
                        displayResultsFromFile();
                        break;

                    case 3:
                        System.out.println("Goodbye! Have a great day!");
                        break;

                    default:
                        System.out.println("Please select 1, 2 or 3 only.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
        } while (choice != 3);
    }
}
