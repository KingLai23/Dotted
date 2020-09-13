/**
* Bejeweled.java (Skeleton)
*
* This class represents a Bejeweled (TM)
* game, which allows player to make moves
* by swapping two pieces. Chains formed after
* valid moves disappears and the pieces on top
* fall to fill in the gap, and new random pieces
* fill in the empty slots.  Game ends after a
* certain number of moves or player chooses to 
* end the game.
*/

import java.awt.Color;
import java.io.*;

public class Dotted {

/* 
 * Constants
 */  
   final Color COLOUR_SELECT = new Color (213, 217, 219);

   final int CHAIN_REQ = 3;	// minimum size required to form a chain 

   final int NUMPIECESTYLE;   // number of different piece style
   final int NUMROW;		  		// number of rows in the game board
   final int NUMCOL;	 	  		// number of columns in the game boar

   final int WHITE = 7;       //number of the white piece in the iconFile array


/* 
 * Global variables
 */   
   DottedGUI gui;	            // the object referring to the GUI, use it when calling methods to update the GUI

   int board[][];		         // the 2D array representing the current content of the game board

   boolean highlighted [][];  //array that keep tracks of all the highlight slots 

   boolean firstSelection;		// indicate if the current selection is the selection of the first piece
   int slot1Row, slot1Col;		// store the location of the first selection

   int score;						// current score of the game
   int numMoveLeft;				// number of move left for the game

   boolean chainMade;         //indicates if a chain was made

   int currentPiece;          //current piece being used to detect chains **used in chainCheckHorizontal and Vertical
   int chain;                 //storing the size of the chain

   int icon;                  //new icon that fills in the slots using gui

   int icon1, icon2;          //store the icons of the selected slots
   
   boolean adjacent;          //indicates if two pieces are adjacent
   
   int moves;                 //number of moves available in one game - may change if bonus feature is used
   
   boolean clearSingleColor;  //indicates if the clear single color bonus is used
   
   int colorClear;            //indicates the color to be cleared if the clear single bonus is used
   
   boolean bonusUsed;         //indicates if a bonus has been used
   
   boolean endGame;           //indicates if end game was clicked
   
   int highscore;             //keeps track of the highscore
   
   int currentScore;          //keeps track of current score being read from highsore file

   /*************************
   * Constructor: Dotted *
   *************************/
   public Dotted(DottedGUI gui) {
      this.gui = gui;
      NUMPIECESTYLE = gui.NUMPIECESTYLE;
      NUMROW = gui.NUMROW;
      NUMCOL = gui.NUMCOL;
   
   // TO DO:  
   // - creation of arrays
   // - initialization of variables 
   // - initialization of game board (on 2D array and on GUI)
   
   	//initializing and creating the starting game board
      initBoard();
   
   	//array to keep track of which pieces are highlighted
      highlighted = new boolean [NUMROW][NUMCOL];
   
   	//initializing key variables
      moves = 15;
      score = 0;
      numMoveLeft = moves;
      
      firstSelection = false;
      
      bonusUsed = false;
      endGame = false;
      
      highscore = 0;
   
   	//setting the number of moves left on the display
      gui.setMoveLeft(numMoveLeft);
      
      //setting the score on the display
      gui.setScore (score);
   
   	//checking for any chains formed in the inital board and giving the player points without having to make a move
      cascade();
      while (chainMade == true) {
         cascade();
      }
   }
	
