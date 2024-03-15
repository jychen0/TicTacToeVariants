import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class SmallBoard {
    private JFrame frame;
    private JButton[] buttons;
    private String winner = "";

    public SmallBoard() {
        frame = new JFrame();
        frame.setLayout(new GridLayout(3, 3));
        frame.setSize(600, 600);

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            frame.add(buttons[i]);
        }
    }

    public void showBoard(String currentPlayer) {
        frame.setVisible(true);
        for (int i = 0; i < 9; i++) 
        {
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JButton tile = (JButton) e.getSource();
                    tile.setBackground(Color.darkGray);
                    tile.setForeground(Color.white);
                    tile.setFont(new Font("Arial", Font.BOLD, 120));
                    tile.setFocusable(false);
                    if (tile.getText() == "")
                    {
                        tile.setText(currentPlayer);
                        if (checkWinner())
                        {
                            winner = currentPlayer;
                        }
                        frame.setVisible(false);
                    }
                }
            });
        }
        
    }

    void setWinner(String w) {
        winner = w;
    }

    String getWinner() {
        return winner;
    }

    boolean checkWinner()
    {
        // horizontal
        if (buttons[0].getText() != "") {
            if (buttons[1].getText() == buttons[0].getText() && buttons[2].getText() == buttons[1].getText())
            {
                setWinner(buttons[0]);
                setWinner(buttons[1]);
                setWinner(buttons[2]);
                winner = buttons[0].getText();
                return true;
            }
        }
        
        if (buttons[3].getText() != "") {
            if (buttons[4].getText() == buttons[3].getText() && buttons[5].getText() == buttons[4].getText())
            {
                setWinner(buttons[3]);
                setWinner(buttons[4]);
                setWinner(buttons[5]);
                winner = buttons[3].getText();
                return true;
            }
        }

        if (buttons[6].getText() != "") {
            if (buttons[7].getText() == buttons[6].getText() && buttons[8].getText() == buttons[7].getText())
            {
                setWinner(buttons[6]);
                setWinner(buttons[7]);
                setWinner(buttons[8]);
                winner = buttons[6].getText();
                return true;
            }
        }

        // vertical
        if (buttons[0].getText() != "") {
            if (buttons[3].getText() == buttons[0].getText() && buttons[6].getText() == buttons[3].getText())
            {
                setWinner(buttons[0]);
                setWinner(buttons[3]);
                setWinner(buttons[6]);
                winner = buttons[0].getText();
                return true;
            }
        }
        
        if (buttons[1].getText() != "") {
            if (buttons[4].getText() == buttons[1].getText() && buttons[7].getText() == buttons[4].getText())
            {
                setWinner(buttons[1]);
                setWinner(buttons[4]);
                setWinner(buttons[7]);
                winner = buttons[1].getText();
                return true;
            }
        }

        if (buttons[2].getText() != "") {
            if (buttons[5].getText() == buttons[2].getText() && buttons[8].getText() == buttons[5].getText())
            {
                setWinner(buttons[2]);
                setWinner(buttons[5]);
                setWinner(buttons[8]);
                winner = buttons[2].getText();
                return true;
            }
        }

        // diagonal
        if (buttons[0].getText() != "") {
            if (buttons[4].getText() == buttons[0].getText() && buttons[8].getText() == buttons[4].getText()) 
            {
                setWinner(buttons[0]);
                setWinner(buttons[4]);
                setWinner(buttons[8]);
                winner = buttons[0].getText();
                return true;
            }
        }

        // anti-diagonally
        if (buttons[2].getText() != "") {
            if (buttons[4].getText() == buttons[2].getText() && buttons[6].getText() == buttons[4].getText()) 
            {
                setWinner(buttons[2]);
                setWinner(buttons[4]);
                setWinner(buttons[6]);
                winner = buttons[2].getText();
                return true;
            }
        }
        return false;
    }

    boolean checkTie()
    {
        if (checkWinner())
            return false;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText() == "") 
                return false;
        }

        return true;
    }

    void setWinner(JButton tile)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
    }
}


