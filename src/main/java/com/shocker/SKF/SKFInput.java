/**********************************************************
 * FILE:
 *  SKFInput.java
 * DATE:
 *  2021-10-18
 * DEV:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Input abstraction class
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

 import java.awt.event.*; /* listener library */

 public final class SKFInput implements KeyListener
 {
    /* defs */
    public static final int MAXKEYS = 10;

    /* current key down */
    public static int[] currentKeysDown;

    /* current char */
    public static char[] currentChars;

    /* init function */
    public SKFInput( )
    {
        currentKeysDown = new int[MAXKEYS];
        currentChars = new char[MAXKEYS];
    }

    /* get input axis */
    public static SKFVector getInputAxis( )
    {
        
        /* create new vector */
        SKFVector rv = new SKFVector( );

        /* check WASD */
        if(isKeyDown('w') || isKeyDown(KeyEvent.VK_UP))    rv.add(0,  1);
        if(isKeyDown('s') || isKeyDown(KeyEvent.VK_DOWN))  rv.add(0, -1);
        if(isKeyDown('a') || isKeyDown(KeyEvent.VK_LEFT))  rv.add(-1, 0);
        if(isKeyDown('d') || isKeyDown(KeyEvent.VK_RIGHT)) rv.add( 1, 0);

        /* return */
        return rv;
    }

    /* check for key down */
    public static boolean isKeyDown(int keyCode)
    {
        /* loop and find matching */
        for(int i = 0; i < MAXKEYS; i++)
        {
            if (currentKeysDown[i] == keyCode)
            {
                return true;
            }
        }

        return false;
    }

    /* check for key down */
    public static boolean isKeyDown(char charNum)
    {
        /* loop and find matching */
        for(int i = 0; i < MAXKEYS; i++)
        {
            if (currentChars[i] == charNum)
            {
                return true;
            }
        }

        return false;
    }

    /* print keys down */
    public static void printKeysDown( )
    {
        System.out.printf("Keys down: [");
        for(int i = 0; i < MAXKEYS; i++)
        {
            System.out.printf("%d, ", currentKeysDown[i]);
        }
        System.out.printf("]\n");
    }

    /* on key pressed */
    public void keyPressed(KeyEvent event)
    {
        int scannedKeyCode = event.getKeyCode( );
        char scannedChar = event.getKeyChar( );

        /* find free space in ckd */
        for(int i = 0; i < MAXKEYS; i++)
        {
            if(currentKeysDown[i] == scannedKeyCode)
            {
                break;
            }

            if(currentKeysDown[i] == 0)
            {
                currentKeysDown[i] = scannedKeyCode;
                break;
            }
        }

        /* find free space in cc */
        for(int i = 0; i < MAXKEYS; i++)
        {
            if(currentChars[i] == scannedChar)
            {
                break;
            }

            if(currentChars[i] == 0)
            {
                currentChars[i] = scannedChar;
                break;
            }
        }
    }

    public void keyTyped(KeyEvent event)
    {
        /* do nothing */
    }

    public void keyReleased(KeyEvent event)
    {
        /* get keys up */
        int getKeyUp = event.getKeyCode( );
        int getCharUp = event.getKeyChar( );

        /* loop and find match */
        for(int i = 0; i < MAXKEYS; i++)
        {
            /* clear if matching */
            if(getKeyUp == currentKeysDown[i])
            {
                currentKeysDown[i] = 0;
            }
            if(getCharUp == currentChars[i])
            {
                currentChars[i] = 0;
            }
        }
    }
 }