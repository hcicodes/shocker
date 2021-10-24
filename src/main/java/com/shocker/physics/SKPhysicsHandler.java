/**********************************************************
 * FILE:
 *  SKPhysicsHandler.java
 * DATE: 
 *  2021-10-20
 *  2021-10-23
 *  2021-10-24
 * DEVS:
 *  Bailey Jia-Tao Brown
 * DESC:
 *  Phyics handler object
 * CONTENTS:
 *  - Package defs
 *  - Imports
 *  - Buffer size defs
 *  - Buffers
 *  - Internal Classes
 *  - Functions
 * 
 *********************************************************/

 package com.shocker.physics; /* package name */

 import java.util.Arrays; /* array sorting function */
 import java.awt.*; /* color class */

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

     /* time defs */
     public static final int DEFAULTPHYSINTERVAL = 7;

     /* physics clamp defs */
     public static final float PUSHFORCEMAX = 3f;

     /* update interval */
     public int updateInterval = DEFAULTPHYSINTERVAL;
     private long lastUpdateTime = System.currentTimeMillis( );
     private long missedUpdates = 0;

     /* buffers */
     public boolean[] aVecUpdateBuff = new boolean[MAXPOBJS];    /* check if avec has been updated */
     public boolean[] overlapBuffer  = new boolean[MAXPOBJS];    /* check if pobj is overlapping */
     public SKEntity[] pObjBuffer    = new SKEntity[MAXPOBJS];   /* pobj array */
     public SKFVector[] vecBuffer    = new SKFVector[MAXPOBJS];  /* a-vector array */
     public SKFVector[] pushBuffer   = new SKFVector[MAXPOBJS];  /* opbj overlap push vector */
     public SKPhysicsGroup[] pGroups = new SKPhysicsGroup[MAXPGROUPS];

     /* pgroup bound threshold */
     public static final int PGROUPTHRESHOLDX = 0x200;
     public static final int PGROUPTHRESHOLDY = 0x200;

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
    * METHOD: getMissedUpdateCount
    * PARAMS:
    *  N/A
    * RETURNS:
    *  long, missed update count
    * ********************************************************/
    public long getMissedUpdateCount( )
    {
        return missedUpdates;
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

               /* init vector objects */
               vecBuffer[i] =  new SKFVector( );
               pushBuffer[i] = new SKFVector( );

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
        pushBuffer[pHndl] = null;

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
       for(int i = 0; i < MAXPGROUPS; i++)
       {
           pGroups[i] = null;
       }
    }

    /* ********************************************************
    * METHOD: findPhysGroup
    * PARAMS:
    *  int x -> object x pos
    *  int y -> object y pos
    *  int w -> object width
    *  int h -> object height
    * RETURNS:
    *  int[][], pgroup position, index (0,0) holds phys group
    * count.
    * ********************************************************/
    public int[][] findPhysGroup(int x, int y, int w, int h)
    {
        int[][] pVec = new int[5][2];
        int vecCount = 1;
        
        /* get max and min vertexes of bounding box */
        int groupXmin = x / PGROUPTHRESHOLDX;
        int groupYmin = y / PGROUPTHRESHOLDY;
        int groupXmax = (x + w) / PGROUPTHRESHOLDX;
        int groupYmax = (y + h) / PGROUPTHRESHOLDY;

        /* get booleans for quick comparison */
        boolean xDiff = groupXmax != groupXmin;
        boolean yDiff = groupYmax != groupYmin;

        /* add and avoid repeats */
        pVec[vecCount][0] = groupXmin;
        pVec[vecCount][1] = groupYmin;
        vecCount++;

        /* this is such a damn hack job, but it works */
        if(xDiff)
        {
            pVec[vecCount][0] = groupXmax;
            pVec[vecCount][1] = groupYmin;
            vecCount++;
        }
        if(yDiff)
        {
            pVec[vecCount][0] = groupXmin;
            pVec[vecCount][1] = groupYmax;
            vecCount++;
        }
        if(xDiff && yDiff)
        {
            pVec[vecCount][0] = groupXmax;
            pVec[vecCount][1] = groupYmax;
            vecCount++;
        }

        /* update array read size */
        pVec[0][0] = vecCount - 1;

        /* debug output */
        if(debugMode)
        {
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            System.out.printf("Create pgroups of size: %d\nContents are:\n", vecCount - 1);
            for(int i = 0; i < vecCount - 1; i++)
            {
                System.out.printf("\t(%d, %d)\n", pVec[i + 1][0], pVec[i + 1][1]);
            }
            System.out.printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }

        return pVec;
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

                /* get entity dimensions */
                float cx = scanPObj.position.x - (scanPObj.physProperties.getBoundingBox())[0];
                float cy = scanPObj.position.y - (scanPObj.physProperties.getBoundingBox())[1];
                float cw = (scanPObj.physProperties.getBoundingBox())[2];
                float ch = (scanPObj.physProperties.getBoundingBox())[3];

                /* get pgroup numbers */
                int[][] pNums = findPhysGroup((int)cx, (int)cy, (int)cw, (int)ch);
                
                /* for all pNums */
                for(int k = 0; k < pNums[0][0]; k++)
                {
                    int pNumX = pNums[k + 1][0];
                    int pNumY = pNums[k + 1][1];

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
                            pGroups[j].addIndex(i);
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

                } /* END PNUM LOOP */
                

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
    * METHOD: canUpdate
    * PARAMS:
    *  N/A
    * RETURNS:
    *  boolean
    * ********************************************************/
    public boolean canUpdate( )
    {
        return System.currentTimeMillis( ) < lastUpdateTime + updateInterval;
    }

    /* ********************************************************
    * METHOD: debugDrawPgroupBounds
    * PARAMS:
    *  SKFWindow target -> window to render to
    * RETURNS:
    *  void
    * ********************************************************/
    public void debugDrawPgroupBounds(SKFWindow target)
    {
        int startX = -50 * PGROUPTHRESHOLDX;
        int endX   =  50 * PGROUPTHRESHOLDX;
        int startY = -50 * PGROUPTHRESHOLDY;
        int endY   =  50 * PGROUPTHRESHOLDY;

        /* draw horizontal lines */
        for(int i = -50; i < 50; i++)
        {
            int drawY = i * PGROUPTHRESHOLDY;
            int drawX = i * PGROUPTHRESHOLDX;
            target.drawLine(startX, drawY, endX, drawY, new Color(0, 64, 255));
            target.drawLine(drawX, startY, drawX, endY, new Color(0, 64, 255));
        }
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
        /* make sure the fixed time step */
        /* has passed. if it's been before */
        /* that amount of time, don't execute */
        if(System.currentTimeMillis( ) < lastUpdateTime + updateInterval)
        {
            /* end early and log if debug mode */
            if(debugMode)
            {
                System.out.printf("[Skipped physUpdate at: %d]\n", System.currentTimeMillis( ));
                System.out.printf("[Expected update time:  %d]\n", lastUpdateTime + updateInterval);
            }

            return;
        }
        else
        {
            /* if missed update and debug mode */
            if(System.currentTimeMillis( ) > lastUpdateTime + updateInterval)
            {
                long timeDiff = System.currentTimeMillis( ) - lastUpdateTime + updateInterval;
                long tMissed = timeDiff / updateInterval;

                if(debugMode)
                {
                    System.out.printf("WARNING: [Missed %d updates at: %d]\n", tMissed, System.currentTimeMillis( ));
                }

                missedUpdates += tMissed;

                if(tMissed >= 10)
                {
                    System.err.printf("SEVERE WARNING: <MISSED %d UPDATES> GAME IS LAGGING SEVERELY!\n", tMissed);
                }
            }

            /* update last exec time */
            lastUpdateTime = System.currentTimeMillis( );
        }

        /* firstly, clear all PGroups */
        clearPhysGroups( );

        /* secondly, generate all PGroups */
        generatePhysGroups( );

        /* thirdly, clear overlap buffer */
        /* and push buffer */
        for(int i = 0; i < MAXPOBJS; i++)
        {
            overlapBuffer[i]  = false; /* clear overlap buffer */
            aVecUpdateBuff[i] = false; /* clear avec update buffer */

            /* if !null */
            if(pushBuffer[i] != null)
            {
                pushBuffer[i].set(0, 0);
            }
        }

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

                    /* if avec has not been calculated */
                    if(!aVecUpdateBuff[vBufferIndex])
                    {
                        /* set corresponding vector to scanEnt position */
                        /* and then update by velocity */
                        vecBuffer[vBufferIndex] = scannedEnt.position;
                        vecBuffer[vBufferIndex].add(scannedEnt.velocity);
                        aVecUpdateBuff[vBufferIndex] = true;
                    }
                    

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

                    /* get source entity */
                    SKEntity  sEnt = pObjBuffer[sIndx];

                    /* check all target ents */
                    for(int k = 0; k < sGroup.piSize; k++)
                    {
                        /* AVOID SELF COLLISION AND ALSO CHECKS FOR SAME OBJECT POINTER */
                        if(!(k == j) && !(sEnt == pObjBuffer[sGroup.pBuffIndexes[k]]))
                        {
                            /* get target index and entity */
                            int tIndx = sGroup.pBuffIndexes[k];
                            SKEntity  tEnt = pObjBuffer[tIndx];

                            /* get collider data object */
                            SKPhysicsProperties tPhysData = tEnt.physProperties;
                            SKPhysicsProperties sPhysData = sEnt.physProperties;

                            /* calculate collider data */
                            /* THIS IS A HUUUUGE PAIN IN THE ASS */
                            SKFVector sourcePos = new SKFVector( );
                            SKFVector targetPos = new SKFVector( );
                            SKFVector sourceDim = new SKFVector( );
                            SKFVector targetDim = new SKFVector( );

                            /* grab collider data in array form */
                            float[] srcBox = sPhysData.getBoundingBox( );
                            float[] trgBox = tPhysData.getBoundingBox( );

                            /* account for collider offset to actual position */
                            sourcePos.set(sEnt.position.x + srcBox[0], sEnt.position.y + srcBox[1]);
                            targetPos.set(tEnt.position.x + trgBox[0], tEnt.position.y + trgBox[1]);

                            /* pack dimensions into vector object */
                            sourceDim.set(srcBox[2], srcBox[3]);
                            targetDim.set(trgBox[2], trgBox[3]);

                            /* check overlap */
                            boolean overlap = checkOverlap(sourcePos, targetPos, sourceDim, targetDim);

                            /* debug output */
                            if(overlap && debugMode)
                            {
                                int pGroupCount = 0;
                                for(int itr = 0; itr < MAXPGROUPS; itr++)
                                {
                                    if(pGroups[itr] != null)
                                    {
                                        pGroupCount++;
                                    }
                                }

                                System.out.printf("CURRENT PGROUP: [%d]/[%d]\n", i, pGroupCount);
                                System.out.printf("PHYSICS OVERLAP BETWEEN:\n");
                                System.out.printf("\tPOBJ[%d] at (%f , %f)\n", sIndx,
                                sEnt.position.x, sEnt.position.y);
                                System.out.printf("\tPOBJ[%d] at (%f , %f)\n", tIndx,
                                tEnt.position.x, tEnt.position.y);
                            }

                            /* if overlapping, compare masses */
                            if(overlap)
                            {
                                /* get difference in mass */
                                float dMass = sEnt.physProperties.mass - tEnt.physProperties.mass;

                                /* if target object is heavier and is !static */
                                if(dMass < 0 && !sEnt.physProperties.isStatic)
                                {
                                    /* set push force to heavier object */
                                    /* only if 0 */
                                    if(pushBuffer[sIndx].x == 0 && pushBuffer[sIndx].y == 0)
                                    {
                                        pushBuffer[sIndx].set(tEnt.velocity.x, tEnt.velocity.y);
                                    }

                                    /* calculate dampen amount by: */
                                    /* (tM - sM) / tM */
                                    float tMass = tEnt.physProperties.mass;
                                    float sMass = sEnt.physProperties.mass;
                                    float dampScale = (tMass - sMass) / tMass;

                                    /* dampen */
                                    pushBuffer[sIndx].scale(dampScale);

                                    /* dampen target velocity by (sM / tM) */
                                    pObjBuffer[tIndx].velocity.scale(sMass / tMass);

                                } /* MASS DIFFERENCE CHECK END */
                                
                            } /* OVERLAP CHECK END */

                            /* set overlap buffer value only if false */
                            if(!overlapBuffer[sIndx])
                            {
                                overlapBuffer[sIndx] = overlap;
                            }
                        } /* SELF COLLISION CHECK END */

                    } /* TARGET COMPARISON LOOP END */

                } /* SOURCE COMPARISON LOOP END */

            } /* PGROUP NULL CHECK END */

        } /* LOOP ALL PGROUPS END */

        /* now that we know which pObjs are overlapping, we can operate on them */
        
        /* for all pObjs */
        for(int i = 0; i < MAXPOBJS; i++)
        {
            /* if object is !null */
            if(pObjBuffer[i] != null)
            {
                /* get entity object */
                SKEntity scanE = pObjBuffer[i];

                /* if overlapping */
                if(overlapBuffer[i])
                {
                    /* debug output */
                    if(debugMode)
                    {
                        System.out.printf(">>>Overlap at: %d, rolling back vectors...\n", i);
                        System.out.printf(">>>Veloctiy before: (%f, %f)\n", scanE.velocity.x, scanE.velocity.y);
                    }

                    /* roll back anticipated vector */
                    vecBuffer[i] = scanE.position;

                    /* get negative current veloctiy */
                    SKFVector nVel = new SKFVector( );
                    nVel.set(scanE.velocity.x, scanE.velocity.y);
                    nVel.scale(-1);

                    /* change velocity based on bounciness */
                    scanE.velocity.scale(-scanE.physProperties.bounciness);

                    /* update vector by -velocity */
                    vecBuffer[i].add(nVel);

                    /* update vector buffer to accomadate for bounciness */
                    vecBuffer[i].add(scanE.velocity);

                    /* clamp push force */
                    if(pushBuffer[i].getMagnitude( ) > PUSHFORCEMAX)
                    {
                        pushBuffer[i].normalize( );
                        pushBuffer[i].scale(PUSHFORCEMAX);

                        /* debug log it */
                        if(debugMode)
                        {
                            System.out.printf(">>>Clamped push vector at %d\n", i);
                        }
                    }

                    /* update velocity based on push force */
                    scanE.velocity.add(pushBuffer[i]);

                    /* update a-vec based on push force */
                    vecBuffer[i].add(pushBuffer[i]);

                    /* UPDATE ENTITY POSITION */
                    scanE.position = vecBuffer[i];

                    /* EXECUTE ENTITY UPDATE CALLBACK */
                    scanE.update(System.currentTimeMillis( ));

                    /* debug output contd */
                    if(debugMode)
                    {
                        System.out.printf(">>>Velocity after: (%f, %f)\n", scanE.velocity.x, scanE.velocity.y);
                    }
                }

                else
                {
                    /* DO NOT UPDATE DRAG UPON BOUNCE */
                    /* dampen velocity by 1 - ent drag */
                    scanE.velocity.scale(1.0f - scanE.physProperties.drag);
                }

                
            } /* END POBJ NULL CHECK */

        } /* END POBJ LOOP */
    }
 }