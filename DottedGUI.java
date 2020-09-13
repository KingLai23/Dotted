/**
 * BejeweledGUI.java (Skeleton)
 * Provide the GUI for the Bejeweled game
 */
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class DottedGUI {
	// the name of the configuration file
   private final String CONFIGFILE = "config.txt";
   private final Color BACKGROUNDCOLOUR = new Color(255, 255, 255);
	
   private JLabel[][] slots;
   private JFrame mainFrame;
   private ImageIcon[] pieceIcon;
   private JButton endGameButton;
   private JTextField score;
   private JTextField numMoveLeft;
	
   //additional buttons I made for the game
   private JButton moreMovesButton;
   private JButton clearSingleColorButton;
   private JButton highscoreButton;
   private JButton newGameButton;
   private JButton exitButton;
   
   private String logoIcon;
   private String[] iconFile;
	
   //Number of different piece styles
   /*The number of piece styles is 8 because I added an 8th piece to the iconFile array.
   The 8th piece is used as an indicator of an empty piece when I shift the array down 
   and fill in empty slots with random pieces*/
   public final int NUMPIECESTYLE = 8; 

   //Number of rows on the game board
   public final int NUMROW = 8; 

   //Number of colums on the game board
   public final int NUMCOL = 8; 


/**
* Constants defining the demensions of the different components
* on the GUI
*/    
   private final int PIECESIZE = 70;
   private final int PLAYPANEWIDTH = NUMCOL * PIECESIZE;
   private final int PLAYPANEHEIGHT = NUMROW * PIECESIZE;

   private final int INFOPANEWIDTH = 2 * PIECESIZE;
   private final int INFOPANEHEIGHT = PLAYPANEHEIGHT;

   private final int LOGOHEIGHT = 2 * PIECESIZE;
   private final int LOGOWIDTH = PLAYPANEWIDTH + INFOPANEWIDTH;

   private final int FRAMEWIDTH = (int)(LOGOWIDTH * 1.07);
   private final int FRAMEHEIGHT = (int)((LOGOHEIGHT + PLAYPANEHEIGHT) * 1.1);

// Constructor:  BejeweledGUI
// - intialize variables from config files
// - initialize the imageIcon array
// - initialize the slots array
// - create the main frame
   public DottedGUI () {
      initConfig();
      initImageIcon();
      initSlots();
      createMainFrame();
   }

   private void initConfig() {
    	// TO DO: 
   	// initialize the following variables with information read from the config file 
   	// - logoIcon
   	// - iconFile  
   	
      try {
         String input;
      	
         BufferedReader in = new BufferedReader(new FileReader("config.txt"));
      
         //first input read from text file which is the file name of the banner
         input = in.readLine(); 
      
         //assigning the file name of the banner to the logoIcon variable
         logoIcon = input; 
      
         //Creating the array which stores the icons.
         iconFile = new String [NUMPIECESTYLE]; 
      
         //reading in the next 8 lines of the text file which contains the file names of the icons
         for (int i = 0; i < NUMPIECESTYLE; i++) { 
            input = in.readLine(); //reading in a line of config.txt
            iconFile [i] = input; //assigning the file name of the icon read to an element in the iconFile array
         }
       
         in.close();
      } 
      catch (IOException iox) {
         System.out.println("Error reading file.");
      }
   }

// initImageIcon
// Initialize pieceIcon arrays with graphic files
   private void initImageIcon() {
      pieceIcon = new ImageIcon[NUMPIECESTYLE];
      for (int i = 0; i < (NUMPIECESTYLE); i++) {
         pieceIcon[i] = new ImageIcon(iconFile[i]);
      }
   }

// initSlots
// initialize the array of JLabels
   private void initSlots() {
      slots = new JLabel[NUMROW][NUMCOL];
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j] = new JLabel ();
         // slots[i][j].setFont(new Font("SansSerif", Font.BOLD, 18));
            slots[i][j].setPreferredSize(new Dimension(PIECESIZE, PIECESIZE));
            slots [i] [j].setHorizontalAlignment (SwingConstants.CENTER);      
         }
      }
   }

