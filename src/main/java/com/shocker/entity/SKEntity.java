/**********************************************************
 * FILE:
 *  SKEntity.java
 * DATE: 
 *  2021-10-18
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Primitve entity class for rendering and "object"
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
    /* entity PHYSICS members */
    public SKFVector position;
    public SKFVector velocity;
    public float     drag;
    public float     colliderRadius;

    /* terrible enum for rendering */
    public final int ENTITY_RENDERSET = 0;
    public final int ENTITY_TEXTURE   = 0;

    /* entity RENDER members */
    public SKFRenderSet renderSet;
    public SKFTexture   texture;
    public boolean      isVisible;
    public float        renderScale;
    public int          renderLayer;
    
 }