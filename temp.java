import java.util.Scanner;

public class temp {
    public static final char EMPTY = ' ';
    public static final char X = 'X';
    public static final char O = 'O';
    static char[][] board = {{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

    public static void main(String[] args) {
        boolean playerTurn = true;
        printBoard();
        while (true) {
            if (playerTurn) {
                playerMove();
            } else {
                aiMove();
            }
            printBoard();
            int result = evaluate();
            if (result == 10) {
                System.out.println("AI wins!");
                break;
            } else if (result == -10) {
                System.out.println("Player wins!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a tie!");
                break;
            }
            playerTurn = !playerTurn;
        }
    }

    static void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell == EMPTY ? '-' : cell);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.print("Enter row (0, 1, 2): ");
            row = scanner.nextInt();
            System.out.print("Enter column (0, 1, 2): ");
            col = scanner.nextInt();
        } while (!isValidMove(row, col));
        board[row][col] = X;
    }

    static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
    }

    static void aiMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == EMPTY) {
                    board[row][col] = O;
                    int moveVal = minimax(0, false);
                    board[row][col] = EMPTY;
                    if (moveVal > bestVal) {
                        bestRow = row;
                        bestCol = col;
                        bestVal = moveVal;
                    }
                }
            }
        }
        board[bestRow][bestCol] = O;
    }

    static int minimax(int depth, boolean isMax) {
        int score = evaluate();

        if (score == 10 || score == -10) return score;
        if (isBoardFull()) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = O;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[row][col] = EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == EMPTY) {
                        board[row][col] = X;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[row][col] = EMPTY;
                    }
                }
            }
            return best;
        }
    }

    static int evaluate() {
        // Rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == O) return 10;
                else if (board[row][0] == X) return -10;
            }
        }
        // Columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == O) return 10;
                else if (board[0][col] == X) return -10;
            }
        }
        // Diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == O) return 10;
            else if (board[0][0] == X) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == O) return 10;
            else if (board[0][2] == X) return -10;
        }
        return 0;
    }

    static boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == EMPTY) return false;
            }
        }
        return true;
    }
}
