DATE: 2021-10-18
DEV:  Bailey Jia-Tao Brown

CONTENT:
    - Intro
    - What is SKF?
    - SKFWindow
    - SKFVector
    - SKFRenderSet
    - SKFTexture
    - Closing statement

TEXT:

INTRO:
 Hey! Yes! Please read this before modifying any SKF files!
 All SKF files were originally made by me, and this text file
 will go through how to use my SKF classes, and other things

WHAT IS SKF?
 SKF stands for ShoKerFramework, great name, I know. The
 purpose of SKF is to abstract rendering and window handling
 so that we can better get things done! AWT is a reeeal pain
 in the ass, I'll tell you that much, and I've gone and wrapped
 it so that it's easy and accesible to use.

WHAT IS SKFWINDOW?
 The SKFWindow class is a non-inheritable window class for you
 to render on to. In AWT, the window and the renderer class are
 seperate, but I've combined them into one thing.
 The window uses double buffering, as to remove a "flashing"
 effect when clearing and redrawing to the screen. I suggest using
 the following rendering logic pipeline:

    - Create a window:
        SKFWindow win = new SKFWindow(dimX, dimY);

    - Clear backbuffer:
        win.clearBuffer( );

    - Render to backbuffer:
        win.drawLine(args...);
        win.drawRect(args...);
        win.drawPolygon(args...);
        win.drawTexture(args...);
        win.drawRenderSet(args...);

    - Present backbuffer:
        win.flipBuffer( );

 There are two special things you can draw, the SKFRenderSet, and
 the SKF texture. Not that I mention those are special, as we will
 be going over how to use them later.

WHAT IS SKFVECTOR?
 The SKFVector class is just a simple positioning class, which can
 be used for all sorts of purposes, primarily rendering.
 The SKFVector class is just like the Position class by Christian
 (if it is still in the source files at the time you read this)
 There are several things you can do with the SKFVector:

    - Create
        SKFVector vec = new SKFVector( ); /* inits both dims to 0 */
        SKFVector vec = new SKFVector(x, y);

    - Set without creating new object
        vec.set(x, y);

    - Add (overloaded)
        vec.add(new SKFVector(x, y));
        vec.add(x, y);

    - Scale
        vec.scale(f); /* scales both X and Y by given amount */
    
    - Get magnitude
        float mag = vec.getMagnitude( );

    - Normalize (this function is kinda broken)
        vec.normalize( ); /* both x and y will add up to 1 */

WHAT IS SKFTEXTURE?
 Okay, so Texture objects in AWT are a REAAAL pain to deal with
 and you are better off just not looking into it. Traditionally,
 textures are a 3D array of unsigned bytes, holding RGBA values.
 However, because java has no unsigned byte support, i've made 
 the SKFTexture object a 3D array of ints, just for convenience.
 PLEASE NOTE THAT SKFTEXTURES ARE VERY RENDER-INNEFICIENT, and
 use them sparringly. Textures are complicated, and to document
 the whole thing is a real pain, but i'll just you a run through
 of all the functions so you know generally how to use it.

    - ctor( ); /* inits all pixels to (0, 0, 0, 0) */
    - ctor(r, g, b); /* sets all pixels to opaque param color */
    - ctor(r, g, b, a); /* all pixels are param color */
    - ctor([][][] data); /* directly read pixel data */
    - setPixel(x, y, color); /* sets pixel color to given color */
    - setPixels(x[], y[], color); /* sets pixels to color */
    - getColor(x, y); /* returns color at given position */

WHAT IS SKFRENDERSET?
 If you've properly read this documentation, you'll notice that
 you can make render calls to the window. A SKFRenderSet is
 simply a stack of rendercalls which you can group together and
 draw all at once to the window. You can also collectively offset
 all rendercalls' positions, making it perfect for sort of a "sprite"
 entity. Here are some important functions from the class:

    - addRect(args...); /* adds rect call to the stack */
    - addOval(args...); /* adds oval call to the stack */
    - addPolygon(args...); /* add polygon */
    - addTexture(args...); /* add texture */

ANY QUESTIONS?
 You can contact me at:
 bailey.jt.brown@gmail.com
