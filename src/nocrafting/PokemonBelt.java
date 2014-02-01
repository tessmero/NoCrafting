/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting;

import gfx.CGraphics;
import gfx.ColorUtil;
import nocrafting.actors.Diglett;
import nocrafting.actors.Pokemon;

/**
 *
 * @author Oliver
 */
public class PokemonBelt {
    private static final int iconBorderSize = 20;
    private static final byte iconBorderColor = ColorUtil.white;
    private static final byte iconBackgroundColor = ColorUtil.black;
    
    private final int nSlots;
    
    private final Pokemon[] team;
    
    public PokemonBelt( int nSlots ){
        this.nSlots = nSlots;
        team = new Pokemon[nSlots];
        team[0] = new Diglett(0,0);
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
        for( Pokemon p : team ){
            g.drawRect( drawX, drawY, iconBorderSize, iconBorderSize );
            if( p != null )
                g.drawImageCentered( p.getIcon(), 
                    drawX + iconBorderSize/2, 
                    drawY + iconBorderSize/2 );
            drawX += iconBorderSize;
        }
    }
}
