/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.util.Date;
import nocrafting.Global;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author Oliver
 */
public class CGraphics {
    public static final int MODE_ADD = 0;
    public static final int MODE_REPLACE = 1;
    
    //
    private static final int TARGETFPS = 60;
    private int REALFPS = 0;
    
    //color data, one byte per game-pixel
    private final byte[] colorBuffer;
    private final int colorW,colorH,colorN;
    
    //current drawing context
    private int drawingMode = MODE_REPLACE; //on eof the MODE constants above
    private byte drawColor = (byte)0;
    private int drawOffsetX = 0;
    private int drawOffsetY = 0;
    
    public CGraphics( int w, int h ){        
        colorW = w;
        colorH = h;
        colorN = w*h;
        colorBuffer = new byte[colorN];
    }
    
    public void setColor( byte b ){
        drawColor = b;
    }
    
    public void setDrawingMode( int mode ){
        drawingMode = mode;
    }
    
    public void drawRect( int x, int y, int w, int h ){
        for( int i = 0 ; i < w ; i++ ){
            pushColorBuffer( x+i, y, drawColor );
            pushColorBuffer( x+i, y+h-1, drawColor );
        }
        
        for( int i = 1 ; i < h-1 ; i++ ){
            pushColorBuffer( x, y+i, drawColor );
            pushColorBuffer( x+w-1, y+i, drawColor );
        }
    }
    
    public void drawStringCentered( String s, int ox, int oy ){
        int w = s.length() * Font.charWidth;
        drawString( s, ox-w/2, oy-Font.charHeight/2 );
    }
    
    public void drawString( String s, int ox, int oy ){
        for( char c : s.toCharArray() ){
            try{
                drawImage( Font.charImages[c-Font.minValue], ox, oy );
            }catch( Exception e ){}
            ox += Font.charWidth;
        }
    }
    
    public void fillRect( int x, int y, int w, int h ){
        for( int i = 0 ; i < w ; i++ )
            for( int j = 0 ; j < h ; j++ )
                pushColorBuffer( x+i, y+j, drawColor );
    }
    
    public void fillColor( byte b ){
        for( int i = 0 ; i < colorN ; i++ )
            colorBuffer[i] = b;
    }
    
    public void translate( int x, int y ){
        drawOffsetX += x;
        drawOffsetY += y;
    }    
    
    public void drawImage( CImage ci, int ox, int oy ){
        int i = 0;
        for( int y = 0 ; y < ci.h ; y++ ){
            for( int x = 0 ; x < ci.w ; x++ ){
                if( ci.opacity[i] )
                    pushColorBuffer( x+ox, y+oy, ci.pixels[i] );
                i++;
            }
        }
    }
    
    public void drawImageCentered( CImage ci, int ox, int oy ){
        drawImage( ci, ox-ci.w/2, oy-ci.h/2 );
    }
    
    public void drawImage( CImage ci ){
        int i = 0;
        for( int y = 0 ; y < ci.h ; y++ ){
            for( int x = 0 ; x < ci.w ; x++ ){
                if( ci.opacity[i] )
                    pushColorBuffer( x, y, ci.pixels[i] );
                i++;
            }
        }
    }
    
    //push one pixel to the buffer, 
    //coordiantes here are relative to translation offsets
    //does nothing if coordinates are outside of viewport
    public void pushColorBuffer( int x, int y, byte color ){
        x += drawOffsetX;
        y += drawOffsetY;
        if( x < 0 || x >= colorW || y < 0 || y >= colorH )
            return;
        int i2 = y*colorW+x;
        
        if( i2 >= 0 && i2 < colorN ){
            if( drawingMode == MODE_ADD )
                colorBuffer[i2] += color;
            else if( drawingMode == MODE_REPLACE )
                colorBuffer[i2] = color;
            else
                throw new Error( "invalid drawing mode" );
        }
    }
    
    private long lastTime = 0;
    private int frameCount = 0;
    public void updateDisplay(){
        drawString( "FPS: " + REALFPS, 0, 0 );
        
        glPushMatrix();
        glBegin(GL_QUADS);
        int i = 0;
        for( int y = 0 ; y < colorH ; y++ ){
            for( int x = 0 ; x < colorW ; x++ ){
                byte color = colorBuffer[i++];
                GL11.glColor3d( 
                    ( ( color & 0xE0 ) >>> 5 ) / 7.0, 
                    ( ( color & 0x1C ) >>> 2 ) / 7.0,
                      ( color & 0x03 )         / 3.0 );
                glVertex2i(x,y);
                glVertex2i(x+1,y);
                glVertex2i(x+1,y+1);
                glVertex2i(x,y+1);
                //glVertex2i(x,y);
            }
        }
        glEnd();
        glPopMatrix();
        
        Display.update();
        if( TARGETFPS != -1 )
            Display.sync(TARGETFPS);
        
        if( new Date().getTime() > lastTime+1000 ){
            lastTime = new Date().getTime();
            REALFPS = frameCount;
            frameCount = 0;
        }
        frameCount++;
    }
    
    public void initDisplay() throws LWJGLException{
        int realWidth = colorW*Global.pixelScaling;
        int realHeight = colorH*Global.pixelScaling;
        boolean set = false;
        for( DisplayMode dm : Display.getAvailableDisplayModes() ){
            if( dm.isFullscreenCapable() == Global.fullscreen && dm.getWidth() == realWidth && dm.getHeight() == realHeight ){
                Display.setDisplayModeAndFullscreen(dm);
                set = true;
                break;
            }
        }
        if( !set )
            Display.setDisplayMode( new DisplayMode(realWidth,realHeight) );
        //Display.setDisplayMode(Display.getAvailableDisplayModes()[0]);
        Display.create( new PixelFormat( 8, 0, 0, 0, 0) );
        Display.setFullscreen(Global.fullscreen);
        glLoadIdentity();
        //glLineWidth( 3f );
        glMatrixMode( GL_PROJECTION );
        glOrtho(0,colorW,0,colorH,1,-1);
        //glMatrixMode(GL_MODELVIEW);
        //glEnable( GL_TEXTURE_2D );
        //glClearColor( 0,0,0,0 );
        //Enable blending so the green background can be seen through the texture
        //glEnable( GL_TEXTURE_2D );
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        Graphics2D testGfx = new BufferedImage( 10, 10, BufferedImage.TYPE_INT_RGB ).createGraphics();
//        drawFont = testGfx.getFont();
//        frc = testGfx.getFontRenderContext();
   
    }
    
    public boolean isCloseRequested(){
        return Display.isCloseRequested();
    }
    
    public void destroyDisplay(){
        Display.destroy();
    }
}
