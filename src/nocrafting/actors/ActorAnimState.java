/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

/**
 *
 * @author Simon
 */
public class ActorAnimState {
    private static final int walkingDelay = 200;
    
    public static final int 
            STATE_WALKING   = 0, 
            STATE_STANDING  = 1;
    
    public static final int
            DIR_UP      = 0,
            DIR_RIGHT    = 1,
            DIR_DOWN    = 2,
            DIR_LEFT   = 3;
    
    //one of the state constants above
    private int state;
    
    //one of the direction constants above
    private int direction;
    
    //0-3 for four different walking frames (1,3 are the same image)
    public int walkingStage;
    private int walkingTimer = walkingDelay;
    
    //the index of the  image corresponding to the animation frame
    public int animIndex = 0;
    
    //true if the actor wants to stop walking, but must wait until he lines up with a tile
    private boolean wantsToStopWalking = false;
    
    //non-negative if the actor wants to change direction, but must wait until he lines up with a tile
    private int wantsToChangeDirection = -1;
    
    public ActorAnimState(){
        state = STATE_STANDING;
        direction = DIR_DOWN;
        walkingStage = 1;
        updateAnimIndex();
    }
    
    public void update( long ms, boolean isOnTile ){
        if( state == STATE_WALKING ){
            walkingTimer -= ms;
            if( walkingTimer <= 0 ){
                walkingTimer = walkingDelay;
                walkingStage = (walkingStage+1)%4;
                updateAnimIndex();
            }
            if( isOnTile ){
                if( wantsToStopWalking ){
                    state = STATE_STANDING;
                    wantsToStopWalking = false;
                    updateAnimIndex();
                }
                if( wantsToChangeDirection > -1 ){
                    direction = wantsToChangeDirection;
                    wantsToChangeDirection = -1;
                    updateAnimIndex();
                }
            }
        }else
            walkingTimer = walkingDelay;
    }
    
    public void randomize(){
        setDirection((int)(Math.random()*4));
        setState((int)(Math.random()*2));
        updateAnimIndex();
    }
    
    //anim frames 0-2 : up
    //anim frames 3-5 : right
    //anim frames 6-8 : down
    //anim frames 9-11 : left
    public void updateAnimIndex(){
        animIndex = getDirection()*3+1;
        if( getState() == ActorAnimState.STATE_WALKING && walkingStage != 3 )
            animIndex += walkingStage - 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int desiredState) {
        if( state == STATE_WALKING && desiredState == STATE_STANDING )
            wantsToStopWalking = true;
        else
            state = desiredState;
    }

    public int getDirection() {
        return direction;
    }
    
    public int getDx(){
        if( direction == DIR_RIGHT )
            return 1;
        if( direction == DIR_LEFT )
            return -1;
        return 0;
    }
    
    public int getDy(){
        if( direction == DIR_UP )
            return 1;
        if( direction == DIR_DOWN )
            return -1;
        return 0;
    }

    public void setDirection(int desiredDir ) {
        if( state == STATE_WALKING && desiredDir != direction )
            wantsToChangeDirection = desiredDir;
        else
            direction = desiredDir;
    }
}
