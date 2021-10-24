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
 *  - Ctor
 *  - Collision getters and setters
 * 
 *********************************************************/

 package com.shocker.physics; /* package name */

 public final class SKPhysicsProperties
 {
     /* special physics properties */
     public boolean enableCollisions = true;
     public boolean isStatic = false;

     /* physics properties */
     public float mass = 0;
     public float drag = 0;
     public float bounciness = 0;

     /* bounding box params */
     private float boundingBoxX = 0;
     private float boundingBoxY = 0;
     private float boundingBoxW = 0;
     private float boundingBoxH = 0;

    /**================================================================
     * METHOD: setPhysicsProperties
     * PARAMS:
     *  float m -> object mass
     *  float d -> object drag
     *  float b -> bounciness
     * RETURNS:
     *  void
     **================================================================*/
    public void setPhysicsProperties(float m, float d, float b)
    {
        mass = m;
        drag = d;
        bounciness = b;
    }

    /**================================================================
     * METHOD: setBoundingBox
     * PARAMS:
     *  float bx -> box X offset
     *  float by -> box Y offset
     *  float bw -> box width
     *  float bh -> box height
     * RETURNS:
     *  void
     **================================================================*/
    public void setBoundingBox(float bx, float by, float bw, float bh)
    {
        boundingBoxX = bx;
        boundingBoxY = by;
        boundingBoxW = bw;
        boundingBoxH = bh;
    }

    /**================================================================
     * METHOD: getBoundingBox
     * PARAMS:
     *  N/A
     * RETURNS:
     *  float[] of size 4
     *  index 0 -> x
     *  index 1 -> y
     *  index 2 -> w
     *  index 3 -> h
     **================================================================*/
    public float[] getBoundingBox( )
    {
        /* create float array and fill */
        float[] rFloatArr = new float[4];
        rFloatArr[0] = boundingBoxX;
        rFloatArr[1] = boundingBoxY;
        rFloatArr[2] = boundingBoxW;
        rFloatArr[3] = boundingBoxH;

        return rFloatArr;
    }
 }