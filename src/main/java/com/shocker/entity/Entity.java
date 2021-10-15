package com.shocker.entity;

import java.awt.*;

public abstract class Entity extends Component {
    protected Rectangle rect;

    public Entity(Rectangle rect) {
        this.rect = rect;
        setBounds(rect);
        setBackground(Color.BLUE);
        setVisible(true);
    }
}
