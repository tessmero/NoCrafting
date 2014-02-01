/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import nocrafting.map.MapObject;
import gfx.CGraphics;
import gfx.CImage;
import gfx.Drawable;
import java.util.List;
import nocrafting.Global;
import nocrafting.map.MapEffect;

/**
 *
 * @author Simon
 */
public abstract class Actor implements Drawable{
    //maximum motion per frame, pixels
    private static final double maxWalkDist = 1;
    
    //movement speed, pixels/millisec
    private static final double walkSpeed = .04;
    
    //distance walked in last update
    public double walkDist = 0;
    
    //position
    public double xPos, yPos;
    
    //contains all possible animation frames
    private final CImage[] allSprites;
    
    //should be set to true when the actor is running into something
    public boolean hittingObstacle = false;
    
    //stores state information related to animation
    public final ActorAnimState state;
    
    //set as non-null if the actor is effecting the game map
    public MapEffect currentEffect = null;
    
    public Actor( int xPos, int yPos ){
        this.xPos = xPos;
        this.yPos = yPos;
        snapToNearestTile();
        allSprites = buildAllSprites();
        state = new ActorAnimState();
    }
    
    public int getDx(){
        if( hittingObstacle || state.getState() == ActorAnimState.STATE_STANDING )
            return 0;
        return state.getDx();
    }
    
    public int getDy(){
        if( hittingObstacle || state.getState() == ActorAnimState.STATE_STANDING )
            return 0;
        return state.getDy();
    }
    
    public void snapToNearestTile(){
        xPos = (int)(((double)xPos+Global.tileSize/2)/Global.tileSize)*Global.tileSize;
        yPos = (int)(((double)yPos+Global.tileSize/2)/Global.tileSize)*Global.tileSize;
    }
    
    public boolean onTile(){
        return ((int)xPos)%Global.tileSize == 0 && ((int)yPos)%Global.tileSize == 0;
    }
    
    public void update(List<MapObject> ents, long ms) {
        state.update( ms, onTile() );
        
        if( state.getState() == ActorAnimState.STATE_WALKING ){
            walkDist = Math.min( maxWalkDist, ms*walkSpeed );
            xPos += state.getDx()*walkDist;
            yPos += state.getDy()*walkDist;
        }
    }
    
    @Override
    public void draw(CGraphics g) {
        g.drawImage( allSprites[state.animIndex], (int)xPos, (int)yPos );
    }
    
    @Override
    public int getZIndex(){
        return (int)yPos;
    }
    
    public int getXTileIndex(){
        int result = (int)xPos/Global.tileSize + getDx();
        if( !hittingObstacle && state.getState() == ActorAnimState.STATE_WALKING && state.getDirection() == ActorAnimState.DIR_LEFT )
            return result+1;
        return result;
    }
    
    public int getYTileIndex(){
        int result = (int)yPos/Global.tileSize + getDy();
        if( !hittingObstacle && state.getState() == ActorAnimState.STATE_WALKING && state.getDirection() == ActorAnimState.DIR_DOWN )
            return result+1;
        return result;
    }
    
    abstract protected CImage[] buildAllSprites();
}
