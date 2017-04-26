/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package zelda;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * The images and animation steps of Link.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 12 March 2015
 */
public enum Link {
    
    /** Link image in the animation cycle. */
    LINK_1("/images/link_1.gif"),
    /** Link image in the animation cycle. */
    LINK_2("/images/link_2.gif"),
    /** Link image in the animation cycle. */
    LINK_3("/images/link_3.gif"),
    /** Link image in the animation cycle. */
    LINK_4("/images/link_4.gif"),
    /** Link image in the animation cycle. */
    LINK_5("/images/link_5.gif"),
    /** Link image in the animation cycle. */
    LINK_6("/images/link_6.gif");
   
    /** The image of Link's current step in the animation. */
    private Image myImage;
    
    
    /**
     * Builds the image from the file path to attach to the Link.
     * 
     * @param theImagePath The file path.
     */
    Link(final String theImagePath) {
        java.net.URL imgURL = getClass().getResource(theImagePath);
        //System.out.println(imgURL);
        myImage = new ImageIcon(imgURL).getImage();
    }
    
    
    /**
     * Returns the image of Link.
     * 
     * @return The image of Link.
     */
    public Image getImage() {
        return myImage;
    }
    
    
    /**
     * Cycles the Links through their animation order.
     * 
     * @return The current Link image.
     */
    public Link advance() {
        Link result = LINK_1;

        switch (this) {
            case LINK_1:
                result = LINK_2;
                break;

            case LINK_2:
                result = LINK_3;
                break;

            case LINK_3:
                result = LINK_4;
                break;
                
            case LINK_4:
                result = LINK_5;
                break;
                
            case LINK_5:
                result = LINK_6;
                break;
                
            case LINK_6:
                result = LINK_1;
                break;
                
            default:
        }

        return result;
    } 
    
}