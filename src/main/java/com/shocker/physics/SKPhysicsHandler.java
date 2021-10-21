/**********************************************************
 * FILE:
 *  SKPhysicsHandler.java
 * DATE: 
 *  2021-10-20
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Phyics handler object
 * CONTENTS:
 *  - Package defs
 *  - Imports
 *  - Buffer size defs
 * 
 *********************************************************/

 package com.shocker.physics; /* package name */

 import com.shocker.SKF.*;    /* all framework classes */
 import com.shocker.entity.*; /* all entity classes    */

 public final class SKPhysicsHandler
 {
     /* buffer size defs */
     public static final int MAXPOBJS   = 0x80;
     public static final int MAXPGROUPS = 0x30;
     public static final int MAXPGSIZE  = 0x40;

     /* buffers */
     public SKEntity[] pObjBuffer = new SKEntity[MAXPOBJS];
     public SKFVector[] vecBuffer = new SKFVector[MAXPOBJS];

 }