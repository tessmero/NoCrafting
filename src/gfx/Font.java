/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gfx;

import java.awt.image.BufferedImage;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public class Font {
    public static final int charWidth = 6;
    public static final int charHeight = 13;
    public static final char minValue = '0';
    public static final char maxValue = 'z';
    
    //character images indexed by ( asciivalue - minValue )
    public static final CImage[] charImages = buildCharImages();
    
    private static CImage[] buildCharImages(){
        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "font" );
        int n = maxValue - minValue;
        CImage[] result = new CImage[n];
        for( int i = 0 ; i < n ; i++ )
            result[i] = new CImage( sheet.getSubimage( i*charWidth, 0, charWidth, charHeight ) );
        return result;
    }
}
