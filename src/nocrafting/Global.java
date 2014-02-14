/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nocrafting;

import java.util.ArrayList;
import java.util.List;
import nocrafting.actors.Player;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public class Global {
    
    //<editor-fold desc="keybindings" defaultstate="collapsed">
    
    //player movement
    public static final int KEY_MOVE_LEFT   = Keyboard.KEY_A;
    public static final int KEY_MOVE_RIGHT  = Keyboard.KEY_D;
    public static final int KEY_MOVE_UP     = Keyboard.KEY_W;
    public static final int KEY_MOVE_DOWN   = Keyboard.KEY_S;
    
    //pokemon commands
    public static final int KEY_CAPTURE         = Keyboard.KEY_C;
    public static final int KEY_PREV_POKEMON    = Keyboard.KEY_Q;
    public static final int KEY_NEXT_POKEMON    = Keyboard.KEY_E;
    public static final int KEY_USE_POKEMON     = Keyboard.KEY_X;
    
    //</editor-fold>
    
    //one game-pixel will contain pixelScaling^2 real-pixels
    public static final int pixelScaling = 2;
    
    //one pixel of brightness info will contain brightScaling^2 game-pixels
    public static final int brightScaling = 4;
    
    //screen dimensions in game-pixels
    public static final int screenWidth = 640/pixelScaling;
    public static final int screenHeight = 480/pixelScaling;
    public static final boolean fullscreen = false;
    
    //tile width in game-pixels
    public static final int tileSize = 16;
    
    //THE ONE AND ONLY PLAYER
    public static final Player player = new Player( tileSize*2,tileSize*2 );
    
    //THE ONE AND ONLY BELT
    public static final PokemonBelt playerBelt = new PokemonBelt( 6 );
    
    public static final List< String > messageQueue = new ArrayList();
}
