package experimentalGUI;
/*
 * GUI Class
 * TCSS 342 - 3/1/23
 * Jacob Erickson
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class GUI {
	
    /** The JFrame. */
    private static final JFrame GUI = new JFrame("TCSS 342 Spreadsheet App");
	
	private static final int LENGTH = 6;
	
	/** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
	private JButton[][] mySpreadsheetCells = new JButton[LENGTH][LENGTH];
	
	private JMenuBar myMenuBar;

	public GUI() {
		start();
	}
	
	public void start() {
		
		myMenuBar = new JMenuBar();
		
		final JPanel buttonPanel = new JPanel(new GridLayout(LENGTH, LENGTH));
		JPanel horizontalMovePanel = new JPanel();
		JPanel verticalMovePanel = new JPanel();
		
		horizontalMovePanel.add(createMovementButton("🡐"));
		horizontalMovePanel.add(createMovementButton("🡒"));
		verticalMovePanel.add(createMovementButton("🡑"));
		verticalMovePanel.add(createMovementButton("🡓"));
		
		horizontalMovePanel.setLayout(new GridLayout(0,3));
		verticalMovePanel.setLayout(new GridLayout(3,0));

		for (int i = 0; i < mySpreadsheetCells.length; i++) {
			for (int j = 0; j < mySpreadsheetCells[i].length; j++) {
				mySpreadsheetCells[i][j] = createCellButton(i, j);
				buttonPanel.add(mySpreadsheetCells[i][j]);
			}
		}
		
		horizontalMovePanel.setAlignmentX(LENGTH);
		
		verticalMovePanel.setAlignmentY(LENGTH);

		final JMenu mLoad = new JMenu("Load...");
        final JMenu mSave = new JMenu("Save...");
        final JMenu mExit = new JMenu("Exit");

        myMenuBar.add(mLoad);
        myMenuBar.add(mSave);
        myMenuBar.add(mExit);
		
		GUI.setJMenuBar(myMenuBar);
		GUI.setSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 2));
		GUI.add(horizontalMovePanel, BorderLayout.SOUTH);
		GUI.add(verticalMovePanel, BorderLayout.EAST);
		GUI.add(buttonPanel, BorderLayout.CENTER);
		
		buttonPanel.setAlignmentX(0);
		buttonPanel.setAlignmentY(0);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setLocationRelativeTo(null);
        GUI.setVisible(true);
	}
	
	/**
     * Creates a single button that either opens, saves, or closes an image,
     * depending on the button pressed on the bottom panel.
     * 
     * @param theName The label of the control panel button.
     * @return the button.
     */
    private JButton createCellButton(int theColumn, int theRow) {
        final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String cellID = "";
        while (theColumn > 26) {
        	int ID = theColumn / 26;
        	cellID += alphanumeric.substring(ID - 1, ID);
        	theColumn -= 26 * ID;
        }
        cellID += alphanumeric.substring(theColumn, theColumn + 1) + (theRow + 1);
        final String cellString = cellID;
        final JButton button = new JButton(cellID);
        button.addActionListener(new ActionListener() {
        
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(cellString + " has been pressed!");
			}
            
        });

        return button;
    }
    
    /**
     * Creates a single button that either opens, saves, or closes an image,
     * depending on the button pressed on the bottom panel.
     * 
     * @param theName The label of the control panel button.
     * @return the button.
     */
    private JButton createMovementButton(String theText) {
        final JButton button = new JButton(theText);
        button.addActionListener(new ActionListener() {
        
			@Override
			public void actionPerformed(ActionEvent e) {
				if (theText.equals("🡐")) {
					System.out.println("Left arrow has been pressed!");
				} else if (theText.equals("🡒")) {
					System.out.println("Right arrow has been pressed!");
				} else if (theText.equals("🡑")) {
					System.out.println("Up arrow has been pressed!");
				} else if (theText.equals("🡓")) {
					System.out.println("Down arrow has been pressed!");
				}
			}
            
        });

        return button;
    }
	
}
