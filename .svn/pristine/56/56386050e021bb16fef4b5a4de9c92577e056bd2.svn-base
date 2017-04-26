/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package gui;


import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Stores the Key Bindings for Tetris.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 01 March 2015
 */
public class KeyBindings {
    
    /** The map of key actions and their key binding code. */
    private final Map<KeyAction, Integer> myKeys;
        
    /** The panel that the key bindings will attach to. */
    private final JPanel myPanel;
    
    
    /**
     * Constructor for the key bindings.
     * 
     * @param theKeys The map of key bindings.
     * @param thePanel The panel the key bindings will attach to.
     */
    public KeyBindings(final Map<KeyAction, Integer> theKeys, final JPanel thePanel) {
        super();
        myKeys = theKeys;
        myPanel = thePanel;
    }
    
    
    /**
     * Enables a single key.
     * 
     * @param theAction The action of the key.
     */
    public void enableKey(final KeyAction theAction) {
        bindKey(theAction.getAction(), myKeys.get(theAction));
    }
    
    
    /**
     * Binds an action to a given key.
     * 
     * @param theAction The action that will be bound.
     * @param theKey The key code that will be bound.
     */
    public void bindKey(final Action theAction, final int theKey) {
        myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                                      KeyStroke.getKeyStroke(theKey, 0), theAction.toString());
        myPanel.getActionMap().put(theAction.toString(), theAction);
    }
    
    
    /**
     * Disables a key binding.
     * 
     * @param theKey The key code that will be disabled.
     */
    public void disableKey(final int theKey) {
        myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                                      KeyStroke.getKeyStroke(theKey, 0), "none");
    }
    
    
    /**
     * Enables key bindings.
     */
    public void enableAllKeys() {
        for (final KeyAction key : myKeys.keySet()) {
            bindKey(key.getAction(), myKeys.get(key));
        }
    }
    
    /**
     * Disables key bindings.
     */
    public void disableAllKeys() {
        for (final KeyAction key : myKeys.keySet()) {
            disableKey(myKeys.get(key));
        }
    }
    
    
    /**
     * Returns a map that contains the current key codes
     * for the key bindings.
     * 
     * @return Map of key codes.
     */
    public Map<KeyAction, Integer> getKeys() {
        final Map<KeyAction, Integer> keys = new HashMap<KeyAction, Integer>();
        for (final KeyAction key : myKeys.keySet()) {
            keys.put(key, myKeys.get(key));
        }
        return keys;
    }
    
    
    /**
     * Updates the key codes for key bindings using the passed
     * map of key codes.
     * 
     * @param theKeys The map of key codes.
     */
    public void setKeys(final Map<KeyAction, Integer> theKeys) {
        //myKeys.clear();
        for (final KeyAction key : theKeys.keySet()) {
            myKeys.put(key, theKeys.get(key));
        }
    }
    
    
    
    
    

}
