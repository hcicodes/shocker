/**********************************************************
 * FILE:
 *  SKFTexture.java
 * DATE:
 *  2021-10-17
 * DEV:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  8x8 Software-rendered texture. Very slow when called alot
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

 import java.awt.*; /* color related classes */

 public final class SKFTexture
 {
    /* class defs */
    public static final int DIMX = 8;
    public static final int DIMY = 8;
    public static final int CDIM = 4;

    /* byte data */
    /* changed to INT BECAUSE THERE ARE NO UBYTES IN JAVA?????*/
    private int[][][] pixelData;

    /* ctor */
    public SKFTexture( )
    {
        /* init pixelData */
        pixelData = new int[DIMX][DIMY][CDIM];

        /* set alpha */
        for(int i = 0; i < DIMX; i++)
        {
            for(int j = 0; j < DIMY; j++)
            {
                pixelData[i][j][3] = 255;
            }
        }
    }

    /* ctor with color */
    public SKFTexture(int r, int g, int b)
    {
        /* init pixelData */
        pixelData = new int[DIMX][DIMY][CDIM];

        /* set alpha */
        for(int i = 0; i < DIMX; i++)
        {
            for(int j = 0; j < DIMY; j++)
            {
                pixelData[i][j][0] = r;
                pixelData[i][j][1] = g;
                pixelData[i][j][2] = b;
                pixelData[i][j][3] = 255;
            }
        }
    }

    /* ctor with color */
    public SKFTexture(int r, int g, int b, int a)
    {
        /* init pixelData */
        pixelData = new int[DIMX][DIMY][CDIM];

        /* set alpha */
        for(int i = 0; i < DIMX; i++)
        {
            for(int j = 0; j < DIMY; j++)
            {
                pixelData[i][j][0] = r;
                pixelData[i][j][1] = g;
                pixelData[i][j][2] = b;
                pixelData[i][j][3] = a;
            }
        }
    }

    /* ctor with data */
    public SKFTexture(int[][][] data)
    {
        /* init pixelData */
        pixelData = new int[DIMX][DIMY][CDIM];

        /* set alpha */
        for(int i = 0; i < DIMX; i++)
        {
            for(int j = 0; j < DIMY; j++)
            {
                pixelData[i][DIMY - j - 1][0] = data[i][j][0];
                pixelData[i][DIMY - j - 1][1] = data[i][j][1];
                pixelData[i][DIMY - j - 1][2] = data[i][j][2];
                pixelData[i][DIMY - j - 1][3] = data[i][j][3];
            }
        }
    }

    /******************************************
     * METHOD: setPixel
     * PARAMS:
     *  int x -> x pos
     *  int y -> y pos
     *  int r -> r col
     *  int g -> g col
     *  int b -> b col
     *  int a -> a col
     * RETURNS:
     *  N/A
    *****************************************/
    public void setPixel(int x, int y, int r, int g, int b, int a)
    {
        pixelData[x][DIMY - y - 1][0] = r;
        pixelData[x][DIMY - y - 1][1] = g;
        pixelData[x][DIMY - y - 1][2] = b;
        pixelData[x][DIMY - y - 1][3] = a;
    }

    /******************************************
     * METHOD: setPixels
     * PARAMS:
     *  int[] aX -> x pos array
     *  int[] aY -> y pos array
     *  int cnt  -> copy count
     *  int r    -> r col
     *  int g    -> g col
     *  int b    -> b col
     *  int a    -> a col
     * RETURNS:
     *  N/A
    *****************************************/
    public void setPixels(int[] aX, int[] aY, int cnt, int r, int g, int b, int a)
    {
        for(int i = 0; i < cnt; i++)
        {
            pixelData[aX[i]][DIMY - aY[i] - 1][0] = r;
            pixelData[aX[i]][DIMY - aY[i] - 1][1] = g;
            pixelData[aX[i]][DIMY - aY[i] - 1][2] = b;
            pixelData[aX[i]][DIMY - aY[i] - 1][3] = a;
        }
    }

    /******************************************
     * METHOD: getColor
     * PARAMS:
     *  int x -> x pos
     *  int y -> y pos
     * RETURNS:
     *  Color of pixel at position
    *****************************************/
    Color getColor(int x, int y)
    {
        int r = pixelData[x][DIMY - y - 1][0];
        int g = pixelData[x][DIMY - y - 1][1];
        int b = pixelData[x][DIMY - y - 1][2];
        int a = pixelData[x][DIMY - y - 1][3];

        Color rc = new Color(r, g, b, a);
        return rc;
    }
 }