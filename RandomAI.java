import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class RandomAI
{
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

    RandomAI(boolean first)
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
                    JButton AIFirstTile = AITurn();
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

                                tile = AITurn();
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

    JButton AITurn()
    {
        Random rand = new Random();
        ArrayList<JButton> availableButtons = new ArrayList<>();
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row][col].getText() == "")
                {
                    availableButtons.add(board[row][col]);
                }
            }
        }

        int rand_int = rand.nextInt(availableButtons.size());
        return availableButtons.get(rand_int);

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
