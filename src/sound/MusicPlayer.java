/*
 * TCSS 305
 */

package sound;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.media.CannotRealizeException;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.protocol.URLDataSource;

/**
 * MusicPlayer is used to play MP3 and WAV music files.
 * 
 * @author Anon
 * @edited Justin Arnett
 * @version 1.2
 */

public class MusicPlayer {

    /** The player. */
    private Player myPlayer;

    /** The file being played. */
    private URL myFile;

    /** The playlist. */
    private List<URL> myPlayList;

    /** The current index within the playlist. */
    private int myIndex;

    /** Indicates if the music is paused or not. */
    private boolean myPaused;
    
    /** The starting default volume level of music */
    private float myVolume = 0.15f;

    /**
     * The newList method creates a new playlist using the passed array of files.
     * 
     * @param theFiles The array of files to add to the playlist.
     */
    public void newList(final URL[] theFiles) {
        myPlayList = new ArrayList<URL>();

        for (int i = 0; i < theFiles.length; i++) {
            myPlayList.add(theFiles[i]);
        }

        myFile = myPlayList.get(0);
        myIndex = 0;
        myPaused = false;
        getSong();
    }

    /**
     * The changed method stops the current song and adds the passed value to
     * the index. The file at the new index is then played
     * 
     * @param theChange The change to the index
     */
    public void change(final int theChange) {
        if (!isStarted()) {
            final int newIndex = myIndex + theChange;

            if (newIndex >= 0 && newIndex <= myPlayList.size() - 1) {
                myIndex = newIndex;
                myFile = myPlayList.get(myIndex);
                myPlayer.stop();
                getSong();
            }
        }
    }

    /**
     * Used to verify if the player has a playlist loaded.
     * 
     * @return true if the player has a playlist.
     */
    public boolean hasList() {
        return myPlayList != null;
    }

    /**
     * Used to check if a song is started.
     * 
     * @return true if there is a song playing.
     */
    public boolean isStarted() {
        return myPlayer != null && myPlayer.getState() == Player.Started;
    }

    /**
     * Stops the current song if applicable.
     */
    public void stopPlay() {
        if (myPlayer != null) {
            myPlayer.stop();
            myPlayer.close();
        }
    }

    /**
     * Restarts a paused song.
     */
    public void play() {
        if (myPlayer != null) {
            myPlayer.start();
            updateVolume();
        }
    }
    
    /**
     * Updates the volume of current track being played.
     * Not running this function will result in different
     * volumes for different tracks when tracks are switched.
     */
    private void updateVolume() {
        myPlayer.getGainControl().setLevel(myVolume);
    }
    
    
    /**
     * Sets the volume of the current track. The provided value
     * with a range of 0.0f to 1.0f is transformed to be more 
     * suitable for decibels (dB), then applied to the volume level.
     * 
     * @param theVolume The provided value for setting the volume.
     */
    public void setVolume(final float theVolume) {
        
        float volume = (float) ((Math.exp(theVolume)-1)/(Math.E-1));
        myPlayer.getGainControl().setLevel(volume);
        myVolume = volume;
        
        /*
        System.out.println(" DB: " + myPlayer.getGainControl().getDB() + 
                           "  Lvl: " + myPlayer.getGainControl().getLevel());
        */
        
    }
    

    /**
     * Pauses or unpauses the current song as applicable.
     */
    public void togglePause() {
        if (myPlayer != null) {
            if (myPaused) {
                myPaused = false;
                myPlayer.start();
            } else {
                myPaused = true;
                myPlayer.stop();
            }
        }
    }
    
    

    // Private Methods

    /**
     * Loads the song in the current file.
     */
    private void getSong() {
        try {
            if (myPlayer != null) {
                myPlayer.stop();
                myPlayer.close();
            }
            URLDataSource uds = new URLDataSource(myFile);
            uds.connect();
            try {
                myPlayer = Manager.createRealizedPlayer(uds);
            }
            catch (CannotRealizeException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myPlayer.addControllerListener(new ControllerListener() {
                public void controllerUpdate(final ControllerEvent theEvent) {
                    if (theEvent instanceof EndOfMediaEvent) {
                        change(1);
                    }
                }
            });
            //myPlayer.prefetch();
            //myPlayer.realize();
            myPlayer.start();
            updateVolume();
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final NoPlayerException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
