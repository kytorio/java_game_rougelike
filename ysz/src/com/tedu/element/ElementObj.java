package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * @说明 所有元素的基类
 */
public abstract class ElementObj {
    // 坐标
    private int x;
    private int y;
    // 宽高
    private int w;
    private int h;
    // 图片对象
    private ImageIcon icon;
    // 用于表示当前对象的状态
    public enum Status {
        ALIVE,
        DEAD,
        // ...
    }
    private Status currentStatus;

    public ElementObj() {

    }

    /**
     * @说明 由子类传输数据到父类
     * @param x 左上角 x 坐标
     * @param y 左上角 y 坐标
     * @param w 宽
     * @param h 高
     * @param icon 图片
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        super();

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    /**
     * 显示这个元素
     * @param g 画笔
     */
    public abstract void showElement(Graphics g);

    /**
     * 子类重写用于实现键盘监听
     * @param key 键盘扫描码
     */
    public void keyPressed(int key) {

    }

    /**
     * 子类重写用于实现键盘监听
     * @param key 键盘扫描码
     */
    public void keyReleased(int key) {

    }

    /**
     * 控制移动
     */
    protected void move(int width, int height) {

    }

    /**
     * 控制图片更换
     * TODO: 选择更合适的参数
     */
    protected void updateImage() {

    }

    // 可以定义一些通用行为, 暴露给子类重写
    // 在父类中通过 final 方法定义这些行为的执行顺序
    // TODO: 可以将 model 的参数提取为一个 ModelConfig
    /**
     * 通用行为模式参数
     * @param width 游戏场景的宽
     * @param height 游戏场景的高
     */
    public final void model(int width, int height) {
        move(width, height);
    }

    /**
     * 死亡时调用, 用于处理游戏元素死亡时要发生的东西, 如: 死亡动画等
     */
    public void die() {}

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

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }
}