// createPlayPanel
   private JPanel createPlayPanel() {
      JPanel panel = new JPanel(); 
      panel.setPreferredSize(new Dimension(PLAYPANEWIDTH, PLAYPANEHEIGHT));
      panel.setBackground(BACKGROUNDCOLOUR);
      panel.setLayout(new GridLayout(NUMROW, NUMCOL));
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            panel.add(slots[i][j]);
         }
      }
      return panel;    
   }

   // createInfoPanel
   private JPanel createInfoPanel() {
   
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(INFOPANEWIDTH, INFOPANEHEIGHT));
      panel.setBackground (BACKGROUNDCOLOUR);
      panel.setBorder (new LineBorder (Color.white)); 
   
      Font headingFont = new Font ("Serif", Font.PLAIN, 24);
      Font regularFont = new Font ("Serif", Font.BOLD, 16);
   
      // Create a panel for the scoreboard
      JPanel scorePanel = new JPanel();
      scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
      scorePanel.setBackground(BACKGROUNDCOLOUR);
   
      // Create the label to display "Score" heading
      JLabel scoreLabel = new JLabel ("     Score     ", JLabel.CENTER);
      scoreLabel.setFont(headingFont);
      scoreLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      //nextLabel.setForeground(Color.white);
   
      score = new JTextField();
      score.setFont(regularFont);
      score.setText("0");
      score.setEditable(false);
      score.setHorizontalAlignment (JTextField.CENTER);
      score.setBackground(BACKGROUNDCOLOUR);
      
      scorePanel.add(scoreLabel);
      scorePanel.add(score);
   
      JPanel moveLeftPanel = new JPanel();
      moveLeftPanel.setLayout(new BoxLayout(moveLeftPanel, BoxLayout.Y_AXIS));
      moveLeftPanel.setBackground(BACKGROUNDCOLOUR);
   
      // Create the label to display "Moves Left" heading
      JLabel moveLeftLabel = new JLabel ("Moves Left", JLabel.CENTER);
      moveLeftLabel.setFont(headingFont);
      moveLeftLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
   
      numMoveLeft = new JTextField();
      numMoveLeft.setFont(regularFont);
      numMoveLeft.setText("20");
      numMoveLeft.setEditable(false);
      numMoveLeft.setHorizontalAlignment (JTextField.CENTER);
      numMoveLeft.setBackground(BACKGROUNDCOLOUR);
      
      JLabel emptyLabel1 = new JLabel (" ", JLabel.CENTER);
      emptyLabel1.setFont(headingFont);
      emptyLabel1.setAlignmentX (Component.CENTER_ALIGNMENT); 
          
      JLabel emptyLabel2 = new JLabel (" ", JLabel.CENTER);
      emptyLabel2.setFont(headingFont);
      emptyLabel2.setAlignmentX (Component.CENTER_ALIGNMENT);
      
      moveLeftPanel.add(emptyLabel1);
      moveLeftPanel.add(moveLeftLabel);
      moveLeftPanel.add(numMoveLeft);
      moveLeftPanel.add(emptyLabel2);
   
      endGameButton = new JButton("End Game");
   
      //additional buttons I added
      moreMovesButton = new JButton ("Add 5 Moves");
      clearSingleColorButton = new JButton ("Clear A Color");
      highscoreButton = new JButton ("Highscore");
      newGameButton = new JButton ("New Game");
      exitButton = new JButton ("Exit");
   
      panel.add(scorePanel);
      panel.add(moveLeftPanel);
      panel.add(endGameButton);
      
      //additional buttons I made for the game
      panel.add(moreMovesButton);
      panel.add(clearSingleColorButton);
      panel.add(highscoreButton);
      panel.add(newGameButton);
      panel.add(exitButton);    
   	    
      return panel;
   }

// createMainFrame
   private void createMainFrame() {
   
   // Create the main Frame
      mainFrame = new JFrame ("Dotted");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = (JPanel)mainFrame.getContentPane();
      panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
   
   // Create the panel for the logo
      JPanel logoPane = new JPanel();
      logoPane.setPreferredSize(new Dimension (LOGOWIDTH, LOGOHEIGHT));
      logoPane.setBackground (BACKGROUNDCOLOUR);
      
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon(logoIcon));
      logoPane.add(logo);
   
   // Create the bottom Panel which contains the play panel and info Panel
      JPanel bottomPane = new JPanel();
      bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.X_AXIS));
      bottomPane.setPreferredSize(new Dimension(PLAYPANEWIDTH + INFOPANEWIDTH, PLAYPANEHEIGHT));
      bottomPane.add(createPlayPanel());
      bottomPane.add(createInfoPanel());
   
   // Add the logo and bottom panel to the main frame
      panel.add(logoPane);
      panel.add(bottomPane);
   
      mainFrame.setContentPane(panel);
      //mainFrame.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
      mainFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
      mainFrame.setVisible(true);
      mainFrame.setResizable(false);
      mainFrame.setDefaultCloseOperation(2);
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getRow(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = i;
            }
         }
      }
      return result;
   }

/**
* Returns the column number of where the given JLabel is on
* 
* @param  label the label whose column number to be requested
* @return the column number
*/
   public int getColumn(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = j;
            }
         }
      }
      return result;
   }

   public void addListener (DottedListener listener) {
   	// add listener for each slot on the game board
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j].addMouseListener (listener);
         }
      }
      
   	// add listener for the end game button
      endGameButton.addMouseListener(listener);
      
      // add listener for more moves button
      moreMovesButton.addMouseListener(listener);
      
      // add listener for clear single color button
      clearSingleColorButton.addMouseListener(listener);
      
      // add listener for highscore button
      highscoreButton.addMouseListener(listener);
      
      // add listener for new game button
      newGameButton.addMouseListener(listener);
      
      // add listener for exit button
      exitButton.addMouseListener(listener);
   }

/**
* Display the specified player icon on the specified slot
* 
* @param row row of the slot
* @param col column of the slot
* @param piece index of the piece to be displayed
*/
   public void setPiece(int row, int col, int piece) {
      slots[row][col].setIcon(pieceIcon[piece]);
   }

