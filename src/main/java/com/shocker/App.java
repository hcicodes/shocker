package com.shocker;

import com.shocker.SKF.*;
import com.shocker.entity.SKEntity;
import com.shocker.entity.SKEntityHandler;
import com.shocker.physics.SKPhysicsHandler;

import java.awt.*;

/* main */
public class App {

    public static void main(String[] args) {

        /* create window object */
        SKFWindow window = new SKFWindow(500, 500);

        /* create rendersets of cube */
        SKFRenderSet renderSet0 = new SKFRenderSet( );
        SKFRenderSet renderSet1 = new SKFRenderSet( );
        renderSet0.addRect(0, 0, 20, 20, new Color(255, 0, 0));
        renderSet1.addRect(0, 0, 20, 20, new Color(0, 255, 64));

        /* create first entity object */
        _TestEntity entity0 = new _TestEntity( );
        entity0.init(250, 250, SKEntity.ENTITY_RENDERSET, 1 , renderSet0, null, 0, 0);
        entity0.physProperties.setBoundingBox(0, 0, 20, 20);

        /* setup entity0 physics properties */
        entity0.physProperties.bounciness = 0.1f;
        entity0.velocity.set(1f, 1f);
        entity0.physProperties.drag = 0.01f;
        entity0.physProperties.mass = 2;

        /* create second entity object */
        _TestEntity entity1 = new _TestEntity( );
        entity1.init(400, 400, SKEntity.ENTITY_RENDERSET, 1, renderSet1, null, 0, 0);
        entity1.physProperties.setBoundingBox(0, 0, 20, 20);
        entity1.physProperties.bounciness = 0.1f;
        entity1.velocity.set(-1f, -1f);
        entity1.physProperties.drag = 0.01f;
        entity1.physProperties.mass = 20;

        
        /* create entity handler and add entities */
        SKEntityHandler entityHandler = new SKEntityHandler( );
        entityHandler.addEntity(entity0);
        entityHandler.addEntity(entity1);

        /* create physics handler */
        SKPhysicsHandler pHandler = new SKPhysicsHandler( );

        pHandler.addPhysObject(entity0);
        pHandler.addPhysObject(entity1);
        pHandler.updateInterval = 10;
        pHandler.debugMode = true;


        /* main render loop */
        while(true)
        {
            window.cameraPosition = new SKFVector(entity0.position.x - 250, entity0.position.y - 250);
            window.clearBuffer( );

            pHandler.debugDrawPgroupBounds(window);

            window.drawOval(500, 500, 10, 10, new Color(128, 128, 0));

            if(pHandler.canUpdate())
            {
                SKFVector iVec = SKFInput.getInputAxis( );
                iVec.scale(0.01f);
                entity0.velocity.add(iVec);
            }
            if(SKFInput.isKeyDown('q'))
            {
                System.exit(0);
            }

            pHandler.physicsUpdate( );
            entityHandler.render(window);

            if(SKFInput.isKeyDown('q'))
            {
                pHandler.removePhysObject(1);
            }

            window.flipBuffer( );
        }


    }
}
