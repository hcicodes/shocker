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
    public final int MAXENTITIES = 0x20;
    public final int MAXLAYERS   = 0x40;

    /* tick counter */
    private long ticksPassed = 0;
    
    /* private entity related vars */
    private int          entityCount;
    private SKEntity[][] entityStack;
    
    /* physics related vars */
    private SKFVector[][] anticipatedPositions;

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
        entityStack = new SKEntity[MAXLAYERS][MAXENTITIES];

        /* inti vector list */
        anticipatedPositions = new SKFVector[MAXLAYERS][MAXENTITIES];
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
        /* loop through layer */
        for(int i = 0; i < MAXENTITIES; i++)
        {
            /* find free space */
            if(entityStack[ent.renderLayer][i] == null)
            {
                /* debug output */
                if(deBugmode)
                {
                    System.out.printf(">>>Found free spot at: %d\n", i);
                    System.out.printf(">>>Adding entity to layer: %d\n", ent.renderLayer);
                }

                /* add entity and increment count */
                anticipatedPositions[ent.renderLayer][i] = new SKFVector( );
                entityStack[ent.renderLayer][i] = ent;
                entityCount++;

                /* create handle */
                /* first two bytes reserved for index */
                /* last two bytes reserved for layer */
                /* BITMASK:
                 * IIIIIIIIIIIIIIIILLLLLLLLLLLLLLLL */
                int handle;          /* handle to return */
                int index = i;       /* upper 2 bits */
                int layer = ent.renderLayer; /* lower 2 bits */
                index = index << 16;  /* shift up */

                handle = index | layer; /* create handle */

                /* return */
                return handle;
            }
        }

        /* no free space */
        System.err.printf("NO ENTITY SPACES LEFT!\n");
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
        /* get layer */
        int eLayer = entHndl & 0b00000000000000001111111111111111;

        /* get index */
        int index = entHndl >> 16;

        /* if debug mode */
        if(deBugmode)
        {
            System.out.printf(">>>Removing entity at: %d %d\n", eLayer, index);
        }

        /* remove entity and decrement */
        entityStack[eLayer][index] = null;
        entityCount--;
    }

    /******************************************
     * METHOD: render
     * PARAMS:
     *  SKFWindow target -> window to render to
     * RETURNS:
     *  void
    *****************************************/
    public void render(SKFWindow target)
    {
        /* loop through all layers and entities */
        for(int i = 0; i < MAXLAYERS; i++)
        {
            for(int j = 0; j < MAXENTITIES; j++)
            {
                /* check for not null */
                if(entityStack[i][j] != null)
                {
                    /* create temp entity */
                    SKEntity tempEntity = entityStack[i][j];

                    /* get variables */
                    int renderFlags = tempEntity.renderFlags;
                    SKFVector ePos  = tempEntity.position;
                    SKFRenderSet rSet = tempEntity.renderSet;
                    SKFTexture texture = tempEntity.texture;
                    int texX = tempEntity.textureX;
                    int texY = tempEntity.textureY;

                    /* switch render based on renderflags */
                    switch (renderFlags) 
                    {
                        case SKEntity.ENTITY_RENDERSET:
                            /* draw renderset and check for null */
                            if(rSet != null)
                            {
                                target.drawRenderSet((int)ePos.x, (int)ePos.y, rSet);
                            }
                            else
                            {
                                System.err.println("Improperly set RenderSet!");
                            }

                            /* end */
                            break;

                        case SKEntity.ENTITY_TEXTURE:
                            /* draw texture and check for null */
                            if(texture != null)
                            {
                                target.drawTexture((int)ePos.x, (int)ePos.y,
                                 texX, texY, texture);
                            }
                            else
                            {
                                System.err.println("Improperly set Texture!");
                            }

                            /* end */
                            break;

                        case SKEntity.ENTITY_NOTINIT:
                            /* log err */
                            System.err.println("ENTITY IMPROPERLY INIT!");
                            System.err.printf("ENTITY AT LAYER: %d, INDEX: %d\n", i, j);

                            /* end */
                            break;
                    }
                }
            }
        }
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
        for(int i = 0; i < MAXLAYERS; i++)
        {
            for(int j = 0; j < MAXENTITIES; j++)
            {
                if(entityStack[i][j] != null)
                {
                    /* increment tick count */
                    ticksPassed++;
    
                    /* if debug mode */
                    if(deBugmode)
                    {
                        System.out.printf("----------------------------------------\n");
                        System.out.printf("CURRENT ENTITY: [%d]\n", j);
                        System.out.printf("CURRENT LAYER:  [%d]\n", i);
                    }
    
                    /* create temp entity object */
                    SKEntity tempEntity = entityStack[i][j];
    
                    /* check if it's entity's first update */
                    if(tempEntity.isOnStart)
                    {
                        /* debug */
                        if(deBugmode)
                        {
                            System.out.printf("Entity start!\n");
                        }
    
                        /* exec */
                        tempEntity.onStart();
                        tempEntity.isOnStart = false;
                    }

                    /* UPDATE TEMP ENTITY */
                    tempEntity.update( );
    
                    /* update positon */
                    tempEntity.position.add(tempEntity.velocity);
    
                    /* update velocity */
                    if(tempEntity.velocity.getMagnitude() > 0.005)
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
 }