package org.nathan.element;

import org.nathan.manager.Loader;

import javax.swing.*;
import java.awt.*;

/**
 * @说明 玩家类
 * @问题 1. 图片需要读取到内存中
 *      2. 图片使用什么数据结构进行存储
 *      3. 什么时候修改图片
 * */
public class Player extends Element{
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // 当前方向, 默认为up
    private String direction = "up";

    // Image Collection

    public Player(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

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

    @Override
    public void onKey(boolean status, int code) {
        super.onKey(status, code);
        // TODO 改成两属性
        if (status) {
            switch (code) {
                case 37:  // left
                    left = true;
                    right = false;
                    up = false;
                    down = false;
                    direction = "left";
                    break;
                case 38:  // up
                    up = true;
                    down = false;
                    left = false;
                    right = false;
                    direction = "up";
                    break;
                case 39:  // right
                    right = true;
                    left = false;
                    up = false;
                    down = false;
                    direction = "right";
                    break;
                case 40:  // down
                    down = true;
                    up = false;
                    left = false;
                    right = false;
                    direction = "down";
                    break;
            }
        } else {
            switch (code) {
                case 37:  // left
                    left = false;
                    break;
                case 38:  // up
                    up = false;
                    break;
                case 39:  // right
                    right = false;
                    break;
                case 40:  // down
                    down = false;
                    break;
            }
        }
    }

    @Override
    protected void onChangeIcon() {
        super.onChangeIcon();
        setIcon(Loader.images.get(direction));
    }

    @Override
    public void onMove() {
        super.onMove();
        if (left) {
            setX(Math.max(getX() - 10, 0));
        }
        if (right) {
            setX(Math.min(getX() + 10, 900 - getW()));
        }
        if (up) {
            setY(Math.max(getY() - 10, 0));
        }
        if (down) {
            setY(Math.min(getY() + 10, 600 - getH()));
        }
    }

}
