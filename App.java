import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception 
    {
        StartGame();
    }

    static void StartGame()
    {
        int boardWidth = 400;
        int boardHeight = 300;

        JFrame frame = new JFrame("Tic-Tac-Toe");
        JLabel textLabel = new JLabel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
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


        JButton regular = new JButton("Play Regular Tic-Tac-Toe");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 0, 0);
        regular.setBackground(Color.darkGray);
        regular.setForeground(Color.white);
        buttonPanel.add(regular, c);

        JButton threeOnly = new JButton("Play 3-At-A-Time");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5, 0, 0, 0);
        threeOnly.setBackground(Color.darkGray);
        threeOnly.setForeground(Color.white);
        buttonPanel.add(threeOnly, c);

        JButton RandomAI = new JButton("Play against Random AI");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(5, 0, 0, 0);
        RandomAI.setBackground(Color.darkGray);
        RandomAI.setForeground(Color.white);
        buttonPanel.add(RandomAI, c);

        JButton AIWins = new JButton("Play Perfect AI (WIP)");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(5, 0, 0, 0);
        AIWins.setBackground(Color.darkGray);
        AIWins.setForeground(Color.white);
        buttonPanel.add(AIWins, c);

        JButton extreme = new JButton("Play Extreme Tic-Tac-Toe (WIP)");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(5, 0, 0 ,0);
        extreme.setBackground(Color.darkGray);
        extreme.setForeground(Color.white);
        buttonPanel.add(extreme, c);

        frame.add(buttonPanel, BorderLayout.CENTER);


        regular.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chooseMode(0);
            }
        });

        threeOnly.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chooseMode(1);
            }
        });

        RandomAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int choice = JOptionPane.showConfirmDialog(frame, "Do you want to go first?", "Choose Turn", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                    chooseMode(2);
                else if (choice == JOptionPane.NO_OPTION)
                    chooseMode(3);
            }
        });

        // AIWins.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e)
        //     {
        //         chooseMode(4);
        //     } 
        // });

        // extreme.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e)
        //     {
        //         chooseMode(5);
        //     }
        // });
    }

    static void chooseMode(int mode)
    {
        switch(mode)
        {
            case 0:
                TicTacToe ticTacToe = new TicTacToe();
                break;
            case 1:
                ThreeOnly threeOnly = new ThreeOnly();
                break;
            case 2:
                RandomAI randomAIFirst = new RandomAI(true);
                break;
            case 3:
                RandomAI randomAISecond = new RandomAI(false);
                break;
            // case 4:
            //     PerfectAI perfectAIFirst = new PerfectAI(false);
            //     break;
            // case 5:
            //     Extreme extreme = new Extreme();
            //     break;
        }
    }
}
