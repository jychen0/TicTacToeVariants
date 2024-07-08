import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PerfectAI {
    class Move {
        int row, col;

        Move(int r, int c) {
            row = r;
            col = c;
        }
    }
    public static final int EMPTY = 0;
    public static final int X = 1;
    public static final int O = 2;

    // UI components
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    static JButton[][] board = new JButton[3][3];
    static String player = "";
    static String other = "";
    boolean playerTurn;

    boolean gameOver = false;
    static int turns = 0;

    // Constructor
    PerfectAI(boolean first) {
        if (first) {
            player = "X";
            other = "O";
            playerTurn = true;
        } else {
            player = "O";
            other = "X";
            playerTurn = false;
        }

        frame.setVisible(true);
        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                JButton tile = new JButton();
                board[row][col] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                if (row == 2 && col == 2 && !playerTurn)
                {
                    JButton AIFirstTile = board[1][1];
                    AIFirstTile.setText(other);
                    turns++;
                    playerTurn = true;
                }
                
                tile.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (gameOver)
                        {
                            return;
                        }

                        if (playerTurn)
                        {
                            JButton tile = (JButton) e.getSource();
                            if (tile.getText() == "")
                            {
                                tile.setText(player);
                                turns++;
                                checkWinner();

                                if (gameOver)
                                {
                                    return;
                                }

                                makeAIMove();
                                // tile.setText(other);
                                turns++;
                                checkWinner();
                            }
                        }
                    }
                });
            }   
        }
    }

    void makeAIMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
    
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().isEmpty()) {
                    board[row][col].setText(other);
                    int moveVal = minimax(0, false);
                    board[row][col].setText("");
                    if (moveVal > bestVal) {
                        bestRow = row;
                        bestCol = col;
                        bestVal = moveVal;
                    }
                }
            }
        }
        board[bestRow][bestCol].setText(other);
    }
    

    int minimax(int depth, boolean isMaximizing) {
        int score = evaluate();

        if (score == 10)
            return score - depth;

        if (score == -10)
            return score + depth;

        if (isBoardFull())
            return 0;

        if (isMaximizing) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(other);
                        best = Math.max(best, minimax(depth + 1, !isMaximizing));
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        } else {
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].getText().equals("")) {
                        board[i][j].setText(player);
                        best = Math.min(best, minimax(depth + 1, !isMaximizing));
                        board[i][j].setText("");
                    }
                }
            }
            return best;
        }
    }
    

    /**
    * Evaluates the current board and returns a score.
    * @return +10 if 'X' wins, -10 if 'O' wins, 0 otherwise.
    */
    static int evaluate() {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++) {

            if (board[row][0].getText().equals(board[row][1].getText()) &&
                board[row][1].getText().equals(board[row][2].getText())) {
                if (board[row][0].getText().equals(other))
                    return +10;
                else if (board[row][0].getText().equals(player))
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {

            if (board[0][col].getText().equals(board[1][col].getText()) &&
                board[1][col].getText().equals(board[2][col].getText())) {
                if (board[0][col].getText().equals(other))
                    return +10;
                else if (board[0][col].getText().equals(player))
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            if (board[0][0].getText().equals(other))
                return +10;
            else if (board[0][0].getText().equals(player))
                return -10;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            if (board[0][2].getText().equals(other))
                return +10;
            else if (board[0][2].getText().equals(player))
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    static boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals("")) return false;
            }
        }
        return true;
    }

    void checkWinner()
    {
        // horizontal
        for (int row = 0; row < 3; row++)
        {
            if (board[row][0].getText().equals(""))
                continue;

            if (board[row][0].getText().equals(board[row][1].getText()) && 
                board[row][1].getText().equals(board[row][2].getText()))
            {
                for (int i = 0; i < 3; i++)
                {
                    if (board[row][i].getText().equals("X"))
                        setWinner(board[row][i], "X");
                    else
                        setWinner(board[row][i], "O");
                }
                gameOver = true;
                return;
            }
        }

        // vertical
        for (int col = 0; col < 3; col++)
        {
            if (board[0][col].getText() == "")
                continue;

            if (board[0][col].getText() == board[1][col].getText() &&
                board[1][col].getText() == board[2][col].getText())
            {
                for (int i = 0; i < 3; i++)
                {
                    if (board[i][col].getText() == "X")
                        setWinner(board[i][col], "X");
                    else
                        setWinner(board[i][col], "O");
                }
                gameOver = true;
                return;
            }
        }

        // diagonal
        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals(""))
        {
            for (int i = 0; i < 3; i++)
            {
                if (board[i][i].getText() == "X")
                    setWinner(board[i][i], "X");
                else    
                    setWinner(board[i][i], "O");
            }
            gameOver = true;
            return;
        }

        // anti-diagonally
        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals(""))
        {
            if (board[0][2].getText().equals("X"))
            {
                setWinner(board[0][2], "X");
                setWinner(board[1][1], "X");
                setWinner(board[2][0], "X");
                gameOver = true;
                return;
            }
            else
            {
                setWinner(board[0][2], "O");
                setWinner(board[1][1], "O");
                setWinner(board[2][0], "O");
                gameOver = true;
                return;
            }
        }

        if (turns == 9)
        {
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    setTie(board[row][col]);
                }
            }

            gameOver = true;
        }
    }

    void printBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String text = board[row][col].getText();
                if (text.equals("")) {
                    System.out.print(" . ");
                } else {
                    System.out.print(" " + text + " ");
                }
                
                if (col < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    void setWinner(JButton tile, String p)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(p + " is the winner!");
    }

    void setTie(JButton tile)
    {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }
}
