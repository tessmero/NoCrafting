/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import gfx.CImage;
import nocrafting.Global;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public abstract class Pokemon extends RandomPerson{

    //while false, this pokemon will move randomly
    public boolean summoned = false;
    
    public Pokemon(int xPos, int yPos) {
        super(xPos, yPos);
    }
    abstract public CImage getIcon();
    
    abstract public void startSummonBehavior( int dir );
}
