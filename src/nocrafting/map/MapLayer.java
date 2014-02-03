/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import gfx.CImage;
import gfx.Drawable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import nocrafting.Global;
import nocrafting.actors.Actor;
import nocrafting.actors.ActorAnimState;
import nocrafting.actors.Pokemon;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public abstract class MapLayer {
    
    //set one of these to true to signal a level change
    public boolean goNextLevel = false;
    public boolean goPreviousLevel = false;
    
    //tile animation vars
    private static final int animationDelay = 200;
    private int animationTimer = animationDelay;
    private int animationFrame = 0;
    
    //dimensions in number of tiles
    protected final int w,h;
    
    //class determining the graphical style
    private final Tileset tileset;
    
    //contains layout information
    protected final int[][] layoutArr;
    
    //regularly updated, trueindicies indicate an actor-occupied tile
    private final boolean[][] tilesOccupiedByActors;
    
    //true indices indicate a collideable tile-based object occupying the tile
    private final boolean[][] tilesOccupiedByObjects;
    
    //contains edge-minitile information [cornerIndex][x][y]
    private final int[][][] miniTileLayout;
    
    public final CImage[] tileImages, minitileImages;
    public final Map< Integer, CImage[] > tileAnimations, minitileAnimations;
  
    protected final List< MapObject > objects = new ArrayList();
    private final List< Actor > actors = new ArrayList();
    
    public MapLayer( Tileset tileset, int w, int h ){
        this.w = w;
        this.h = h;
        this.tileset = tileset;
        tilesOccupiedByActors = new boolean[w][h];
        tilesOccupiedByObjects = new boolean[w][h];
        layoutArr = buildLayoutArr();
        tileImages = tileset.buildTiles();
        minitileImages = tileset.buildMinitiles();
        tileAnimations = tileset.buildTileAnimations();
        minitileAnimations = tileset.buildMinitileAnimations();
        miniTileLayout = new int[4][w][h];
        for( int x = 0 ; x < w ; x++ )
            for( int y = 0 ; y < h ; y++ )
                updateTileEdges( x, y );
    }
    
    public void addObject( MapObject mo ){
        objects.add( mo );
        if( TiledObject.class.isAssignableFrom(mo.getClass()) ){
            TiledObject to = (TiledObject)mo;
            if( to.isWalkable() )
                return;
            for( int x = 0 ; x < to.widthInTiles ; x++ )
                for( int y = 0 ; y < to.heightInTiles ; y++ )
                    tilesOccupiedByObjects[to.xPos/Global.tileSize+x][to.yPos/Global.tileSize+y] = true;
        }
    }
    
    public void addActor( Actor a ){
        if( !actors.contains( a ) )
            actors.add( a );
    }
    
    public void removePlayer(){
        actors.remove( Global.player );
    }
    
    public void draw( CGraphics g ){
        
        //draw tiles
        int x,y, ts = Global.tileSize;
        for( x = 0 ; x < w ; x++ )
            for( y = 0 ; y < h ; y++ )
                g.drawImage( tileImages[layoutArr[x][y]], x*ts, y*ts );  
        
        //draw edge minitiles where necessary
        int d, xo, yo;
        for( d = 0 ; d < 4 ; d++ ){
            xo = (d==1||d==3) ? ts/2 : 0;
            yo = (d<2)        ? ts/2 : 0;
            for( x = 0 ; x < w ; x++ )
                for( y = 0 ; y < h ; y++ )
                    if( miniTileLayout[d][x][y] > -1 ) 
                        g.drawImage( minitileImages[miniTileLayout[d][x][y]], 
                            x*ts + xo, 
                            y*ts + yo );
        }
        
//        //debug, highlight occupied tile borders
//        g.setColor( ColorUtil.light_gray );
//        g.setDrawingMode( CGraphics.MODE_ADD );
//        for( x = 0 ; x < w ; x++ )
//            for( y = 0 ; y < h ; y++ )
//                if( occupiedTiles[x][y] )
//                    g.drawRect( x*ts, y*ts, ts, ts );  
//        g.setDrawingMode( CGraphics.MODE_REPLACE );
        
        //create a list of drawables and sort based on Z index
        List< Drawable > drawables = new ArrayList();
        drawables.addAll( objects );
        drawables.addAll( actors );
        Collections.sort( drawables, zComparator );
        
        //draw objects and actors
        for( Drawable dr : drawables )
            dr.draw( g );
    }
    
    //comparator used to sort drawables for correct occlusion
    private static final Comparator<Drawable> zComparator = new Comparator<Drawable>(){
        @Override
        public int compare(Drawable t, Drawable t1) {
            return t1.getZIndex() - t.getZIndex();
        }
    };
    
    public void update( long ms ){
        int x,y;
        
        //set all tiles as unnoccupied
        for( x = 0 ; x < w ; x++ )
            for( y = 0 ; y < h ; y++ )
                tilesOccupiedByActors[x][y] = false;
        
        //set tiles with objects as occupied
        for( MapObject mo : objects  )
            tilesOccupiedByActors[mo.xPos/Global.tileSize][mo.yPos/Global.tileSize] = false;
//        
//        //set tiles with actors in them as occupied
//        for( Actor a : actors ){
//            x = a.getXTileIndex();
//            y = a.getYTileIndex();
////            occupiedTiles[x][y] = a;
////            if( a.state.getState() == ActorAnimState.STATE_WALKING )
//                occupiedTiles[x][y] = a;
//        }
        
        //update actors, confine them to walkable tiles
        for( Actor a : actors ){
            a.update( objects, ms );
            x = a.getXTileIndex();
            y = a.getYTileIndex();
            if( !isTileWalkable( x, y ) || isTileOccupied( x, y, a ) ){
                a.xPos -= a.walkDist*a.getDx();
                a.yPos -= a.walkDist*a.getDy();
                a.snapToNearestTile();
                a.hittingObstacle = true;
            }else
                a.hittingObstacle = false;
            if( a.currentEffect != null ){
                applyEffect( a.currentEffect, x, y );
                a.currentEffect = null;
            }
        }
        
        //check if the player is an a ladder
        if( Global.player.onTile() ){
            for( MapObject mo : objects ){
                if( DownLadder.class.isAssignableFrom( mo.getClass() ) )
                    if( ((TiledObject)mo).containsTile( 
                            (int)Global.player.xPos/Global.tileSize,
                            (int)Global.player.yPos/Global.tileSize ) )
                        goNextLevel = true;
                if( UpLadder.class.isAssignableFrom( mo.getClass() ) )
                    if( ((TiledObject)mo).containsTile( 
                            (int)Global.player.xPos/Global.tileSize,
                            (int)Global.player.yPos/Global.tileSize ) )
                        goPreviousLevel = true;
            }
        }
        
        //if the player pressed the "USE POKEMON" key, check if there's a pokemon selected...
        if( Global.player.state.getState() == ActorAnimState.STATE_STANDING && Keyboard.isKeyDown( Global.KEY_USE_POKEMON ) ){
            Pokemon p = Global.playerBelt.getSelectedPokemon();
            if( p != null && !p.summoned ){
                p.xPos = Global.player.xPos + Global.tileSize*Global.player.state.getDx()/2;
                p.yPos = Global.player.yPos + Global.tileSize*Global.player.state.getDy()/2;
                p.startSummonBehavior( Global.player.state.getDirection() );
                addActor( p );
            }
        }
        
        //remove any "unsummoned" pokemon from the map
        actors.removeAll( Global.playerBelt.listUnsummonedPokemon() );
        
        //advance tile animation timer and return, unless it's time to update tile animations
        animationTimer -= ms;
        if( animationTimer <= 0 ){
            animationFrame++;
            animationTimer = Math.max( 0, animationTimer + animationDelay );
        }else
            return;
        
        //update tile animations when necessary
        CImage[] frames;
        if( tileAnimations != null ){
            for( int key : tileAnimations.keySet() ){
                frames = tileAnimations.get( key );
                tileImages[key] = frames[animationFrame%frames.length];
            }
        }
        if( minitileAnimations != null ){
            for( int key : minitileAnimations.keySet() ){
                frames = minitileAnimations.get( key );
                minitileImages[key] = frames[animationFrame%frames.length];
            }
        }
    }
    
    abstract protected void applyEffect( MapEffect effect, int tileX, int tileY );
    
    private boolean isTileOccupied( int tx, int ty, Actor except ){
        if( tilesOccupiedByActors[tx][ty] )
            return true;
        for( Actor a : actors ){
            if( a.equals( except ) )
                continue;
            if( a.getXTileIndex() == tx && a.getYTileIndex() == ty )
                return true;
        }
        return false;
    }

    protected boolean isTileWalkable(int xIndex, int yIndex) {
        if( xIndex < 0 || xIndex >= w || yIndex < 0 || yIndex >= h )
            return false;
        if( tilesOccupiedByObjects[xIndex][yIndex] )
            return false;
        return tileset.isTileIDWalkable( layoutArr[xIndex][yIndex] );
    }
    
    //spawn location as tile index
    abstract public int getSpawnX();
    abstract public int getSpawnY();
    
    abstract protected int[][] buildLayoutArr();    
    
    public void updateAllEgesAroundTile( int x, int y, int[][][] layoutArr ){
        for( int i = -1 ; i < 2 ; i++ ){
            for( int j = -1 ; j < 2 ; j++ ){
                updateTileEdges( x+i, y+j );
            }
        }
    }
    
    public void updateTileEdges( int x, int y ){
        int i;
        int[] edgeTileOutput = new int[4];
        int[] edgeTileInput = new int[9];
        for( i = 0 ; i < 9 ; i++ )
            edgeTileInput[i] = -1;

        edgeTileInput[4] = layoutArr[x][y];
        if( x > 0 && y > 0 )
            edgeTileInput[0] = layoutArr[x-1][y-1];
        if( y > 0 ){
            edgeTileInput[1] = layoutArr[x][y-1];
            if( x < w-1 )
                edgeTileInput[2] = layoutArr[x+1][y-1];
        }if( x > 0 ){
            edgeTileInput[3] = layoutArr[x-1][y];
            if( y < h-1 ) 
                edgeTileInput[6] = layoutArr[x-1][y+1];
        }if( x < w-1 && y < h-1 )
            edgeTileInput[8] = layoutArr[x+1][y+1];
        if( y < h-1 )
            edgeTileInput[7] = layoutArr[x][y+1];
        if( x < w-1 )
            edgeTileInput[5] = layoutArr[x+1][y];

        tileset.assignEdgeMinitiles(edgeTileInput, edgeTileOutput);

        for( i = 0 ; i < 4 ; i++ )
            miniTileLayout[i][x][y] = edgeTileOutput[i];
    }
}
