/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nocrafting;

import gfx.CGraphics;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Oliver
 */
public class GameDriver {
    private GameState state;
    
    //every iteration of main loop, this object will be updated 
    //and be allowed to draw to graphics object below
    private Screen screen;
    
    private final CGraphics g;
    
    public GameDriver() throws Exception{
        g = new CGraphics( Global.screenWidth, Global.screenHeight );
        g.initDisplay();
        setState( GameState.MAIN_MENU );
    }
    
    public void launch() throws Exception{
        
        //init timing variables
        long prevTime = System.currentTimeMillis();
        long currTime;
        
        //main loop
        while(!g.isCloseRequested()){
            currTime = System.currentTimeMillis();
            
            //do update,drawing
            screen.update( currTime-prevTime );
            screen.draw( g );
            
            g.updateDisplay();
            
            prevTime = currTime;
            
        }
        
        g.destroyDisplay();
    }
    
    public void setState( GameState target ){
        state = target;
        switch( state ){
            case PLAYING:
                screen = PlayScreen.getInstance();
                break;
            case PLAY_MENU:
            case MAIN_MENU:
                screen = MainMenuScreen.getInstance();
                break;
        }
        screen.setParentDriver( this );
    }
}
