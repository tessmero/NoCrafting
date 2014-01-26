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
        if( currentLayer.goNextLevel || currentLayer.goPreviousLevel ){
            
            visitedLevels.put( mapLevel, currentLayer );
            mapLevel -= ( currentLayer.goNextLevel ? 1 : -1 );
            
            currentLayer.goNextLevel = false;
            currentLayer.goPreviousLevel = false;
            currentLayer.removePlayer();
            
            if( visitedLevels.containsKey( mapLevel ) ){
                currentLayer = visitedLevels.get( mapLevel );
            }else
                currentLayer = buildLayer( mapLevel );
            Global.player.yPos -= Global.tileSize;
            visitedLevels.put( mapLevel, currentLayer );
            currentLayer.addActor( Global.player );
        }
    }
    
    private MapLayer buildLayer( int level ){
        MapLayer result = new CaveLayer();
        result.addObject( new UpLadder( (int)Global.player.xPos, (int)Global.player.yPos ) );
        return result;
    }
}
