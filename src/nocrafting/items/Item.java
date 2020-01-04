/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.items;

import gfx.CGraphics;
import gfx.CImage;
import nocrafting.Global;
import nocrafting.map.MapObject;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public abstract class Item extends MapObject{
    public static class Rock extends Item{

        public Rock(int xPos, int yPos) {
            super(xPos, yPos);
        }

        @Override
        protected CImage buildSprite() {
//            CImage result = new CImage( RawImageHandler.getInstance().getResourceImage( "rock" ) );
//            System.out.println( "private static final CImage icon = new CImage( \"" + result.toVerboseString() + "\" );" );
            return icon;
        }
        private static final CImage icon = new CImage( "16:16:0000004945454549494545454545490000494945D68D8D4545B18D8D654545490045454545D6D645B1B1B18D656545496565B1B16565654545D6D6D66565454965B1B1B18D65656545D6D6D6D665454965D6D6D6D68D65654549D6D66565490065D6D6D6D6D6656565454545456549490065D6D6D6D66565B1B18D8D6565454900006565658D8D65B1B1B18D8D65454900000065D6B18D65B1B1B1B18D8D454900000065656545458DD6D6D6D68D4500000065658D8D8D6545D6D6D6D68D65000065B6B6B68D8D8D458D65D6D66500000065B6D6D6D68D8D4500006565000000000065D6D6D68D65000000000000000000000065656565000000000000000000:0001111111111110011111111111111101111111111111111111111111111111111111111111111111111111111111101111111111111111011111111111111100111111111111110001111111111111000111111111111000111111111111100111111111111100011111111001100000111111000000000001111000000000" );
    }
    
    private Item(int xPos, int yPos) {
        super(xPos, yPos);
    }
    
    public CImage getIcon(){
        return sprite;
    }
    
}
