
/*
 * GUI Class
 * TCSS 342 - HW 6b
 * 3/6/2023
 * Jacob Erickson
 */

/**
 * A class to display and alter the Spreadsheet and its contents.
 * @author Jacob Erickson
 * @version 2023.3.8 
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;

public class GUI {
	
    /** The JFrame. */
    private static final JFrame GUI = new JFrame();
	
	/** The n-by-n size of the spreadsheet display. */
	private static final int MAX_DISPLAY = 7;
	
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
	
    /** File chooser object to initialize at the current directory. */
    private final JFileChooser myFileChooser = new JFileChooser("."); 
    
    /** Filter to only loading/saving text files. */
    private FileNameExtensionFilter myFileFilter = new FileNameExtensionFilter(
            "Text Files (.txt)", "txt");
    
    /** Label to put in the corner of the spreadsheet display. */
    private final JLabel cornerLabel = new JLabel();
    
    /** Array of labels to denote the rows. */
    private JLabel[] myRowLabels;
    
    /** Array of labels to denote the columns. */
    private JLabel[] myColumnLabels;
    
    /** Array of buttons to act as the interactive cells. */
	private JButton[][] mySpreadsheetCells;
	
	/** The menu bar. */
	private JMenuBar myMenuBar;
	
    /** The size of the spreadsheet. */
	private int mySize;
	
	/** Coordinates of what position the top left cell in the spreadsheet display indicates. */
	private int myCurrentX = 0;
	private int myCurrentY = 0;
	
	/** Directional spreadsheet movement buttons. */
	private JButton myLeftButton;
	private JButton myRightButton;
	private JButton myUpButton;
	private JButton myDownButton;
	
	/** Indicates the number of rows and columns in the spreadsheet. */
	private int mySSDimension = 0;
	
	/** Determines whether or not to show the values or the formulas of each cell. */
	private boolean viewValues = false;
	
	/** Determines whether or not a spreadsheet has been loaded into the program yet. */
	private boolean spreadsheetLoaded = false;
	
	/** Clear button. */
	private final JMenuItem mClear = new JMenuItem("Clear");
	
	/** The panel to contain the cell buttons. */
	private JPanel myButtonPanel;
	
	/** The panel to contain the left/right movement buttons. */
	private JPanel myHorizontalMovePanel;
	
	/** The panel to contain the left/right movement buttons. */
	private JPanel myVerticalMovePanel;
	
	/** The label that gives instructions upon application launch. */
	private JLabel myInstructions;
	
	/** The name of the spreadsheet. */
	private String mySpreadsheetName = "";
	
	/** The button group to contain the radio display buttons. */
	private final ButtonGroup myGroup = new ButtonGroup();
	
	/** Radio buttons to toggle between displaying formulas and displaying calculated values. */
	private final JRadioButtonMenuItem mDisplayFormulas = new JRadioButtonMenuItem("Display Formulas");
	private final JRadioButtonMenuItem mDisplayValues = new JRadioButtonMenuItem("Display Values");
	
	/**
	 * Calls the start method to initialize the GUI with its components.
	 */
	public GUI() {
		start();
	}
	
	/**
	 * Adds all the proper components to the 
	 */
	public void start() {
		
		myFileChooser.setFileFilter(myFileFilter);
		
		// If we do not have a spreadsheet yet, we cannot load the components below
		if (spreadsheetLoaded) {
			
			mySize = mySpreadsheet.getNumRows();
			
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
			
			myLeftButton = createMovementButton("????");
			myRightButton = createMovementButton("????");
			myUpButton = createMovementButton("????");
			myDownButton = createMovementButton("????");
			
			
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
			
			// Adds labels surrounding the buttons
			for (int i = 0; i < mySSDimension; i++) {
				myColumnLabels[i] = new JLabel("");
				myColumnLabels[i].setHorizontalAlignment(JLabel.CENTER);
				myColumnLabels[i].setVerticalAlignment(JLabel.BOTTOM);
				myButtonPanel.add(myColumnLabels[i]);
			}
			// Adds buttons to panel
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
			
			relabel(myCurrentX, myCurrentY);
			
		} else {
			myInstructions = new JLabel("Load a new spreadsheet (Home > Load...) or create a new spreadsheet (Home > New...) to get started!");
			GUI.add(myInstructions, BorderLayout.CENTER);
			myInstructions.setHorizontalAlignment(0);
			myInstructions.setVerticalAlignment(0);
		}
		
		mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	mySpreadsheet.clear();
        		relabel(myCurrentX, myCurrentY);
            }
        });
		
		
		
		final JMenu mHome = new JMenu("Home");
		
		final JMenu mSSOptions = new JMenu("Spreadsheet");
		mSSOptions.setEnabled(spreadsheetLoaded);
		
		final JMenu mCOptions = new JMenu("Cell");
		mCOptions.setEnabled(spreadsheetLoaded);
		
		final JMenu mDisplay = new JMenu("Display");
		
		final JMenuItem mCellEdit = new JMenuItem("Edit Formula...");
		mCellEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		mCellEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                remoteCellChangeHelper();
            }
        });
		
		final JMenuItem mNew = new JMenuItem("New...");
		mNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        mNew.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	newSpreadsheetHelper();
            }
        });
		
		final JMenuItem mLoad = new JMenuItem("Load...");
		mLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        mLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                loadFileHelper();
            }
        });
        
		final JMenuItem mSave = new JMenuItem("Save...");
        mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        mSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                saveFileHelper();
            }
        });
        mSave.setEnabled(spreadsheetLoaded);
              
        final JMenuItem mExit = new JMenuItem("Exit");
        mExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        mExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                GUI.dispatchEvent(new WindowEvent(GUI, WindowEvent.WINDOW_CLOSING));
            }
        });
        
    	final ButtonGroup myButtonGroup = new ButtonGroup();
    	
    	
    	final JRadioButtonMenuItem mDisplayValues = new JRadioButtonMenuItem("Display Values");
    	myButtonGroup.add(mDisplayValues);
    	mDisplayValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                viewValues = true;
                relabel(myCurrentX, myCurrentY);
            }
        });
    	
    	final JRadioButtonMenuItem mDisplayFormulas = new JRadioButtonMenuItem("Display Formulas");
    	myButtonGroup.add(mDisplayFormulas);
    	mDisplayFormulas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
            	viewValues = false;
                relabel(myCurrentX, myCurrentY);
            }
        });
        
        myMenuBar = new JMenuBar();
        myMenuBar.add(mHome);
        myMenuBar.add(mCOptions);
        myMenuBar.add(mSSOptions);
        GUI.setJMenuBar(myMenuBar);	
        
        mHome.add(mNew);
        mHome.addSeparator();
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
		
        GUI.setTitle("TCSS 342 Spreadsheet App" + mySpreadsheetName);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(new Dimension(3 * SCREEN_WIDTH / 5, 2 * SCREEN_HEIGHT / 3));
		GUI.setLocationRelativeTo(null);
		GUI.setVisible(true);
	}
	
	/**
	 * Helper method to create a new spreadsheet.
	 */
	private void newSpreadsheetHelper() {
    	String s = "";
    	boolean exit = false;
    	Spreadsheet myTestSpreadsheet = null;
    	int dimension = 0;
    	
    	// Loop maintained if user does not cancel or close out dialog
    	while (!exit && s != null) {
    		
    		// Opens dialog
			s = (String) JOptionPane.showInputDialog(GUI, "Input length of spreadsheet (value will also be the width)", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, 
					s);
			if (s != null) {
				try {
					
					// Tries to parse dialog value as integer
					dimension = Integer.valueOf(s);
					exit = true;
					
					// Spreadsheet cannot be smaller than 1x1
					if (dimension < 1) {
						exit = false;
						JOptionPane.showMessageDialog(GUI, "Spreadsheet length value must be at least 1", 
								"Error!", JOptionPane.ERROR_MESSAGE, null);
					} else {
						// New spreadsheet is created
						myTestSpreadsheet = new Spreadsheet(dimension);
					}
				} catch (Exception e) {
					exit = false;
					// Non-integer value given
					JOptionPane.showMessageDialog(GUI, "Invalid length value", 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}	
			}
		}
    	// Only runs if spreadsheet successfully loaded
    	if (exit) {
			if (spreadsheetLoaded) {
				// Removes panels if visible
				GUI.remove(myButtonPanel);
				GUI.remove(myHorizontalMovePanel);
				GUI.remove(myVerticalMovePanel);
			} else {
				// Otherwise, instructions are only visible, so we remove them
				spreadsheetLoaded = true;
				GUI.remove(myInstructions);
			}
			
			mySpreadsheetName = " - New Spreadsheet";
			mySpreadsheet = myTestSpreadsheet;
			
			myCurrentX = 0;
			myCurrentY = 0;
			
			start();
		}
    }
	
	/**
	 * Helper method to save a spreadsheet.
	 */
	private void saveFileHelper() {
		int result = 0;
		boolean exit = false;
		while (result != JFileChooser.CANCEL_OPTION && !exit){
			
			// Opens file save dialog
			result = myFileChooser.showSaveDialog(GUI);
			if (result == JFileChooser.APPROVE_OPTION) {
				boolean save = false;
				try {
					exit = true;
					File data = myFileChooser.getSelectedFile();
					
					// Does the file we are trying to save to already exist?
					if (data.exists()) {
						
						// If so, prompt the user if they want to overwrite
	                    int resultOverwrite = JOptionPane.showConfirmDialog(null, 
	                            "This file already exists. Overwrite?", 
	                            "Overwrite", 
	                            JOptionPane.OK_CANCEL_OPTION);
	                    
	                    // If they say OK, continue with saving
	                    if (resultOverwrite == JOptionPane.OK_OPTION) {
	                    	save = true;
	                    } else {
	                    	// Otherwise, keep them in the save loop
	                    	exit = false;
	                    }
	                } else {
	                	// If the file doesn't exist yet, proceed with saving
	                	save = true;
	                }
					if (save) {
						String filePath = data.getAbsolutePath();
						if (!filePath.endsWith(".txt")) { //Edited this in to append .txt at the end of the file name -Louis
							filePath += ".txt";
						}
						
						// Generates the contents of the save file, then writes them to the file path
						BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
						writer.write(mySpreadsheet.generateFile());
						writer.close();
						
						// Adds the file path to the GUI
						mySpreadsheetName = " - " + data;
						GUI.setTitle("TCSS 342 Spreadsheet App" + mySpreadsheetName); 
					}
				} catch (Exception e) {
					exit = false;
					JOptionPane.showMessageDialog(GUI, "Invalid save", 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		}
	}
	
	/**
	 * Helper method to load a spreadsheet.
	 */
	private void loadFileHelper() {
		int result = 0;
		Spreadsheet myTestSpreadsheet = null;
		boolean exit = false;
		File data = null;
		while (result != JFileChooser.CANCEL_OPTION && !exit){
			result = myFileChooser.showOpenDialog(GUI);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					exit = true;
					data = myFileChooser.getSelectedFile();			
					
					myTestSpreadsheet = Spreadsheet.generateLoadedSpreadsheet(data);
					
				} catch (Exception e) {
					exit = false;
					JOptionPane.showMessageDialog(GUI, "Invalid spreadsheet file", 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		}
		if (exit) {
			// Checks to see if this is the first spreadsheet we are loading
			if (spreadsheetLoaded) {
				// Removes panels if they are already loaded
				GUI.remove(myButtonPanel);
				GUI.remove(myHorizontalMovePanel);
				GUI.remove(myVerticalMovePanel);
			} else {
				// Instructions still visible, so we remove them
				spreadsheetLoaded = true;
				GUI.remove(myInstructions);
			}
			
			mySpreadsheetName = (" - " + data);
			mySpreadsheet = myTestSpreadsheet;
			
			myCurrentX = 0;
			myCurrentY = 0;
			
			start();
		}
	}
	
	
	
	/**
	 * Relabels all the spreadsheet cells from given positions.
	 * @param theStartColumn The first column in the visible spreadsheet cells.
	 * @param theStartRow The first row in the visible spreadsheet cells.
	 */
	private void relabel(int theStartColumn, int theStartRow) {
		CellToken displayToken = new CellToken();
		
		// Sets up the labels for the rows and columns
		for (int i = 0; i < mySSDimension; i++) {
			myRowLabels[i].setText("" + (i + theStartRow + 1));
			myColumnLabels[i].setText(Spreadsheet.generateColumnLabel(i + theStartColumn));
		}
		
		// Goes through each cell and displays the respective value
		for (int i = 0; i < mySSDimension; i++) {
			for (int j = 0; j < mySSDimension; j++) {
				displayToken.setRow(i + theStartRow); //Edited this to properly shift the spreadsheet display
				displayToken.setColumn(j + theStartColumn);
				
				// Displays values or formulas based on which mode is selected
				if (viewValues && !(mySpreadsheet.getCellFormula(displayToken).equals(""))) {
					mySpreadsheetCells[i][j].setText("" + mySpreadsheet.getCellValue(displayToken));
				} else {
					mySpreadsheetCells[i][j].setText(mySpreadsheet.getCellFormula(displayToken));
				}
			}
		}
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
				cellChangeHelper(theColumn + myCurrentX, theRow + myCurrentY);
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
					
					// If the designated cell could not be parsed correctly (invalid cell)
					if (remoteCellToken.getRow() == CellToken.BADCELL || remoteCellToken.getColumn() == CellToken.BADCELL) {
						exit = false;
						JOptionPane.showMessageDialog(GUI, "Invalid cell", 
								"Error!", JOptionPane.ERROR_MESSAGE, null);
					}
					else {
						cellChangeHelper(remoteCellToken.getColumn(), remoteCellToken.getRow());
						break;
					}
				} catch (Exception e) {
					
					// If the designated cell is outside the bounds of the spreadsheet
					exit = false;
					JOptionPane.showMessageDialog(GUI, "Designated cell outside spreadsheet bounds", 
							"Error!", JOptionPane.ERROR_MESSAGE, null);
				}	
			}
		}
    }
    
    /**
     * Helper method to assist in changing the formula of a cell.
     * @param theColumn The column of the cell to change.
     * @param theRow The row of the cell to change.
     */
	private void cellChangeHelper(int theColumn, int theRow) {
		boolean exit = false;
		while (!exit) {
			CellToken cellToken = new CellToken();
			
			// Obtains label for cell
			String cellID = (Spreadsheet.generateColumnLabel(theColumn) + (theRow + 1));//Edited this so the arrows move the spreadsheet correctly
			cellToken.setRow(theRow);
			cellToken.setColumn(theColumn);
			String former = mySpreadsheet.getCellFormula(cellToken);
			String s = (String) JOptionPane.showInputDialog(GUI, "Change formula for cell " + cellID + ":", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, former);

			// Loop maintained as long as dialog box is not closed out/user presses Cancel
			if (s != null) {
				exit = true;
				try {
					mySpreadsheet.changeCellFormulaAndRecalculate(cellToken, s.toUpperCase());
					relabel(myCurrentX, myCurrentY);
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
				if (theText.equals("????")) {
					myCurrentX -= moveValue;
					myLeftButton.setEnabled(myCurrentX > 0);
					myRightButton.setEnabled(myCurrentX < mySize);
					
					if (myCurrentX < 0) {
						myCurrentX = 0;
					}
					
			// Right movement
				} else if (theText.equals("????")) {
					myCurrentX += moveValue;
					myLeftButton.setEnabled(myCurrentX > 0);
					myRightButton.setEnabled(myCurrentX + MAX_DISPLAY < mySize);
					if (myCurrentX + MAX_DISPLAY > mySize) {
						myCurrentX = mySize - MAX_DISPLAY;
					}
					
			// Up movement
				} else if (theText.equals("????")) {
					myCurrentY -= moveValue;
					myUpButton.setEnabled(myCurrentY > 0);
					myDownButton.setEnabled(myCurrentY < mySize);
					
					if (myCurrentY < 0) {
						myCurrentY = 0;
					}
					
			// Down movement		
				} else if (theText.equals("????")) {
					myCurrentY += moveValue;
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
