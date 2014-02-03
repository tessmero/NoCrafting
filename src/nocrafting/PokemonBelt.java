/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting;

import gfx.CGraphics;
import gfx.ColorUtil;
import java.util.ArrayList;
import java.util.List;
import nocrafting.actors.Diglett;
import nocrafting.actors.Pokemon;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public class PokemonBelt {
    
    //input vars
    private static final int inputDelay = 500;
    private int inputTimer = inputDelay;
    
    //drawing settings
    private static final int iconBorderSize = 20;
    private static final byte iconBorderColor = ColorUtil.white;
    private static final byte iconBackgroundColor = ColorUtil.black;
    
    private final int nSlots;
    
    private int selectedIndex = 0;
    
    private final Pokemon[] team;
    
    public PokemonBelt( int nSlots ){
        this.nSlots = nSlots;
        team = new Pokemon[nSlots];
        team[0] = new Diglett(0,0);
    }
    
    public Pokemon getSelectedPokemon(){
        return team[selectedIndex];
    }
    
    public List< Pokemon > listUnsummonedPokemon(){
        List< Pokemon > result = new ArrayList();
        for( Pokemon p : team )
            if( p!=null && !p.summoned )
                result.add( p );
        return result;
    }
    
    public void update( long ms ){
        boolean nextDown = Keyboard.isKeyDown( Global.KEY_NEXT_POKEMON );
        boolean prevDown = Keyboard.isKeyDown( Global.KEY_PREV_POKEMON );
        if( !nextDown && !prevDown ){
            inputTimer = 0;
            return;
        }
        
        if( inputTimer > 0 )
            inputTimer -= ms;
        else{
            if( nextDown )
                selectedIndex++;
            else if( prevDown )
                selectedIndex--;
            inputTimer = inputDelay;
            selectedIndex = (selectedIndex+nSlots)%nSlots;
        }
        
    }
    
    public void drawHudView( CGraphics g ){
        int drawWidth = iconBorderSize*nSlots;
        int drawX = Global.screenWidth/2 - drawWidth/2;
        int drawY = Global.screenHeight - iconBorderSize - 2;
        g.setColor( iconBorderColor );
        g.fillRect( drawX-1,drawY-1,drawWidth+2,iconBorderSize+2 );
        g.setColor( iconBackgroundColor );
        g.fillRect( drawX, drawY, drawWidth, iconBorderSize );
        g.setColor( iconBorderColor );
        for( int i = 0 ; i < nSlots ; i++ ){
            if( i != selectedIndex )
                g.drawRect( drawX, drawY, iconBorderSize, iconBorderSize );
            if( team[i] != null ){
                g.drawImageCentered( team[i].getIcon(), 
                    drawX + iconBorderSize/2, 
                    drawY + iconBorderSize/2 );
                g.translate( drawX, 116 );
                team[i].inventory.draw( g );
                g.translate( -drawX, -116 );
            }
            drawX += iconBorderSize;
        }
    }
}
