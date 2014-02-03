/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CGraphics;
import gfx.CImage;
import gfx.Drawable;
import java.util.List;
import nocrafting.Global;

/**
 *
 * @author Simon
 */
public abstract class MapObject implements Drawable{
    
    public int xPos, yPos;
    
    protected final CImage sprite;
    
    public MapObject( int xPos, int yPos ){
        this.xPos = xPos;
        this.yPos = yPos;
        sprite = buildSprite();
    }
    
    public void snapToTile(){
        xPos = (xPos/Global.tileSize)*Global.tileSize;
        yPos = (yPos/Global.tileSize)*Global.tileSize;
    }
    
    @Override
    public void draw( CGraphics g ){
        g.drawImage( sprite, xPos, yPos );
    }
    
    @Override
    public int getZIndex(){
        return yPos;
    }
    
    abstract protected CImage buildSprite();
}
