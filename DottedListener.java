import javax.swing.*;
import java.awt.event.*;
import java.awt.Component;

public class DottedListener implements MouseListener
{
   private DottedGUI gui;
   private Dotted game;
   public DottedListener (Dotted game, DottedGUI gui) {
      this.game = game;
      this.gui = gui;
      gui.addListener (this);
   }

   //Constants storing the vertical location of the 5 buttons
   final int ENDGAMELOCATE = 196;
   final int MOREMOVESLOCATE = 227;
   final int CLEARSINGLECOLORLOCATE = 258;
   final int HIGHSCORELOCATE = 289;
   final int NEWGAMELOCATE = 320;
   final int EXITLOCATE = 351;

   public void mouseClicked (MouseEvent event) {
      Component objectClicked = event.getComponent();
   
      if (objectClicked instanceof JLabel) {
         JLabel label = (JLabel) objectClicked;		
         int row = gui.getRow(label);
         int column = gui.getColumn (label);
         game.play(row, column); 
      } //end game option
      else if (objectClicked instanceof JButton && event.getComponent().getY() == ENDGAMELOCATE) {
         game.endGame();
      } //add 5 moves bonus
      else if (objectClicked instanceof JButton && event.getComponent().getY() == MOREMOVESLOCATE) {
         game.moreMoves();
      } //clear single color bonus
      else if (objectClicked instanceof JButton && event.getComponent().getY() == CLEARSINGLECOLORLOCATE) {
         game.clearSingleColor();
      } //new game option
      else if (objectClicked instanceof JButton && event.getComponent().getY() == NEWGAMELOCATE) {
         game.newGame();
      } //exit the window
      else if (objectClicked instanceof JButton && event.getComponent().getY() == EXITLOCATE) {
         game.exitGame();
      }
      else if (objectClicked instanceof JButton && event.getComponent().getY() == HIGHSCORELOCATE) {
         game.displayHighscore();
      }
   
   }

   public void mousePressed (MouseEvent event) {
   }

   public void mouseReleased (MouseEvent event) {
   }

   public void mouseEntered (MouseEvent event) {
   }

   public void mouseExited (MouseEvent event) {
   }
}
