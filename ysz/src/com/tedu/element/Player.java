package com.tedu.element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends ElementObj {
    // 可以用 Rectangle 做碰撞检测
    private enum VerDir {
        UP,
        DOWN,
        None,
    }

    private enum HorDir {
        LEFT,
        RIGHT,
        None,
    }

     VerDir verDir = VerDir.None;
     HorDir horDir = HorDir.None;

    private final int SPEED = 2;

    public Player(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    @Override
    public void keyPressed(int key) {
        switch (key) {
            // 上
            case KeyEvent.VK_UP    -> this.verDir = VerDir.UP;
            // 下
            case KeyEvent.VK_DOWN  -> this.verDir = VerDir.DOWN;
            // 左
            case KeyEvent.VK_LEFT  -> this.horDir = HorDir.LEFT;
            // 右
            case KeyEvent.VK_RIGHT -> this.horDir = HorDir.RIGHT;
        }
    }

    @Override
    public void keyReleased(int key) {
        switch (key) {
            // 垂直方向
            case 87 :
            case 83 :
                this.verDir = VerDir.None;
                break;
            // 水平方向
            case 65 :
            case 68 :
                this.horDir = HorDir.None;
                break;
            default:
                break;
        }
    }

    @Override
    protected void move(int width, int height) {
        switch (verDir) {
            case UP -> {
                if (this.getY() > 0) {
                    this.setY(this.getY() - SPEED);
                }
            }
            case DOWN -> {
                if (this.getY() < height - this.getH()) {
                    this.setY(this.getY() + SPEED);
                }
            }
            case None -> { /* Do Noting */ }
        }

        switch (horDir) {
            case LEFT -> {
                if (this.getX() > 0) {
                    this.setX(this.getX() - SPEED);
                }
            }
            case RIGHT -> {
                if (this.getX() < width - this.getW()) {
                    this.setX(this.getX() + SPEED);
                }
            }
            case None -> { /* Do Noting */ }
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(),
                null);
    }
}
