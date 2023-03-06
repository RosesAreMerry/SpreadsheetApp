/*
 * TCSS 305 Autumn 2022
 * Assignment 5
 */

package experimentalGUI;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.AbstractAction;


/**
 * Represents a menu action 
 * 
 * @author Jacob Erickson
 * @version 2022.12.09
 */
public class MenuAction extends AbstractAction {
    
    /** Generated serialization number. */
    private static final long serialVersionUID = 8962446411373552100L;

    /** Support for firing property change events from this class. */
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);
    
    /** The label on the Action. */
    private final String myName;
    
    /**
     * Default constructor for a default action.
     * @param The name of the action.
     */
    public MenuAction(final String theName) {
        super(theName);
        
        myName = theName;
    }
    
    /**
     * Adds a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }
    
    /**
     * Removes a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to remove.
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }
    
    /**
     * Fires an event based on the name of the Action.
     * 
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myPCS.firePropertyChange(myName, null, myName);
    }    
}