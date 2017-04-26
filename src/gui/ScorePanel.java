/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board.CompletedLines;

import sound.SoundEffects;
import sound.SoundPlayer;


/**
 * Observes when lines are cleared and updates the score accordingly.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 08 March 2015
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel implements Observer {
    
    /** Default score for a block cleared. */
    private static final int DEFAULT_SCORE = 50;
    /** Lines required to clear to advance a level. */
    private static final int LINES_PER_LEVEL = 10;
    /** The score multiplier for clearing one line on the board. */
    private static final double ONE_LINE_MULTIPLIER = 1;
    /** The score multiplier for clearing two lines on the board. */
    private static final double TWO_LINES_MULTIPLIER = 1.3;
    /** The score multiplier for clearing three lines on the board. */
    private static final double THREE_LINES_MULTIPLIER = 1.6;
    /** The score multiplier for clearing four lines on the board. */
    private static final double FOUR_LINES_MULTIPLIER = 2.0;
    /** The number that equals one line cleared. */
    private static final int ONE = 1;  // DO NOT CHANGE.
    /** The number that equals two lines cleared. */
    private static final int TWO = 2;  // DO NOT CHANGE.
    /** The number that equals three lines cleared. */
    private static final int THREE = 3;  // DO NOT CHANGE.
    /** The number that equals four lines cleared. */
    private static final int FOUR = 4;  // DO NOT CHANGE.
    /** Preferred panel size. */
    private static final Dimension PANEL_SIZE = new Dimension(250, 500);
    /** Default font style. */
    private static final String FONT_STYLE = "Verdana";
    /** Default font. */
    private static final Font DEFAULT_FONT = new Font(FONT_STYLE, 1, 18);
    /** Default large font. */
    private static final Font DEFAULT_FONT_LARGE = new Font(FONT_STYLE, 1, 25);
    /** Height of the score board box. */
    private static final int BOX_HEIGHT = 300;
    /** Width of the score board box. */
    private static final int BOX_WIDTH = 210;
    /** Level score multiplier. */
    private static final int LEVEL_MULTIPLIER = 10;
    /** White space to align score values. */
    private static final String WHITE_SPACE = "   ";
    
    /** The coordinates of the rows that were cleared. */
    private List<Integer> myClearedLines;
    
    /** The width of the row used to add a multiplier. */
    private final int myRowWidth;
    
    /** Total amount of lines cleared. */
    private int myTotalLines;
    
    /** Current level of difficulty the game is. */
    private int myLevel;
    
    /** The lines left to clear until the level advances. */
    private int myLinesLeft;
    
    /** The score of the current game. */
    private int myScore;
    
    /** The display of the score. */
    private JLabel myScoreDisplay;
    
    /** The display of the level. */
    private JLabel myLevelDisplay;
    
    /** The display of the lines cleared. */
    private JLabel myLinesDisplay;
    
    /** The sound player. */
    private final SoundPlayer mySoundPlayer;
    

    /**
     * Constructor for the Score board panel.
     * 
     * @param theRowWidth Width of a row in the current tetris game.
     * @param thePlayer The sound player.
     */
    public ScorePanel(final int theRowWidth, final SoundPlayer thePlayer) {
        super();
        init();
        myRowWidth = theRowWidth;
        mySoundPlayer = thePlayer;
        start();
    }
    
    
    /**
     * Helper method for the constructor.
     */
    private void init() {
        myScore = 0;
        myLevel = 1;
        myTotalLines = 0;
        myLinesLeft = LINES_PER_LEVEL;
        myScoreDisplay = new JLabel(WHITE_SPACE + myScore);
        myLevelDisplay = new JLabel(WHITE_SPACE + myLevel);
        myLinesDisplay = new JLabel(WHITE_SPACE + myTotalLines);
    }
    
    
    /**
     * Returns the current level of the game.
     * 
     * @return The current level of the game.
     */
    public int getLevel() {
        return myLevel;
    }
    
    
    /**
     * Sets the current level.
     * 
     * @param theLevel The level.
     */
    public void setLevel(final int theLevel) {
        myLevel = theLevel;
    }
    
    
    /**
     * Builds the panel of the score board panel.
     */
    private void start() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(PANEL_SIZE));
        
        final JPanel gridPanel = new JPanel(new GridLayout(6, 1, 0, 0));
        gridPanel.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        gridPanel.setBackground(Color.DARK_GRAY);
        
        final JLabel scoreLabel = new JLabel(" Score: ");
        scoreLabel.setFont(DEFAULT_FONT);
        scoreLabel.setForeground(Color.GREEN);
        gridPanel.add(scoreLabel);
        myScoreDisplay.setFont(DEFAULT_FONT_LARGE);
        myScoreDisplay.setForeground(Color.YELLOW);
        gridPanel.add(myScoreDisplay);
        
        final JLabel levelLabel = new JLabel(" Level: ");
        levelLabel.setFont(DEFAULT_FONT);
        levelLabel.setForeground(Color.GREEN);
        gridPanel.add(levelLabel);
        myLevelDisplay.setFont(DEFAULT_FONT_LARGE);
        myLevelDisplay.setForeground(Color.YELLOW);
        gridPanel.add(myLevelDisplay);
        
        final JLabel linesLabel = new JLabel(" Lines Cleared: ");
        linesLabel.setFont(DEFAULT_FONT);
        linesLabel.setForeground(Color.GREEN);
        gridPanel.add(linesLabel);
        myLinesDisplay.setFont(DEFAULT_FONT_LARGE);
        myLinesDisplay.setForeground(Color.YELLOW);
        gridPanel.add(myLinesDisplay);
        
        add(gridPanel, BorderLayout.CENTER);
    }
    
    
    /**
     * Calculates the score whenever lines are cleared in the game.
     */
    private void updateScore() {
        int score = 0;
        switch (myClearedLines.size()) {
            case ONE:
                score += ONE * (DEFAULT_SCORE + LEVEL_MULTIPLIER * myLevel)
                                            * ONE_LINE_MULTIPLIER * myRowWidth;
                myTotalLines += ONE;
                break;
            case TWO:
                score += TWO * (DEFAULT_SCORE + LEVEL_MULTIPLIER * myLevel)
                                            * TWO_LINES_MULTIPLIER * myRowWidth;
                myTotalLines += TWO;
                break;
            case THREE:
                score += THREE * (DEFAULT_SCORE + LEVEL_MULTIPLIER * myLevel)
                                            * THREE_LINES_MULTIPLIER * myRowWidth;
                myTotalLines += THREE;
                break;
            case FOUR:
                score += FOUR * (DEFAULT_SCORE + LEVEL_MULTIPLIER * myLevel)
                                            * FOUR_LINES_MULTIPLIER * myRowWidth;
                myTotalLines += FOUR;
                break;
            default:
                break;
        }
        myLinesLeft -= myClearedLines.size();
        if (myLinesLeft <= 0) {
            myLevel += 1;
            myLinesLeft += LINES_PER_LEVEL;
        }
        myScore += score;
        SoundEffects.CLEAR.play(mySoundPlayer);
        repaint();
    }
    
    
    /**
     * Resets the score, level and lines cleared.
     * 
     * @param theLevel The new level of the game.
     */
    public void newGame(final int theLevel) {
        myScore = 0;
        myLevel = theLevel;
        myTotalLines = 0;
        myLinesLeft = LINES_PER_LEVEL;
        repaint();
    }
    
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphic = (Graphics2D) theGraphics;
        
        graphic.setPaint(Color.ORANGE);
        
        graphic.setPaint(Color.YELLOW);
        myScoreDisplay.setText(WHITE_SPACE + myScore);
        myLevelDisplay.setText(WHITE_SPACE + myLevel);
        myLinesDisplay.setText(WHITE_SPACE + myTotalLines);
        
    }


    @Override
    public void update(final Observable theClass, final Object theData) {
        if (theData instanceof CompletedLines) {
            myClearedLines = ((CompletedLines) theData).getCompletedLines();
            updateScore();
        }
    }
    
    
    

}
