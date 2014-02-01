/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nocrafting.resources.images;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Oliver
 */
public class RawImageHandler {
    private static final RawImageHandler instance = new RawImageHandler();
    public static RawImageHandler getInstance(){ return instance; }
    
    private Map< String, BufferedImage > resourceImages = new HashMap();
    
    private RawImageHandler(){
        System.out.print( "loading raw images from resources... " );
        for( String s : new String[]{
            /*"diglett_icon",*/"doors", "font", /*"grass",/* "objects_raw", /*"people",*/ "pokecenter",
            "overworld_tiles", /*"tree",*/ "harvested_tree", "interiors", "exit_tile",
            "water_animation", "shoreline_animation", "grass_sand_edge", 
            "oldale_grass_edge", /*"upladder",*/ "secret_base_tiles", "cave_tiles"/*"player"*/
        })
        resourceImages.put( s, loadResourceImage( s + ".png" ) );
        System.out.println( "done!" );
    }
    
    private BufferedImage loadResourceImage( String filename ){
        try{
            return ImageIO.read( RawImageHandler.class.getResourceAsStream(filename) );
        }catch( Exception e ){
            System.err.println( "failed to load resoruce image \"" + filename + "\"" );
            throw new Error( e );
        }
    }
    
    public BufferedImage getResourceImage( String filename ){
        if( !resourceImages.containsKey( filename ) )
            throw new Error( "no resource image \"" + filename + "\" exists." );
        return resourceImages.get( filename );
    }
}