/**
* Highlight the specified slot with the specified colour
* 
* @param row row of the slot
* @param col column of the slot
* @param colour colour used to highlight the slot
*/
   public void highlightSlot(int row, int col, Color colour) {
      slots[row][col].setBorder (new LineBorder (colour, 5));   
   }

/**
* Unhighlight the specified slot to the default grid colour
* 
* @param row row of the slot
* @param col column of the slot
*/
   public void unhighlightSlot(int row, int col) {
      slots[row][col].setBorder (new LineBorder (BACKGROUNDCOLOUR));   
   }

/**
* Display the score on the corresponding textfield
* 
* @param point the score to be displayed
*/
   public void setScore(int point) {
      score.setText(point+"");
   }
	
/**
* Display the number of moves left on the corresponding textfield
* 
* @param num number of moves left to be displayed
*/
   public void setMoveLeft(int num) {
      numMoveLeft.setText(num+"");
   }	
  
/**
* Reset the game board (clear all the pieces on the game board)
* 
*/
   public void resetGameBoard() {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].setIcon(null);
         }
      }
   }
      

   // Display a pop up window displaying the message warning the user they have one move left
   public void showMovesWarning(){
      JOptionPane.showMessageDialog(null, "ONE MOVE LEFT!", "WARNING", JOptionPane.PLAIN_MESSAGE, null); 
   }
   

   // Display a pop up window displaying the user has set a new highscore
   public void showNewHighscore(int score){
      JOptionPane.showMessageDialog(null, "NEW HIGHSCORE: " + score, "HIGHSCORE", JOptionPane.PLAIN_MESSAGE, null); 
   }
   

   // Display a pop up window displaying the user has set a new highscore
   public void showHighscore(int highscore){
      JOptionPane.showMessageDialog(null, "Highscore: " + highscore, "HIGHSCORE", JOptionPane.PLAIN_MESSAGE, null); 
   }
   

   // Display a pop up window telling the user to start a new game
   public void showStartNewGameMessage(){
      JOptionPane.showMessageDialog(null, "Start a new game to use bonus moves!", "Nope!", JOptionPane.PLAIN_MESSAGE, null); 
   }
   

   // Display a secondary pop up window telling the user to start a new game
   public void showStartNewGameMessageTwo(){
      JOptionPane.showMessageDialog(null, "Start a new game to play again!", "Nope!", JOptionPane.PLAIN_MESSAGE, null); 
   }
   
   
   // Display a pop up window telling the user a new game has started
   public void showNewGameMessage(){
      JOptionPane.showMessageDialog(null, "A new game will start!", "New Game", JOptionPane.PLAIN_MESSAGE, null); 
   }  
   

   // Display a pop up window telling the user they have used their bonus move
   public void showBonusUsed(){
      JOptionPane.showMessageDialog(null, "You can only use one bonus move per game!", "BONUS MOVE USED", JOptionPane.PLAIN_MESSAGE, null); 
   }
   
   
   // Display a pop up window teling the user the color they have selected will be removed
   public void showColorToClear(){
      JOptionPane.showMessageDialog(null, "Selected color will be cleared for DOUBLE POINTS!", "CLEAR SINGLE COLOR", JOptionPane.PLAIN_MESSAGE, null); 
   }
   

   // Display a pop up window asking user to select a color to delete
   public void showClearColorInstructions(){
      JOptionPane.showMessageDialog(null, "Click on a piece on the board.\nAll pieces on the board of the same color will be cleared.\nEach cleared piece is worth TWO POINTS.", "BONUS MOVE", JOptionPane.PLAIN_MESSAGE, null); 
   }
   
   
   // Display a pop up window telling the user that there will be 5 moves added
   public void showMovesAdded(){
      JOptionPane.showMessageDialog(null, "5 moves will be added.", "BONUS MOVE", JOptionPane.PLAIN_MESSAGE, null); 
   }
   
   
   // Display a pop up window displaying message about invalid move
   public void showInvalidMoveMessage(){
      JOptionPane.showMessageDialog(null, " This move is invalid", "Invalid Move", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the size of the chain(s) that is (are) formed after the swap
* 
* @param chainSize the size of the chain(s) that is (are) formed
*/
   public void showChainSizeMessage(int chainSize){
      JOptionPane.showMessageDialog(null, "Chain(s) with total size of " + chainSize + " is (are) formed.", "Chain Formed!", JOptionPane.PLAIN_MESSAGE, null); 
   }

/**
* Display a pop up window specifying the game is over with the score and number of moves used
* 
* @param point the score earned in the game
* @param numMove the number of moves used in the game
*/
   public void showGameOverMessage(int point, int numMove){
      JOptionPane.showMessageDialog(null, "Game Over!\nYour score is " + point + " in " + numMove + " moves.", "Game Over!", JOptionPane.PLAIN_MESSAGE, null); 
   }

   public static void main (String[] args) {
      DottedGUI gui = new DottedGUI ();
      Dotted game = new Dotted (gui);
      DottedListener listener = new DottedListener (game, gui);
   }
}