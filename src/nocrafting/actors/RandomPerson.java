/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import nocrafting.map.MapObject;
import java.util.List;

/**
 *
 * @author Oliver
 */
public class RandomPerson extends Person{

    public RandomPerson(int xPos, int yPos) {
        super(xPos, yPos);
    }
    
    private long timer = 1000;
    @Override
    public void update( List<MapObject> l, long ms ){
        super.update( l, ms );
        timer -= ms;
        if( timer <= 0 ){
            state.randomize();
            timer = 1000;
        }
    }
}
