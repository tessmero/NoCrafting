/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting;

import gfx.CGraphics;

/**
 *
 * @author Oliver
 */
public abstract class Screen {
    private GameDriver parent = null;
    
    public abstract void draw( CGraphics g );
    
    public abstract void update( long ms );
    
    public void setParentDriver( GameDriver gd ){
        parent = gd;
    }
    
    protected void triggerGameStateChange( GameState targetState ){
        if( parent == null )
            throw new Error( "parent GameDriver not set" );
        parent.setState( targetState );
    }
}
