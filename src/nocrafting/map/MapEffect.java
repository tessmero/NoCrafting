/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import nocrafting.actors.Actor;

/**
 *
 * @author Oliver
 */
public abstract class MapEffect {
    
    //stores a pointer to the actor that did the effect
    public final Actor sender;
    
    private MapEffect( Actor sender ){
        this.sender = sender;
    }
    
    public static class Dig extends MapEffect{
        public Dig(Actor sender) {
            super(sender);
        }
    }
}