   /******************************************************
   * play                                                *
   * This method is called when a piece is clicked.      *
   * Parameter "row" and "column" is the location of the * 
   * piece that is clicked by the player                 *
   ******************************************************/
   public void play (int row, int column) {
    // TO DO:  implement the logic of the game     
    
      //checking if the clear single color bonus move was selected
      if (clearSingleColor) {
         //highlighting the piece the user selected so the user knows what he/she selected to clear
         gui.highlightSlot (row, column, COLOUR_SELECT); 
         
         //window telling user that the color he/she selected will be cleared and how points are awarded
         gui.showColorToClear();
         
         //unhighlighting the piece the user selected
         gui.unhighlightSlot (row, column);
      
         //storting the color the user selected so it can be used to compare with other pieces of the same color
         colorClear = board [row][column];
         
         /*running through the board and seeing which pieces are the same color and the user selected color 
         and changing it to white so it can be detected when cascading the board afterwards*/ 
         for (int i = 0; i < NUMROW; i++) {
            for (int k = 0; k < NUMCOL; k++) {
               if (board [i][k] == colorClear) {
                  board [i][k] = WHITE;
                  score+=2; //awarding the user two points per piece deleted 
               }
            }
         }
         
         //updating the score on the graphics before cascading
         gui.setScore(score);
         
         //cascading the board
         fallAndReplace();
         cascade();
         while (chainMade == true) {
            cascade();
         }
         
         //indicating that the user has already used clear single color bonus move
         clearSingleColor = false;
      
      } 
      //checking if the first selection has been made
      else if (!firstSelection) { 
         //highlighting the selected slot
         gui.highlightSlot(row, column, COLOUR_SELECT); 
      
      	//storing the location of the selected slot
         slot1Row = row;
         slot1Col = column;
      
      	//changing firstSelection to true so that the next click will go to the second selection
         firstSelection = true;
      
      } 
      /*Checking if the second selection is the same as the first, 
      if it is, that means the user wants to redo their first selection.*/
      else if (row == slot1Row && column == slot1Col) {
      	//unhighlighting the first selection
         gui.unhighlightSlot (slot1Row, slot1Col);
      
      	//setting first selection to false so the user can redo their first selecton
         firstSelection = false;
      
      } 
      //User made two different selections
      else {
         //setting firstSelection back to false so the player can make next move
         firstSelection = false;
      
         //unhighlighting the first selection
         gui.unhighlightSlot (slot1Row, slot1Col);
        
         //storing the location of the second selection
         int slot2Row = row;
         int slot2Col = column; 
      
         //checking if the selections are adjacent
         if (adjacentPieces (slot1Row, slot1Col, slot2Row, slot2Col)) {
            //storing the selected pieces in a variable
            icon1 = board [slot1Row][slot1Col]; //first selection
            icon2 = board [slot2Row][slot2Col]; //second selection
         
            //swapping the values of the selections in the actual board array
         	//second selections icon is placed into the first selections location
            board [slot1Row][slot1Col] = icon2;
         	//first selections icon is placed into the second selections location 
            board [slot2Row][slot2Col] = icon1; 
         
            //swapping the icons on the display
         	//second icon is placed into the first selection location
            gui.setPiece (slot1Row, slot1Col, icon2);
         	//first icon is placed into the second selection location
            gui.setPiece (slot2Row, slot2Col, icon1);
            
            //checking for chains formed, highlighting them, shifting the pieces down and generating new random pieces
            cascade();   
            
            //checking if a chain was formed  
            if (chainMade == true) {
               /*looping through the new board formed from shifting pieces down and checking if more chains were formed 
                within the same move*/
               while (chainMade == true) {
                  cascade();
               }     
            
            	//updating the moves left
               numMoveLeft--;
               gui.setMoveLeft(numMoveLeft);
            
            	//checking how many moves are left to determine whether or not the user used all their moves
               if (numMoveLeft == 0) {
                  gui.showGameOverMessage(score, moves);
                  gui.resetGameBoard();
                  
                  //checking if the score reached was a highscore
                  highscore(score);
               } 
               //outputting a warning window if the user has one move left 
               else if (numMoveLeft == 1) {
                  gui.showMovesWarning();
               } 
            } 
            //no chain was made, so the selections are swapped back to original positions and an error message is outputted
            else {
            	//swapping the values of the selections back to their original positions
               board [slot1Row][slot1Col] = icon1;
               board [slot2Row][slot2Col] = icon2; 
              
            	//swapping the values of the graphics back to their original positions
               gui.setPiece (slot1Row, slot1Col, icon1);
               gui.setPiece (slot2Row, slot2Col, icon2);
            
               //outputting error message
               gui.showInvalidMoveMessage();
            }                      
         }
         //The selections are not adjacent
         else {
         	//outputting error message
            gui.showInvalidMoveMessage();
         }
      
      }
   }

	/**********************************
   * METHOD: INITITALIZING THE BOARD *
   **********************************/   
   public void initBoard () {
      board = new int [NUMROW][NUMCOL];
   
      //looping thru every piece of the board and giving them an icon
      for (int i = 0; i < NUMROW; i++) {
         for (int k = 0; k < NUMCOL; k++) {
            /*The random number generator code uses 'NUMPIECESTYLE-1' because the there is an 8th piece, which
            is a white circle, that is used to determine empty slots when shifting the array down to fill in with
            new random pieces. When you generate a piece, you want to exclude the 8th piece, which is why the code
            is 'NUMPIECESTYLE-1'*/
            icon = (int)(Math.random() * (NUMPIECESTYLE - 1));
            board [i][k] = icon;
            gui.setPiece (i, k, icon);
         }
         
      }
   
   
   } 

