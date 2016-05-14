/**
*Lior Vansteenkiste
*Gallatin 2nd
*GameBoard
*The meat of the program, a 9x9 array of buttons that store values and deal with user interaction.
**/
//package Sudoku;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * a frame which contains a 9x9 array of buttons, 3 buttons with 
 * other functionality
 * @author liort_000
 *
 */
public class GameBoard extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Sudoku";
	
	JButton [][] board=new JButton[9][9];
	JButton checkSolution, clearBoard, newBoard;
	//NEED TIMER AS WELL
	int[][] row, column, grid;
	/**
	 * creates the empty game board, makes 9 3x3 grids and adds them
	 * in addition to 3 buttons with various functionality
	 */
	public GameBoard()
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle(TITLE);
		this.setResizable(false);
		this.setLocation(350,175);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridLayout(4,3,3,3));
		
		clearBoard=new JButton("New Board");
		checkSolution=new JButton("Check Answers");	
		newBoard=new JButton("Solve Current Board");
		grid=new int[9][9];
		row=new int[9][9];
		column=new int[9][9];
		
		checkSolution.addActionListener(this);
		clearBoard.addActionListener(this);
		newBoard.addActionListener(this);
		
		for(int x=0;x<9;x++)
		{
			JPanel gridPanel=new JPanel();
			gridPanel.setLayout(new GridLayout(3,3));
			for(int i=0;i<9;i++)
			{
				JButton button= new JButton("");//COMMENT OUT TO AUTOFILL
				//int rand=(int)(Math.random()*9)+1;//UNCOMMENT IF YOU WANT TO AUTOFILL BOARD WITH VALUES
				//JButton button= new JButton(""+rand);//UNCOMMENT IF YOU WANT TO AUTOFILL BOARD WITH VALUES
				board[x][i]=button;
				button.addActionListener(this);
				gridPanel.add(button);
			}
			this.add(gridPanel);
		}
				
		this.add(checkSolution);
		this.add(clearBoard);
		this.add(newBoard);
		newBoard(0,0);
		for(int i=0;i<50;i++)
		{
			int q= (int)(Math.random()*9);
			int w= (int)(Math.random()*9);				
			board[q][w].setText("");
		}
		this.setVisible(true);
	}
	/**
	 * fills the board with a complete board then removes until the board
	 * cannot be solved, creates a new board to be solved
	 * @param rowVal the row to add the value
	 * @param colVal the column to add the value
	 * @return if the board is complete or not
	 */
	public boolean newBoard(int rowVal, int colVal)
	{
		int gridNum=-1;
		int colNum=-1;

		if(rowVal<=2&&colVal<=2)
			gridNum=0;
		else if(rowVal<=2&&(colVal<=5&&colVal>=3))
			gridNum=1;
		else if(rowVal<=2&&colVal>=6)
			gridNum=2;
		else if((rowVal>=3&&rowVal<=5)&&colVal<=2)
			gridNum=3;
		else if((rowVal>=3&&rowVal<=5)&&(colVal<=5&&colVal>=3))
			gridNum=4;
		else if((rowVal>=3&&rowVal<=5)&&colVal>=6)
			gridNum=5;
		else if(rowVal>=6&&colVal<=2)
			gridNum=6;
		else if(rowVal>=6&&(colVal<=5&&colVal>=3))
			gridNum=7;
		else if(rowVal>=6&&colVal>=6)
			gridNum=8;

		if(((colVal==0)||(colVal==3)||(colVal==6))&&((rowVal==0)||(rowVal==3)||(rowVal==6)))
			colNum=0;
		else if(((colVal==1)||(colVal==4)||(colVal==7))&&((rowVal==0)||(rowVal==3||(rowVal==6))))
			colNum=1;
		else if(((colVal==2)||(colVal==5)||(colVal==8))&&((rowVal==0)||(rowVal==3)||(rowVal==6)))
			colNum=2;
		else if(((colVal==0)||(colVal==3)||(colVal==6))&&((rowVal==1)||(rowVal==4)||(rowVal==7)))
			colNum=3;
		else if(((colVal==1)||(colVal==4)||(colVal==7))&&((rowVal==1)||(rowVal==4)||(rowVal==7)))
			colNum=4;
		else if(((colVal==2)||(colVal==5)||(colVal==8))&&((rowVal==1)||(rowVal==4)||(rowVal==7)))
			colNum=5;
		else if(((colVal==0)||(colVal==3)||(colVal==6))&&((rowVal==2)||(rowVal==5)||(rowVal==8)))
			colNum=6;
		else if(((colVal==1)||(colVal==4)||(colVal==7))&&((rowVal==2)||(rowVal==5)||(rowVal==8)))
			colNum=7;
		else if(((colVal==2)||(colVal==5)||(colVal==8))&&((rowVal==2)||(rowVal==5)||(rowVal==8)))
			colNum=8;
		
		
		if(rowVal==9)
			return true;
		
		if(!board[gridNum][colNum].getText().equals(""))
		{
			if (newBoard(colVal == 8? (rowVal + 1): rowVal, (colVal + 1) % 9))
				return true;
		}
		else
		{
			ArrayList<Integer> vals=possValues(rowVal,colVal);
			Stack<Integer> stack= new Stack<Integer>();
			ArrayList<Integer> rand=new ArrayList<Integer>();
			for(int i=1;i<=9;i++)
				stack.push((Integer)i);
			Collections.shuffle(stack);
			for(int i=0;i<9;i++)
				rand.add(stack.pop());
			for(int i=0;i<9;i++)
			{
				if(vals.contains(rand.get(i)))
				{
					board[gridNum][colNum].setText(String.valueOf(rand.get(i)));
				
				if (newBoard(colVal == 8? (rowVal + 1): rowVal, (colVal + 1) % 9))
					return true;
				else
					board[gridNum][colNum].setText("");
				}
			}
		}
		
		return false;
	}
		//}
	//}
	/**
	 * checks to see if the board is properly solved with no repeats or blank spaces
	 */
	public void checkSolve()
	{
		//Adding to Grid Array
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				if(!board[x][i].getText().equals(""))
				{
					grid[x][i]=Integer.parseInt(board[x][i].getText());
					//System.out.print(""+grid[x][i]+" ");
				}
				else
					grid[x][i]=-1;
			}
			//System.out.println();
		}
		//System.out.println();
		//Adding to Row Array
		int gridSquare=0;
		for(int k=0; k<7; k+=3)
		{
			for(int j=0;j<7;j+=3)
			{
				int num=0;
				for(int r=0+k;r<3+k;r++)
				{
					for(int c=0+j;c<3+j;c++)
					{
						row[gridSquare][num]=grid[r][c];
						//System.out.print(""+row[gridSquare][num]+" ");
						num++;
					}
					
				}
				//System.out.println();
				gridSquare++;
			}
		}
		//Adding to Column Array
		for(int r=0;r<9;r++)
		{
			for(int c=0;c<9;c++)
			{
				column[r][c]=row[c][r];
			}
		}
		if((isUniqueTest(grid)&&isUniqueTest(row)&&isUniqueTest(column))&&noEmptyVals(grid))
			JOptionPane.showMessageDialog(null, "This board has been correctly solved.");
		else if(!noEmptyVals(grid))
			JOptionPane.showMessageDialog(null, "This board is incomplete.");
		else
			JOptionPane.showMessageDialog(null, "This board has duplicate values");
	}
	/**
	 * gives all the possible values that can be plugged into the space at
	 * this point, checks the row, col, and grid of the coordinate and 
	 * returns an arraylist of valid answers
	 * @param rowVal the row of the coordinate to check
	 * @param colVal the column of the coordinate to check
	 * @return an arraylist of all possible values that can be plugged in at this coordinate
	 */
	public ArrayList<Integer> possValues(int rowVal, int colVal)
	{
		ArrayList<Integer> possibleVals= new ArrayList<Integer>();
		int[] gridVals=new int[9];
		int[] rowVals=new int[9];
		int[] colVals=new int[9];
		int gridNum=-1;
		
		if(rowVal<=2&&colVal<=2)
			gridNum=0;
		else if(rowVal<=2&&(colVal<=5&&colVal>=3))
			gridNum=1;
		else if(rowVal<=2&&colVal>=6)
			gridNum=2;
		else if((rowVal>=3&&rowVal<=5)&&colVal<=2)
			gridNum=3;
		else if((rowVal>=3&&rowVal<=5)&&(colVal<=5&&colVal>=3))
			gridNum=4;
		else if((rowVal>=3&&rowVal<=5)&&colVal>=6)
			gridNum=5;
		else if(rowVal>=6&&colVal<=2)
			gridNum=6;
		else if(rowVal>=6&&(colVal<=5&&colVal>=3))
			gridNum=7;
		else if(rowVal>=6&&colVal>=6)
			gridNum=8;
		
		//Adding to Grid Array
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				if(!board[x][i].getText().equals(""))
					grid[x][i]=Integer.parseInt(board[x][i].getText());
				else
					grid[x][i]=-1;
			}
		}
		
		//Adding to Row Array
		int gridSquare=0;
		for(int k=0; k<7; k+=3)
		{
			for(int j=0;j<7;j+=3)
			{
				int num=0;
				for(int r=0+k;r<3+k;r++)
				{
					for(int c=0+j;c<3+j;c++)
					{
						if(!board[r][c].getText().equals(""))
							row[gridSquare][num]=Integer.parseInt(board[r][c].getText());
						else
							row[gridSquare][num]=-1;						
						num++;
					}
				}
				gridSquare++;
			}
		}
		
		//Getting all values associated with the number 
		for(int i=0;i<9;i++)
		{
			rowVals[i]=row[rowVal][i];
			colVals[i]=row[i][colVal];
			if(!board[gridNum][i].getText().equals(""))
				gridVals[i]=Integer.parseInt(board[gridNum][i].getText());
			else
				gridVals[i]=-1;
		}
		for(int i=1;i<=9;i++)
		{
			possibleVals.add(Integer.valueOf(i));
		}
		
		for(int i=0;i<9;i++)
		{
			possibleVals.remove(Integer.valueOf(rowVals[i]));
			possibleVals.remove(Integer.valueOf(colVals[i]));
			possibleVals.remove(Integer.valueOf(gridVals[i]));
		}
		
		return possibleVals;
	}
	/**
	 * checks to see if there are any duplicate values in the row, col, or grid
	 * @param my2DArray the 2D array that must be checked to see if there are any repeated values
	 * @return if the 2D array is unique in that it has no duplicate values
	 */
	public boolean isUniqueTest(int[][] my2DArray)
	{
		int[] myArray;
		for(int row=0;row<9;row++)
		{
			myArray= new int[9];
			for(int col=0;col<9;col++)
			{
				myArray[col]=my2DArray[row][col];
			}
			for (int i = 0; i < myArray.length; i++) 
			{
				for (int x = i; x < myArray.length; x++) 
				{
					if (i != x) 
					{
						if (myArray[i] == myArray[x]) 
						{
							if(myArray[i]!=-1)
								return false; // means there are duplicate values
						}
					}
				}
			}
		}
        return true; // means there are no duplicate values.
	}
	/**
	 * checks to see if all coordinates on the board have been filled
	 * @param my2DArray the 2-D array to check for completeness
	 * @return if the board is full of values
	 */
	public boolean noEmptyVals(int[][] my2DArray)
	{
		for(int i=0; i<9;i++)
			for(int x=0;x<9;x++)
				if(my2DArray[i][x]==-1)
					return false;
		return true;
	}
	/**
	 * clears the board, erasing all values
	 */
	public void clear()
	{
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				board[x][i].setText("");
			}
		}
	}
	/**
	 * Checks to see which button was pressed as an action
	 * can either give you clear the board, give a new board, or check for correctness
	 * @param arg0 the actionEvent that was given by a listener
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		JButton check = (JButton)arg0.getSource();
		if(check.equals(checkSolution))
		{
			//System.out.println("Checked");
			checkSolve();
		}
		else if(check.equals(clearBoard))
		{
			JOptionPane.showMessageDialog(null, "New board has been created.");
			clear();
			newBoard(0,0);
			for(int i=0;i<50;i++)
			{
				int q= (int)(Math.random()*9);
				int w= (int)(Math.random()*9);				
				board[q][w].setText("");
			}
			
		}
		else if(check.equals(newBoard))
		{
			JOptionPane.showMessageDialog(null, "Your board has been solved.");
			newBoard(0,0);
			
		}
		else
			new Number_Menu(check);
	}
	/**
	 * prints the 3 different 2-D arrays that represent the row, column and grid
	 */
	public void printArrays()
	{
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				System.out.print(""+grid[x][i]+" ");
			}
			System.out.println();
		}
		System.out.println();
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				System.out.print(""+row[x][i]+" ");
			}
			System.out.println();
		}
		System.out.println();
		for(int x=0;x<9;x++)
		{
			for(int i=0;i<9;i++)
			{
				System.out.print(""+column[x][i]+" ");
			}
			System.out.println();
		}
	}
	/**
	 * inner menu that gives the user the prompt to enter 1-9 as a value
	 * @author liort_000
	 *
	 */
	private class Number_Menu extends JFrame implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		public static final int WIDTH = 300;
		public static final int HEIGHT = 300;
		public static final String TITLE = "Choose your value";
		
		private JButton changeButton;
		/**
		 * creates a number menu with 1-9 as choice to place as the button's text
		 * @param b the button that will receive the text change
		 */
		public Number_Menu(JButton b)
		{
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setTitle(TITLE);
			this.setResizable(false);
			this.setLocation(350,225);
			this.setFocusable(true);
			this.setSize(WIDTH, HEIGHT);
			this.setLayout(new GridLayout(3, 3));
			
			changeButton=b;
			
			for(int i=1;i<=9;i++)
			{
				JButton button= new JButton(""+i);
				button.addActionListener(this);
				this.add(button);
			}
			this.setVisible(true);
			
		}
		/**
		 * Checks to see which button was pressed as an action
		 * one of the numbers that will change the button's text
		 * @param arg0 the actionEvent that was given by a listener
		 */
		public void actionPerformed(ActionEvent arg0) 
		{
			JButton check = (JButton)arg0.getSource();
			String num= check.getText();
			if(changeButton.getText().equals(num))
			{
				changeButton.setText("");
			}
			else
				changeButton.setText(num);
			dispose();
		}
	}
}