/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public abstract class Tileset {
    protected static final boolean debugMode = false;
    
    protected CImage[] autoBuildTiles( String sheetname, int tileSize ){
        BufferedImage sheetImage = RawImageHandler.getInstance().getResourceImage( sheetname );
        int w = sheetImage.getWidth();
        int h = sheetImage.getHeight();
        if( h % tileSize != 0 )
            throw new Error( "sprite sheet \"" + sheetname + "\" has height " + 
                    h + " (not a multiple of tilesize " + tileSize + ")" );
        if( w % tileSize != 0 )
            throw new Error( "sprite sheet \"" + sheetname + "\" has width " + 
                    w + " (not a multiple of tilesize " + tileSize + ")" );
        List< CImage > rList = new ArrayList();
        int x,y;
        for( y = 0 ; y < h ; y += tileSize )
            for( x = 0 ; x < w ; x += tileSize )
                rList.add( new CImage( sheetImage.getSubimage( x, y, tileSize, tileSize ) ) );
        int n = rList.size();
        CImage[] result = new CImage[n];
        for( int i = 0 ; i < n ; i++ )
            result[i] = rList.get( i );
        return result;
    }
    
    abstract protected CImage[] buildTiles();
    
    abstract protected CImage[] buildMinitiles();
    
    abstract protected Map< Integer, CImage[] > buildTileAnimations(); 
    
    abstract protected Map< Integer, CImage[] > buildMinitileAnimations();
    
    abstract public void assignEdgeMinitiles( int[] nineTiles, int[] output );
}
