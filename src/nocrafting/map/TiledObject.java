/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import nocrafting.Global;

/**
 *
 * @author Oliver
 */
public abstract class TiledObject extends MapObject {
    public final int widthInTiles, heightInTiles;

    public TiledObject(int xPos, int yPos) {
        super( xPos, yPos );
        snapToTile();
        widthInTiles = buildWidthInTiles();
        heightInTiles = buildHeightInTiles();
    }
    
    public boolean containsTile( int tileX, int tileY ){
        int x = xPos / Global.tileSize;
        int y = yPos / Global.tileSize;
        return tileX >= x && tileX < x+widthInTiles &&
                tileY >= y && tileY < y+heightInTiles;
    }
    
    public boolean collided( TiledObject other ){
        for( int x = 0 ; x < widthInTiles ; x++ )
            for( int y = 0 ; y < heightInTiles ; y++ )
                if( other.containsTile( xPos/Global.tileSize+x, yPos/Global.tileSize+y ) )
                    return true;
        return false;
    }
    
    abstract protected int buildWidthInTiles();
    abstract protected int buildHeightInTiles();
    abstract public boolean isWalkable();
}
