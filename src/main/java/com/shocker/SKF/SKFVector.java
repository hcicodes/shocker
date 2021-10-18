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

 package SKF; /* package name */

 public final class SKFVector {

    /* dim count */
    private static final int DIMCOUNT = 2;

    /* x and y position */
    public int x;
    public int y;

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
     *  int X -> x pos
     *  int Y -> y pos
     * RETURNS:
     *  N/A
    *****************************************/
    public SKFVector(int X, int Y)
    {
        x = X;
        y = Y;
    }

    /******************************************
     * METHOD: set
     * PARAMS:
     *  int X -> x pos to set
     *  int Y -> y pos to set
     * RETURNS:
     *  void
     *****************************************/
    public void set(int X, int Y)
    {
        x = X;
        y = Y;
    }

    /******************************************
     * METHOD: add
     * PARAMS:
     *  int X -> x pos to add to x member
     *  int Y -> y pos to add to y member
     * RETURNS:
     *  void
     *****************************************/
    public void add(int X, int Y)
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
     *  int X -> x pos to add to x member
     *  int Y -> y pos to add to y member
     * RETURNS:
     *  void
     *****************************************/
    public void subtract(int X, int Y)
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
        float  sqrX = (float)Math.pow(x, 2);
        float  sqrY = (float)Math.pow(y, 2);
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
     * METHOD: toInts
     * PARAMS:
     *  N/A
     * RETURNS:
     *  an integer array, index 0 is x, index 1
     * is y
     *****************************************/
    public int[] toInts( )
    {
        /* NOO! HEAP ALLOCATION IS MAKING ME CRY */
        int[] iArr = new int[DIMCOUNT];

        /* set indexes and return */
        iArr[0] = x;
        iArr[1] = y;
        return iArr;
    }
}
