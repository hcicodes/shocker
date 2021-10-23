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

 import java.lang.reflect.Array;
import java.util.Arrays;

import com.shocker.SKF.*;    /* all framework classes */
 import com.shocker.entity.*; /* all entity classes    */

 public final class SKPhysicsHandler
 {
     /* debug flag */
     public boolean debugMode = false;
     
     /* buffer size defs */
     public static final int MAXPOBJS   = 0x80;
     public static final int MAXPGROUPS = 0x30;
     public static final int MAXPGSIZE  = 0x40;

     /* buffers */
     public SKEntity[] pObjBuffer    = new SKEntity[MAXPOBJS];
     public SKFVector[] vecBuffer    = new SKFVector[MAXPOBJS];
     public SKPhysicsGroup[] pGroups = new SKPhysicsGroup[MAXPGROUPS];

     /* pgroup bound threshold */
     public static final int PGROUPTHRESHOLDX = 500;
     public static final int PGROUPTHRESHOLDY = 500;

     /* internal physics group class */
    private final class SKPhysicsGroup
    {
        public int gX = 0; /* group X */
        public int gY = 0; /* group Y */
        /* array of pObjBuffer indexes */
        int piSize = 0;
        public int[] pBuffIndexes = new int[MAXPGSIZE];

        /* ctor */
        public SKPhysicsGroup(int gx, int gy)
        {
            gX = gx;
            gY = gy;
        }

        /* add index */
        public void addIndex(int indx)
        {
            pBuffIndexes[piSize] = indx;
            piSize++;
        }
    }

    /* ********************************************************
    * METHOD: addPhysObject
    * PARAMS:
    *  SKEntity pObj -> entity to add
    * RETURNS:
    *  int, entity handle, -1 for fail
    * ********************************************************/
    public int addPhysObject(SKEntity pObj)
    {
        /* search for free spot and insert */
        for(int i = 0; i < MAXPOBJS; i++)
        {
            /* find empty */
            if(pObjBuffer[i] == null)
            {
                /* set buffer object */
               pObjBuffer[i] = pObj;

               /* init vector object */
               vecBuffer[i] = new SKFVector( );

               /* end */
               if(debugMode)
               {
                   System.out.printf("PObj added at: %d\n", i);
               }
               return i;
            }
        }

        /* if loop break, no empty spot */
        System.err.printf("PhysHandler err! No free ent spaces!\n");
        return -1;
    }

    /* ********************************************************
    * METHOD: removePhysObject
    * PARAMS:
    *  int pHndl -> pObj handle
    * RETURNS:
    *  void
    * ********************************************************/
    public void removePhysObject(int pHndl)
    {
        pObjBuffer[pHndl] = null;
        vecBuffer[pHndl]  = null;

        if(debugMode)
        {
            System.out.printf("PObj removed at: %d\n", pHndl);
        }
    }

    /* ********************************************************
    * METHOD: clearPhysGroups
    * PARAMS:
    *  N/A
    * RETURNS:
    *  void
    * ********************************************************/
    public void clearPhysGroups( )
    {
       /* sets all pgroup objects to NULL */
       for(int i = 0; i < MAXPGSIZE; i++)
       {
           pGroups[i] = null;
       }
    }

    /* ********************************************************
    * METHOD: generatePhysGroups
    * PARAMS:
    *  N/A
    * RETURNS:
    *  void
    * ********************************************************/
    public void generatePhysGroups( )
    {
        /* for every Pobj */
        for(int i = 0; i < MAXPOBJS; i++)
        {
            if(pObjBuffer[i] != null)
            {
                SKEntity scanPObj = pObjBuffer[i]; /* actively scanned PObj */

                /* get pGroup number */
                int pNumX = (int)scanPObj.position.x / PGROUPTHRESHOLDX;
                int pNumY = (int)scanPObj.position.y / PGROUPTHRESHOLDY;

                boolean foundGroup = false;

                /* for every PGroup */
                for(int j = 0; j < MAXPGROUPS; j++)
                {
                    /* if pGroup is active */
                    /* and pNums are matching */
                    if(pGroups[j] != null &&
                       pGroups[j].gX == pNumX &&
                       pGroups[j].gY == pNumY)
                    {
                        /* add and break for loop */
                        pGroups[i].addIndex(i);
                        foundGroup = true;
                        break;
                    }
                }

                /* when exit loop, check if group found */
                /* if not, create group */
                if(!foundGroup)
                {
                    /* loop thru pGroups to find empty, create and add */
                    for(int j = 0; j < MAXPGROUPS; j++)
                    {
                        if(pGroups[j] == null)
                        {
                            /* create group, add and break */
                            pGroups[j] = new SKPhysicsGroup(pNumX, pNumY);
                            pGroups[j].addIndex(i);
                            break;
                        }
                    }
                } /* END PGROUP CREATION LOOP */
            } /* END POBJ NULL CHECK */
        } /* END POBJ CHECK LOOP */

        /* pgroups are now all sorted */
        /* if debug, log all pgroup values */
        if(debugMode)
        {
            System.out.printf("----------------------------------------\n");
            System.out.printf("LOGGING ALL PGROUP VALS:\n");

            /* loop all pgroups, log if !null */
            for(int i = 0; i < MAXPGROUPS; i++)
            {
                if(pGroups[i] != null)
                {
                    System.out.printf("PGROUP[%d] vals:\n", i);
                    System.out.printf("\tGroup Val: (%d, %d)\n", pGroups[i].gX, pGroups[i].gY);
                    System.out.printf("\tIList: [ ");
                    for(int j = 0; j < pGroups[i].piSize; j++)
                    {
                        System.out.printf("%d,", pGroups[i].pBuffIndexes[j]);
                    }
                    System.out.printf("]\n");
                }
            }

            /* log segment end */
            System.out.printf("----------------------------------------\n");
        }
    }

    /* ********************************************************
    * METHOD: checkOverlap
    * PARAMS:
    *   SKFVector sPos -> position of source
    *   SKFVector tPos -> position of target
    *   SKFVector sDims -> source dimensions
    *   SKFVector tDims -> target dimensions
    * RETURNS:
    *  boolean, true for overlap, false for not
    * ********************************************************/
    public boolean checkOverlap(SKFVector sPos, SKFVector tPos, 
    SKFVector sDims, SKFVector tDims)
    {
        boolean overlapX = false;
        boolean overlapY = false;

        /* get maxmost and minmost x values */
        /* this is such a a terrible hack but */
        /* it works fast */
        float[] xVals = {sPos.x, sPos.x + sDims.x, tPos.x, tPos.x + tDims.x };
        Arrays.sort(xVals);
        float xMin = xVals[0];
        float xMax = xVals[3];

        /* this makes sense if you check out the following website: */
        /* https://tinyurl.com/h58z28np */
        overlapX = (sDims.x + tDims.x > xMax - xMin);

        /* get max and min y values, compare overlap */
        float[] yVals = {sPos.y, sPos.y + sDims.y, tPos.y, tPos.y + tDims.y };
        Arrays.sort(yVals);
        float yMin = yVals[0];
        float yMax = yVals[3];

        overlapY = (sDims.y + tDims.y > yMax - yMin);

        /* if both true, return true */
        return overlapX && overlapY;
    }

    /* ********************************************************
    * METHOD: physicsUpdate
    * PARAMS:
    *  N/A
    * RETURNS:
    *  void
    * ********************************************************/
    public void physicsUpdate( )
    {
        /* firstly, clear all PGroups */
        clearPhysGroups( );

        /* secondly, generate all PGroups */
        generatePhysGroups( );

        /* for all PGroups */
        for(int i = 0; i < MAXPGROUPS; i++)
        {
            if(pGroups[i] != null)
            {
                /* actively scanned object */
                SKPhysicsGroup sGroup = pGroups[i];

                /* generate all anticipated vectors */
                for(int j = 0; j < sGroup.piSize; j++)
                {
                    /* get corresponding vector index to the */
                    /* scanned pBuffer index */
                    /* this may seem a little confusing but i promise */
                    /* if you review all the code before this it will */
                    /* make sense */
                    int vBufferIndex = sGroup.pBuffIndexes[j];

                    /* get entity object */
                    /* remember, vBuff[i] corresponds to pOBuff[i]*/
                    SKEntity scannedEnt = pObjBuffer[vBufferIndex];

                    /* set corresponding vector to scanEnt position */
                    /* and then update by velocity */
                    vecBuffer[vBufferIndex] = scannedEnt.position;
                    vecBuffer[vBufferIndex].add(scannedEnt.velocity);

                } /* COMPLETED GENERATING A-VECTORS OF ALL POBJS IN PGROUP */

                /* check for collisions */
                /* for each object, check all other objects and see if */
                /* the bounding boxes overlap. however, instead of using */
                /* their current position, comapare using the anticipated */
                /* vector/position */
                for(int j = 0; j < sGroup.piSize; j++)
                {
                    /* here's the naming scheme: i will compare */
                    /* a source entity with a target entity */

                    /* get source indx */
                    int sIndx = sGroup.pBuffIndexes[j];

                    /* get source entity and source vector */
                    SKEntity  sEnt = pObjBuffer[sIndx];
                    SKFVector sVec = vecBuffer[sIndx];

                    /* check all target ents */
                    for(int k = 0; k < sGroup.piSize; k++)
                    {
                        if(!(k == j)) /* AVOID SELF COLLISION */
                        {
                            /* get target index, entity and vector */
                            int tIndx = sGroup.pBuffIndexes[k];
                            SKEntity  tEnt = pObjBuffer[tIndx];
                            SKFVector tVec = vecBuffer[tIndx];

                            /* get collider data object */
                            SKPhysicsProperties tPhysData = tEnt.physProperties;
                            SKPhysicsProperties sPhysData = sEnt.physProperties;
                        }

                    } /* TARGET COMPARISON LOOP END */

                } /* SOURCE COMPARISON LOOP END */

            } /* PGROUP NULL CHECK */

        } /* LOOP ALL PGROUPS */
    }

 }