/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package sound;

/**
 * The sound effects.
 * 
 * @author Justin Arnett
 * @version 12 March 2015
 */
public enum SoundEffects {
    
    /** Sound for when a piece freezes in place. */
    BLOCK("/audio/sfx/block.wav"),
    /** Sound for when a row(s) of blocks is cleared. */
    CLEAR("/audio/sfx/clear.wav"),
    /** Sound for when an error in the menu occurs. */
    ERROR("/audio/sfx/error.wav");
   

    /** Starting volume level. */
    private boolean myMuted;

    /** Each sound effect has its own clip, loaded with its own sound file. */
    private String mySoundFileName;

    
    /**
     * Constructor to construct each element of the enum with its own sound file.
     * 
     * @param theSoundFileName The name of the sound file.
     */
    SoundEffects(final String theSoundFileName) {
        mySoundFileName = theSoundFileName;
        myMuted = false;
    }
    
    
    /**
     * Preloads the clip to the player.
     * 
     * @param thePlayer The player that needs to preload the clip.
     */
    public void preLoad(final SoundPlayer thePlayer) {
        thePlayer.preLoad(mySoundFileName);
    }

    
    /**
     * Play or Re-play the sound effect from the beginning, by rewinding.
     * 
     * @param thePlayer The sound player.
     */
    public void play(final SoundPlayer thePlayer) {
        if (!myMuted) {
            thePlayer.play(mySoundFileName);
        }
    }
    
    
    /**
     * Gets the file name of the sound file. 
     * 
     * @return The file name.
     */
    public String getFileName() {
        return mySoundFileName;
    }
    
    
    /**
     * Mutes and unmutes the sound player.
     * 
     * @param theVolume Status of the player being muted.
     * @param thePlayer The sound player.
     */
    public void setVolume(final boolean theVolume, final SoundPlayer thePlayer) {
        myMuted = theVolume;
        if (myMuted) {
            thePlayer.stopAll();
        }
    }
    
    /**
     * Sets the volume of the sound player.
     * 
     * @param theVolume Status of the player being muted.
     * @param thePlayer The sound player.
     */
    public void setVolume(final float theVolume, final SoundPlayer thePlayer) {
        thePlayer.setVolume(theVolume);
    }


    /**
     * Optionally pre-loads all of the sound files.
     * 
     * @param thePlayer The sound player.
     */
    static void init(final SoundPlayer thePlayer) {
        values(); // calls the constructor for all the elements
    }

}


