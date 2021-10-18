/**********************************************************
 * FILE:
 *  SKFRenderSet.java
 * DATE:
 *  2021-10-17
 * DEV:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  More advanced image rendering class, takes multiple
 * primitive render calls to a window class and packages
 * them all into one call
 * CONTENTS:
 *  - Package def
 *  - Imports
 *  - Class defs
 *  - Priavte subclasses
 *  - Private members
 *  - Public members
 *  - Ctors
 *  - Private methods
 *  - Public methods
 *********************************************************/
package com.shocker.SKF; /* package name */

import java.awt.*; /* color class */

public final class SKFRenderSet
{
    /* maximum render elements in the set */
    public static final int MAXELEMS = 0x20;

    /* argset subclass */
    /* used for storing args for render calls */
    public final class SKFArgset
    {
        /* defs */
        public static final int IARRLEN = 0x40;

        /* drawtype defs */
        /* yes, i know enums exist, but  */
        /* since i hate how java handles */
        /* them i'm gonna brute force it */
        public static final int RECTTYPE = 0;
        public static final int OVALTYPE = 1;
        public static final int LINETYPE = 2;
        public static final int POLYTYPE = 3;
        public static final int UNUSED   = 4;
        public static final int TEXTURE  = 5;

        /* argset type */
        public int argType;

        /* int args (NOT AN ARRAY INTENTIONALLY) */
        public int p1;
        public int p2;
        public int p3;
        public int p4;
        
        /* int[] args */
        public int[] ia1;
        public int[] ia2;

        /* color args */
        public Color col;

        /* texture arg */
        public SKFTexture targ;

        /* ctor inits all to 0 */
        public SKFArgset( )
        {
            /* init type */
            argType = UNUSED;

            /* init int args */
            p1 = p2 = p3 = p4 = 0;

            /* init int[] args */
            ia1 = new int[IARRLEN];
            ia2 = new int[IARRLEN];

            /* init color */
            col = new Color(0, 0, 0);

            /* init texture */
            targ = new SKFTexture();
        }
    }

    /* argset array for render calls */
    public SKFArgset[] elems;

    /* how many layers to use */
    public int useLayers;

    /* ctor */
    public SKFRenderSet( )
    {
        /* init argset array and init layercount */
        elems = new SKFArgset[MAXELEMS];
        for(int i = 0; i < MAXELEMS; i++)
        {
            elems[i] = new SKFArgset();
        }
        useLayers = 0;
    }

    /******************************************
     * METHOD: addRect
     * PARAMS:
     *  int x   -> x pos
     *  int y   -> y pos
     *  int w   -> width
     *  int h   -> height
     *  Color c -> color
     * RETURNS:
     *  N/A
    *****************************************/
    public void addRect(int x, int y, int w, int h, Color c)
    {
        /* set args */
        elems[useLayers].argType = SKFArgset.RECTTYPE;
        elems[useLayers].p1  = x;
        elems[useLayers].p2  = y;
        elems[useLayers].p3  = w;
        elems[useLayers].p4  = h;
        elems[useLayers].col = c;

        /* increment */
        useLayers++;
    }

    /******************************************
     * METHOD: addOval
     * PARAMS:
     *  int x   -> x pos
     *  int y   -> y pos
     *  int w   -> width
     *  int h   -> height
     *  Color c -> color
     * RETURNS:
     *  N/A
    *****************************************/
    public void addOval(int x, int y, int w, int h, Color c)
    {
        /* set args */
        elems[useLayers].argType = SKFArgset.OVALTYPE;
        elems[useLayers].p1  = x;
        elems[useLayers].p2  = y;
        elems[useLayers].p3  = w;
        elems[useLayers].p4  = h;
        elems[useLayers].col = c;

        /* increment */
        useLayers++;
    }

    /******************************************
     * METHOD: addPolygon
     * PARAMS:
     *  int[] xArr -> array of x position
     *  int[] yArr -> array of y position
     *  int rLen   -> how much to red
     *  Color col  -> color of polygon
     * RETURNS:
     *  N/A
    *****************************************/
    public void addPolygon(int[] xArr, int[] yArr, int rLen, Color col)
    {
        /* set args */
        elems[useLayers].argType = SKFArgset.POLYTYPE;
        elems[useLayers].ia1 = xArr;
        elems[useLayers].ia2 = yArr;
        elems[useLayers].p1  = rLen;
        elems[useLayers].col = col;

        /* increment */
        useLayers++;
    }

    /******************************************
     * METHOD: addLine
     * PARAMS:
     *  int x1     -> initial x
     *  int y1     -> initial y
     *  int x2     -> final x
     *  int y2     -> final y
     *  Color col  -> color of polygon
     * RETURNS:
     *  N/A
    *****************************************/
    public void addLine(int x1, int y1, int x2, int y2, Color col)
    {
        /* set args */
        elems[useLayers].argType = SKFArgset.LINETYPE;
        elems[useLayers].p1 = x1;
        elems[useLayers].p2 = y1;
        elems[useLayers].p3 = x2;
        elems[useLayers].p4 = y2;
        elems[useLayers].col = col;

        /* increment */
        useLayers++;
    }

    /******************************************
     * METHOD: addTexture
     * PARAMS:
     *  int x -> tex x
     *  int y -> tex y
     *  int w -> width
     *  int h -> height
     *  SKFTexture tex -> texture data
     * RETURNS:
     *  N/A
    *****************************************/
    public void addTexture(int x, int y, int w, int h, SKFTexture tex)
    {
        /* set args */
        elems[useLayers].argType = SKFArgset.TEXTURE;
        elems[useLayers].p1 = x;
        elems[useLayers].p2 = y;
        elems[useLayers].p3 = w;
        elems[useLayers].p4 = h;
        elems[useLayers].targ = tex;

        /* increment */
        useLayers++;
    }
    
}