public class Extreme extends JFrame {
    private SmallBoard[] bigBoard = new SmallBoard[9];
    private JButton[] buttons = new JButton[9];
    private String currentPlayer = "X";
    private boolean gameOver = false;

    public Extreme() {
        JFrame frame = new JFrame("Extreme Tic-Tac-Toe");
        JLabel textLabel = new JLabel();
        JPanel textPanel = new JPanel();

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
        
        for (int i = 0; i < 9; i++) {
            bigBoard[i] = new SmallBoard();
        }

        for (int i = 0; i < 9; i++) {
            final int index = i;
            JButton tile = new JButton();
            buttons[i] = tile;
            tile.setBackground(Color.darkGray);
            tile.setForeground(Color.white);
            tile.setFont(new Font("Arial", Font.BOLD, 120));
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (gameOver)
                        return;
                    JButton tile = (JButton) e.getSource();
                    if (tile.getText() == "")
                        bigBoard[index].showBoard(currentPlayer);
                        if (bigBoard[index].getWinner() != "") {
                            tile.setText(bigBoard[index].getWinner());
                        }
                        checkWinner();
                        if (!gameOver)
                            currentPlayer = currentPlayer == "X" ? "O" : "X";
                }
            });
            frame.add(tile);
        }
        frame.setVisible(true);

    }
    void checkWinner()
    {
        // horizontal
        if (buttons[0].getText() != "") {
            if (buttons[1].getText() == buttons[0].getText() && buttons[2].getText() == buttons[1].getText())
            {
                setWinner(buttons[0]);
                setWinner(buttons[1]);
                setWinner(buttons[2]);
                gameOver = true;
                return;
            }
        }
        
        if (buttons[3].getText() != "") {
            if (buttons[4].getText() == buttons[3].getText() && buttons[5].getText() == buttons[4].getText())
            {
                setWinner(buttons[3]);
                setWinner(buttons[4]);
                setWinner(buttons[5]);
                gameOver = true;
                return;
            }
        }

        if (buttons[6].getText() != "") {
            if (buttons[7].getText() == buttons[6].getText() && buttons[8].getText() == buttons[7].getText())
            {
                setWinner(buttons[6]);
                setWinner(buttons[7]);
                setWinner(buttons[8]);
                gameOver = true;
                return;
            }
        }

        // vertical
        if (buttons[0].getText() != "") {
            if (buttons[3].getText() == buttons[0].getText() && buttons[6].getText() == buttons[3].getText())
            {
                setWinner(buttons[0]);
                setWinner(buttons[3]);
                setWinner(buttons[6]);
                gameOver = true;
                return;
            }
        }
        
        if (buttons[1].getText() != "") {
            if (buttons[4].getText() == buttons[1].getText() && buttons[7].getText() == buttons[4].getText())
            {
                setWinner(buttons[1]);
                setWinner(buttons[4]);
                setWinner(buttons[7]);
                gameOver = true;
                return;
            }
        }

        if (buttons[2].getText() != "") {
            if (buttons[5].getText() == buttons[2].getText() && buttons[8].getText() == buttons[5].getText())
            {
                setWinner(buttons[2]);
                setWinner(buttons[5]);
                setWinner(buttons[8]);
                gameOver = true;
                return;
            }
        }

        // diagonal
        if (buttons[0].getText() != "") {
            if (buttons[4].getText() == buttons[0].getText() && buttons[8].getText() == buttons[4].getText()) 
            {
                setWinner(buttons[0]);
                setWinner(buttons[4]);
                setWinner(buttons[8]);
                gameOver = true;
                return;
            }
        }

        // anti-diagonally
        if (buttons[2].getText() != "") {
            if (buttons[4].getText() == buttons[2].getText() && buttons[6].getText() == buttons[4].getText()) 
            {
                setWinner(buttons[2]);
                setWinner(buttons[4]);
                setWinner(buttons[6]);
                gameOver = true;
                return;
            }
        }
        
    }

    void setWinner(JButton tile)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
    }

    public static void main(String[] args){
        new Extreme();
    }
}