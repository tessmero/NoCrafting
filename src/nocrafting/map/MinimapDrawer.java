/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import gfx.ColorUtil;

/**
 *
 * @author Oliver
 */
public class MinimapDrawer {
    private static final byte walkableTileColor = ColorUtil.white;
    private static final byte blockedTileColor = ColorUtil.black;
    
    public static void draw( MapLayer layer, CGraphics g ){
        int w = layer.w;
        int h = layer.h;
        g.setColor( blockedTileColor );
        g.fillRect( 0, 0, w, h );
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                if( layer.isTileWalkable( x, y ) )
                    g.pushColorBuffer( x, y, walkableTileColor );
    }
}
