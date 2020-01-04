/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import nocrafting.Global;
import nocrafting.actors.Diglett;
import nocrafting.items.Item;

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
        int r2 = 40;
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                if( getD2FromCenter(x,y) < r2 )
                    result[x][y] = CaveTileset.INDEX_FLOOR;
        
        //Create four rooms randomly
        for( int bx = 0 ; bx < 2 ; bx++ ){
            for( int by = 0 ; by < 2 ; by++ ){
                int rw = 5+(int)(Math.random()*5);
                int rh = 5+(int)(Math.random()*5);
                int rx = (w/6)+(int)(Math.random()*(w/6-rw));
                int ry = (h/6)+(int)(Math.random()*(h/6-rh));
                for( int x = 0 ; x < rw ; x++ )
                    for( int y = 0 ; y < rh ; y++ )
                        result[(bx*w/2)+rx+x][(by*h/2)+ry+y] = CaveTileset.INDEX_FLOOR;
            }
        }
        
        addActor( new Diglett( (w/2+3)*Global.tileSize, (h/2)*Global.tileSize ) );
        
        //assign appropriate corner/edge tiles
        for( int x = 1 ; x < w-1 ; x++ ){
            for( int y = 1 ; y < h-1 ; y++ ){
                updateTile( x, y, result );
            }
        }
        
        return result;
    }
   
    @Override
    protected void applyEffect(MapEffect effect, int tileX, int tileY) {
        Class c = effect.getClass();
        if( c == MapEffect.Dig.class ){
            if( tileX == 0 || tileX == w-1 || tileY == 0 || tileY == h-1 )
                return;
            effect.sender.inventory.addItem( getDropItemForTile( tileX, tileY ) );
            layoutArr[tileX][tileY] = CaveTileset.INDEX_FLOOR;
            for( int x = tileX-1 ; x <= tileX+1 ; x++ )
                for( int y = tileY-1 ; y <= tileY+1 ; y++ )
                    updateTile( x, y, layoutArr );
        }
    }
    
    private Item getDropItemForTile( int tileX, int tileY ){
        if( layoutArr[tileX][tileY] != CaveTileset.INDEX_FLOOR )
            return new Item.Rock( tileX*Global.tileSize, tileY*Global.tileSize );
        return null;
    }
    
    private static final int f = CaveTileset.INDEX_FLOOR;
    private void updateTile( int x, int y, int[][] layout ){
        try{
            if( layout[x][y] == f )
                return;
            if( layout[x-1][y] == f ){
                if( layout[x+1][y] == f ){
                    layout[x][y] = CaveTileset.INDEX_ISLAND;
                }else{
                    if( layout[x][y-1] == f ){
                        if( layout[x][y+1] == f ){
                            layout[x][y] = CaveTileset.INDEX_ISLAND;
                        }else{
                            layout[x][y] = CaveTileset.INDEX_SW_WALL;
                        }
                    }else{
                        if( layout[x][y+1] == f ){
                            layout[x][y] = CaveTileset.INDEX_NW_WALL;
                        }else{
                            layout[x][y] = CaveTileset.INDEX_W_WALL;
                        }
                    }
                }
            }else{
                if( layout[x+1][y] == f ){
                    if( layout[x][y+1] == f ){
                        if( layout[x][y-1] == f ){
                            layout[x][y] = CaveTileset.INDEX_ISLAND;
                        }else{
                            layout[x][y] = CaveTileset.INDEX_NE_WALL;
                        }
                    }else{
                        if( layout[x][y-1] == f ){
                            layout[x][y] = CaveTileset.INDEX_SE_WALL;
                        }else{
                            layout[x][y] = CaveTileset.INDEX_E_WALL;
                        }
                    }
                }else{
                    if( layout[x][y+1] == f ){
                        if( layout[x][y-1] == f ){
                            layout[x][y] = CaveTileset.INDEX_ISLAND;
                        }else{
                            layout[x][y] = CaveTileset.INDEX_N_WALL;
                        }
                    }else{
                        if( layout[x][y-1] == f ){
                            layout[x][y] = CaveTileset.INDEX_S_WALL;
                        }
                    }
                }
            }if( layout[x][y] == CaveTileset.INDEX_WALL ){
                readCorners( x, y, layout );
                if( corners[0] ){
                    if( corners[1] )
                        layout[x][y] = CaveTileset.INDEX_ISLAND;
                    else if( corners[2] )
                        layout[x][y] = CaveTileset.INDEX_ISLAND;
                    else if( corners[3] )
                        layout[x][y] = CaveTileset.INDEX_ISLAND;
                    else
                        layout[x][y] = CaveTileset.INDEX_SW_BEND;
                }else{
                    if( corners[1] ){
                        if( corners[2] )
                            layout[x][y] = CaveTileset.INDEX_ISLAND;
                        else if( corners[3] )
                            layout[x][y] = CaveTileset.INDEX_ISLAND;
                        else
                            layout[x][y] = CaveTileset.INDEX_NW_BEND;
                    }else{
                        if( corners[2] ){
                            if( corners[3] )
                                layout[x][y] = CaveTileset.INDEX_ISLAND;
                            else
                                layout[x][y] = CaveTileset.INDEX_NE_BEND;
                        }else if( corners[3] )
                            layout[x][y] = CaveTileset.INDEX_SE_BEND;                            
                    }
                }
            }
        }catch( ArrayIndexOutOfBoundsException e ){
            //do nothing
        }
    }
    
    private static final boolean[] corners = new boolean[4];
    private void readCorners( int x, int y, int[][] layout ){
        corners[0] = layout[x-1][y-1] == f;
        corners[1] = layout[x-1][y+1] == f;
        corners[2] = layout[x+1][y+1] == f;
        corners[3] = layout[x+1][y-1] == f;
    }
    
    private int getD2FromCenter( int x, int y ){
        x -= w/2;
        y -= h/2;
        return x*x + y*y;
    }
}