	/**********************************************
   * METHOD: CHECKING IF TWO PIECES ARE ADJACENT *
   **********************************************/ 
   public boolean adjacentPieces (int row1, int col1, int row2, int col2) {
      //setting the variable that idicates whether or not the pieces are adjacent to false
      adjacent = false;
   
      //checking if the row number of the pieces are the same
      if (row1 == row2) {
         /*checking if the absolute value of the differences of the columns of the two pieces is one,
         if it is, that means the pieces are adjacent*/
         if ((Math.abs (col1 - col2)) == 1) {
            adjacent = true;
         }
      } 
      //checking if the column number of the pieces are the same
      else if (col1 == col2) {
         /*checking if the absolute value of the differences of the rows of the two pieces is one,
         if it is, that means the pieces are adjacent*/
         if ((Math.abs (row1 - row2)) == 1) {
            adjacent = true;
         }
      }
      
      return adjacent;
   }

	/*********************************************
   * METHOD: CHECKING FOR ALL HORIZONTAL CHAINS *
   *********************************************/ 
   public void chainCheckHorizontal () {
    	//running through all the rows
      for (int j = 0; j < NUMROW; j++) {
      	//setting the current piece im looking for to form a chain to the first piece of that row
         currentPiece = board [j][0];  
         
      	//since the current piece is set, the chain is automatically equal to one
         chain = 1; 
         
      	//running through each column of each row
         for (int k = 1; k < NUMCOL; k++) {
         	/*checking if the current slot being ran through is equal to the currentPiece and increasing
         	the chain size by one if it is equal*/
            if (board [j][k] == currentPiece) {
               chain++;            
            } 
            //the current slot is not equal to the currentPiece
            else {
            	/*checking if the chain that was made previously to the slot being looked at was more or equal
            	to the minimum requirement*/
               if (chain >= CHAIN_REQ) {
               	//highlighting the chain
               	/*since  I know the location of current slot, and I that the pieces behind the slot are
               	part of the chain. I can use the chain size to figure out how many pieces behind the slot
               	are apart of the chain, and highlight them.*/
                  for (int i = (k-1); i >= (k - chain); i--) {
                     gui.highlightSlot(j, i, COLOUR_SELECT);
                  	
                  	/*setting the slot that is highlighted in another array to true, so that when I
                  	run through the array (which is the same size as the board), I will know which
                  	slots are hihglighted, which means they must be removed when I shift the board down.*/
                     highlighted [j][i] = true;
                  }
               	
               	/*a chain was made, so a variable will store this information so that the program knows
               	the users move was valid*/
                  chainMade = true;          
                  gui.showChainSizeMessage(chain);
               }
            	
            	/*since the current slot is not the same as the piece before and is not part of the chain, the
            	currentPiece is set to current slot, so the program will now look for pieces that are the same
            	as the current slot*/
               currentPiece = board [j][k];
            	
            	//setting the chain size to one since a new piece is being looked for
               chain = 1;
            }
         
         	/*since my program only outputs the chain size after a piece that is not equal to the chain pieces
         	is detected, the last piece of the array will never be accounted for apart of an array, since
         	there are no pieces after it to detect. This code will check for the last piece of the board and 
         	whether or not a chain formed included the last piece using the same logic as the code above^^.*/
            if (k == (NUMCOL-1)) {
               if (chain >= CHAIN_REQ) {
                  for (int i = k; i >= (k - chain + 1); i--) {
                     gui.highlightSlot(j, i, COLOUR_SELECT);
                  
                     highlighted [j][i] = true;
                  }
                  chainMade = true;
                  gui.showChainSizeMessage(chain);
               }
            }
         }
      } 
   }

