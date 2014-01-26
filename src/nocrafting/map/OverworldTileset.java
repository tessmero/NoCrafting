/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nocrafting.map;

import gfx.CImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nocrafting.Global;
import nocrafting.resources.images.RawImageHandler;

/**
 *
 * @author Oliver
 */
public class OverworldTileset extends Tileset{
    private static final OverworldTileset instance = new OverworldTileset();
    public static OverworldTileset getInstance(){ return instance; }
    
    public static final int INDEX_GRASS = 0;
    public static final int INDEX_OCEAN = 1;
    public static final int INDEX_SAND  = 2;
    
    //stores the starting index of minitiles corresponding to the edge defined
    //by an int[]{ centerindex, adjacentindex }
    private final Map< int[], Integer > definedEdges = new HashMap();
    
    private static final Object[][] edgeMap = {
        { "shoreline_animation",    INDEX_SAND,                 INDEX_OCEAN             },
        { "grass_sand_edge",        INDEX_GRASS,                INDEX_SAND              }
    };
    
    private OverworldTileset(){
        int i = 0;Object[][] edgeMap = {
            { "shoreline_animation",    INDEX_SAND,                 INDEX_OCEAN             },
            { "grass_sand_edge",        INDEX_GRASS,                INDEX_SAND              }
        };
        for( Object[] row : edgeMap ){
            definedEdges.put( new int[]{ (Integer)row[1], (Integer)row[2] }, i );
            i += 12;
        }
    }
    
    @Override
    protected CImage[] buildTiles() {
        System.out.print( "processing overworld map tile images..." );
    
        CImage[] result = autoBuildTiles( "overworld_tiles", Global.tileSize );
//        CheapImage[] result = new CheapImage[8];
//        int ts =  Global.tileSize;
//        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "tiles_ground" );
//        int x, i = 0;
//        for( x = 0 ; x < 8 ; x++ )
//            result[i++] = new CheapImage(
//                    debugMode ? getFramedTile( sheet.getSubimage( x*ts, 0, ts, ts ) )
//                    : sheet.getSubimage( x*ts, 0, ts, ts ) );
        
        System.out.println( " done!" );
        return result;
    }

    @Override
    protected Map<Integer, CImage[]> buildTileAnimations() {
        Map< Integer, CImage[] > result = new HashMap();
        
        int ts =  Global.tileSize;
        CImage[] waterAnim = new CImage[8];
        BufferedImage waterAnimSheet = RawImageHandler.getInstance().getResourceImage( "water_animation" );
        for( int i = 0 ; i < 8 ; i++ )
            waterAnim[i] = new CImage( waterAnimSheet.getSubimage( (ts+1)*i+1, 1, ts, ts ) );
        result.put( INDEX_OCEAN, waterAnim );
        return result;
    }
    
    private static final Map< String, CImage[] > builtSets = new HashMap();
    @Override
    protected CImage[] buildMinitiles(){
        int n = edgeMap.length*12;
        CImage[] result = new CImage[n];
        CImage[] singleset;
        int i = 0;
        String filename;
        for( int j = 0 ; j < edgeMap.length ; j++ ){
            filename = (String)edgeMap[j][0];
            if( builtSets.containsKey( filename ) ){
                singleset = builtSets.get( filename );
            }else{
                singleset = buildMinitileSet( filename );
                builtSets.put( filename, singleset );
            }
            for( CImage ci : singleset )
                result[i++] = ci;
        }
        return result;
    }
    
    private CImage[] buildMinitileSet( String filename ){
        int ts = Global.tileSize/2;
        CImage[] result = new CImage[12];
        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( filename );        
        CImage temp;
        int i;
        for( int j = 0 ; j < 3 ; j++ ){
            temp = new CImage( sheet.getSubimage( 1, j*(ts+1)+1, ts, ts ) );
            for( i = 0 ; i < 4 ; i++ ){
                result[j*4+i] = temp;
                if( i < 3 )
                    temp = temp.getRotated90();
            }
        }
        return result;
    }

