/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.actors;

import nocrafting.map.MapObject;
import gfx.CImage;
import java.util.List;
import nocrafting.Global;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Oliver
 */
public class Player extends Person{
    //keybindings
    
    //Player should ONLY be constructed in global
    public Player(int xPos, int yPos){super(xPos, yPos);}
    
    public Player setTilePosition( int xTile, int yTile ){
        this.xPos = xTile*Global.tileSize;
        this.yPos = yTile*Global.tileSize;
        return this;
    }
    
    @Override
    public void update( List< MapObject > objs, long ms ){
        super.update( objs, ms );
        
        if( Keyboard.isKeyDown( Global.KEY_MOVE_LEFT ) ){
            state.setDirection( ActorAnimState.DIR_LEFT );
            state.setState( ActorAnimState.STATE_WALKING );
        
        }else if( Keyboard.isKeyDown( Global.KEY_MOVE_RIGHT ) ){
            state.setDirection( ActorAnimState.DIR_RIGHT );
            state.setState( ActorAnimState.STATE_WALKING );
        
        }else if( Keyboard.isKeyDown( Global.KEY_MOVE_UP ) ){
            state.setDirection( ActorAnimState.DIR_UP );
            state.setState( ActorAnimState.STATE_WALKING );
        
        }else if( Keyboard.isKeyDown( Global.KEY_MOVE_DOWN ) ){
            state.setDirection( ActorAnimState.DIR_DOWN );
            state.setState( ActorAnimState.STATE_WALKING );
        
        }else
            state.setState( ActorAnimState.STATE_STANDING );
        
        state.updateAnimIndex();
    }

    //<editor-fold desc="main() produces verboseString for sprites in getResourceImage( "player" )" defaultstate="collapsed">
    
    //dictates the amount to trim off the sides of the sprites
//    private static final int xMargin = 4;
//    private static final int yMargin = 8;
//    public static void main(String[] args) {
//        System.out.print( "processing player sprites..." );
//        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "player" );
//        int x,y,i;
//        CImage[] arr = new CImage[12];
//        i = 0;
//        for( y = 0 ; y < 4 ; y++ ){
//            for( x = 0 ; x < 3 ; x++ ){
//                arr[i++] = new CImage( sheet.getSubimage( 
//                        x*24 + xMargin, 
//                        y*32 + yMargin, 
//                        width, height ) );
//            }
//        }
//        System.out.println( " done!" );
//        
//        System.out.println( "private static final CImage[] spriteSet = new Cimage[]{" );
//        for( int j  = 0 ; j < 12 ; j++ )
//            System.out.println( "new CImage( \"" + arr[j].toVerboseString() + "\" )," );
//        System.out.println( "};" );
//        
//    }
    //</editor-fold>
    
    @Override
    protected CImage[] buildAllSprites() {
        return spriteSet;
    }
    
