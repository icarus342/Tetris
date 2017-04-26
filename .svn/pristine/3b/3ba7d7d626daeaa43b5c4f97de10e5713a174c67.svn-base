/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package gui;


import java.awt.EventQueue;

/**
 * Driver class for Tetris.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 01 March 2015
 */
public final class TetrisMain {
    
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    

    /**
     * Launches TetrisGUI.
     * 
     * @param theArgs Array of strings.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final TetrisGUI gui = new TetrisGUI();
                gui.start();    
            }
        });
    }

}
