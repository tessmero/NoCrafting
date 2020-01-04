/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map.flavor;

import gfx.CGraphics;
import gfx.CImage;

/**
 *
 * @author Oliver
 */
public abstract class CaptureEffect extends StaticDecayingObject{

    private static final long duration = 1000;
    
    public CaptureEffect( int xPos, int yPos ) {
        super( duration, xPos, yPos );
    }
    
    @Override
    public void draw( CGraphics g ){
        super.draw( g );
    }
}
