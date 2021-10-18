/**********************************************************
 * FILE:
 *  SKEntity.java
 * DATE: 
 *  2021-10-18
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

 public abstract class SKEntity
 {
    /* some callback related vars */
    public boolean isOnStart;

    /* entity PHYSICS members */
    public SKFVector position;
    public SKFVector velocity;
    public float     drag;
    public float     colliderRadius;

    /* terrible enum for rendering */
    public final int ENTITY_RENDERSET = 0;
    public final int ENTITY_TEXTURE   = 1;
    public final int ENTITY_NOTINIT   = 2;

    /* entity RENDER members */
    public SKFRenderSet renderSet;
    public SKFTexture   texture;
    public boolean      isVisible;
    public int          renderLayer;
    public int          renderFlags;

    /* empty ctor */
    public SKEntity( )
    {
        /* set isOnStart to true, so that onStart callback can execute */
        isOnStart = true;

        /* set everything to default values */
        position = new SKFVector( );
        velocity = new SKFVector( );
        colliderRadius = 0;
        drag = 0;

        renderFlags = ENTITY_NOTINIT;
        renderSet   = null;
        texture     = null;
        renderLayer = 0;
        isVisible   = false;
    }

    /* proper ctor */
    public SKEntity(int x, int y, int entityRenderFlags, int layer, SKFRenderSet rs, SKFTexture tx)
    {
        /* set isOnStart to true, so that onStart callback can execute */
        isOnStart = true;

        /* setup PHYSICS vars */
        position = new SKFVector(x, y);
        velocity = new SKFVector( );
        colliderRadius = 0f;
        drag = 0f;

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
    }

    /******************************************
     * METHOD: update
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
    *****************************************/
    public abstract void update( );

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