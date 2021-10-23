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

     /* internal physics group class */
    private final class SKPhysicsGroup
    {
       public int gX = 0; /* group X */
       public int gY = 0; /* group Y */
       /* array of pObjBuffer indexes */
       public int[] pBuffIndexes = new int[MAXPGSIZE];
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
            }
        }
    }

 }