    private static final CImage[] spriteSet = new CImage[]{
new CImage( "16:24:0000000000000000000000000000000000000000007272000000000000000000000000004949B16969B10000000000000000000049B1B1F9F9B1B1490000000000000000A9B1F9F9F9F9B1A9D1D1000000000069A969F9F9F9F969A949D1000000000049A9B1A9A9A9A9B16949000000006949496969000000006949000000000069D149000069696969000069000000006900006969AD6969AD6969000000000000D100696464646464646900D100000000AD6964A9A90000A9A96469AD000000000064A9A9A9A9A9A9A9A964000000000000A9A9A9A9A9A9A9A9A9A9000000000064A9A9EDEDEDEDEDEDA9A9640000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000001100000000000001111110000000000111111110000000111111111110000011111111111100001111111111110001111111111110001111111111110000111111111111000011111111111110001111111111111100111111111111110001111111111110000111111111111000011111111111100001111111111110000011111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:000000000000000000000000000000000000000000000000000000000000000000000000494900000000494900000000000000000072B16969B17200000000000000004972B1B1B1B1B1B172490000000000690000B1F9F9F9F9B100006900000069D169A969F9F9F9F969A969D169000069F649A9B1A9A9A9A9B1A949F669000000004969B100000000B1690000000000000000000069696969000000000000000000006969AD6969AD6969000000000000D100696464646464646900D100000000AD6964A9A90000A9A96469AD000000000064A9A9A9A9A9A9A9A964000000000000A9A9A9A9A9A9A9A9A9A9000000000064A9A9EDEDEDEDEDEDA9A9640000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA9640000000000000064646464646464640000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000001100001100000001111111111000000111111111100000011111111110000011111111111100011111111111111001111111111111100011111111111100000111111111100000111111111111000111111111111110011111111111111000111111111111000011111111111100001111111111110000111111111111000001111111111000000111111111100000001111111100000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:0000000000000000000000000000000000000000000000000072720000000000000000000000B16969B14949000000000000000049B1B1F9F9B1B149000000000000D1D1A9B1F9F9F9F9B1A9000000000000D149A969F9F9F9F969A9690000000000004969B1A9A9A9A9B1A9490000000000000049690000000069694949690000000069000069696969000049D16900000000006969AD6969AD6969000069000000D100696464646464646900D100000000AD6964A9A90000A9A96469AD000000000064A9A9A9A9A9A9A9A964000000000000A9A9A9A9A9A9A9A9A9A9000000000064A9A9EDEDEDEDEDEDA9A9640000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000110000000000011111100000000111111110000001111111111100001111111111110000111111111111000001111111111110000011111111111100001111111111110001111111111111001111111111111100111111111111110001111111111110000111111111111000011111111111100001111111111110000011111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:00000000000000000000000000000000000000000000000000000000000000000000000049494900000000000000000000000000B14972720000690000000000000000B16964A9004949D1D10000000000000069F9B1A9494949F6F600000000000000F9F9B16964496969000000000000000000B16969D1D1D1D169000000000000000000D1F6D1D1F669F6690000000000000069D1F669D1D100D1D169000000000069696964646464646449690000000000696464A9A9A9A9A9B6B6B6000000000064A9A9A9A9A9A9A9A9A9B6DB00000064A9A9A9A9A9A9A9A9A9B6A9DB00000064A9A9EDEDEDEDEDEDA9DB64DB00000064A9EDEDEDEDEDEDEDEDDB64640000000064EDEDEDEDEDEDEDEDA964000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000001110000000000001111111100000000111111111000000111111111110000011111111111000001111111111000000011111111100000000111111111000000111111111110000111111111111000011111111111110001111111111111100111111111111110011111111111111001111111111111000011111111111000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:00000000000000000000000000000000000000000000000000000000000000000000000000000049A9490000000000000000000000007269697200000000000000000000B10000D1D100000000000000000000B1696400494964A9000000000000000069F9B100494900A96400000000000000F9F9B16400006969640000000000000000B16969D1D1D1D169000000000000000000D1F6D1D1F669F6690000000000000069D1F6ADD1D100D1D169000000000069696964646464646449690000000000696464A9A9A9A9A9B6B6B6000000000064A9A9A9A9A9A9A9A9A9B6DB00000064A9A9A9A9A9A9A9A9A9B6A9DB00000064A9A9EDEDEDEDEDEDA9DB64DB00000064A9EDEDEDEDEDEDEDEDDB64640000000064EDEDEDEDEDEDEDEDA964000000000064A9EDEDEDEDEDEDA9640000000000000064646464646464640000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000000001110000000000001111100000000011111110000000011111111000000011111111110000001111111111000000111111111100000001111111110000000011111111100000011111111111000011111111111100001111111111111000111111111111110011111111111111001111111111111100111111111111100001111111111100000111111111100000001111111100000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:000000000000000000000000000000000000000000000000000000000000000000000000000000000049A9000000000000000000696900007200004900000000000000B169D1D14900A90000000000000000006969F6F64949A9A96400000000000000F9F9B14949006969640000000000000000B16969D1D1D1D169000000000000000000D1F6D1D1F669F6690000000000000069D1F669D1D100D1D169000000000069696964646464646449690000000000696464A9A9A9A9A9B6B6B6000000000064A9A9A9A9A9A9A9A9A9B6DB00000064A9A9A9A9A9A9A9A9A9B6A9DB00000064A9A9EDEDEDEDEDEDA9DB64DB00000064A9EDEDEDEDEDEDEDEDDB64640000000064EDEDEDEDEDEDEDEDA964000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000000000011100000000111111111000000111111111100000111111111100000011111111110000001111111111000000011111111100000000111111111000000111111111110000111111111111000011111111111110001111111111111100111111111111110011111111111111001111111111111000011111111111000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:00000000000000000000000000000000000000000000000000494900000000000000000000000000497272000000000000000069690000000000497200000000000069D1D169EDDBDBEDA90000000000000069F6F669A9B6B6A9A9A9690000000000004949A90000000064A949000000000000000069D1D1D1D169000049D16900000069D1D169F6F669D1D16949D169000000D1D1F600F6F600F6D1D100690000000069D1494949494949D1690000000000D10049DBDBDBDBDBDB4900D100000000AD64B6A9B6A9A9B6A9B664AD0000000000A9A9A9A9DBDBA9A9A9A9000000000000A9A9EDEDEDEDEDEDA9A9000000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000110000000000000111100000000001111110000000111111111100000111111111110000011111111111000001111111111111000011111111111110001111111111111001111111111111000111111111111000111111111111110011111111111111000111111111111000011111111111100001111111111110000011111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:00000000000000000000000000000000000000000000000000000000000000000000000049490000000049490000000000000000007272000072720000000000000000497249000000004972490000000000690000A9EDDBDBEDA900006900000069D1D149A9A9B6B6A9A949D1D169000069F64949A900000000A94949F66900000000490069D1D1D1D169004900000000000069D1D169F6F669D1D169000000000000D1D1F600F6F600F6D1D100000000000069D1494949494949D1690000000000D10049DBDBDBDBDBDB4900D100000000AD64B6A9B6A9A9B6A9B664AD0000000000A9A9A9A9DBDBA9A9A9A9000000000000A9A9EDEDEDEDEDEDA9A9000000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA9640000000000000064646464646464640000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000001100001100000001111001111000000111111111100000011111111110000011111111111100011111111111111001111111111111100011111111111100000111111111100000111111111111000011111111111100011111111111111001111111111111100011111111111100001111111111110000111111111111000001111111111000000111111111100000001111111100000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:000000000000000000000000000000000000000000494900000000000000000000000000007272490000000000000000000000007249000000000069690000000000000000A9EDDBDBED69D1D169000000000069A9A9A9B6B6A969F6F669000000000049A96400000000A9494900000069D149000069D1D1D1D169000000000069D14969D1D169F6F669D1D169000000006900D1D1F600F6F600F6D1D100000000000069D1494949494949D1690000000000D10049DBDBDBDBDBDB4900D100000000AD64B6A9B6A9A9B6A9B664AD0000000000A9A9A9A9DBDBA9A9A9A9000000000000A9A9EDEDEDEDEDEDA9A9000000000064A9EDEDEDEDEDEDEDEDA964000000000064EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000001100000000000001111000000000000111111000000000111111111100000011111111111000001111111111100011111111111110011111111111110001111111111111000011111111111110000111111111111000111111111111110011111111111111000111111111111000011111111111100001111111111110000011111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:00000000000000000000000000000000000000000000000000000000000000000000000000A94900000000000000000000000000490000720000696900000000000000000000A90049D1D169B10000000000000064A9A94949F6F6696900000000000000646969004949B1F9F90000000000000069D1D1D1D16969B10000000000000069F669F6D1D1F6D10000000000000069D1D100D1D169F6D16900000000000069496464646464646969690000000000B6B6B6A9A9A9A9A964646900000000DBB6A9A9A9A9A9A9A9A9A96400000000DBA9B6A9A9A9A9A9A9A9A9A964000000DB64DBA9EDEDEDEDEDEDA9A9640000006464DBEDEDEDEDEDEDEDEDA9640000000064A9EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000001110000000000001111111110000000111111111100000001111111111000000111111111100000011111111110000001111111110000001111111110000001111111111100000111111111111000111111111111100111111111111110011111111111111001111111111111100011111111111110000111111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:000000000000000000000000000000000000000000000000000000000000000000000000000049A949000000000000000000000000007269697200000000000000000000000000D1D10000B1000000000000000000A9644949006469B10000000000000064A900494900B1F96900000000000000646969000064B1F9F90000000000000069D1D1D1D16969B10000000000000069F669F6D1D1F6D10000000000000069D1D100D1D1ADF6D16900000000000069496464646464646969690000000000B6B6B6A9A9A9A9A964646900000000DBB6A9A9A9A9A9A9A9A9A96400000000DBA9B6A9A9A9A9A9A9A9A9A964000000DB64DBA9EDEDEDEDEDEDA9A9640000006464DBEDEDEDEDEDEDEDEDA9640000000064A9EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA9640000000000000064646464646464640000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000000011100000000000011111000000000001111111000000000111111110000000111111111100000011111111110000001111111111000000111111111000000111111111000000111111111110000011111111111100011111111111110011111111111111001111111111111100111111111111110001111111111111000011111111111000000111111111100000001111111100000000000000000000000000000000000000000000000000000000000000000000" ),
new CImage( "16:24:0000000000000000000000000000000000000000000000000000000000000000000000000000000000494949000000000000000000690000727249B10000000000000000D1D1494900A96469B100000000000000F6F6494949A9B1F96900000000000000006969496469B1F9F90000000000000069D1D1D1D16969B10000000000000069F669F6D1D1F6D10000000000000069D1D100D1D169F6D16900000000000069496464646464646969690000000000B6B6B6A9A9A9A9A964646900000000DBB6A9A9A9A9A9A9A9A9A96400000000DBA9B6A9A9A9A9A9A9A9A9A964000000DB64DBA9EDEDEDEDEDEDA9A9640000006464DBEDEDEDEDEDEDEDEDA9640000000064A9EDEDEDEDEDEDEDED6400000000000064A9EDEDEDEDEDEDA964000000000000006464646464646464000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000:000000000000000000000000011100000000011111111000000011111111100000011111111111000001111111111100000011111111110000001111111110000001111111110000001111111111100000111111111111000111111111111100111111111111110011111111111111001111111111111100011111111111110000111111111110000001111111111000000011111111000000000000000000000000000000000000000000000000000000000000000000000000000000000000" ),
};
}
