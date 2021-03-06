/*
 * TCSS 305 � Winter 2015
 * Assignment 6 - Tetris
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sound.MusicList;
import sound.MusicPlayer;
import sound.SoundEffects;
import sound.SoundPlayer;

/**
 * The menu bar for Tetris.  Includes option for changing key bindings.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 04 March 2015
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
    
    
    /** Name of the property update for when the game is paused. */
    private static final String PAUSE_UPDATE = "PauseGameUpdate";
    /** The field length of the text fields in the key bindings window. */
    private static final int TEXT_LENGTH = 5;
    /** File path of the basic icon. */
    private static final String ICON = "/images/icon.jpg";
    
    /** Default first difficulty. */
    private static final int FIRST_DIFFICULTY = 1;
    /** Default second difficulty. */
    private static final int SECOND_DIFFICULTY = 5;
    /** Default third difficulty. */
    private static final int THIRD_DIFFICULTY = 10;
    /** Default fourth difficulty. */
    private static final int FOURTH_DIFFICULTY = 15;
    /** Default fifth difficulty. */
    private static final int FIFTH_DIFFICULTY = 20;
    
    /** The primary JPanel of the key bindings window. */
    private JPanel myPrimary;
    
    /** The JFrame of the key bindings window. */
    private KeyBindingsWindow myKeyBindingsWindow;
    
    /** The panel for rendering the game on the main window. */
    private final GamePanel myGamePanel;
    
    /** A map of the current key bindings. */
    private Map<KeyAction, Integer> myKeys;
    
    /** A map of the text fields attached to the action. */
    private Map<KeyAction, JTextField> myActionFields;
    
    /** The list of text fields for key bindings. */
    private Map<JTextField, Integer> myBindFields;
    
    /** The list of text fields and their label. */
    private Map<JTextField, String> myTextFields;
    
    /** The text field that is the focus of the KeyListener. */
    private JTextField myFocusedField;
    
    /** The action that will is used for the focused field. */
    private KeyAction myFocusedAction;
    
    /** The KeyListener that is used for monitoring keystrokes to bind a key. */
    private MyKeyListener myKeyStroke;
    
    /** Menu item for starting a new game. */
    private JMenuItem myNewGameItem;
    
    /** Menu item for ending the current game. */
    private JMenuItem myEndGameItem;
    
    /** The sound player. */
    private final SoundPlayer mySoundPlayer;
    
    /** The music player. */
    private final MusicPlayer myMusicPlayer;
    
    

    /**
     * Constructor for the menu bar.
     *
     * 
     * @param theGamePanel The panel that renders the Tetris game.
     * @param thePlayer The sound Player.
     * @param theMusic The music Player.
     */
    public MenuBar(final GamePanel theGamePanel, final SoundPlayer thePlayer,
                   final MusicPlayer theMusic) {
        super();
        init();
        myGamePanel = theGamePanel;
        myKeys = myGamePanel.getKeyBindings();
        mySoundPlayer = thePlayer;
        myMusicPlayer = theMusic;
        initializeFields();
        createMenuBar();
    }
    
    
    /**
     * Helper method for the constructor.
     */
    private void init() {
        myPrimary = new JPanel();
        myKeyStroke = new MyKeyListener();
        myBindFields = new HashMap<JTextField, Integer>();
        myTextFields = new HashMap<JTextField, String>();
        myActionFields = new HashMap<KeyAction, JTextField>();
        myEndGameItem = new JMenuItem("End Game");
        myNewGameItem = new JMenuItem("New Game");
    }
    
    
    
    /**
     * Initializes the text fields for the constructor.
     */
    private void initializeFields() {
        for (final KeyAction key : myKeys.keySet()) {
            final JTextField field = createBindField(key);
            myBindFields.put(field, myKeys.get(key));
            myTextFields.put(field, key.getName());
            myActionFields.put(key, field);
        }
    }
    
    
    /**
     * Creates a text field with a MouseListener that will
     * attach a KeyListener on click for binding a key.
     * 
     * @param theAction The action used for the key binding map.
     * @return The created text field.
     */
    private JTextField createBindField(final KeyAction theAction) {
        final JTextField field = new JTextField(TEXT_LENGTH);
        field.setFocusable(false);
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent theEvent) {
                if (myFocusedField == null) {
                    field.setBackground(Color.LIGHT_GRAY);
                    myFocusedField = field;
                    myFocusedAction = theAction;
                    myPrimary.setFocusable(true);
                    myPrimary.requestFocusInWindow();
                    myPrimary.addKeyListener(myKeyStroke);
                }
            }
        });
        return field;
    }
    
    
    /**
     * Creates the menu bar for the constructor.
     */
    private void createMenuBar() {

        myKeyBindingsWindow = new KeyBindingsWindow("Key Bindings");
        
        // File Menu setup.
        final JMenu fileMenu = createFileMenu();
        add(fileMenu);
        
        // Difficulty Menu setup.
        final JMenu difficultyMenu = createDifficultyMenu();
        add(difficultyMenu);        
        
        // Options Menu setup.
        final JMenu optionsMenu = createOptionMenu();
        add(optionsMenu);
        
        // Help menu setup.
        final JMenu helpMenu = createHelpMenu();
        add(helpMenu);
    }
    
    
    /**
     * Creates the difficulty menu for the game.
     * 
     * @return The difficulty menu.
     */
    private JMenu createDifficultyMenu() {
        final JMenu difficulty = new JMenu("Difficulty");
        difficulty.setMnemonic(KeyEvent.VK_D);
        final ButtonGroup difficultyGroup = new ButtonGroup();
        
        final JRadioButtonMenuItem levelOne = new JRadioButtonMenuItem("Level 1");
        levelOne.addActionListener(new DifficultyListener(FIRST_DIFFICULTY));
        difficultyGroup.add(levelOne);
        difficulty.add(levelOne);
        
        final JRadioButtonMenuItem levelTwo = new JRadioButtonMenuItem("Level 5");
        levelTwo.addActionListener(new DifficultyListener(SECOND_DIFFICULTY));
        difficultyGroup.add(levelTwo);
        difficulty.add(levelTwo);
        
        final JRadioButtonMenuItem levelThree = new JRadioButtonMenuItem("Level 10");
        levelThree.addActionListener(new DifficultyListener(THIRD_DIFFICULTY));
        difficultyGroup.add(levelThree);
        difficulty.add(levelThree);
        
        final JRadioButtonMenuItem levelFour = new JRadioButtonMenuItem("Level 15");
        levelFour.addActionListener(new DifficultyListener(FOURTH_DIFFICULTY));
        difficultyGroup.add(levelFour);
        difficulty.add(levelFour);
        
        final JRadioButtonMenuItem levelFive = new JRadioButtonMenuItem("Level 20");
        levelFive.addActionListener(new DifficultyListener(FIFTH_DIFFICULTY));
        difficultyGroup.add(levelFive);
        difficulty.add(levelFive);
        
        levelOne.setSelected(true);
        return difficulty;
    }
    
    
    
    /**
     * Creates a file menu.
     * 
     * @return The file menu.
     */
    private JMenu createFileMenu() {
        final JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        
        myNewGameItem.setMnemonic(KeyEvent.VK_N);
        myNewGameItem.setEnabled(false);
        myNewGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                firePropertyChange("NewGameUpdate", null, theEvent);
                myNewGameItem.setEnabled(false);
                myEndGameItem.setEnabled(true);
            }
        });
        menu.add(myNewGameItem);
        
        myEndGameItem.setMnemonic(KeyEvent.VK_E);
        myEndGameItem.setEnabled(true);
        myEndGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                firePropertyChange("EndGameUpdate", null, theEvent);
            }
        });
        menu.add(myEndGameItem);
        return menu;
    }
    
    
    /**
     * Creates the options menu.
     * 
     * @return The options menu.
     */
    private JMenu createOptionMenu() {
        final JMenu options = new JMenu("Options");
        options.setMnemonic(KeyEvent.VK_O);
        
        // Start of Music Volume Slider //
        JPanel musicPanel = new JPanel();
        JLabel musicLabel = new JLabel("Music");
        
        JSlider musicSlider = new JSlider(0, 40, 15);
        musicSlider.setPreferredSize(new Dimension(100, 20));
        musicSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                float volume = (float) ((JSlider) ce.getSource()).getValue() / 100.0f;
                for (final MusicList ml : MusicList.values()) {
                    ml.setVolume(volume, myMusicPlayer);
                }
            }
        });
        musicPanel.add(musicLabel);
        musicPanel.add(musicSlider);
        options.add(musicPanel);
        // End of Music Volume Slider //
        
        // Start of Sound Volume Slider //
        JPanel soundPanel = new JPanel();
        JLabel soundLabel = new JLabel("Sound");
        
        JSlider soundSlider = new JSlider(0, 100, 50);
        soundSlider.setPreferredSize(new Dimension(100, 20));
        soundSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                float volume = (float) ((JSlider) ce.getSource()).getValue() / 100.0f;
                for (final SoundEffects sfx : SoundEffects.values()) {
                    sfx.setVolume(volume, mySoundPlayer);
                }
            }
        });
        soundPanel.add(soundLabel);
        soundPanel.add(soundSlider);
        options.add(soundPanel);
        // End of Sound Volume Slider //
        
        options.addSeparator();
        
        final JCheckBoxMenuItem muteMusic = new JCheckBoxMenuItem("Mute Music");
        muteMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                for (final MusicList ml : MusicList.values()) {
                    ml.setVolume(muteMusic.isSelected(), myMusicPlayer);
                }
                
            }
        });
        options.add(muteMusic);
        
        final JCheckBoxMenuItem muteSound = new JCheckBoxMenuItem("Mute Sound");
        muteSound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                for (final SoundEffects sfx : SoundEffects.values()) {
                    sfx.setVolume(muteSound.isSelected(), mySoundPlayer);
                }
            }
        });
        options.add(muteSound);
        
        options.addSeparator();
        final JMenuItem keyBindings = new JMenuItem("Key Bindings...");
        keyBindings.setMnemonic(KeyEvent.VK_K);       
        keyBindings.addActionListener(new MyActionListener());
        options.add(keyBindings);
        
        options.addSeparator();
        final JCheckBoxMenuItem zeldaTheme = new JCheckBoxMenuItem("Zelda Theme");
        zeldaTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                firePropertyChange("ZeldaThemeUpdate", null, zeldaTheme.isSelected());
            }
        });
        options.add(zeldaTheme);
        return options;
    }
    
    
    /**
     * Creates the help menu.
     * 
     * @return The help menu.
     */
    private JMenu createHelpMenu() {
        final JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);
        
        final JMenuItem scoreItem = new JMenuItem("Scoring...", KeyEvent.VK_S);
        scoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                firePropertyChange(PAUSE_UPDATE, null, theEvent);
                JOptionPane.showMessageDialog(myGamePanel.getParent(),
                                "The scoring system is as follows. \n"
                                + "The base score per line clear is "
                                             + "50 points + (10 * the current level). \n"
                                + "The base score is then multiplied by the amount "
                                             + "of the lines cleared at once along with "
                                             + "its corresponding multiplier as follows. \n"
                                + "One Line    - x 1.0 score multiplier\n"
                                + "Two Lines   - x 1.3 score multiplier\n"
                                + "Three Lines - x 1.6 score multiplier\n"
                                + "Four Lines  - x 2.0 score multiplier\n"
                                + "Then finally multiplied by the width of "
                                             + "the row of the tetris game. \n",
                                "Scoring", JOptionPane.OK_OPTION, new ImageIcon(getClass().getResource(ICON)));
            }
        });
        help.add(scoreItem);
        
        final JMenuItem aboutItem = new JMenuItem("About...", KeyEvent.VK_A);
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                firePropertyChange(PAUSE_UPDATE, null, theEvent);
                launchAbout();
            }
        });
        help.add(aboutItem);
        return help;
    }
    
    
    /**
     * The about pop up option pane.
     */
    private void launchAbout() {
        JOptionPane.showMessageDialog(myGamePanel.getParent(),
                                      "TCSS 305 Tetris - Winter 2015\n"
                                      + "\n"
                                      + "Thanks to:\n"
                                      + "https://www.freesound.org/\n"
                                      + "http://www.nes-snes-sprites.com/"
                                      + "LegendofZeldaTheALinktothePastBruiceJuice.html\n"
                                      + "http://static.giantbomb.com/"
                                      + "uploads/original/1/19975/1474145-tetris.jpg\n"
                                      + "http://www.newgrounds.com/audio/listen/162764\n"
                                      + "http://www.theisozone.com/downloads/misc/ost/"
                                      + "the-legend-of-zelda-a-link-to-the-past-sound-track/\n"
                                      + "http://th05.deviantart.net"
                                      + "/fs21/PRE/f/2007/293/d/3/d3db1721cb51847e.png\n",
                                      "About",
                                      JOptionPane.OK_OPTION,
                                      new ImageIcon(getClass().getResource(ICON)));
    }
    
    
    
    
    
    /**
     *  Toggles menu items based on state of the game. 
     */
    public void gameOver() {
        myNewGameItem.setEnabled(true);
        myEndGameItem.setEnabled(false);
    }
    
    
    /**
     * The pop up window for changing key bindings.
     * 
     * @author Justin A.
     */
    private class KeyBindingsWindow extends JFrame {
        
        /**
         * Key bindings window constructor.
         * 
         * @param theName The window title.
         */
        KeyBindingsWindow(final String theName) {
            super(theName);
            start();
        }
        
        
        /**
         * Builds the components of the key binding window.
         */
        private void start() {
            // The main panel.
            final Box keyPanel = new Box(BoxLayout.PAGE_AXIS);
            
            // The individual panels for each key binding.
            for (final JTextField field : myBindFields.keySet()) {
                final JPanel panel = createPanel(myTextFields.get(field) + ": ", field);
                //panel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
                keyPanel.add(panel);
            }
            
            // The buttons
            final JPanel buttonPanel = new JPanel();
            // The save button that will commit the changed key bindings.
            final JButton saveButton = new JButton("Commit");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    myGamePanel.setKeyBindings(myKeys);
                    closeWindowOperations();
                    myKeyBindingsWindow.dispatchEvent(new WindowEvent(myKeyBindingsWindow,
                                                                WindowEvent.WINDOW_CLOSING));
                }
            });
            buttonPanel.add(saveButton);
            // The cancel button that will revert the changed key bindings.
            final JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    closeWindowOperations();
                    myKeyBindingsWindow.dispatchEvent(new WindowEvent(myKeyBindingsWindow,
                                                                WindowEvent.WINDOW_CLOSING));
                }
            });
            buttonPanel.add(cancelButton);
         
            keyPanel.add(buttonPanel);
            
            myPrimary.add(keyPanel, BorderLayout.WEST);
            
            
            add(myPrimary);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent theWindowEvent) {
                    closeWindowOperations();
                }
            });
            myPrimary.setFocusable(true); // needs this or keyListener will not work!
            this.setFocusable(true);
            this.setResizable(false);
            pack();
            this.setLocationRelativeTo(null);  // need to center on other JFrame later
        }
        
        
        /**
         * Operations to remove KeyListener, change background
         * colors back to white, clears the focused field, and unpauses
         * the game.
         */
        private void closeWindowOperations() {
            if (myFocusedField != null) {
                myFocusedField.setBackground(Color.WHITE);
                myKeyBindingsWindow.removeKeyListener(myKeyStroke);
                myFocusedField = null; // Enables use of binding other keys.
            }
            //myGamePanel.unpause();
        }
        
        
        /**
         * Creates a JPanel that has a label and a text field for a key bind.
         * 
         * @param theLabel The label for the key bind.
         * @param theField The text field for the key bind.
         * @return The created panel for the key bind.
         */
        private JPanel createPanel(final String theLabel, final JTextField theField) {
            final JPanel panel = new JPanel(new FlowLayout());
            panel.add(new JLabel(theLabel));
            panel.add(theField);
            return panel;
        }
        
        
    }
    
    
    
    /**
     * Key listener that is used to listen for key presses to register
     * key bindings.
     * 
     * @author Justin Arnett
     *
     */
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            /* Checks to make sure there is a focused field and that
             * the key event doesn't already exist in the key map unless
             * it is for the current binding.
             */
            if (myFocusedField != null && (!myKeys.containsValue(theEvent.getKeyCode()) 
                               || myKeys.get(myFocusedAction).equals(theEvent.getKeyCode()))) {
                myFocusedField.setText(KeyEvent.getKeyText(theEvent.getKeyCode()));
                myFocusedField.setBackground(Color.WHITE);
                myKeys.put(myFocusedAction, theEvent.getKeyCode());
                myPrimary.removeKeyListener(myKeyStroke);
                myFocusedField = null;  // Enables use of binding other keys.
            } else if (myFocusedField != null && myKeys.containsValue(theEvent.getKeyCode())) {
                // In the event of the key event already exists, throw error pop up.
                SoundEffects.ERROR.play(mySoundPlayer);
                JOptionPane.showMessageDialog(myPrimary.getParent(),
                                              "This Key is already bound!",
                                              "Error!",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        @Override
        public void keyReleased(final KeyEvent theEvent) {
            // Not used.
        }
        @Override
        public void keyTyped(final KeyEvent theEvent) {
            // Not used.
        }        
    }
    
    /**
     * Menu item action listener for launching the key binding window.
     * 
     * @author Justin Arnett
     *
     */
    private class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            //myKeys.clear();
            myKeys = myGamePanel.getKeyBindings();
            myKeyBindingsWindow.setVisible(true);
            myKeyBindingsWindow.setAlwaysOnTop(true);
            myGamePanel.pause();
            
            // Updates text fields to new key bindings.
            for (final KeyAction key : myKeys.keySet()) {
                myBindFields.put(myActionFields.get(key), myKeys.get(key));
            }
            for (final JTextField field : myBindFields.keySet()) {
                field.setText(KeyEvent.getKeyText(myBindFields.get(field)));
            }
        }
        
    }
    
    
    /**
     * Action Listener class used for adjusting difficulty of the game.
     * 
     * @author Justin Arnett
     */
    private class DifficultyListener implements ActionListener {
        
        /** The level for this action listener. */
        private final int myLevel;
        
        /**
         * The constructor for this action listener.
         * 
         * @param theLevel The level of difficulty.
         */
        public DifficultyListener(final int theLevel) {
            myLevel = theLevel;
        }
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            firePropertyChange("DifficultyUpdate", null, myLevel);
            myGamePanel.pause();
            JOptionPane.showMessageDialog(myPrimary.getParent(),
                                          "Difficulty will change when you start a new game.",
                                          "Difficulty Changed",
                                          JOptionPane.OK_OPTION,
                                          new ImageIcon(getClass().getResource(ICON)));
        }
    }

}
