/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map.flavor;

import gfx.CImage;
import nocrafting.actors.Actor;
import nocrafting.map.MapObject;

/**
 *
 * @author Oliver
 */
public abstract class StaticDecayingObject extends MapObject {
    
    public long lifetime;
    
    public StaticDecayingObject( long lifetime, int xPos, int yPos) {
        super(xPos, yPos);
        
        this.lifetime = lifetime;
    }
}
