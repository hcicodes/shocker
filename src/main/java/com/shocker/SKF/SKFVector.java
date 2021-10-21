/**********************************************************
 * FILE:
 *  SKFVector.java
 * DATE:
 *  2021-10-16
 * DEV:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Two dimensional vector class for coordinate positioning
 * CONTENTS:
 *  - Package def
 *  - Imports
 *  - Class defs
 *  - Private members
 *  - Public members
 *  - Ctors
 *  - Private methods
 *  - Public Methods
 *********************************************************/

package com.shocker.SKF; /* package name */

 public final class SKFVector {

    /* dim count */
    private static final int DIMCOUNT = 2;

    /* x and y position */
    public float x;
    public float y;

    /******************************************
     * METHOD: SKFVector
     * PARAMS:
     *  N/A
     * RETURNS:
     *  N/A
    *****************************************/
    public SKFVector( )
    {
        /* sets all members to 0 */
        x = 0;
        y = 0;
    }

    /******************************************
     * METHOD: SKFVector
     * PARAMS:
     *  float X -> x pos
     *  float Y -> y pos
     * RETURNS:
     *  N/A
    *****************************************/
    public SKFVector(float X, float Y)
    {
        x = X;
        y = Y;
    }

    /******************************************
     * METHOD: set
     * PARAMS:
     *  float X -> x pos to set
     *  float Y -> y pos to set
     * RETURNS:
     *  void
     *****************************************/
    public void set(float X, float Y)
    {
        x = X;
        y = Y;
    }

    /******************************************
     * METHOD: add
     * PARAMS:
     *  float X -> x pos to add to x member
     *  float Y -> y pos to add to y member
     * RETURNS:
     *  void
     *****************************************/
    public void add(float X, float Y)
    {
        x += X;
        y += Y;
    }

    /******************************************
     * METHOD: add
     * PARAMS:
     *  SKFVector vec -> vector to add
     * RETURNS:
     *  void
     *****************************************/
    public void add(SKFVector vec)
    {
        x += vec.x;
        y += vec.y;
    }

    /******************************************
     * METHOD: add
     * PARAMS:
     *  float X -> x pos to add to x member
     *  float Y -> y pos to add to y member
     * RETURNS:
     *  void
     *****************************************/
    public void subtract(float X, float Y)
    {
        x -= X;
        y -= Y;
    }

    /******************************************
     * METHOD: subtract
     * PARAMS:
     *  SKFVector vec -> vector to subtract
     * RETURNS:
     *  void
     *****************************************/
    public void subtract(SKFVector vec)
    {
        x -= vec.x;
        y -= vec.y;
    }

    /******************************************
     * METHOD: multiply
     * PARAMS:
     *  float scale -> scale to multiply both
     * members by
     * RETURNS:
     *  void
     *****************************************/
    public void scale(float scale)
    {
        x *= scale;
        y *= scale;
    }

    /******************************************
     * METHOD: getMagnitude
     * PARAMS:
     *  N/A
     * RETURNS:
     *  float, vector magnitude
     *****************************************/
    public float getMagnitude( )
    {
        /* a magnitude of a vector is  */
        /* basically just like finding */
        /* the hypotenuse              */
        float  sqrX = (float)Math.pow(x, 2f);
        float  sqrY = (float)Math.pow(y, 2f);
        double fMag = Math.sqrt(sqrX + sqrY);
        return (float)fMag;
    }

    /******************************************
     * METHOD: normalize
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
     *****************************************/
    public void normalize( )
    {
        /* normalization ensures that the sum    */
        /* of the two dimensions is equal to one */
        float vMag = getMagnitude( );
        x /= vMag;
        y /= vMag;
    }

    /******************************************
     * METHOD: toFloats
     * PARAMS:
     *  N/A
     * RETURNS:
     *  an float array, index 0 is x, index 1
     * is y
     *****************************************/
    public float[] toFloats( )
    {
        /* NOO! HEAP ALLOCATION IS MAKING ME CRY */
        float[] iArr = new float[DIMCOUNT];

        /* set indexes and return */
        iArr[0] = x;
        iArr[1] = y;
        return iArr;
    }

    /******************************************
     * METHOD: distanceTo
     * PARAMS:
     *  SKFVector dVec -> compare vect
     * RETURNS:
     *  the distance to another vector
     *****************************************/
    public float distanceTo(SKFVector dVec)
    {
        /* get difference in position */
        float dx = Math.abs(x - dVec.x);
        float dy = Math.abs(y - dVec.y);

        /* make into new vector and get distance */
        SKFVector tVec = new SKFVector(dx, dy);

        return tVec.getMagnitude( );

    }
}
