/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.Color;

/**
 *
 * @author Oliver
 */
public class ColorUtil {
    public static final byte black = (byte)0x00;
    public static final byte white = (byte)0xFF;
    public static final byte red = toByte( Color.RED );
    public static final byte light_gray = toByte( new Color( 30,30,30 ) );
    
    public static byte toByte( java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        return (byte) (((int) Math.round(red / 255.0 * 7.0) << 5) |
                    ((int) Math.round(green / 255.0 * 7.0) << 2) |
                    ((int) Math.round(blue / 255.0 * 3.0)));
    }
}
