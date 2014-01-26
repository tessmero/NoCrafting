/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nocrafting;

import nocrafting.actors.Player;

/**
 *
 * @author Oliver
 */
public class Global {
    
    //one game-pixel will contain pixelScaling^2 real-pixels
    public static final int pixelScaling = 2;
    
    //screen dimensions in game-pixels
    public static final int screenWidth = 640/pixelScaling;
    public static final int screenHeight = 480/pixelScaling;
    public static final boolean fullscreen = false;
    
    //tile width in game-pixels
    public static final int tileSize = 16;
    
    //THE ONE AND ONLY PLAYER
    public static final Player player = new Player( tileSize*2,tileSize*2 );
}
