/**********************************************************
 * FILE:
 *  SKFWindow.java
 * DATE:
 *  2021-10-16
 *  2021-10-17
 * DEV:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Abstracted window class for easy rendering
 * CONTENTS:
 *  - Package def
 *  - Imports
 *  - Class defs
 *  - Private members
 *  - Public members
 *  - Ctors
 *  - Private methods
 *  - Public methods
 *********************************************************/

package com.shocker.SKF; /* package name */

 import java.awt.*;    /* rendering library      */
 import javax.swing.*; /* window handler library */

import com.shocker.SKF.SKFRenderSet.SKFArgset; /* argset class */

 public final class SKFWindow
 {
    /* number of framebuffers to swap between */
    private static final int NUMBUFFERS  = 2;

    /* internal window dimensions */
    private int wDimX;
    private int wDimY;

    /* window object and rendering object */
    private JFrame   jWindow;
    private Graphics renderObject;

    /* clear color */
    private Color clearColor;

    /* virtural camera position */
    public SKFVector cameraPosition;

    /******************************************
     * METHOD: SKFWindow
     * PARAMS:
     *  int winX -> window X size
     *  int winY -> window Y size
     * RETURNS:
     *  N/A
     *****************************************/
    public SKFWindow(int winX, int winY)
    {  
        /* init internal resolution */
        wDimX = winX;
        wDimY = winY;

        /* set clearColor to default value (black) */
        clearColor = new Color(000, 000, 000);

        /* create window and set visible */
        jWindow = new JFrame();
        jWindow.setSize(wDimX, wDimY);
        jWindow.setResizable(false);
        jWindow.setVisible(true);

        /* on window close, kill everything */
        jWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* enable DOUBLE BUFFERING */
        jWindow.createBufferStrategy(NUMBUFFERS);

        /* grab rendering object */
        renderObject = jWindow.getBufferStrategy().getDrawGraphics();

        /* setup virtural camera position */
        cameraPosition = new SKFVector();
    }

    /******************************************
     * METHOD: flipBuffer
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
     *****************************************/
    public void flipBuffer( )
    {
        /* this flips the renderbuffer   */
        /* all subsequent render calls   */
        /* to the back buffer until this */
        /* function is called again      */
        jWindow.getBufferStrategy().show();
    }

    /******************************************
     * METHOD: setClearColor
     * PARAMS:
     *  int cR -> red color   (0 - 255)
     *  int cG -> green color (0 - 255)
     *  int cB -> blue color  (0 - 255)
     * RETURNS:
     *  void
     *****************************************/
    public void setClearColor(int cR, int cG, int cB)
    {
        /* ARRRGH I'M CREATING A NEW OBJECT     */
        /* GARBAGE COLLECTION IS GONNA SCREW ME */
        clearColor = new Color(cR, cG, cB);
    }

    /******************************************
     * METHOD: clearBuffer
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
     *****************************************/
    public void clearBuffer( )
    {
        /* set renderObject drawcolor to clearColor */
        renderObject.setColor(clearColor);

        /* clear and draw rect over all canvas */
        renderObject.clearRect(0, 0, wDimX, wDimY);
        renderObject.fillRect(0, 0, wDimX, wDimY);
    }

    /******************************************
     * METHOD: setCameraPosition
     * PARAMS:
     *  int x -> cam X pos
     *  int y -> cam Y pos
     * RETURNS:
     *  void
     *****************************************/
    public void setCameraPosition(int x, int y)
    {
        cameraPosition.set(x, y);
    }

    /******************************************
     * METHOD: drawLine
     * PARAMS:
     *  SKFVector p1 -> starting pos
     *  SKFVector p2 -> ending pos
     *  Color dc     -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawLine(SKFVector p1, SKFVector p2, Color dc)
    {
        /* final draw position vars */
        SKFVector p1f = new SKFVector( );
        SKFVector p2f = new SKFVector( );

        /* offset draw coords by camera  */
        p1f.x = p1.x - cameraPosition.x;
        p1f.y = p1.y - cameraPosition.y;
        p2f.x = p2.x - cameraPosition.x;
        p2f.y = p2.y - cameraPosition.y;

        /* set draw color and render */
        /* remember to inverse Y     */
        renderObject.setColor(dc);
        renderObject.drawLine((int)p1f.x, (int)wDimY - (int)p1f.y, (int)p2f.x, (int)wDimY - (int)p2f.y);
    }

    /******************************************
     * METHOD: drawLine
     * PARAMS:
     *  int x1   -> initial x pos
     *  int y1   -> initial y pos
     *  int x2   -> final x pos
     *  int y2   -> final y pos
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawLine(int x1, int y1, int x2, int y2,  Color dc)
    {
        /* final draw position vars */
        SKFVector p1f = new SKFVector( );
        SKFVector p2f = new SKFVector( );

        /* offset draw coords by camera  */
        p1f.x = x1 - cameraPosition.x;
        p1f.y = y1 - cameraPosition.y;
        p2f.x = x2 - cameraPosition.x;
        p2f.y = y2 - cameraPosition.y;

        /* set draw color and render */
        /* remember to inverse Y     */
        renderObject.setColor(dc);
        renderObject.drawLine((int)p1f.x, (int)wDimY - (int)p1f.y, (int)p2f.x, (int)wDimY - (int)p2f.y);
    }

    /******************************************
     * METHOD: drawRect
     * PARAMS:
     *  int x    -> posX
     *  int y    -> posY
     *  int w    -> width
     *  int h    -> height
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawRect(int x, int y, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)x - (int)cameraPosition.x;
        int fy = (int)y - (int)cameraPosition.y;

        /* set draw color and render */
        renderObject.setColor(dc);
        renderObject.fillRect(fx, wDimY - fy - h, w, h);
    }

    /******************************************
     * METHOD: drawRect
     * PARAMS:
     *  SKFVector pos -> draw position
     *  int w         -> width
     *  int h         -> height
     *  Color dc      -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawRect(SKFVector pos, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)pos.x - (int)cameraPosition.x;
        int fy = (int)pos.y - (int)cameraPosition.y;

        /* set draw color and render */
        /* REMEMBER TO INVERSE Y     */
        renderObject.setColor(dc);
        renderObject.fillRect(fx, wDimY - fy - h, w, h);
    }

    /******************************************
     * METHOD: outlineRect
     * PARAMS:
     *  int x    -> posX
     *  int y    -> posY
     *  int w    -> width
     *  int h    -> height
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void outlineRect(int x, int y, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)x - (int)cameraPosition.x;
        int fy = (int)y - (int)cameraPosition.y;

        /* set draw color and render */
        renderObject.setColor(dc);
        renderObject.drawRect(fx, wDimY - fy - h, w, h);
    }

    /******************************************
     * METHOD: outlineRect
     * PARAMS:
     *  SKFVector pos -> draw position
     *  int w         -> width
     *  int h         -> height
     *  Color dc      -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void outlineRect(SKFVector pos, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)pos.x - (int)cameraPosition.x;
        int fy = (int)pos.y - (int)cameraPosition.y;

        /* set draw color and render */
        /* REMEMBER TO INVERSE Y     */
        renderObject.setColor(dc);
        renderObject.drawRect(fx, wDimY - fy, w, h);
    }

    /******************************************
     * METHOD: drawOval
     * PARAMS:
     *  int x    -> posX
     *  int y    -> posY
     *  int w    -> width
     *  int h    -> height
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawOval(int x, int y, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = x - (int)cameraPosition.x - (w / 2);
        int fy = y - (int)cameraPosition.y + (h / 2);

        /* set draw color and render */
        renderObject.setColor(dc);
        renderObject.fillOval(fx, wDimY - fy, w, h);
    }

    /******************************************
     * METHOD: drawOval
     * PARAMS:
     *  SKFVector pos -> draw position
     *  int w         -> width
     *  int h         -> height
     *  Color dc      -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawOval(SKFVector pos, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)pos.x - (int)cameraPosition.x - (w / 2);
        int fy = (int)pos.y - (int)cameraPosition.y + (h / 2);

        /* set draw color and render */
        /* REMEMBER TO INVERSE Y     */
        renderObject.setColor(dc);
        renderObject.fillOval(fx, wDimY - fy, w, h);
    }

    /******************************************
     * METHOD: outlineOval
     * PARAMS:
     *  int x    -> posX
     *  int y    -> posY
     *  int w    -> width
     *  int h    -> height
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void outlineOval(int x, int y, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)x - (int)cameraPosition.x - (w / 2);
        int fy = (int)y - (int)cameraPosition.y + (h / 2);

        /* set draw color and render */
        renderObject.setColor(dc);
        renderObject.drawOval(fx, wDimY - fy, w, h);
    }

    /******************************************
     * METHOD: outlineOval
     * PARAMS:
     *  SKFVector pos -> draw position
     *  int w         -> width
     *  int h         -> height
     *  Color dc      -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void outlineOval(SKFVector pos, int w, int h, Color dc)
    {
        /* get final draw positions */
        int fx = (int)pos.x - (int)cameraPosition.x - (w / 2);
        int fy = (int)pos.y - (int)cameraPosition.y + (h / 2);

        /* set draw color and render */
        /* REMEMBER TO INVERSE Y     */
        renderObject.setColor(dc);
        renderObject.drawOval(fx, wDimY - fy, w, h);
    }

    /******************************************
     * METHOD: drawPolygon
     * PARAMS:
     *  SKFVector pos -> draw position
     *  int[] pxv     -> x position vector
     *  int[] pyv     -> y position vector
     *  int rLen      -> read lenth of arrays
     *  Color dc      -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawPolygon(SKFVector pos, int[] pxv, int[] pyv, int rLen, Color dc)
    {
        /* create new arrays of size rLen */
        int[] txVArr = new int[rLen];
        int[] tyVArr = new int[rLen];

        /* fill and transform */
        for(int i = 0; i < rLen; i++)
        {
            txVArr[i] = (int)pxv[i] - (int)cameraPosition.x + (int)pos.x;
            tyVArr[i] = (int)wDimY - (int)pyv[i] - (int)cameraPosition.y - (int)pos.y;
        }

        /* set color and send arrays down    */
        /* to graphics handler for rendering */
        renderObject.setColor(dc);
        renderObject.fillPolygon(txVArr, tyVArr, rLen);
    }

    /******************************************
     * METHOD: drawPolygon
     * PARAMS:
     *  SKFVector pos  -> draw position
     *  SKFVector[] vA -> vector array
     *  int rLen       -> read lenth of arrays
     *  Color dc       -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawPolygon(SKFVector pos, SKFVector[] vA,  int rLen, Color dc)
    {
        /* create new arrays of size rLen */
        int[] txVArr = new int[rLen];
        int[] tyVArr = new int[rLen];

        /* fill and transform */
        for(int i = 0; i < rLen; i++)
        {
            txVArr[i] = (int)vA[i].x - (int)cameraPosition.x + (int)pos.x;
            tyVArr[i] = (int)wDimY - (int)vA[i].y - (int)cameraPosition.y - (int)pos.y;
        }
        /* set color and send arrays down    */
        /* to graphics handler for rendering */
        renderObject.setColor(dc);
        renderObject.fillPolygon(txVArr, tyVArr, rLen);
    }

    /******************************************
     * METHOD: drawScreenspaceRect
     * PARAMS:
     *  int x    -> x position
     *  int y    -> y position
     *  int w    -> width
     *  int h    -> height
     *  Color dc -> draw color
     * RETURNS:
     *  void
     *****************************************/
    public void drawScreenspaceRect(int x, int y, int w, int h, Color dc)
    {
        /* a screenspace rect is not affected */
        /* by the position of the camera      */

        /* set color and render */
        renderObject.setColor(dc);
        renderObject.fillRect(x, wDimY - y - h, w, h);
    }

    /******************************************
     * METHOD: drawRenderSet
     * PARAMS:
     *  int x -> x position
     *  int y -> y position
     *  SKFRenderSet rs -> renderset to draw
     * RETURNS:
     *  void
     *****************************************/
    public void drawRenderSet(int x, int y, SKFRenderSet rs)
    {
        /* loop through renderset */
        for(int i = 0; i < rs.useLayers; i++)
        {
            /* get argset object */
            SKFArgset dArgs = rs.elems[i];

            /* switch based on rType */
            int renType = dArgs.argType;

            switch(renType)
            {
                /* if render rect */
                case SKFArgset.RECTTYPE:
                    drawRect(x + dArgs.p1, y + dArgs.p2, dArgs.p3, dArgs.p4, dArgs.col);
                    break;
                
                /* if render oval */
                case SKFArgset.OVALTYPE:
                    drawOval(x + dArgs.p1, y + dArgs.p2, dArgs.p3, dArgs.p4, dArgs.col);
                    break;

                /* if render polygon */
                case SKFArgset.POLYTYPE:
                    drawPolygon(new SKFVector(x, y), dArgs.ia1, dArgs.ia2, dArgs.p1, dArgs.col);
                    break;

                /* if render line */
                case SKFArgset.LINETYPE:
                    SKFVector p1 = new SKFVector(x + dArgs.p1, y + dArgs.p2);
                    SKFVector p2 = new SKFVector(x + dArgs.p3, y + dArgs.p4);
                    drawLine(p1, p2, dArgs.col);
                    break;

                /* if texture */
                case SKFArgset.TEXTURE:
                    drawTexture(x + dArgs.p1, y + dArgs.p2, 
                    dArgs.p3, dArgs.p4, dArgs.targ);
                    break;

                /* if err type */
                case SKFArgset.UNUSED:
                    System.err.println("Corrupted Argset!");
                    System.err.println("Unmatching args at RenderSet index " + i);
                    System.err.println("Args were:");
                    
                    /* print single args */
                    System.err.println(dArgs.p1 + "," + dArgs.p2 + "," +
                    dArgs.p3 + "," + dArgs.p4);

                    /* print array args */
                    System.err.println("ia1 :");
                    for(int j = 0; j < SKFArgset.IARRLEN; j++)
                    {
                        System.err.print(dArgs.ia1 + ",");
                    }
                    System.err.println(" ");
                    System.err.println("ia2 :");
                    for(int j = 0; j < SKFArgset.IARRLEN; j++)
                    {
                        System.err.print(dArgs.ia2 + ",");
                    }

                    /* print color */
                    System.err.println("Color:" + dArgs.col.getRGB());

                    break;
            }
        }
    }

    /******************************************
     * METHOD: drawTexture
     * PARAMS:
     *  int x    -> x position
     *  int y    -> y position
     *  int w    -> width
     *  int h    -> height
     *  SKFTexture tex -> texture to draw
     * RETURNS:
     *  void
     *****************************************/
    public void drawTexture(int x, int y, int w, int h, SKFTexture tex)
    {
        /* get rect dims */
        int rx = w / SKFTexture.DIMX;
        int ry = h / SKFTexture.DIMY;
        int drawX = x;
        int drawY = y;

        for(int i = 0; i < SKFTexture.DIMX; i++)
        {
            /* reset draw x */
            drawX = x;

            for(int j = 0; j < SKFTexture.DIMY; j++)
            {
                /* create color */
                /* invert colors because i'm silly :( */
                Color dc = tex.getColor(j, i);

                /* draw texture element */
                drawRect(drawX, drawY, rx, ry, dc);

                /* increment draw x */
                drawX += rx;
            }

            /* increment draw Y */
            drawY += ry;
        }
    }
 }
