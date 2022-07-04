package org.nathan.element;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends Element{
    private int hp = 1;
    @Override
    public Element build(String data) {
        Random r = new Random();
        int x = r.nextInt(800);
        int y = r.nextInt(500);
        setX(x);
        setY(y);
        setW(50);
        setH(50);
        setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
        return this;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getIcon().getImage(), getX(), getY(), getW(), getH(), null);
    }

    @Override
    public void onShot(int attack) {
        super.onShot(attack);
        hp -= attack;
        if (hp <= 0) {
            setAlive(false);
        }
    }
}
