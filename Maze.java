//*** Maze
//*** Forouraghi

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

//***********************************************************************
public class Maze extends JFrame
{
   //*** can keep track of visited cell positions here
   static boolean [][] visited = new boolean[19][20];

   //*** the maze itself
   //***    0 means Power Pellet
   //***    1 means wall
   //***    2 means Stripes
   //***    3 means Pirate
   static int [][] mazePlan =
      {
         {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
         {1,0,1,0,0,3,0,0,0,0,0,0,0,0,0,0,1,0,1},
         {1,0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0,1},
         {1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
         {1,0,0,0,0,0,0,0,0,1,2,0,0,0,0,0,3,0,1},
         {1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
         {1,1,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,1},
         {1,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,1},
         {1,2,1,1,1,0,0,0,0,3,0,0,0,0,0,0,0,0,1},
         {1,0,0,0,1,0,0,1,1,1,1,1,0,0,0,0,0,0,1},
         {1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
         {1,0,0,0,1,0,0,1,0,0,0,1,0,3,1,1,1,0,1},
         {1,0,0,0,1,0,0,1,1,1,1,1,0,0,1,0,0,3,1},
         {1,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,1,1},
         {1,0,0,3,1,0,0,1,1,1,1,1,2,0,1,0,0,0,1},
         {1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,1},
         {1,0,1,1,1,3,0,1,0,0,0,1,0,0,1,0,1,0,1},
         {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1},
         {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1},
         {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      };

   //*** set up the maze wall positions and set all visited states to false
   static MazePanel mp = new MazePanel(mazePlan, visited);

   //*** set up and display main characters' initial maze positions
   static int  ghostX = 1, ghostY = 17;              //** Ghost
   static int  pacmanX = 2, pacmanY = 1;             //*** Pacman

   //*** each maze cell is 37 pixels long and wide
   static int panelWidth = 37;

   //*** a simple random number generator for random search
   static int randomInt(int n) {return (int)(n * Math.random());}

   //******************************************************
   //*** main constructor
   //******************************************************
   public Maze()
   {
      //*** display the ghost
      mp.setupChar(ghostX, ghostY, "ghost.gif");

      //*** display Pacman
      mp.setupChar(pacmanX, pacmanY, "pacman.gif");

      //*** set up the display panel
      getContentPane().setLayout(new GridLayout());
      setSize(mazePlan[0].length*panelWidth, mazePlan[0].length*panelWidth);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      getContentPane().add(mp);
   }

   //******************************************************
   //*** a delay routine
   //******************************************************
   public void wait(int milliseconds)
   {
      try
         {Thread.sleep(milliseconds);}
      catch (Exception e)
         {}
   }


   //******************************************************
   //*** move Pacman to position (i, j)
   //******************************************************

   public void movePacman(int i, int j)
   {
      mp.setupChar(i, j, "pacman.gif");
   }


   //******************************************************
   //*** remove Pacman from position (i, j)
   //******************************************************
   public void removePacman(int i, int j)
   {
     	mp.removeChar(i, j);
   }
   
   /* check the surround is that wall or already visited
	 * if the cell is available return true else return false*/
	public static boolean isSpace(int i, int j){
		if(i < 0 || j < 0){
			return false;
		}
		return (openSpace(i, j) && !isVisited(i, j));
	}

   //******************************************************
   //*** is position (i,j) a power-pellet cell?
   //******************************************************
   public static boolean openSpace(int i, int j)
   {
      return (mazePlan[i][j] == 0);
   }

   public static boolean isVisited(int i, int j){
	   return (visited[i][j]);
   }
   
   //** set the cell has been visited */
   public static void setVisited(int i, int j){
	   visited[i][j] = true;
   }
   
   //******************************************************
   //***   MODIFY HERE --  MODIFY HERE  --  MODIFY HERE
   //******************************************************
   public static void main(String [] args)
   {
	   Cell goal = new Cell(ghostX, ghostY);
	   Cell start = new Cell(pacmanX, pacmanY, goal);
	   
       //*** create a new frame and make it visible
       Maze mz = new Maze();
       mz.setVisible(true);


       //*** Pacman's current board position
       int gbx = pacmanX, gby = pacmanY;

       ArrayList<Cell> solution = ASearch.startSearch(start, goal);
       for(int i=0; i< solution.size(); i++){
    	   Cell cell = solution.get(solution.size() -1 - i);
    	   mz.movePacman(cell.getIndexX(),cell.getIndexY());
    	   mz.wait(200);
    	   mz.removePacman(cell.getIndexX(),cell.getIndexY());
       }
       /*
       //*** exhaustively search all open spaces one row at a time
       for (gbx = 1; gbx < mazePlan.length - 1; gbx++)
          for (gby = 1; gby < mazePlan.length - 1; gby++)

             if (mz.openSpace(gbx, gby)) {
                 //*** move Pacman to new location (gbx, gby)
                 mz.movePacman(gbx, gby);


                 //*** delay updating the screen
                 //*** change this parameter as you wish
                 mz.wait(200);


                 //*** remove Pacman from location (gbx, gby)
                 mz.removePacman(gbx, gby);
             }
       */
   } // main

} // Maze

