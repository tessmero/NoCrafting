/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CImage;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public class DownLadder extends TiledObject{

    public DownLadder(int xPos, int yPos) {
        super(xPos, yPos);
    }
    @Override
    protected int buildWidthInTiles() {
        return 1;
    }

    @Override
    protected int buildHeightInTiles() {
        return 1;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    protected CImage buildSprite() {
        return sprite;
    }
    
    //<editor-fold desc="main() produces verboseString for sprite in getResourceImage( "ladders" )" defaultstate="collapsed">
    
//    //dictates the amount to trim off the sides of the sprites
//    public static void main(String[] args) {        
//        System.out.println( "private static final CImage sprite = new CImage( \"" + 
//                new CImage( RawImageHandler.getInstance()
//                        .getResourceImage( "ladders" ) ).toVerboseString() + "\" )," );
//    }
    //</editor-fold>
    
private static final CImage sprite = new CImage( "16:16:00000000004921212149494900000000000000494921212149212121490000000049498D49212121212121218D4900000049218D8D8D8D8D8D8D8D8D8D2149000021498D49494949494949498D214900492149D621212121212121498D212100492149D64949494949494949D6212149212149D6FFFFFFFFFFFFFFD6D6212149212149D64949494949494949D6212149212149D64921212121212149D6212149492149D64949494949494949D6212100002149D6FFFFFFFFFFFFFFD6D6214900004949D64949494949494949D6494900004949D64921212121212149D6490000000049FF4921212121212149FF00000000000049000049494900000049000000:0000011111110000000111111111100001111111111111000111111111111110011111111111111011111111111111101111111111111111111111111111111111111111111111111111111111111111111111111111111001111111111111100111111111111110011111111111110000111111111110000001001110001000" );

}
