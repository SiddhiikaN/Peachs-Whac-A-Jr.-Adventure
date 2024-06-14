import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Peach's Whac-A-Jr. Adventure");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon princessIcon; //moleIcon
    ImageIcon bowserIcon; //plantIcon

    Set<JButton> currPrincessTiles = new HashSet<>(); // Multiple princess tiles
    Set<JButton> currBowserTiles = new HashSet<>(); // Multiple bowser tiles

    Random random = new Random();
    Timer setPrincessTimer; //setMoleTimer
    Timer setBowserTimer; //setPlantTimer
    int score = 0;


    WhacAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        Image bowserImg = new ImageIcon(getClass().getResource("./bowser.png")).getImage();
        bowserIcon = new ImageIcon(bowserImg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));

        Image princessImg = new ImageIcon(getClass().getResource("./princess.png")).getImage();
        princessIcon = new ImageIcon(princessImg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));


        for (int i = 0; i < 9; i++){
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //tile.setIcon(bowserIcon);
            tile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (currPrincessTiles.contains(tile)) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                        tile.setIcon(null);
                        currPrincessTiles.remove(tile);
                    }
                    else if (currBowserTiles.contains(tile)) {
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setPrincessTimer.stop();
                        setBowserTimer.stop();
                        for( int i = 0; i < 9; i++ ) {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        setPrincessTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (!currPrincessTiles.isEmpty()) {
                    for (JButton tile : currPrincessTiles) {
                        tile.setIcon(null);
                    }
                    currPrincessTiles.clear();
                }

                int numOfPrincesses = random.nextInt(3) + 1; // Randomly 1 to 3 princesses
                for (int i = 0; i < numOfPrincesses; i++) {
                    int num = random.nextInt(9);
                    JButton tile = board[num];

                    //if tile is occupied by bowser, skip tile for this turn
                    if (currBowserTiles.contains(tile) || currPrincessTiles.contains(tile)) continue;

                    currPrincessTiles.add(tile);
                    tile.setIcon(princessIcon);
                }
            }
        });

        setBowserTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (!currBowserTiles.isEmpty()) {
                    for (JButton tile : currBowserTiles) {
                        tile.setIcon(null);
                    }
                    currBowserTiles.clear();
                }

                int numOfBowsers = random.nextInt(3) + 1; // Randomly 1 to 3 bowsers
                for (int i = 0; i < numOfBowsers; i++) {
                    int num = random.nextInt(9);
                    JButton tile = board[num];

                    if (currPrincessTiles.contains(tile) || currBowserTiles.contains(tile)) continue;

                    currBowserTiles.add(tile);
                    tile.setIcon(bowserIcon);
                }
            }
        });

        setPrincessTimer.start();
        setBowserTimer.start();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WhacAMole();
    }
}
