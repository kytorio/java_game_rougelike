package org.nathan.element;

import org.nathan.manager.Loader;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

    Type type;

    // type,x,y
    @Override
    public Element build(String data) {
        String[] components = data.split(",");
        type = Type.valueOf(components[0]);
        setX(Integer.parseInt(components[1]));
        setY(Integer.parseInt(components[2]));
        setW(20);
        setH(20);
        return this;
    }
}
