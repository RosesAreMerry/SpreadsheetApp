package experimentalGUI;
/*
 * GUI Class
 * TCSS 342 - 3/1/23
 * Jacob Erickson
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;

public class GUI {
	
    /** The JFrame. */
    private static final JFrame GUI = new JFrame("TCSS 342 Spreadsheet App");
	
	private static final int LENGTH = 8;
	
	private static final Spreadsheet SPREADSHEET = new Spreadsheet(LENGTH);
	
	/** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
    private JLabel cornerLabel = new JLabel();
    private JLabel[] myRowLabels = new JLabel[LENGTH];
    private JLabel[] myColumnLabels = new JLabel[LENGTH];
    
	private JButton[][] mySpreadsheetCells = new JButton[LENGTH][LENGTH];
	
	private JMenuBar myMenuBar;
	
	private int myCurrentX = 0;
	private int myCurrentY = 0;
	
	private CellToken cToken = new CellToken();
	
	private JButton myLeftButton;
	private JButton myRightButton;
	private JButton myUpButton;
	private JButton myDownButton;

	private ButtonGroup myButtonGroup;
	
	public GUI() {
		start();
	}
	
	public void start() {
		

		myMenuBar = new JMenuBar();
		
		final JPanel buttonPanel = new JPanel(new GridLayout(LENGTH + 1, LENGTH + 1));
		JPanel horizontalMovePanel = new JPanel();
		JPanel verticalMovePanel = new JPanel();
		
		
		myLeftButton = createMovementButton("🡐");
		myRightButton = createMovementButton("🡒");
		myUpButton = createMovementButton("🡑");
		myDownButton = createMovementButton("🡓");
		
		horizontalMovePanel.add(myLeftButton);
		horizontalMovePanel.add(myRightButton);
		verticalMovePanel.add(myUpButton);
		verticalMovePanel.add(myDownButton);
		
		myLeftButton.setEnabled(false);
		myUpButton.setEnabled(false);
		
		horizontalMovePanel.setLayout(new FlowLayout());
		verticalMovePanel.setLayout(new BoxLayout(verticalMovePanel, BoxLayout.Y_AXIS));

		verticalMovePanel.setAlignmentY(0);
		
		buttonPanel.add(cornerLabel);
		for (int i = 0; i < LENGTH; i++) {
			myColumnLabels[i] = new JLabel("");
			myColumnLabels[i].setHorizontalAlignment(JLabel.CENTER);
			myColumnLabels[i].setVerticalAlignment(JLabel.BOTTOM);
			buttonPanel.add(myColumnLabels[i]);
		}
		for (int i = 0; i < LENGTH; i++) {
			myRowLabels[i] = new JLabel("");
			myRowLabels[i].setHorizontalAlignment(JLabel.CENTER);
			myRowLabels[i].setVerticalAlignment(JLabel.CENTER);
			buttonPanel.add(myRowLabels[i]);
			for (int j = 0; j < LENGTH; j++) {
				mySpreadsheetCells[i][j] = createCellButton(i, j);
				buttonPanel.add(mySpreadsheetCells[i][j]);
			}
		}
		
		relabel(myCurrentX, myCurrentY);
		
		
		horizontalMovePanel.setAlignmentX(LENGTH);
		
		verticalMovePanel.setAlignmentY(LENGTH);

		final JMenu mHome = new JMenu("Home");
		final JMenu mOptions = new JMenu("Spreadsheet");
		final JMenuItem mClear = new JMenuItem("Clear");
		final JMenuItem mLoad = new JMenuItem("Load...");
        final JMenuItem mSave = new JMenuItem("Save...");
        final JMenuItem mExit = new JMenuItem("Exit");
        
        mLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                GUI.dispatchEvent(new WindowEvent(GUI, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        
        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                GUI.dispatchEvent(new WindowEvent(GUI, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        myMenuBar.add(mHome);
        myMenuBar.add(mOptions);
        mHome.add(mExit);
        mOptions.add(mLoad);
        mOptions.add(mSave);
        mOptions.addSeparator();
        mOptions.add(mClear);
        
		GUI.setJMenuBar(myMenuBar);
		
		
		GUI.add(horizontalMovePanel, BorderLayout.SOUTH);
		GUI.add(verticalMovePanel, BorderLayout.EAST);
		GUI.add(buttonPanel, BorderLayout.CENTER);
		
		buttonPanel.setAlignmentX(0);
		buttonPanel.setAlignmentY(0);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(new Dimension(3 * SCREEN_WIDTH / 5, 2 * SCREEN_HEIGHT / 3));
		GUI.setLocationRelativeTo(null);
        GUI.setVisible(true);
	}
	
	private void relabel(int theStartColumn, int theStartRow) {
		for (int i = 0; i < LENGTH; i++) {
			myRowLabels[i].setText("" + (i + theStartRow + 1));
			myColumnLabels[i].setText(generateColumnLabel(i + theStartColumn));
		}
	}
	
	private String generateColumnLabel(int theColumn) {	
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String cellID = "";
		
		while (theColumn >= 26) {
			int product = theColumn / 26;
			cellID += alphanumeric.substring(product - 1, product);
			theColumn -= product * 26;
		}
		
		cellID += alphanumeric.substring(theColumn, theColumn + 1);
		
        return cellID;
	}
	
	/**
     * Creates a single button that either opens, saves, or closes an image,
     * depending on the button pressed on the bottom panel.
     * 
     * @param theName The label of the control panel button.
     * @return the button.
     */
    private JButton createCellButton(int theRow, int theColumn) {
        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
        
			@Override
			public void actionPerformed(ActionEvent e) {
				cellChangeHelper(theRow, theColumn);
			}
            
        });

        return button;
    }
    
	private void cellChangeHelper(int theRow, int theColumn) {
		
		String cellID = (generateColumnLabel(theColumn + myCurrentX) + (theRow + myCurrentY + 1));
		
		String s = (String) JOptionPane.showInputDialog(GUI, "Change formula for cell " + cellID + ":", 
				"Change Cell Fomula", JOptionPane.QUESTION_MESSAGE, null, null, "Type Here!");
		
		cToken.setRow(theRow);
		cToken.setColumn(theColumn);
		SPREADSHEET.changeCellFormulaAndRecalculate(cToken, s);
		
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
					myCurrentX -= LENGTH;
				} else if (theText.equals("🡒")) {
					myCurrentX += LENGTH;
				} else if (theText.equals("🡑")) {
					myCurrentY -= LENGTH;
				} else if (theText.equals("🡓")) {
					myCurrentY += LENGTH;
				}
				relabel(myCurrentX, myCurrentY);
				myLeftButton.setEnabled(myCurrentX > 0);
				myUpButton.setEnabled(myCurrentY > 0);
			}
            
        });

        return button;
    }
	
}
