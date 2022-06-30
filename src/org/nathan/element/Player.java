package org.nathan.element;

import javax.swing.*;
import java.awt.*;

public class Player extends Element{
    @Override
    public void render(Graphics g) {
        g.drawImage(
                getIcon().getImage(),
                getX(),
                getY(),
                getW(),
                getH(),
                null
        );
    }

    public Player(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }
}