	/*******************************************
   * METHOD: CHECKING FOR ALL VERTICAL CHAINS *
   *******************************************/ 
	/*the logic of checking vertical chains is the same as the logic for checking horizontal chains,
	check the commenting in chainCheckHorizontal for a deeper explaination of the code*/
   public void chainCheckVertical () {
    	//running through all the columns
      for (int j = 0; j < NUMCOL; j++) {
      	//setting the current piece im looking for to form a chain to the first piece of that column
         currentPiece = board [0][j];  
         
      	//since the current piece is set, the chain is automatically equal to one
         chain = 1; 
         
      	//running through each row of the column
         for (int k = 1; k < NUMROW; k++) {
         	//checking if the current slot is equal to the current piece being checked for
            if (board [k][j] == currentPiece) {
               chain++;               
            } 
            //the current slot is not equal to the current piece
            else {
            	//checking if the chain made is equal to the minimum requirement or more
               if (chain >= CHAIN_REQ) {
               	//highlighting the chain
                  for (int i = (k-1); i >= (k - chain); i--) {
                     gui.highlightSlot(i, j, COLOUR_SELECT);
                  
                     highlighted [i][j] = true;
                  }
                  chainMade = true;
                  gui.showChainSizeMessage(chain);
               }
            	
            	//setting the current piece being looked for to the current slot
               currentPiece = board [k][j];
               chain = 1;
            }
         
         	//checking if the last piece of the column was part of a chain
            if (k == (NUMROW-1)) {
               if (chain >= CHAIN_REQ) {
                  for (int i = k; i >= (k - chain + 1); i--) {
                     gui.highlightSlot(i, j, COLOUR_SELECT);
                  
                     highlighted [i][j] = true;
                  }
                  chainMade = true;
                  gui.showChainSizeMessage(chain);
               }
            }
         }
      }        
   }

