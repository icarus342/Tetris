/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package gui;

import javax.swing.Action;

/**
 * The Key binding that contains the action, key, and string.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 08 March 2015
 */
public class KeyAction {
    
    /** The action for the key binding. */
    private final Action myAction;
    
    /** The name of the key binding. */
    private final String myName;

    
    /**
     * Constructor for a Key Action.
     * 
     * @param theAction The action.
     * @param theName The label of the action.
     */
    public KeyAction(final Action theAction, final String theName) {
        myAction = theAction;
        myName = theName;
    }


    /**
     * Returns the action.
     * 
     * @return The action.
     */
    public Action getAction() {
        return myAction;
    }


    /**
     * Return the name of the action.
     * 
     * @return The name of the action.
     */
    public String getName() {
        return myName;
    }



}
