/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author Oliver
 */
public class CImage {
    
    //for constructing from BufferedImage
    private static final java.awt.Color transparentColor = new java.awt.Color( 222, 0, 222 );
    
    public final byte[] pixels;
    
    public final boolean[] opacity;
    
    public final int w,h,n;
    
    public CImage( byte[] pixels, boolean[] opacity, int w, int h ){
        this.pixels = pixels;
        this.opacity = opacity;
        this.w = w;
        this.h = h;
        this.n = w*h;
    }
    
    public CImage( BufferedImage bi ){
        w = bi.getWidth();
        h = bi.getHeight();
        n = w*h;
        pixels = new byte[n];
        opacity = new boolean[n];
        int x,y,i=0;
        java.awt.Color jac;
        byte c;
        for( y = h-1 ; y >= 0 ; y-- ){
            for( x = 0 ; x < w ; x++ ){
                jac = new java.awt.Color( bi.getRGB( x, y ) );
                if( !jac.equals( transparentColor ) ){
                    opacity[i] = true;
                    pixels[i] = jacToByte( jac );
                }
                i++;
            }
        }
    }
    
    public CImage( String verboseString ){
        String[] parts = verboseString.split( ":" );
        
        w = Integer.parseInt( parts[0] );
        h = Integer.parseInt( parts[1] );
        n = w*h;
        
        pixels = hexStringToByteArray( parts[2] );
        
        opacity = new boolean[n];
        char[] cs = parts[3].toCharArray();
        for( int i = 0 ; i < n ; i++ )
            if( cs[i] == '1' )
                opacity[i] = true;
    }
    
    //only been tested on square images
    public CImage getRotated90(){
        byte[] b = new byte[n];
        boolean[] o = new boolean[n];
        int x,y;
        for( x = 0 ; x < w ; x++ ){
            for( y = 0 ; y < h ; y++ ){
                b[y*w+x] = pixels[x*w+(h-y-1)];
                o[y*w+x] = opacity[x*w+(h-y-1)];
            }
        }
        return new CImage( b, o, h, w );
    }
    
    public String toVerboseString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append( w ).append( ":" ).append( h ).append( ":" );
        sb.append( bytesToHex( pixels ) ).append( ":" );
        for( boolean b : opacity )
            sb.append( b ? "1" : "0" );
        
        return sb.toString();
    }
    
    public static byte jacToByte( java.awt.Color jac ) {
        int red = jac.getRed();
        int green = jac.getGreen();
        int blue = jac.getBlue();

        return (byte) (((int) Math.round(red / 255.0 * 7.0) << 5) |
                    ((int) Math.round(green / 255.0 * 7.0) << 2) |
                    ((int) Math.round(blue / 255.0 * 3.0)));
    }
    
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
