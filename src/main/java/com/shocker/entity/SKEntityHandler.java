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

 import com.shocker.SKF.*; /* all framework classes */

 public final class SKEntityHandler
 {
    /* definitions */
    public final int MAXENTITIES = 0xff;

    /* tick counter */
    private long ticksPassed = 0;
    
    /* private entity related vars */
    private int        entityCount;
    private SKEntity[] entityStack;
    
    /* physics related vars */
    private SKFVector[] anticipatedPositions;

    /* debug mode boolean */
    public boolean deBugmode;

    /* ctor */
    public SKEntityHandler( )
    {
        /* set debugmode to false */
        deBugmode = false; 

        /* reset entitycount */
        entityCount = 0;

        /* init estack */
        entityStack = new SKEntity[MAXENTITIES];

        /* inti vector list */
        anticipatedPositions = new SKFVector[MAXENTITIES];
    }

    /******************************************
     * METHOD: getEntityCount
     * PARAMS:
     *  N/A
     * RETURNS:
     *  int, entity count
    *****************************************/
    public int getEntityCount( )
    {
        return entityCount;
    }

    /******************************************
     * METHOD: addEntity
     * PARAMS:
     *  SKEntity ent -> entity to add
     * RETURNS:
     *  int, entity handle
    *****************************************/
    public int addEntity(SKEntity ent)
    {
        /* find empty spot for ent */
        for(int i = 0; i < MAXENTITIES; i++)
        {
            if(entityStack[i] == null)
            {
                /* add entity and increment count */
                entityStack[i] = ent;
                entityCount++;

                /* if debug mode, log it */
                if(deBugmode)
                {
                    System.out.printf("Added entity to stack position: %d\n", i);
                }

                /* create physics vars */
                anticipatedPositions[i] = new SKFVector(ent.position.x, ent.position.y);

                return i;
            }
        }

        /* no free spot found! */
        System.err.printf("TOO MANY ENTITIES!\n");
        return -1;
    }

    /******************************************
     * METHOD: removeEntity
     * PARAMS:
     *  int entHndl -> entity handle
     * RETURNS:
     *  void
    *****************************************/
    public void removeEntity(int entHndl)
    {
        /* if debug mode */
        if(deBugmode)
        {
            System.out.printf("Removing entity from stack position: %d\n", entHndl);
        }

        /* free stack space and decrement eCount */
        entityStack[entHndl] = null;
        entityCount--;
    }

    /******************************************
     * METHOD: update
     * PARAMS:
     *  N/A
     * RETURNS:
     *  void
    *****************************************/
    public void update( )
    {
        /* LOOP THROUGH ALL THE ENTITIES */
        /* only exec if !null            */
        for(int i = 0; i < MAXENTITIES; i++)
        {
            if(entityStack[i] != null)
            {
                /* increment tick count */
                ticksPassed++;

                /* if debug mode */
                if(deBugmode)
                {
                    System.out.printf("----------------------------------------\n");
                    System.out.printf("CURRENT ENTITY: [%d]\n", i);
                }

                /* create temp entity object */
                SKEntity tempEntity = entityStack[i];

                /* check if it's entity's first update */
                if(tempEntity.isOnStart)
                {
                    /* debug */
                    if(deBugmode)
                    {
                        System.out.printf("Entity start!\n", i);
                    }

                    /* exec */
                    tempEntity.onStart();
                    tempEntity.isOnStart = false;
                }

                /* update positon */
                tempEntity.position.add(tempEntity.velocity);

                /* update velocity */
                if(tempEntity.velocity.getMagnitude() > 0.075)
                {
                    /* damp and clamp */
                    float clampDrag = 1 - tempEntity.drag;
                    if(clampDrag < 0) { clampDrag = 0; }
                    tempEntity.velocity.scale(clampDrag);
                }
                else
                {
                    /* if velocity is negigble, set to 0 */
                    tempEntity.velocity.set(0, 0);
                }

                /* debug */
                if(deBugmode)
                {
                    System.out.printf("Time: %d\n", ticksPassed);
                    System.out.printf("Velocity: (%f, %f)\n",
                    tempEntity.velocity.x, tempEntity.velocity.y);
                    System.out.printf("VMag: %f\n", tempEntity.velocity.getMagnitude());
                    System.out.printf("Position: (%f, %f)\n",
                    tempEntity.position.x, tempEntity.position.y);
                }

                /* debug */
                if(deBugmode)
                {
                    System.out.printf("----------------------------------------\n");
                }
            }
        }
    }
 }