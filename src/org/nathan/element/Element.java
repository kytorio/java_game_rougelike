package org.nathan.element;

import javax.swing.*;
import java.awt.*;

public abstract class Element {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    public Element() {
    }

    public Element(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    public abstract void render(Graphics g);

    /**
     * @param status true -- press, false -- release
     * @param code The code of the key
     * */
    public void onKey(boolean status, int code) {

    }

    protected void onMove() {

    }

    /**
     * @设计模式 模板模式: 在模板模式中定义对象执行方法的先后顺序, 由子类选择性重写方法
     * */
    public final void onTick() {
        onChangeIcon();
        onMove();
        onShoot();
    }

    protected void onShoot() {

    }

    protected void onChangeIcon() {

    }

    // VO(视图对象) 必须 定义 Getters & Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
