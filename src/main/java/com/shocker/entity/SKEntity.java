/**********************************************************
 * FILE:
 *  SKEntity.java
 * DATE: 
 *  2021-10-18
 *  2021-10-20
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Primitve abstract entity class for rendering and "object"
 * abstraction
 * CONTENTS:
 *  - Package defs
 *  - Imports
 *  - Private members
 *  - Public members
 *  - Ctors
 *  - Private methods
 *  - Public methods
 *********************************************************/

 package com.shocker.entity; /* entity name */

 import com.shocker.SKF.*; /* all framework classes */
 import com.shocker.physics.*; /* physics classes */

 public abstract class SKEntity
 {
    /* some callback related vars */
    public boolean isOnStart;

    /* entity PHYSICS members */
    public SKFVector position;
    public SKFVector velocity;
    public SKPhysicsProperties physProperties;

    /* terrible enum for rendering */
    public static final int ENTITY_RENDERSET = 0;
    public static final int ENTITY_TEXTURE   = 1;
    public static final int ENTITY_NOTINIT   = 2;

    /* entity RENDER members */
    public SKFRenderSet renderSet;
    public SKFTexture   texture;
    public boolean      isVisible;
    public int          renderLayer;
    public int          renderFlags;

    /* texture params */
    public int textureX;
    public int textureY;

    /* empty ctor */
    public SKEntity( )
    {
        /* set isOnStart to true, so that onStart callback can execute */
        isOnStart = true;

        /* set everything to default values */
        position = new SKFVector( );
        velocity = new SKFVector( );
        physProperties = new SKPhysicsProperties( );

        renderFlags = ENTITY_NOTINIT;
        renderSet   = null;
        texture     = null;
        renderLayer = 0;
        isVisible   = false;
    }

    /* proper ctor */
    public SKEntity(int x, int y, int entityRenderFlags, int layer, SKFRenderSet rs, 
    SKFTexture tx, int tX, int tY)
    {
        /* set isOnStart to true, so that onStart callback can execute */
        isOnStart = true;

        /* setup PHYSICS vars */
        position = new SKFVector(x, y);
        velocity = new SKFVector( );
        physProperties = new SKPhysicsProperties( );

        /* setup RENDER vars */
        renderFlags = entityRenderFlags;
        
        if (rs != null)
        {
            renderSet = rs;
        }
        if (tx != null)
        {
            texture = tx;
        }

        renderLayer = layer;
        isVisible   = true;

        /* set texture vars */
        textureX = tX;
        textureY = tY;
    }

    /******************************************
     * METHOD: init
     * PARAMS:
     *  int x -> x pos
     *  int y -> y pos
     *  int flags -> entity render type
     *  int layer -> layer
     *  SKFRenderSet rs -> renderset (can be null)
     *  SKFTexture tx -> texture (can be null)
     *  int tX -> texture scale X
     *  int tY -> texture scale Y
     * RETURNS:
     *  void
    *****************************************/
    public void init(int x, int y, int flags, int layer, SKFRenderSet rs,
     SKFTexture tx, int tX, int tY)
    {
        /* set isOnStart to true, so that onStart callback can execute */
        isOnStart = true;

        /* setup PHYSICS vars */
        position = new SKFVector(x, y);
        velocity = new SKFVector( );
        physProperties = new SKPhysicsProperties( );

        /* setup RENDER vars */
        renderFlags = flags;
        
        if (rs != null)
        {
            renderSet = rs;
        }
        if (tx != null)
        {
            texture = tx;
        }

        renderLayer = layer;
        isVisible   = true;

        /* set texture vars */
        textureX = tX;
        textureY = tY;
    }

    /******************************************
     * METHOD: update
     * PARAMS:
     *  long timePassed -> update iterations passed
     * RETURNS:
     *  void
    *****************************************/
    public abstract void update(long timePassed);

    /******************************************
     * METHOD: onStart
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
    *****************************************/
    public abstract void onStart( );

    /******************************************
     * METHOD: onCollide
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
    *****************************************/
    public abstract void onCollide( );


    
 }