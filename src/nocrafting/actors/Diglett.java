/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import gfx.CImage;
import java.awt.image.BufferedImage;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public class Diglett extends RandomPerson{

    public Diglett(int xPos, int yPos) {
        super(xPos, yPos);
    }

    @Override
    protected CImage[] buildAllSprites() {
        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "diglett" );
        CImage[] rawSprites = new CImage[8];
        for( int i = 0 ; i < 8 ; i++ )
            rawSprites[i] = new CImage( sheet.getSubimage( i*16, 0, 16, 16 ) );
        CImage[] result = new CImage[12];
        int i = 0;
        for( int j : new int[]{ 0,1,0,2,3,2,4,5,4,6,7,6 } )
            result[i++] = rawSprites[j];
        return result;
    }
    
}
