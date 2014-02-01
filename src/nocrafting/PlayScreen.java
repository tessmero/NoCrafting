/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting;

import gfx.CGraphics;
import nocrafting.map.GameMap;

/**
 *
 * @author Oliver
 */
public class PlayScreen extends Screen{    
    private PlayScreen(){
        map = new GameMap();
    }
    private static final PlayScreen instance = new PlayScreen();
    public static PlayScreen getInstance(){ return instance; }
    
    private final GameMap map;
    
    private String hudMessage = null;
    private static final long hudMessageDuration = 2000;
    private long hudMessageTimer = 0;
    
    @Override
    public void draw(CGraphics g) {
        int camX = -(int)Global.player.xPos + Global.screenWidth/2 - 12;
        int camY = -(int)Global.player.yPos + Global.screenHeight/2 - 12;
        
        g.translate( camX, camY );
        
        g.fill( (byte)0x00 );
        g.setColor( (byte)0xFF );
        g.fillRect( 0, 0, 20, 20 );
        
        map.draw( g );
        
        g.translate( -camX, -camY );
        
        Global.playerBelt.drawHudView( g );
        
        g.drawString( "POS: " + ((int)Global.player.xPos/Global.tileSize) + ", " + ((int)Global.player.yPos/Global.tileSize), 50, 0 );
        
        if( hudMessage != null )
            g.drawStringCentered( hudMessage, Global.screenWidth/2, Global.screenHeight/2+Global.tileSize*2 );
    }

    @Override
    public void update(long ms) {
        map.update( ms );
        
        if( hudMessageTimer >= 0 ) 
            hudMessageTimer -= ms;
        else{
            if( Global.messageQueue.isEmpty() )
                hudMessage = null;
            else{
                hudMessage = Global.messageQueue.remove( 0 );
                hudMessageTimer = hudMessageDuration;
            }
        }
                
    }
}
