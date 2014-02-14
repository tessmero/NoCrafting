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
public class OverworldLayer extends MapLayer {
  
    
    public OverworldLayer(){
        super( OverworldTileset.getInstance(),60,60 );
    }
    
    @Override //spawn location in tile index
    public int getSpawnX() {return 20;}
    
    @Override
    public int getSpawnY() {return 15;}
    
//    @Override
//    public void draw( CGraphics g ){
//        
//        //draw layer tiles the standard way
//        super.draw( g );
//    }

    @Override
    protected int[][] buildLayoutArr() {
        int[][] result = new int[w][h];
        
        //start by filling everything with water
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                result[x][y] = OverworldTileset.INDEX_OCEAN;
        
        //create a circle of sand
        int r2 = 20*20;
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                if( getD2FromCenter(x,y) < r2 )
                    result[x][y] = OverworldTileset.INDEX_SAND;
        
        //create a smaller circle of grass
        r2 = 15*15;
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                if( getD2FromCenter(x,y) < r2 )
                    result[x][y] = OverworldTileset.INDEX_GRASS;
        
        //create a list of tiled objects to add, starting with downladder
        List< TiledObject > toAdd = new ArrayList();
        toAdd.add( new DownLadder( w*Global.tileSize/2, h*Global.tileSize/2 ) );
        
        //add some trees
        for( int i = 0 ; i < 10 ; i++ ){
            TiledObject t = new Tree( (w/2)*Global.tileSize,(h/2)*Global.tileSize );
            while( collides( t, toAdd ) ){
                t.xPos = (20+(int)(Math.random()*(w-40)))*Global.tileSize;
                t.yPos = (20+(int)(Math.random()*(h-40)))*Global.tileSize;
                t.snapToTile();
            }
            toAdd.add( t );
        }
        
        for( TiledObject to : toAdd )
            addObject( to );
//        
//        //add grass tufts where there is grass, but no object
//        for( int x = 0 ; x < w ; x++ )
//            for( int y = 0 ; y < h ; y++ )
//                if( result[x][y] == OverworldTileset.INDEX_GRASS )
//                    for( int i = 0 ; i < 2 ; i++ )
//                        addObject( new GrassTuft( 
//                            x*Global.tileSize+(int)(Math.random()*Global.tileSize)-3, 
//                            y*Global.tileSize+(int)(Math.random()*Global.tileSize)-2 ) );
        
        //add some random people
        int x = 40, y = 40;
        for( int i = 0 ; i < 4 ; i++ ){
            addActor( new RandomPerson( x*Global.tileSize, y*Global.tileSize ) );
        }
        
        return result;
    }
    
    private int getD2FromCenter( int x, int y ){
        x -= w/2;
        y -= h/2;
        return x*x + y*y;
    }
    
    private boolean collides( TiledObject test, List< TiledObject > others ){
        for( TiledObject o : others )
            if( test.collided( o ) )
                return true;
        return false;
    }

    @Override
    protected void applyEffect(MapEffect effect, int tileX, int tileY) {
        //do nothing
    }
}
