package com.shocker.entity.enemy;

import com.shocker.common.models.Dimension;
import com.shocker.common.models.Position;
import com.shocker.entity.Entity;
import com.shocker.entity.player.Player;

public class BasicEnemy extends Entity implements Enemy {
    public BasicEnemy(Position position, Dimension dimension) {
        super(position, dimension);
    }

    public void attack(Player p) {

    }
}
