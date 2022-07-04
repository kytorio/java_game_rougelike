package com.tedu.element;

public class Direction {

    public static final int NONE  = 0x00;
    public static final int UP    = 0x01 << 1;
    public static final int DOWN  = 0x01 << 2;
    public static final int LEFT  = 0x01 << 3;
    public static final int RIGHT = 0x01 << 4;

    public static final int VER_MASK = 0b0011;
    public static final int HOR_MASK = 0b1100;

    /**
     * 垂直方向
     */
    public int ver = NONE;

    /**
     * 水平方向
     */
    public int hor = NONE;

    public int  getDir() {
        return ver | hor;
    }
    public void setDir(int dir) {
        this.ver = dir & VER_MASK;
        this.hor = dir & HOR_MASK;
    }
}
