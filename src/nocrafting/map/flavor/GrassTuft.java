/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map.flavor;

import gfx.CImage;
import nocrafting.map.MapObject;

/**
 *
 * @author Oliver
 */
public class GrassTuft extends MapObject{
    private static final int width = 7;
    private static final int height = 3;
    
    public GrassTuft(int xPos, int yPos) {
        super(xPos, yPos);
    }

    //<editor-fold desc="main() produces verboseString for sprites in getResourceImage( "grass" )" defaultstate="collapsed">
    
    //dictates the amount to trim off the sides of the sprites
//    public static void main(String[] args) {
//        System.out.print( "processing grass sprites..." );
//        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "grass" );
//        int x,y,i;
//        CImage[] arr = new CImage[2];
//        i = 0;
//        for( y = 0 ; y < 2 ; y++ ){
//            arr[i++] = new CImage( sheet.getSubimage( 0,height*y,width, height ) );
//        }
//        System.out.println( " done!" );
//        
//        System.out.println( "private static final CImage[] spriteSet = new CImage[]{" );
//        for( int j  = 0 ; j < 2 ; j++ )
//            System.out.println( "new CImage( \"" + arr[j].toVerboseString() + "\" )," );
//        System.out.println( "};" );
//        
//    }
    //</editor-fold>
    
    private static final CImage[] spriteSet = new CImage[]{
        new CImage( "7:3:003500000035003500003500003500000035000000:010001010010010001000" ),
        new CImage( "7:3:009A0000009A009A00009A00009A0000009A000000:010001010010010001000" ),
    };

    @Override
    protected CImage buildSprite() {
        return spriteSet[(int)(Math.random()*spriteSet.length)];
    }
}
