package com.shocker;

import com.shocker.entity.*;
import com.shocker.SKF.*;
import java.awt.*;

/* main */
public class App {

    public static void main(String[] args) {
        
        /* setup window */
        SKFWindow win = new SKFWindow(500, 500);

        /* setup renderSet */
        SKFRenderSet renSet = new SKFRenderSet( );
        renSet.addOval(0, 0, 50, 50, new Color(255, 0, 0));

        /* create test entity */
        _TestEntity tent = new _TestEntity( );
        tent.init(20, 250, SKEntity.ENTITY_RENDERSET, 2, renSet, null, 0, 0);

        /* set entity params */
        tent.velocity.set(4f, 0.2f);

        /* create entity handler object */
        SKEntityHandler eh = new SKEntityHandler( );

        /* add entity */
        int hndl = eh.addEntity(tent);

        /* update loop */
        while(true)
        {
            /* update velocity */
            SKFVector acceleration = SKFInput.getInputAxis( );
            acceleration.scale(0.01f);
            tent.velocity.add(acceleration);

            /* test thing */
            if(SKFInput.isKeyDown('q'))
            {
                eh.removeEntity(hndl);
            }

            /* UPDATE PHYSICS */
            eh.update( );

            /* clear window */
            win.clearBuffer( );

            /* draw */
            eh.render(win);

            /* flip */
            win.flipBuffer( );
        }
    }
}
