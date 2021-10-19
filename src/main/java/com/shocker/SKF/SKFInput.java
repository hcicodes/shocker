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
    /* current key down */
    public static int  currentKeyDown;

    /* current char */
    public static char currentChar;

    /* get input axis */
    public static SKFVector getInputAxis( )
    {
        SKFVector rv = new SKFVector(0, 0);

        /* arrow keys */
        if(upArrowPressed( ))    rv.add(0,  1);
        if(downArrowPressed( ))  rv.add(0, -1);
        if(leftArrowPressed( ))  rv.add(-1, 0);
        if(rightArrowPressed( )) rv.add(1,  0);

        /* WASD */
        int kd = currentKeyDown;
        if(kd == KeyEvent.VK_W) rv.add(0,  1);
        if(kd == KeyEvent.VK_S) rv.add(0, -1);
        if(kd == KeyEvent.VK_A) rv.add(-1, 0);
        if(kd == KeyEvent.VK_D) rv.add(1,  0);

        return rv;
    }

    public static boolean upArrowPressed( )
    {
        return currentKeyDown == KeyEvent.VK_UP;
    }

    public static boolean downArrowPressed( )
    {
        return currentKeyDown == KeyEvent.VK_DOWN;
    }

    public static boolean leftArrowPressed( )
    {
        return currentKeyDown == KeyEvent.VK_LEFT;
    }

    public static boolean rightArrowPressed( )
    {
        return currentKeyDown == KeyEvent.VK_RIGHT;
    }

    public void keyPressed(KeyEvent event)
    {
        /* update variables */
        currentKeyDown = event.getKeyCode( );
        currentChar    = event.getKeyChar( );
    }

    public void keyTyped(KeyEvent event)
    {
        /* do nothing */
    }

    public void keyReleased(KeyEvent event)
    {
        /* clear all */
        currentKeyDown = -1;
        currentChar = 0;
    }
 }