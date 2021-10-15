package com.shocker.entity.player;

import com.shocker.common.models.Dimension;
import com.shocker.common.models.Position;
import com.shocker.entity.Entity;

public class BasicPlayer extends Entity implements Player {
    public BasicPlayer(Position position, Dimension dimension) {
        super(position, dimension);
    }
}
