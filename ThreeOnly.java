import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class ThreeOnly
{
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    Queue<JButton> totalMoves = new LinkedList<JButton>();
    String playerX  = "X";
    String playerO  = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;

    ThreeOnly()
    {
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

                tile.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (gameOver) 
                            return;

                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "")
                        {
                            tile.setText(currentPlayer);
                            System.out.println(totalMoves.size());
                            if (totalMoves.size() == 6)
                            {
                                totalMoves.peek().setText("");
                                totalMoves.poll();
                                totalMoves.add((JButton) e.getSource());
                            }
                            else
                            {
                                totalMoves.add((JButton) e.getSource());
                            }

                            checkWinner();
                            if (!gameOver)
                            {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn");
                            }
                            
                        }
                    }
                });
            }
        }
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
                    setWinner(board[row][i]);
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
                    setWinner(board[i][col]);
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
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        // anti-diagonally
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "")
        {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }
    }

    void setWinner(JButton tile)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile)
    {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }
}