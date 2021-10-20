DATE: 2021-10-19
DEV: Bailey Jia-Tao Brown

CONTENT:
    - Intro
    - What is an entity?
    - How to create an entity?
    - Entity graphics
    - Entity Handler
    - Closing statement

TEXT:

------------INTRO------------
 If you're looking at this file, and have not reviewed SKFREADME.txt yet, 
 I would highly, highly advise doing so, to familiarize yourself with what
 is going on.

------------WHAT IS AN ENTITY------------
 An entity is like an abstracted graphical object, used to group and manage a
 localized coordinate system and graphical representation of itself, so that it
 may be seen as it's own sort of "being", within the window. However, this is not
 true. An entity is really just an SKFRenderSet with an offset, and several callback
 methods attatched to it so that the EntityHandler may manipulate it so that it
 looks like it's own "being"

------------ENTITY GRAPHICS------------
 An entity can be graphically represented by either a SKFRenderSet or a SKFTexture.
 I couldn't be bothered to overload the entity ctor, so I have all the parameters for
 both a renderset entity and a texture entity, but you can set the flags so that the
 entity only uses one or the other.
 Here's an example:

 /* renderset entity ctor */
 Entity eObj = new SKEntity(x, y, SKEntity.ENTITY_RENDERSET, layer, renderSetObj, null, 0, 0);

 Notice how the last 3 params are null and 0. This is because the last three params ask for
 a texture and it's dimensions, and since we set the 3rd param to ENTITY_RENDERSET, the entity
 handler will know only to check for the renderSetObject that you passed to the entity ctor
 when using it. How about a texture entity?
 Here's an example:

 /* texture entity ctor */
 Entity eObj = new SKEntity(x, y, SKEntity.ENTITY_TEXTURE, layer, null, textureData, tx, ty);

 This time I set the enum to ENTITY_TEXTURE, and the renderSetObject to null, since the entity
 handler will know not to use any rendersets when displaying the entity.

------------ENTITY HANDLER------------

 You will need a way to handle your entities, and luckily I have nicely abstracted that for you.
 Simply create an SKEntityHandler object, and add all of your entities to it.
 Here's an example:

 /* create SKEntityHandler object */
 SKEntityHandler eh = new SKEntityHandler( );

 /* add entity objects */
 int eHndl0 = eh.addEntity(e0);
 int eHndl1 = eh.addEntity(e1);
 int eHndl2 = eh.addEntity(e2);

 When adding an entity to the entity handler object, it will return an integer, which will be a 
 handle to where the entity data is internally stored. If you want to know the nitty-gritty, the
 first 16 bits of the handle represent the position of the entity in the entity stack, and the
 last 16 bits of the handle represent the layer that the entity is in. It is important to hold on
 to this entity handle, as you will need it to remove entities.
 Here's an example:

 /* remove entity */
 eh.removeEntity(eHndl0); /* removes e0 */
 eh.removeEntity(eHndl1); /* removes e1 */
 eh.removeEntity(eHndl2); /* removes e2 */

 To update all entity behaviours and entity physics, do the following:

 /* update all entities */
 eh.update( );

 And finally, to render all entities to the window, simply pass your target window object to the
 entity handler, and it will draw all the entities in their correct positions onto the back buffer.
 Here's an example:

 /* create window object */
 SKFWindow win = new SKFWindow(w, h);

 /* setup entities here.... */

 /* main render loop */
 while(true)
 {
     win.clearBuffer( ); /* clear buffer */

     eh.update( );   /* updates all entities */
     eh.render(win); /* renders to window */
     
     win.flipBuffer( ); /* present render */
 }

 ------------CLOSING STATEMENT------------
 Whew! That was quite a brainfull to cover. There were some things that I did
 not mention, but their effect should be negligble and you should be able to
 figure it out by yourself.
 If you have any further questions you can contact me at:
 bailey.jt.brown@gmail.com
 