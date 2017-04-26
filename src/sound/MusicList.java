/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package sound;

import java.net.URL;

/**
 * This enum encapsulates all the music of the game.
 * 
 * @author Justin Arnett
 * @version 12 March 2015
 */
public enum MusicList {
    
    
    /** Main music of the tetris game. */
    MAIN("/audio/music/main.wav"),
    /** Music for when the game is over. */
    GAME_OVER("/audio/music/game_over.wav"),
    /** The main music of the zelda theme. */
    OVERWORLD("/audio/music/overworld.wav");
   
    
    /** State of sound. */
    private boolean myMuted;

    /** Each sound effect has its own clip, loaded with its own sound file. */
    private URL mySoundFile;

    
    /**
     * Constructor to construct each element of the enum with its own sound file.
     * 
     * @param theSoundFileName The name of the sound file.
     */
    MusicList(final String theSoundFileName) {
        //mySoundFile = new File(theSoundFileName);
        /*
        URL test = getClass().getResource(theSoundFileName);
        mySoundFile = new File(test.getPath());
        */
        mySoundFile = getClass().getResource(theSoundFileName);
    }

    
    /**
     * Play or Re-play the sound effect from the beginning, by rewinding.
     * 
     * @param thePlayer The music player.
     */
    public void play(final MusicPlayer thePlayer) {
        final URL[] files = {mySoundFile};
        
        if (!myMuted) {
            thePlayer.newList(files);
            thePlayer.play(); 
        }
        
    }
    
    
    
    /**
     * Mutes and unmutes the sound player.
     * 
     * @param theVolume Status of the player being muted.
     * @param thePlayer The music player.
     */
    public void setVolume(final boolean theVolume, final MusicPlayer thePlayer) {
        myMuted = theVolume;
        if (myMuted) {
            thePlayer.stopPlay();
        }
    }
    
    
    public void setVolume(final float theVolume, final MusicPlayer thePlayer) {
        thePlayer.setVolume(theVolume);
    }


    /**
     * Optionally pre-loads all of the sound files.
     * 
     * @param thePlayer The music Player.
     */
    static void init(final MusicPlayer thePlayer) {
        values(); // calls the constructor for all the elements
    }

}