/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;
import model.Board.GameStatus;
import model.TetrisPiece;

import sound.MusicList;
import sound.MusicPlayer;
import sound.SoundEffects;
import sound.SoundPlayer;


/**
 * The graphical representation of Tetris.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 01 March 2015
 */
@SuppressWarnings("serial")
public class TetrisGUI extends JFrame implements Observer, PropertyChangeListener {
    
    /** Amount of time the timer is initially set to. */
    private static final int MILLISECONDS = 1000;
    /** Minimum size of the window frame. */
    private static final Dimension MIN_SIZE = new Dimension(526, 583);
    /** Used to calculate timer speed based on game level. */
    private static final int INITIAL = 1250;
    /** Used to calculate timer speed based on game level. */
    private static final int SCALE = 250;
    /** The maximum level before the speed of the game stops changing. */
    private static final int SPEED_LEVEL_CAP = 22;
    /** File path of the basic icon. */
    private static final String ICON = "/images/icon.jpg";
    /** The level of default volume for the sfx player */
    private static final float SFX_VOLUME = 0.5f;
    
    /** The Tetris game. */
    private final Board myTetris;
    
    /** The panel that renders the Tetris game. */
    private final GamePanel myGamePanel;
    
    /** The timer that runs the Tetris game. */
    private final Timer myTimer;
    
    /** The next piece in the Tetris game. */
    private TetrisPiece myNextPiece;
    
    /** Current level of the game. */
    private int myLevel;
    
    /** The scoring panel. */
    private final ScorePanel myScorePanel;
    
    /** The menu bar. */
    private final MenuBar myMenuBar;
    
    /** The sound player. */
    private SoundPlayer mySoundPlayer;
    
    /** The music player. */
    private MusicPlayer myMusicPlayer;
    
    /** Game over status. */
    private boolean myGameIsOver;
    
    /** Zelda theme status. */
    private boolean myZeldaTheme;
    
    /** Next tetris piece panel. */
    private NextPiecePanel myNextPiecePanel;
    
    /** The difficult level of the next game. */
    private int myDifficulty;
    
    
    
