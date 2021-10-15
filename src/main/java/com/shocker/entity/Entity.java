package com.shocker.entity;

import java.awt.*;

import com.shocker.common.models.Dimension;
import com.shocker.common.models.Position;

public abstract class Entity extends Component {
    public Position position;
    public Dimension dimension;

    public Entity(Position position, Dimension dimension) {
        this.position = position;
        this.dimension = dimension;

        setBackground(Color.BLUE);
        setVisible(true);
    }
}
