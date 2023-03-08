package GUI;
/**
 * GUI Class
 * TCSS 342 - HW 6b
 * 3/6/2023
 * Jacob Erickson
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;

public class GUI {
	
    /** The JFrame. */
    private static final JFrame GUI = new JFrame("TCSS 342 Spreadsheet App");
	
    /** The size of the spreadsheet. */
	private int mySize = 11;
	
	/** The n-by-n size of the spreadsheet display. */
	private static final int MAX_DISPLAY = 4;
	
	/** The spreadsheet. */
	private Spreadsheet mySpreadsheet;
	
	/** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
    /**
     * File chooser object to initialize at the current directory.
     */
    private final JFileChooser myFileChooser = new JFileChooser("."); 
    
    private JLabel cornerLabel = new JLabel();
    private JLabel[] myRowLabels;
    private JLabel[] myColumnLabels;
    
	private JButton[][] mySpreadsheetCells;
	
	private JMenuBar myMenuBar;
	
	private int myCurrentX = 0;
	private int myCurrentY = 0;
	
	private JButton myLeftButton;
	private JButton myRightButton;
	private JButton myUpButton;
	private JButton myDownButton;
	
	private int mySSDimension = 0;
	
	private boolean viewValues = false;
	
	private boolean spreadsheetLoaded = false;
	
	private final JMenuItem mClear = new JMenuItem("Clear");
	
	private JPanel myButtonPanel;
	private JPanel myHorizontalMovePanel;
	private JPanel myVerticalMovePanel;
	
	private JLabel myInstructions;
	
	private final ButtonGroup myGroup = new ButtonGroup();
	
	private final JRadioButtonMenuItem mDisplayFormulas = new JRadioButtonMenuItem("Display Formulas");
	private final JRadioButtonMenuItem mDisplayValues = new JRadioButtonMenuItem("Display Values");
	
	public GUI() {
		start();
	}
	
	public void start() {
		
		if (spreadsheetLoaded) {
			mySpreadsheet = new Spreadsheet(mySize);
			
			if (mySize < MAX_DISPLAY) {
				mySSDimension = mySize;
			} else {
				mySSDimension = MAX_DISPLAY;
			}
			
			myButtonPanel = new JPanel(new GridLayout(mySSDimension + 1, mySSDimension + 1));
			
			myHorizontalMovePanel = new JPanel();
			myVerticalMovePanel = new JPanel();
			
			myRowLabels = new JLabel[mySSDimension];
			myColumnLabels = new JLabel[mySSDimension];
			mySpreadsheetCells = new JButton[mySSDimension][mySSDimension];
			
			myLeftButton = createMovementButton("🡐");
			myRightButton = createMovementButton("🡒");
			myUpButton = createMovementButton("🡑");
			myDownButton = createMovementButton("🡓");
			
			
			myHorizontalMovePanel.add(myLeftButton);
			myHorizontalMovePanel.add(myRightButton);
			myVerticalMovePanel.add(myUpButton);
			myVerticalMovePanel.add(myDownButton);
			
			myLeftButton.setEnabled(false);
			myUpButton.setEnabled(false);
		
			myRightButton.setEnabled(mySize > MAX_DISPLAY);
			myDownButton.setEnabled(mySize > MAX_DISPLAY);
			
			myHorizontalMovePanel.setLayout(new FlowLayout());
			myVerticalMovePanel.setLayout(new BoxLayout(myVerticalMovePanel, BoxLayout.Y_AXIS));

			myVerticalMovePanel.setAlignmentY(0);
			
			myButtonPanel.add(cornerLabel);
			for (int i = 0; i < mySSDimension; i++) {
				myColumnLabels[i] = new JLabel("");
				myColumnLabels[i].setHorizontalAlignment(JLabel.CENTER);
				myColumnLabels[i].setVerticalAlignment(JLabel.BOTTOM);
				myButtonPanel.add(myColumnLabels[i]);
			}
			for (int i = 0; i < mySSDimension; i++) {
				myRowLabels[i] = new JLabel("");
				myRowLabels[i].setHorizontalAlignment(JLabel.CENTER);
				myRowLabels[i].setVerticalAlignment(JLabel.CENTER);
				myButtonPanel.add(myRowLabels[i]);
				for (int j = 0; j < mySSDimension; j++) {
					mySpreadsheetCells[i][j] = createCellButton(i, j);
					myButtonPanel.add(mySpreadsheetCells[i][j]);
				}
			}
			
			myHorizontalMovePanel.setAlignmentX(0);
			
			myVerticalMovePanel.setAlignmentY(0);

			
			GUI.add(myHorizontalMovePanel, BorderLayout.SOUTH);
			GUI.add(myVerticalMovePanel, BorderLayout.EAST);
			GUI.add(myButtonPanel, BorderLayout.CENTER);
			
			myButtonPanel.setAlignmentX(0);
			myButtonPanel.setAlignmentY(0);
		} else {
			myInstructions = new JLabel("Load a new spreadsheet (Home > Load...) or create a new spreadsheet (Home > New...) to get started!");
			GUI.add(myInstructions, BorderLayout.CENTER);
			myInstructions.setHorizontalAlignment(0);
			myInstructions.setVerticalAlignment(0);
		}
		
		mClear.setEnabled(false);
		
		final JMenu mHome = new JMenu("Home");
		final JMenu mSSOptions = new JMenu("Spreadsheet");
		final JMenu mCOptions = new JMenu("Cell");
		final JMenu mDisplay = new JMenu("Display");
		
		final JMenuItem mCellEdit = new JMenuItem("Edit Formula...");
		final JMenuItem mLoad = new JMenuItem("Load...");
        final JMenuItem mSave = new JMenuItem("Save...");
        final JMenuItem mExit = new JMenuItem("Exit");
        mCellEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        
        mExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        
        mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        mSave.setEnabled(spreadsheetLoaded);
        
        mLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        mCOptions.setEnabled(spreadsheetLoaded);
        mSSOptions.setEnabled(spreadsheetLoaded);
        
        
    	final ButtonGroup myButtonGroup = new ButtonGroup();
    	
    	final JRadioButtonMenuItem mDisplayFormulas = new JRadioButtonMenuItem("Display Formulas");
    	final JRadioButtonMenuItem mDisplayValues = new JRadioButtonMenuItem("Display Values");
        
    	myButtonGroup.add(mDisplayValues);
    	myButtonGroup.add(mDisplayFormulas);
    	
    	mDisplayValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                viewValues = true;
                relabel(myCurrentX, myCurrentY);
            }
        });
    	
    	mDisplayFormulas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	viewValues = false;
                relabel(myCurrentX, myCurrentY);
            }
        });
        
        mLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                loadFileHelper();
            }
        });
        
        mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	mySpreadsheet.clear();
        		relabel(myCurrentX, myCurrentY);
                mClear.setEnabled(false);
            }
        });
        
        mCellEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                remoteCellChangeHelper();
            }
        });
        
        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                GUI.dispatchEvent(new WindowEvent(GUI, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        myMenuBar = new JMenuBar();
        myMenuBar.add(mHome);
        myMenuBar.add(mCOptions);
        myMenuBar.add(mSSOptions);
        
        mHome.add(mLoad);
        mHome.add(mSave);
        mHome.addSeparator();
        mHome.add(mExit);
        
        mDisplay.add(mDisplayFormulas);
        mDisplay.add(mDisplayValues);
        
        mDisplayFormulas.setSelected(true);
        
        mCOptions.add(mCellEdit);
        mCOptions.add(mDisplay);
        
        mSSOptions.add(mClear);
        
		GUI.setJMenuBar(myMenuBar);		
		
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(new Dimension(3 * SCREEN_WIDTH / 5, 2 * SCREEN_HEIGHT / 3));
		GUI.setLocationRelativeTo(null);
        
		relabel(myCurrentX, myCurrentY);
		
		GUI.setVisible(true);
        
	}
	
	private void loadFileHelper() {
		int result = 0;
		boolean exit = false;
		while (result != JFileChooser.CANCEL_OPTION && !exit){
			result = myFileChooser.showOpenDialog(GUI);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					exit = true;
					File data = myFileChooser.getSelectedFile();
					Scanner scan = new Scanner(data);
					scan.useDelimiter("\n");
					
					GUI.setTitle("TCSS 342 Spreadsheet App - " + data);
					
					mySize = Integer.valueOf(scan.next());
					
					scan.close();
					
					if (spreadsheetLoaded) {
						GUI.remove(myButtonPanel);
						GUI.remove(myHorizontalMovePanel);
						GUI.remove(myVerticalMovePanel);
					} else {
						spreadsheetLoaded = true;
						GUI.remove(myInstructions);
					}
					
					myCurrentX = 0;
					myCurrentY = 0;
					
					start();
				} catch (Exception e) {
					exit = false;
					JOptionPane.showMessageDialog(GUI, "Invalid spreadsheet file", 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		}
	}
	
	
	
	/**
	 * Relabels all the spreadsheet cells from given positions.
	 * @param theStartColumn The first column in the visible spreadsheet cells.
	 * @param theStartRow The first row in the visible spreadsheet cells.
	 */
	private void relabel(int theStartColumn, int theStartRow) {
		CellToken displayToken = new CellToken();
		for (int i = 0; i < mySSDimension; i++) {
			myRowLabels[i].setText("" + (i + theStartRow + 1));
			myColumnLabels[i].setText(generateColumnLabel(i + theStartColumn));
		}
		for (int i = 0; i < mySSDimension; i++) {
			for (int j = 0; j < mySSDimension; j++) {
				displayToken.setRow(i + theStartRow); //Edited this to properly shift the spreadsheet display
				displayToken.setColumn(j + theStartColumn);
				if (viewValues && !(mySpreadsheet.getCellFormula(displayToken).equals(""))) {
					mySpreadsheetCells[i][j].setText("" + mySpreadsheet.getCellValue(displayToken));
				} else {
					mySpreadsheetCells[i][j].setText(mySpreadsheet.getCellFormula(displayToken));
				}
			}
		}
	}
	
	/**
	 * Generates a column ID from a column index value.
	 * @param theColumn The column index
	 * @return The column ID.
	 */
	private String generateColumnLabel(int theColumn) {	
		theColumn++;
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int base = 1;
		int power = 0;
		String cellID = "";
		while (theColumn >= base) {
			power++;
			int count = (int) Math.pow(alphanumeric.length(), power - 1);
			int index = (((theColumn - 1) / count) + alphanumeric.length());
			if (power > 1) {
				index--;
			}
			index %= alphanumeric.length();
			cellID = alphanumeric.substring(index, index+1) + cellID;
			count *= alphanumeric.length();
			base += count;
		}
		return cellID;
	}
	
	/**
     * Creates a single button that corresponds to a given cell in the spreadsheet.
     * @param theRow The row of the cell.
     * @param theColumn The column of the cell.
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
    
    /**
     * Helper method to remotely edit a cell.
     */
    private void remoteCellChangeHelper() {
    	CellToken remoteCellToken = new CellToken();
    	String s = "";
    	boolean exit = false;
    	while (!exit) {
			s = (String) JOptionPane.showInputDialog(GUI, "Input cell ID to edit:", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, 
					s);
			if (s == null) {
				break;
			} else {
				try {
					remoteCellToken = CellToken.getCellToken(s.toUpperCase());
					exit = true;
				} catch (Exception e) {
					exit = false;
					JOptionPane.showMessageDialog(GUI, e.getMessage(), 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}	
			}
			if (remoteCellToken.getRow() == CellToken.BADCELL || remoteCellToken.getColumn() == CellToken.BADCELL) {
				exit = false;
				JOptionPane.showMessageDialog(GUI, "Invalid cell!", 
						"Error!", JOptionPane.ERROR_MESSAGE, null);
			}
			else if (remoteCellToken.getRow() >= mySize || remoteCellToken.getColumn() >= mySize) {
				exit = false;
				JOptionPane.showMessageDialog(GUI, "Designated cell outside of spreadsheet bounds", 
						"Error!", JOptionPane.ERROR_MESSAGE, null);
			} else {
				cellChangeHelper(remoteCellToken.getRow(), remoteCellToken.getColumn());
				break;
			}
		}
    }
    
    
    /**
     * Helper method to assist in changing the formula of a cell.
     * @param theRow The row of the cell to change.
     * @param theColumn The column of the cell to change.
     */
	private void cellChangeHelper(int theRow, int theColumn) {
		
		CellToken cellToken = new CellToken();
		String cellID = (generateColumnLabel(theColumn + myCurrentY) + (theRow + myCurrentX + 1));//Edited this so the arrows move the spreadsheet correctly
		cellToken.setRow(theRow + myCurrentX);
		cellToken.setColumn(theColumn + myCurrentY);
		
		boolean exit = false;
		while (!exit) {
			String former = mySpreadsheet.getCellFormula(cellToken);
			String s = (String) JOptionPane.showInputDialog(GUI, "Change formula for cell " + cellID + ":", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, former);
			if (s != null) {
				exit = true;
				try {
					mySpreadsheet.changeCellFormulaAndRecalculate(cellToken, s.toUpperCase());
					relabel(myCurrentX, myCurrentY);
					mClear.setEnabled(true);
				} catch (Exception e) {
					exit = false;
					mySpreadsheet.changeCellFormulaAndRecalculate(cellToken, former);
					JOptionPane.showMessageDialog(GUI, e.getMessage(), 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}
			} else {
				exit = true;
			}
		}
	}
    
    /**
     * Creates a single button that moves the spreadsheet in a given direction.
     * 
     * @param theText The label of the control panel button.
     * @return the button.
     */
    private JButton createMovementButton(String theText) {
        final JButton button = new JButton(theText);
        button.addActionListener(new ActionListener() {
        
        	
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				
			int moveValue = MAX_DISPLAY;	
			// Left movement
				if (theText.equals("🡐")) {
					myCurrentX -= moveValue;
					myLeftButton.setEnabled(myCurrentX > 0);
					myRightButton.setEnabled(myCurrentX < mySize);
					
					if (myCurrentX < 0) {
						myCurrentX = 0;
					}
					
			// Right movement
				} else if (theText.equals("🡒")) {
					myCurrentX += moveValue;
					myLeftButton.setEnabled(myCurrentX > 0);
					myRightButton.setEnabled(myCurrentX + MAX_DISPLAY < mySize);
					if (myCurrentX + MAX_DISPLAY > mySize) {
						myCurrentX = mySize - MAX_DISPLAY;
					}
					
			// Up movement
				} else if (theText.equals("🡑")) {
					myCurrentY -= moveValue;
					myUpButton.setEnabled(myCurrentY > 0);
					myDownButton.setEnabled(myCurrentY < mySize);
					
					if (myCurrentY < 0) {
						myCurrentY = 0;
					}
					
			// Down movement		
				} else if (theText.equals("🡓")) {
					myCurrentY += mySSDimension;
					myUpButton.setEnabled(myCurrentY > 0);
					myDownButton.setEnabled(myCurrentY + MAX_DISPLAY + 1 <= mySize);
					
					if (myCurrentY + MAX_DISPLAY > mySize) {
						myCurrentY = mySize - MAX_DISPLAY;
					}
					
				}
					
				relabel(myCurrentX, myCurrentY);
				
			}
            
        });

        return button;
    }
	
}