    /**
     * The GUI of Tetris.
     */
    public TetrisGUI() {
        super();
        myTetris = new Board();
        init();
        myScorePanel = new ScorePanel(myTetris.getWidth(), mySoundPlayer);
        myTimer = createTimer();
        myGamePanel = new GamePanel(myTetris, myTimer, mySoundPlayer);
        myMenuBar = new MenuBar(myGamePanel, mySoundPlayer, myMusicPlayer);
    }
    
    
    /**
     * Helper method for the constructor.
     */
    private void init() {
        myDifficulty = 1;
        myGameIsOver = false;
        myZeldaTheme = false;
        myLevel = 1;
        mySoundPlayer = new SoundPlayer();
        for (final SoundEffects sfx : SoundEffects.values()) {
            sfx.preLoad(mySoundPlayer);
            sfx.setVolume(SFX_VOLUME, mySoundPlayer);
        }
        
        myMusicPlayer = new MusicPlayer();
    }
    
    
    /**
     * Creates a timer for Tetris.
     * 
     * @return The timer for Tetris.
     */
    private Timer createTimer() {
        final Timer time = new Timer(MILLISECONDS, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myTetris.step();
                updateTimer();
                playMusic();
            }
        });
        return time;
    }
    

    /**
     * Builds the main frame of the GUI.
     */
    public void start() {
        
        myTimer.start();
                
        myGamePanel.setLayout(new BoxLayout(myGamePanel, BoxLayout.PAGE_AXIS));
        add(myGamePanel, BorderLayout.CENTER);
        
        final JPanel eastPanel = new JPanel(new BorderLayout());
        myNextPiecePanel = new NextPiecePanel(myNextPiece);
        final JPanel piecePaddingPanel = new JPanel();
        piecePaddingPanel.setBackground(Color.BLACK);
        piecePaddingPanel.add(myNextPiecePanel);
        eastPanel.add(piecePaddingPanel, BorderLayout.NORTH);
        
        eastPanel.add(myScorePanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
        
        
        setJMenuBar(myMenuBar);
        myMenuBar.addPropertyChangeListener(this);
        
        myTetris.addObserver(this);
        myTetris.addObserver(myScorePanel);
        myTetris.addObserver(myGamePanel);
        myTetris.addObserver(myNextPiecePanel);
        myTetris.clear(); // Starts a new game.
        
        
        
        
        this.setIconImage(new ImageIcon(getClass().getResource(ICON)).getImage());
        this.setMinimumSize(MIN_SIZE);
        this.setTitle("TCSS 305 Tetris");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Updates the delay interval of the game timer based on the current level.
     * Timer stops speeding up after level 22 to avoid less than 50ms speed.
     */
    private void updateTimer() {
        if (myLevel != myScorePanel.getLevel() && myLevel <= SPEED_LEVEL_CAP) {
            myLevel = myScorePanel.getLevel();
            // Math algorithm used to set the speed dependent on the level.
            myTimer.setDelay((int) (INITIAL - (SCALE * Math.sqrt(myLevel))));
        }
    }
    
    
    /**
     * Plays the background music during gameplay.
     */
    private void playMusic() {
        if (!myMusicPlayer.isStarted() && !myGameIsOver) {
            if (myZeldaTheme) {
                MusicList.OVERWORLD.play(myMusicPlayer);
            } else {
                MusicList.MAIN.play(myMusicPlayer);
            }
        } 
        
    }
    
    
    /**
     * Ends the current game.
     */
    private void endGame() {
        myMusicPlayer.stopPlay();
        MusicList.GAME_OVER.play(myMusicPlayer);
        myGameIsOver = true;
        myGamePanel.gameOver();
        myTimer.stop();
        myMenuBar.gameOver();
    }
    
    
    /**
     * Starts a new game.
     */
    private void newGame() {
        myMusicPlayer.stopPlay();
        playMusic();
        myGameIsOver = false;
        myGamePanel.newGame();
        myScorePanel.newGame(myDifficulty);
        updateTimer();
        myTimer.start();
        myNextPiecePanel.setNextPiece(myNextPiece);
    }
    
    
    /**
     * Pauses the current game.
     */
    private void pauseGame() {
        myGamePanel.pause();
    }
    
    
    /**
     * Updates the global theme settings of the game.
     * 
     * @param theThemeStatus The status of the Zelda theme.
     */
    private void updateTheme(final boolean theThemeStatus) {
        myGamePanel.updateTheme(theThemeStatus);
        myZeldaTheme = theThemeStatus;
        myMusicPlayer.stopPlay();
    }
    
    
    /**
     * Updates the difficulty for the next game.
     * 
     * @param theLevel The level of the game.
     */
    private void updateDifficulty(final int theLevel) {
        myDifficulty = theLevel;
    }
    


    /**
     * The update method for the Observer interface.
     * 
     * @param theObj The observable that called us.
     * @param theArg The argument it passed us.
     */
    public void update(final Observable theObj, final Object theArg) {
        if (theArg instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theArg;
        }
        if (theArg instanceof GameStatus && ((GameStatus) theArg).isGameOver()) {
            endGame();
        }
    }


    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch(theEvent.getPropertyName()) {
            case "NewGameUpdate":
                newGame();
                break;
                
            case "EndGameUpdate":
                endGame();
                break;
                
            case "PauseGameUpdate":
                pauseGame();
                break;
                
            case "ZeldaThemeUpdate":
                updateTheme((boolean) theEvent.getNewValue());
                break;
                
            case "DifficultyUpdate":
                updateDifficulty((int) theEvent.getNewValue());
                break;
                
            default:
                break;
        }
    }
    

}
