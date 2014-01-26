/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import java.util.HashMap;
import java.util.Map;
import nocrafting.Global;

/**
 *
 * @author Oliver
 */
public class GameMap {
    private final Map< Integer, MapLayer > visitedLevels = new HashMap();
    
    public int mapLevel = 0;
    
    private MapLayer currentLayer;
    
    public GameMap(){
        currentLayer = new OverworldLayer();
        currentLayer.addActor( Global.player.setTilePosition( 
                currentLayer.getSpawnX(), currentLayer.getSpawnY() ) );
    }
    
    public void draw( CGraphics g ){
        currentLayer.draw( g );
    }
    
    public void update( long ms ){
        currentLayer.update( ms );
        
        //check for flags thrown when player stands on a ladder
        if( currentLayer.goNextLevel || currentLayer.goPreviousLevel ){
            
            //associate this old level with this old level index, in case it hasn't already been
            visitedLevels.put( mapLevel, currentLayer );
            
            //change the level index
            mapLevel -= ( currentLayer.goNextLevel ? 1 : -1 );
            
            //remove the player and flags from the old level
            currentLayer.goNextLevel = false;
            currentLayer.goPreviousLevel = false;
            currentLayer.removePlayer();
            
            //create/load a level based on the updated level index
            if( visitedLevels.containsKey( mapLevel ) ){
                currentLayer = visitedLevels.get( mapLevel );
            }else
                currentLayer = buildLayer( mapLevel );
            
            //place the player in the new level, just below the ladder
            Global.player.yPos -= Global.tileSize;
            currentLayer.addActor( Global.player );
            
            //associate the new level index with the new level object
            visitedLevels.put( mapLevel, currentLayer );
        }
    }
    
    private MapLayer buildLayer( int level ){
        MapLayer result = new CaveLayer();
        result.addObject( new UpLadder( (int)Global.player.xPos, (int)Global.player.yPos ) );
        return result;
    }
}
