package com.shocker;

import com.shocker.entity.SKEntityHandler;

/* main */
public class App {

    public static void main(String[] args) {

        /* create test entity */
        _TestEntity tent = new _TestEntity( );

        /* set entity params */
        tent.velocity.set(0.5f, 0.1f);
        tent.drag = 0.3f;

        /* create entity handler object */
        SKEntityHandler eh = new SKEntityHandler( );

        /* enable debug mode */
        eh.deBugmode = true;

        /* add entity */
        eh.addEntity(tent);

        /* update loop */
        for(int i = 0; i < 20; i++)
        {
            eh.update( );
        }
    }
}