	/*********************************************************************************************
   * METHOD: SHIFTING THE GAME BOARD PIECES DOWN AND FILLING IN EMPTY PIECES WITH A RANDOM ICON *
   *********************************************************************************************/ 
   public void fallAndReplace() {
   	//setting highlighted pieces to white
   	//running through every piece of the highlighted array to se which pieces are highlighted
      for (int i = (NUMROW - 1); i >= 0; i--) {
         for (int j = 0; j < NUMCOL; j++) {
         	/*setting the current slot in the board array to WHITE so I know which slots need to be shifted
         	and whether or not I need to shift white pieces down*/
            if (highlighted [i][j] == true) {
               board [i][j] = WHITE;
               gui.unhighlightSlot (i, j);
            }
         }
      }
   
   	//Shifting the slots of the board down
   	/*	when I shift a column down with more than one white piece, the bottom slot can remain white, since the
   	piece above it might have been white aswell, therefore, I need to run through the entire board multiple
    	times to ensure that all white pieces have been shifted down. Since there can be up to 8 white peices
   	on top of each other, the program runs through the entire board 8 times.*/
      for (int m = 0; m < NUMROW; m++) {
      	//running through the entire array
         for (int i = (NUMROW - 1); i >= 0; i--) {
            for (int j = 0; j < NUMCOL; j++) {
            	/*checking if the slot being ran through is a white piece, if it is, 
            	then its column needs to be shifted down*/
               if (board [i][j] == WHITE) {  
               	//shifting the pieces above the white piece down once
               	/*since I am only shifting pieces down of the same column, the column never changes, 
               	and only the row changes. So to determine the size of the loop I used to shift the
               	pieces down, I set the size of my loop to the current row the white piece, since all
               	the rows above the white piece need to be shifted (k = i).*/               
                  for (int k = i; k >= 0; k--) {
                  	/*checking if I reached the end of the loop. The end of the loop is the top piece
                  	of the column, which must be white, so I set the last piece of the row to white.*/
                     if (k == 0) {
                        board [k][j] = WHITE;
                     } 
                     //setting the piece to be equal to the piece above it
                     else {
                        board [k][j] = board [k-1][j];
                        icon = board [k][j];
                        gui.setPiece (k, j, icon);
                     }
                  }
               }
            }
         }
      }
   
   	//Assigning empty pieces with a new icon
   	//running through every piece of the board
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
         	/*if the piece is white, that means it is empty, which means an icon needs to be generated to 
         	fill it in*/
            if (board [i][j] == WHITE) {
            	/*The random number generator code uses 'NUMPIECESTYLE-1' because the there is an 8th piece which
            	is a white white, that is used to determine empty slots when shifting the array down to fill in with
            	new random pieces. When you generate a piece, you want to exclude the 8th piece, which is why the code
            	is 'NUMPIECESTYLE-1'*/
            	
            	/*generating random piece and storing it in the current slot of the board, aswell as to
            	display it in the graphis*/
               icon = (int)(Math.random() * (NUMPIECESTYLE - 1));
               board [i][j] = icon;
               gui.setPiece (i, j, icon);
            }
         }
      }
   }
   
   /****************************************************************************************************
   * METHOD: COMBINATION OF CHAIN CHECK METHODS AND THE FALLANDREPLACE METHOD TO A SINGLE METHOD       * 
   * - combining these methods allow me to call them all at once which lets me to do cascading simpler *
   ****************************************************************************************************/ 
   /*This method checks for all chains that formed, shifts the board down accordingly, and updates the score*/
   public void cascade () {
   	//resetting all pieces in the highlight array to false
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            highlighted [i][j] = false;
         }
      }
      
   	//resetting variables that tell you if a chain was made to false
      chainMade = false;
      
   	//checking for chains and highlighting them if they are formed
      chainCheckHorizontal();
      chainCheckVertical();
   	
   	//updating the score variable if a chain was formed
      if (chainMade == true) {
      	//checking for highlighted pieces, each highlight is one point
         for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
               if (highlighted [i][j] == true) {
                  score++;
               }
            }
         }
            
      	//updating the score on the graphics
         gui.setScore(score); 
            
      	//shifting all pieces down and replacing the empty slots with a random piece
         fallAndReplace();
      }
   }

   /********************************************
   * METHOD: KEEPS TRACK AND OUTPUTS HIGHSCORE *
   ********************************************/ 
   public void highscore (int score) {
      //checking if the score in from the current game is greater than the highscore
      if (score > highscore) {
         highscore = score;
         gui.showNewHighscore(score);
      }
   }

   /**************************************************
   * METHOD: CALLED WHEN HIGHSCORE BUTTON IS CLICKED *
   **************************************************/ 
   public void displayHighscore () {
      //displaying the highscore on a window
      gui.showHighscore (highscore);
   }

   /******************************************************
   * endGame                                             *
   * This method is called when the player clicks on the *
    * "End Game" button                                  *
   ******************************************************/
   public void endGame (){
      //checking if end game has been clicked already
      if (!endGame) {
         //display the score and numbe of moves used
         gui.showGameOverMessage(score, (moves - numMoveLeft));
         
         //gui.resetGameBoard();
      
         endGame = true;
         
         highscore (score);
      }
      //end game has been clicked already 
      else if (endGame) {
         gui.showStartNewGameMessageTwo();
      }
   }
   
   /******************************************************
   * METHOD: CALLED WHEN "ADD 5 MOVES" BUTTON IS CLICKED *
   ******************************************************/ 
   /*Add 5 Moves is a bonus move which is used to add 5 more moves to the playes move count*/
   //only one bonus can be used per game
   public void moreMoves (){
      // checking if user clicked on end game yet
      if (endGame) {
         gui.showStartNewGameMessage();
      } 
      //checking if user already used a bonus
      else if (!bonusUsed) {
         gui.showMovesAdded();
      
         gui.setMoveLeft(numMoveLeft);
      
         numMoveLeft += 5;
         moves += 5;
      
         gui.setMoveLeft (numMoveLeft);
      
         bonusUsed = true;
      }  
      //user has used a bonus already 
      else if (bonusUsed){
         gui.showBonusUsed();
      }
   }
   
   /*************************************************************
   * METHOD: CALLED WHEN "CLEAR SINGLE COLOR" BUTTON IS CLICKED *
   *************************************************************/ 
   /*Clear Single Color is a bonus move used to clear all icons of a color. Each icon is worth 2 points instead of 1*/
   //only one bonus can be used per game
   public void clearSingleColor (){
      //checking if user clicked end game
      if (endGame) {
         gui.showStartNewGameMessage();
      } 
      //checking if user already used a bonus
      else if (!bonusUsed) {
         gui.showClearColorInstructions();
      
         clearSingleColor = true;
      
         bonusUsed = true;
      } 
      //user used a bonus already
      else if (bonusUsed) {
         gui.showBonusUsed();
      }
   }
   
   /**************************************************
   * METHOD: CALLED WHEN "NEW GAME" BUTTON IS CLICKED*
   **************************************************/ 
   //New Game starts a brand new game.
   //Everything is re-initialized.
   public void newGame (){
      gui.showNewGameMessage();
   
      //initializing and creating the starting game board
      initBoard();
   
   	//initializing key variables
      moves = 15;
      score = 0;
      numMoveLeft = moves;
      
      firstSelection = false;
      bonusUsed = false;
      endGame = false;
      clearSingleColor = false;
   
   	//setting the number of moves left and score on the display
      gui.setMoveLeft(numMoveLeft);
      gui.setScore (score);
   
   	//continously checking for any chains formed in the inital board and giving points without having to make a move
      cascade();
      while (chainMade == true) {
         cascade();
      }
   }
   
   /**********************************************
   * METHOD: CALLED WHEN "EXIT" BUTTON IS CLICKED*
   **********************************************/
   //Exit button closes the entire window
   public void exitGame (){
      System.exit (0);
   }
}