/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import java.util.ArrayList;
import java.util.List;
import nocrafting.Global;
import nocrafting.actors.RandomPerson;
import nocrafting.map.flavor.GrassTuft;
import nocrafting.map.flavor.Tree;

/**
 *
 * @author Oliver
 */
public class CaveLayer extends MapLayer {
  
    
    public CaveLayer(){
        super( CaveTileset.getInstance(),60,60 );
    }
    
    @Override //spawn location in tile index
    public int getSpawnX() {
        return 20;
    }

    @Override
    public int getSpawnY() {
        return 15;
    }
    
    @Override
    public void draw( CGraphics g ){
        
        //draw layer tiles the standard way
        super.draw( g );
    }

    @Override
    protected int[][] buildLayoutArr() {
        int[][] result = new int[w][h];
        
        //start by filling everything with wall
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                result[x][y] = CaveTileset.INDEX_WALL;
        
        //create a circle of walkable floor
        int r2 = 20*20;
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                if( getD2FromCenter(x,y) < r2 )
                    result[x][y] = CaveTileset.INDEX_FLOOR;
        
        return result;
    }
    
    private int getD2FromCenter( int x, int y ){
        x -= w/2;
        y -= h/2;
        return x*x + y*y;
    }
}
