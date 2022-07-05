package org.nathan.element;

import org.nathan.manager.Loader;

import javax.swing.*;
import java.awt.*;

public class MapItem extends Element {
    public enum Type {
        BASE, BRICK, GRASS, IRON, RIVER
    }
    @Override
    public void render(Graphics g) {
        ImageIcon icon = Loader.blocks.get(type);
        g.drawImage(icon.getImage(), getX(), getY(), getW(), getH(), null);
    }

    public MapItem() {
    }

    private Type type;

    public Type getType() {
        return type;
    }

    // type,x,y
    @Override
    public Element build(String data) {
        String[] components = data.split(",");
        type = Type.valueOf(components[0]);
        setX((int) (Integer.parseInt(components[1]) * 1.5));
        setY((int) (Integer.parseInt(components[2]) * 1.5));
        setW(30);
        setH(30);
        return this;
    }
}
