/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.items;

import gfx.CGraphics;
import gfx.ColorUtil;
import nocrafting.Global;
import nocrafting.actors.Pokemon;

/**
 *
 * @author Oliver
 */
public class ActorInventory {
    
    //drawing settings
    private static final int iconBorderSize = 16;
    private static final byte iconBorderColor = ColorUtil.white;
    private static final byte iconBackgroundColor = ColorUtil.black;
    
    private Item[] itemArr;
    
    private int[] quantityArr;
    
    private int size;
    
    public ActorInventory( int size ){
        this.size = size;
        itemArr = new Item[size];
        quantityArr = new int[size];
    }
    
    public boolean addItem( Item thing ){
        if( thing == null )
            return false;
        Class c = thing.getClass();
        for( int i = 0 ; i < size ; i++ ){
            if( itemArr[i]!=null && itemArr[i].getClass() == c ){
                quantityArr[i]++;
                return true;
            }
        }
        for( int i = 0 ; i < size ; i++ ){
            if( itemArr[i]==null ){
                itemArr[i] = thing;
                quantityArr[i] = 1;
                return true;
            }
        }
        return false;
    }
    
    public void draw( CGraphics g ){
        int drawHeight = iconBorderSize * size;
        int drawY = 0;
        g.setColor( iconBorderColor );
        g.fillRect( -1,-1, iconBorderSize+2, drawHeight+2 );
        g.setColor( iconBackgroundColor );
        g.fillRect( 0, 0, iconBorderSize, drawHeight );
        g.setColor( iconBorderColor );
        for( int i = 0 ; i < size ; i++ ){
            g.drawRect( 0, drawY, iconBorderSize, iconBorderSize );
            if( itemArr[i] != null ){
                g.drawImageCentered( itemArr[i].getIcon(), 
                    iconBorderSize/2, 
                    drawY + iconBorderSize/2 );
                g.drawString( String.valueOf( quantityArr[i] ), 0, drawY );
            }
            drawY += iconBorderSize;
        }
    }
}
