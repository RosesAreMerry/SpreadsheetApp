package GUI;
/**
 * GUI Class
 * TCSS 342 - HW 6b
 * 3/6/2023
 * Jacob Erickson
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
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
	
	private static final int SIZE = 8;
	
	private static final int MAX_DISPLAY = 8;
	
	private static final Spreadsheet SPREADSHEET = new Spreadsheet(SIZE);
	
	/** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /** The width of the screen. */
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    
    /** The height of the screen. */
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;
	
    private JLabel cornerLabel = new JLabel();
    private JLabel[] myRowLabels = new JLabel[SIZE];
    private JLabel[] myColumnLabels = new JLabel[SIZE];
    
	private JButton[][] mySpreadsheetCells = new JButton[SIZE][SIZE];
	
	private JMenuBar myMenuBar;
	
	private int myCurrentX = 0;
	private int myCurrentY = 0;
	
	private JButton myLeftButton;
	private JButton myRightButton;
	private JButton myUpButton;
	private JButton myDownButton;
	
	private final JMenuItem mClear = new JMenuItem("Clear");
	
	public GUI() {
		start();
	}
	
	public void start() {
		
		myMenuBar = new JMenuBar();
		
		final JPanel buttonPanel = new JPanel(new GridLayout(SIZE + 1, SIZE + 1));
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
		
			if (SIZE >= MAX_DISPLAY) {
				myRightButton.setEnabled(false);
				myDownButton.setEnabled(false);
			}
		
		horizontalMovePanel.setLayout(new FlowLayout());
		verticalMovePanel.setLayout(new BoxLayout(verticalMovePanel, BoxLayout.Y_AXIS));

		verticalMovePanel.setAlignmentY(0);
		
		buttonPanel.add(cornerLabel);
		for (int i = 0; i < SIZE; i++) {
			myColumnLabels[i] = new JLabel("");
			myColumnLabels[i].setHorizontalAlignment(JLabel.CENTER);
			myColumnLabels[i].setVerticalAlignment(JLabel.BOTTOM);
			buttonPanel.add(myColumnLabels[i]);
		}
		for (int i = 0; i < SIZE; i++) {
			myRowLabels[i] = new JLabel("");
			myRowLabels[i].setHorizontalAlignment(JLabel.CENTER);
			myRowLabels[i].setVerticalAlignment(JLabel.CENTER);
			buttonPanel.add(myRowLabels[i]);
			for (int j = 0; j < SIZE; j++) {
				mySpreadsheetCells[i][j] = createCellButton(i, j);
				buttonPanel.add(mySpreadsheetCells[i][j]);
			}
		}
		
		relabel(myCurrentX, myCurrentY);
		
		
		horizontalMovePanel.setAlignmentX(SIZE);
		
		verticalMovePanel.setAlignmentY(SIZE);

		final JMenu mHome = new JMenu("Home");
		final JMenu mSSOptions = new JMenu("Spreadsheet");
		final JMenu mCOptions = new JMenu("Cell");
		mClear.setEnabled(false);
		final JMenuItem mCellEdit = new JMenuItem("Edit Formula...");
		final JMenuItem mLoad = new JMenuItem("Load...");
        final JMenuItem mSave = new JMenuItem("Save...");
        final JMenuItem mExit = new JMenuItem("Exit");
        
        mLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                GUI.dispatchEvent(new WindowEvent(GUI, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        mClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                clearSpreadsheet();
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
        
        myMenuBar.add(mHome);
        myMenuBar.add(mCOptions);
        myMenuBar.add(mSSOptions);
        mHome.add(mExit);
        mCOptions.add(mCellEdit);
        mSSOptions.add(mLoad);
        mSSOptions.add(mSave);
        mSSOptions.addSeparator();
        mSSOptions.add(mClear);
        
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
		CellToken displayToken = new CellToken();
		for (int i = 0; i < SIZE; i++) {
			myRowLabels[i].setText("" + (i + theStartRow + 1));
			myColumnLabels[i].setText(generateColumnLabel(i + theStartColumn));
			for (int j = 0; j < SIZE; j++) {
				displayToken.setRow(i + theStartColumn);
				displayToken.setColumn(j + theStartRow);
				mySpreadsheetCells[i][j].setText(SPREADSHEET.printCellFormula(displayToken));
				String cellID = (generateColumnLabel(i + myCurrentX) + (j + myCurrentY + 1));
			}
		}
	}
	
	private void clearSpreadsheet() {
		CellToken clearToken = new CellToken();
		String cellID = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				clearToken.setRow(i);
				clearToken.setColumn(j);
				cellID = (generateColumnLabel(j) + (i + 1));
				if (!(SPREADSHEET.printCellFormula(clearToken).equals(""))) {
					SPREADSHEET.changeCellFormula(clearToken, "");
				}
			}
		}
		relabel(myCurrentX, myCurrentY);
	}
	
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
     * Creates a single button that 
     * @param theName The row of the cell.
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
    
    private void remoteCellChangeHelper() {
    	CellToken remoteCellToken = new CellToken();
    	String s = "";
    	while (s != null) {
			s = (String) JOptionPane.showInputDialog(GUI, "Input cell ID to edit:", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, 
					s);
			remoteCellToken = CellToken.getCellToken(s.toUpperCase());
			if (remoteCellToken.getRow() > SIZE || remoteCellToken.getColumn() > SIZE) {
				JOptionPane.showMessageDialog(GUI, "Cell outside of spreadsheet bounds!", 
						"Cycle Detected!", JOptionPane.ERROR_MESSAGE, null);
			} else {
				cellChangeHelper(remoteCellToken.getRow(), remoteCellToken.getColumn());
				break;
			}
    	}
    }
    
    
    /**
     * Helps display a cell change
     * @param theRow
     * @param theColumn
     */
	private void cellChangeHelper(int theRow, int theColumn) {
		
		CellToken cellToken = new CellToken();
		
		String cellID = (generateColumnLabel(theColumn + myCurrentX) + (theRow + myCurrentY + 1));
		cellToken.setRow(theRow);
		cellToken.setColumn(theColumn);
		boolean exit = false;
		while (!exit) {
			String s = (String) JOptionPane.showInputDialog(GUI, "Change formula for cell " + cellID + ":", 
					"Change Cell Formula", JOptionPane.QUESTION_MESSAGE, null, null, 
					SPREADSHEET.printCellFormula(cellToken));
			if (s != null) {
				exit = true;
				try {
					SPREADSHEET.changeCellFormulaAndRecalculate(cellToken, s.toUpperCase());
					relabel(myCurrentX, myCurrentY);
					mClear.setEnabled(true);
				} catch (Exception e) {
					exit = false;
					SPREADSHEET.changeCellFormulaAndRecalculate(cellToken, "");
					JOptionPane.showMessageDialog(GUI, e.getLocalizedMessage(), 
							"Cycle Detected!", JOptionPane.ERROR_MESSAGE, null);
				}
			} else {
				exit = true;
			}
		}
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
					myCurrentX -= SIZE;
				} else if (theText.equals("🡒")) {
					myCurrentX += SIZE;
				} else if (theText.equals("🡑")) {
					myCurrentY -= SIZE;
				} else if (theText.equals("🡓")) {
					myCurrentY += SIZE;
				}
				relabel(myCurrentX, myCurrentY);
				myLeftButton.setEnabled(myCurrentX > 0);
				myUpButton.setEnabled(myCurrentY > 0);
			}
            
        });

        return button;
    }
	
}
