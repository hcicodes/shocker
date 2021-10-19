package com.shocker;

import com.shocker.SKF.SKFInput;
import com.shocker.SKF.SKFRenderSet;
import com.shocker.SKF.SKFVector;
import com.shocker.SKF.SKFWindow;
import com.shocker.entity.SKEntity;
import com.shocker.entity.SKEntityHandler;
import java.awt.*;

/* main */
public class App {

    public static void main(String[] args) {
        
        /* setup window */
        SKFWindow win = new SKFWindow(500, 500);

        /* setup texture */
        SKFRenderSet renSet = new SKFRenderSet( );
        renSet.addOval(0, 0, 50, 50, new Color(255, 0, 0));

        /* create test entity */
        _TestEntity tent = new _TestEntity( );
        tent.init(20, 250, SKEntity.ENTITY_RENDERSET, 0, renSet, null, 0, 0);

        /* set entity params */
        tent.velocity.set(4f, 0.2f);
        tent.drag = 0.0125f;

        /* create entity handler object */
        SKEntityHandler eh = new SKEntityHandler( );

        /* add entity */
        eh.addEntity(tent);

        /* update loop */
        while(true)
        {
            /* update velocity */
            SKFVector acceleration = SKFInput.getInputAxis( );
            acceleration.scale(0.01f);
            tent.velocity.add(acceleration);

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
