import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PerfectAI
{
    public static final int EMPTY = 0;
    public static final int X = 1;
    public static final int O = 2;

    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String player = "";
    String other = "";
    boolean playerFirst;
    boolean AIFirstTurnDone;

    boolean gameOver = false;
    int turns = 0;

    PerfectAI(boolean first)
    {
        if (first)
        {
            player = "X";
            other  ="O";
            playerFirst = true;
        }
        else
        {
            player = "O";
            other = "X";
            playerFirst = false;
        }


        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
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

                if (row == 2 && col == 2 && !playerFirst)
                {
                    JButton AIFirstTile = board[1][1];
                    AIFirstTile.setText(other);
                    turns++;
                    playerFirst = true;
                }
                
                tile.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (gameOver)
                        {
                            return;
                        }

                        if (playerFirst)
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

                                tile = AITurn(board);
                                tile.setText(other);
                                turns++;
                                checkWinner();
                            }
                        }
                    }
                });
            }   
        }
    }

    JButton AITurn(JButton[][] board)
    {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col].getText() == "")
                {
                    board[row][col].setText("X");
                    int moveValue = miniMax(board, 0, false);
                    board[row][col].setText("");
                    if (moveValue > bestValue)
                    {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }

        return board[bestMove[0]][bestMove[1]];
    }

    int miniMax(JButton[][] board, int depth, boolean isMax)
    {
        int boardVal = evaluateBoard(board, depth);

        if (Math.abs(boardVal) > 0 || depth == 0 || turns <= 9)
        {
            return boardVal;
        }

        // Maximising player
        if (isMax)
        {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    if (board[row][col].getText() == "")
                    {
                        board[row][col].setText("X");
                        highestVal = Math.max(highestVal, miniMax(board, depth - 1, false));
                        board[row][col].setText("");
                    }
                }
            }
            return highestVal;
        }
        else
        {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < 3; col++)
                {
                    if (board[row][col].getText() == "")
                    {
                        board[row][col].setText("O");
                        lowestVal = Math.min(lowestVal, miniMax(board, depth - 1, true));
                        board[row][col].setText("");
                    }
                }
            }
            return lowestVal;
        }
    }

    int evaluateBoard(JButton[][] board, int depth)
    {
        int rowSum = 0;
        int XWin = 3;
        int OWin = -3;

        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col].getText() == "X")
                {
                    rowSum += 1;
                }
                else if (board[row][col].getText() == "O")
                {
                    rowSum -= 1;
                }

                if (rowSum == XWin)
                {
                    return 10 + depth;
                } 
                else if (rowSum == OWin)
                {
                    return -10 - depth;
                }
                rowSum = 0;
            }
        }

        rowSum = 0;
        for (int col = 0; col < 3; col++)
        {
            for (int row = 0; row < 3; row++)
            {
                if (board[col][row].getText() == "X")
                {
                    rowSum += 1;
                }
                else if (board[col][row].getText() == "O")
                {
                    rowSum -= 1;
                }

                if (rowSum == XWin)
                {
                    return 10 + depth;
                } 
                else if (rowSum == OWin)
                {
                    return -10 - depth;
                }

                rowSum = 0;
            }
        }

        rowSum = 0;
        for (int i = 0; i < 3; i++)
        {
            if (board[i][i].getText() == "X")
            {
                rowSum += 1;
            }
            else if (board[i][i].getText() == "O")
            {
                rowSum -= 1;
            }

            if (rowSum == XWin)
            {
                return 10 + depth;
            } 
            else if (rowSum == OWin)
            {
                return -10 - depth;
            }

            rowSum = 0;
        }

        rowSum = 0;
        for (int i = 0; i < 3; i++)
        {
            if (board[i][2-i].getText() == "X")
            {
                rowSum += 1;
            }
            else if (board[i][2-i].getText() == "O")
            {
                rowSum -= 1;
            }

            if (rowSum == XWin)
            {
                return 10 + depth;
            } 
            else if (rowSum == OWin)
            {
                return -10 - depth;
            }

            rowSum = 0;
        }

        return rowSum;
    }

    void checkWinner()
    {
        // horizontal
        for (int row = 0; row < 3; row++)
        {
            if (board[row][0].getText() == "")
                continue;

            if (board[row][0].getText() == board[row][1].getText() && 
                board[row][1].getText() == board[row][2].getText())
            {
                for (int i = 0; i < 3; i++)
                {
                    if (board[row][i].getText() == "X")
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
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "")
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
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "")
        {
            if (board[0][2].getText() == "X")
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
