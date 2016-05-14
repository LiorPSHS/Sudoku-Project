/**
*Lior Vansteenkiste
*Gallatin 2nd
*Main_Menu
*Is the Main Menu that deals with the user interaction in the beginning. Can propmt to build a new GameBoard, give instructions, or exit the game. 
**/
//package Sudoku;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The main menu that prompts the user with starting the game, instructions, or leaving
 * @author liort_000
 *
 */
public class Main_Menu extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Sudoku";
		
	private JPanel titlePane, buttonPane; 
	private JButton play, instruction, solver, exit;
	private JLabel sudokuTitle;
	private Icon titlePic;
	/**
	 * Creates the main menu instance
	 */
	public Main_Menu()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(TITLE);
		this.setResizable(false);
		this.setLocation(350,175);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridLayout(2, 1));
		
		titlePane= new JPanel();
		titlePane.setLayout(new GridLayout(1,1));
		//System.out.println(this.getClass().getResource("TitlePic.jpg"));
		titlePic=new ImageIcon(this.getClass().getResource("TitlePic.jpg"));
		sudokuTitle=new JLabel(titlePic, JLabel.CENTER);
		titlePane.add(sudokuTitle);
		
		buttonPane= new JPanel();
		buttonPane.setLayout(new GridLayout(2,2));
		
		play = new JButton("Play Sudoku");		
		instruction= new JButton("Instructions");
		solver=new JButton("Puzzle Solver");
		exit=new JButton("Exit?");
		
		
		play.addActionListener(this);
		instruction.addActionListener(this);
		solver.addActionListener(this);
		exit.addActionListener(this);
		
		buttonPane.add(play);
		buttonPane.add(instruction);
		buttonPane.add(solver);
		buttonPane.add(exit);
		
		this.add(titlePane);
		this.add(buttonPane);
		this.setVisible(true);
			
	}
		
	
	/**
	 * Checks to see which button was pressed as an action
	 * can either give you instructions, start the game, or exit
	 * @param arg0 the actionEvent that was given by a listener
	 */
	public void actionPerformed(ActionEvent arg0) 
	{
		JButton check = (JButton)arg0.getSource();
		if(check.equals(play))
		{
			new GameBoard();
		}
		else if(check.equals(instruction))
		{
			String instruct = "Welcome to Sudoku! Press play to begin a new game. A 9x9 board will appear before you.";
			JOptionPane.showMessageDialog(null, instruct);
			String rules = "The Basic Rules of Sudoku: There is only one valid solution to each Sudoku puzzle. \nThe only way the ";
			rules+="puzzle can be considered solved correctly is when all 81 boxes contain numbers and the other Sudoku rules have been followed.";
			JOptionPane.showMessageDialog(null, rules);
			rules="When you start a game of Sudoku, some blocks will be pre-filled for you. You cannot change these numbers ";
			rules+="in the course of the game.\nIMPORTANT: Each column, row and 3x3 grid must contain all of the numbers 1 through 9 ";
			rules+="and no two numbers in the same column, row or grid of a Sudoku puzzle can be the same.\nPress the buttons given to see ";
			rules+="if you are correct, clear your answers, or to generate a new puzzle.\nGood luck and have fun!";
			JOptionPane.showMessageDialog(null, rules);
		}
		else if(check.equals(solver))
		{
			new GameBoard().clear();
			JOptionPane.showMessageDialog(null, "Please enter all you have so far and press Solve Current Board!");
		}
		else if(check.equals(exit))
		{
			System.exit(0);
		}

	}

}


