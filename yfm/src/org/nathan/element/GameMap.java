package org.nathan.element;

import javax.swing.*;
import java.awt.*;

public class GameMap extends Element{
    public enum Type {
        BASE, BRICK, GRASS, IRON, RIVER
    }

    private Type[][] map;

    public GameMap(int w, int h, ImageIcon icon) {
        super(0, 0, w, h, icon);
        map = new Type[15][15];
    }



    @Override
    public void render(Graphics g) {

    }
}