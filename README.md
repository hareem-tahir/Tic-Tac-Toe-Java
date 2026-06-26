**Tic-Tac-Toe-Java**
**Features**
- Player vs Computer mode
- Player vs Player mode
- Basic AI (center, win-blocking, priority moves)
- Toss system to decide first turn
- Input validation (names, moves, menu)
- Exception handling (invalid input protection)
- File handling (stores game results)
- Replay option after each game
- View past match results

---

**Concepts Used**
- Arrays (game board)
- Loops (game flow control)
- Conditionals (win/tie logic)
- Functions (modular design)
- Random class (toss system)
- File Handling (BufferedReader, FileWriter)
- Exception Handling (InputMismatchException)
- Recursion (input retry system)


---

**Game Flow**
```
Main Menu
├── Play Game
│ ├── Choose Mode (PvP / PvC)
│ ├── Toss System
│ ├── Gameplay Loop
│ ├── Win / Tie Check
│ └── Replay Option
│
├── Display Results
│ └── Reads results from file
│
└── Exit
```

---

**AI Logic (Computer Player)**
The computer follows this priority:
1. Take center if available
2. Try to win immediately
3. Block player winning move
4. Take corners first
5. Then take remaining spaces

---

**File Handling**
The game stores results in:
tictactoe_results.txt


Format:

Winner of Game 1 is: PlayerName
Winner of Game 2 is: Computer
Winner of Game 3 is: Tie


---

** How to Run**
1. Compile the program:
bash
javac TicTacToe.java
Run the program:
java TicTacToe
