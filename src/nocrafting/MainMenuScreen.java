/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting;

import gfx.CGraphics;
import gfx.Font;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public class MainMenuScreen extends Screen{
    private MainMenuScreen(){}
    private static final MainMenuScreen instance = new MainMenuScreen();
    public static MainMenuScreen getInstance(){ return instance; }
    
    @Override
    public void draw(CGraphics g) {
        g.fill( (byte)0xFF );        
        
        g.drawStringCentered( "SINGLE PLAYER", Global.screenWidth/2, Global.screenHeight/2 + 2*(Font.charHeight+2) );
        g.drawStringCentered( "NO CRAFTING", Global.screenWidth/2, Global.screenHeight/2 + Font.charHeight+2 );
        g.drawStringCentered( "[press enter to start]", Global.screenWidth/2, Global.screenHeight/2 );
    }

    @Override
    public void update(long ms) {
        if( Keyboard.isKeyDown( Keyboard.KEY_RETURN ) )
            triggerGameStateChange( GameState.PLAYING );
    }
    
}
