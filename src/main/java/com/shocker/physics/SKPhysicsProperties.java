/**********************************************************
 * FILE:
 *  SKPhysicsProperties.java
 * DATE: 
 *  2021-10-20
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Physics properties data container for later use
 * CONTENTS:
 *  - Package defs
 *  - Imports
 *  - Physics properties
 *  - Collision properties
 * 
 *********************************************************/

 package com.shocker.physics; /* package name */
 
 import java.math.*; /* math functions */

 public final class SKPhysicsProperties
 {
     /* physics properties */
     public float mass = 0;
     public float drag = 0;
     public float bounciness = 0;

     /* bounding box params */
     public float boundingBoxX = 0;
     public float boundingBoxY = 0;
     public float boundingBoxW = 0;
     public float boundingBoxH = 0;

     /* bounding circle params */
     public float boundingCircleX = 0;
     public float boundingCircleY = 0;
     public float boundingCircleR = 0;

     /* collision enum */
     public static final int BOUNDING_NONE   = 0;
     public static final int BOUNDING_BOX    = 1;
     public static final int BOUNDING_CIRCLE = 2;
     
     /* collision flags */
     public int boundingFlags = BOUNDING_NONE;
 }