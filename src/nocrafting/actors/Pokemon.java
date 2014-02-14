/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import gfx.CImage;
import nocrafting.Global;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public abstract class Pokemon extends RandomPerson{

    //while false, this pokemon will move randomly
    public boolean summoned = false;
    
    public final CImage icon, silhouetteImage;
    
    public Pokemon(int xPos, int yPos) {
        super(xPos, yPos);
        
        icon = buildIcon();
        silhouetteImage = buildSillhouette( buildIcon() );
        
    }
    
    private CImage buildSillhouette( CImage original ){
        byte color = (byte)0xFF;
        byte[] bArr = new byte[original.n];
        for( int i = 0 ; i < original.n ; i++ )
            bArr[i] = color;
        return new CImage( bArr, original.opacity, original.w, original.h );
    }
    
    abstract protected CImage buildIcon();
    
    abstract public void startSummonBehavior( int dir );
}
