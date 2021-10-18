/**********************************************************
 * FILE:
 *  SKEntityHandler.java
 * DATE: 
 *  2021-10-18
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  EntityHandler for updating and managing all entities.
 * CONTENTS:
 *  - Package defs
 *  - Imports
 *  - Class defs
 *  - Private members
 *  - Public members
 *  - Ctors
 *  - Private methods
 *  - Public methods
 *********************************************************/

 package com.shocker.entity; /* package name */

 import javax.swing.text.html.parser.Entity;

import com.shocker.SKF.*; /* all framework classes */

 public final class SKEntityHandler
 {
    /* definitions */
    public final int MAXENTITIES = 0xff;
    
    /* private entity related vars */
    private int        entityCount;
    private SKEntity[] entityStack;
    
    /* physics related vars */
    private SKFVector[] anticipatedPositions;

    /* ctor */
    public SKEntityHandler( )
    {
        /* reset entitycount */
        entityCount = 0;

        /* init estack */
        entityStack = new SKEntity[MAXENTITIES];

        /* inti vector list */
        anticipatedPositions = new SKFVector[MAXENTITIES];
    }
 }