    @Override
    protected Map<Integer, CImage[]> buildMinitileAnimations() {
        int timeScaling = 3;
        int i,j,k,q;
        Map< Integer, CImage[] > result = new HashMap();
        for( i = 0 ; i < 12 ; i++ )
            result.put( i, new CImage[7*timeScaling] );
        int ts =  Global.tileSize/2;
        BufferedImage sheet = RawImageHandler.getInstance().getResourceImage( "shoreline_animation" );
        CImage temp;
        for( j = 0 ; j < 3 ; j++ ){
            for( k = 0 ; k < 7 ; k++ ){
                temp = new CImage( sheet.getSubimage( k*(ts+1)+1, j*(ts+1)+1, ts, ts ) );
                for( i = 0 ; i < 4 ; i++ ){
                    for( q = 0 ; q < timeScaling ; q++ )
                        result.get( j*4+i )[k*timeScaling+q] = temp;
                    if( i < 3 )
                        temp = temp.getRotated90();
                }
            }
        }
        return result;
    }
    
    @Override
    public void assignEdgeMinitiles( int[] in, int[] out ){
        
        //determine the center tile index, along with the 
        //the set of adjecent tile indices
        int c = in[4];
        List< Integer > aList = new ArrayList();
        for( int i = 0 ; i < 9 ; i++ )
            if( in[i] != c && !aList.contains( in[i] ) )
                aList.add( in[i] );
        
        //start by clearing the output
        for( int i = 0 ; i < 4 ; i++ )
            out[i] = -1;
        
        //add minitiles for every type (defined by c and a )
        for( int a : aList )
            appendEdgeMinitiles( in, out, c, a );
    }
    
    private void appendEdgeMinitiles( int[] in, int[] out, int c, int a ){
        //determine the starting index for edge tiles based on a,c
        //if there is no defined edgetile set between a and c return
        int startIndex = -1;
        for( int[] key : definedEdges.keySet() ){
            if( key[0] == c && key[1] == a ){
                startIndex = definedEdges.get( key );
                break;
            }
        }
        if( startIndex == -1 )
            return;
        
        //this isn't optomized by any means, but it's still fast
        //and only runs once upon loading
        //<editor-fold desc="nitty gritty" defaultstate="collapsed">
        
        //assign out[0] (top-left edge)
        if( in[6] == c && in[7] == c && in[3] == c )
            out[0] = -1;
        else if( /*in[6] == a &&*/ in[7] == a && in[3] == a )
            out[0] = 10 + startIndex;
        else if( in[6] == a && in[7] == c && in[3] == c )
            out[0] = 2 + startIndex;
        else if( in[7] == a && in[3] == c )
            out[0] = 7 + startIndex;
        else if( in[7] == c && in[3] == a )
            out[0] = 6 + startIndex;
        
        //assign out[1] (top-right edge)
        if( in[8] == c && in[7] == c && in[5] == c )
            out[1] = -1;
        else if( /*in[8] == a &&*/ in[7] == a && in[5] == a )
            out[1] = 11 + startIndex;
        else if( in[8] == a && in[7] == c && in[5] == c )
            out[1] = 3 + startIndex;
        else if( in[7] == a && in[5] == c )
            out[1] = 7 + startIndex;
        else if( in[7] == c && in[5] == a )
            out[1] = 4 + startIndex;
        
        //assign out[2] (bottom-left edge)
        if( in[0] == c && in[1] == c && in[3] == c )
            out[2] = -1;
        else if( /*in[0] == a &&*/ in[1] == a && in[3] == a )
            out[2] = 9 + startIndex;
        else if( in[0] == a && in[1] == c && in[3] == c )
            out[2] = 1 + startIndex;
        else if( in[1] == a && in[3] == c )
            out[2] = 5 + startIndex;
        else if( in[1] == c && in[3] == a )
            out[2] = 6 + startIndex;
        
        //assign out[3] (bottom-right edge)
        if( in[2] == c && in[1] == c && in[5] == c )
            out[3] = -1;
        else if( /*in[2] == a &&*/ in[1] == a && in[5] == a )
            out[3] = 8 + startIndex;
        else if( in[2] == a && in[1] == c && in[5] == c )
            out[3] = 0 + startIndex;
        else if( in[1] == a && in[5] == c )
            out[3] = 5 + startIndex;
        else if( in[1] == c && in[5] == a )
            out[3] = 4 + startIndex;
        //</editor-fold>
    }
}
