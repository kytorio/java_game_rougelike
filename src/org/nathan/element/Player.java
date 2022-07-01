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
    private int keyDown = 0;

    // 当前方向, 默认为up
    private Direction direction = Direction.UP;

    private boolean shooting = false;

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
                    keyDown++;
                    direction = Direction.LEFT;
                    break;
                case 38:  // up
                    keyDown++;
                    direction = Direction.UP;
                    break;
                case 39:  // right
                    keyDown++;
                    direction = Direction.RIGHT;
                    break;
                case 40:  // down
                    keyDown++;
                    direction = Direction.DOWN;
                    break;
                case 32:
                    shooting = true;
                    break;
            }
        } else {
            switch (code) {
                case 37:  // left
                case 38:  // up
                case 39:  // right
                case 40:  // down
                    keyDown--;
                    break;
                case 32:
                    shooting = false;
                    break;
            }
        }
    }

    @Override
    protected void onChangeIcon() {
        super.onChangeIcon();
        setIcon(Loader.players.get(direction));
    }

    @Override
    public void onMove() {
        super.onMove();

        if (keyDown == 0) {
            return;
        }

        switch (direction) {
            case RIGHT:
                setX(Math.min(getX() + 10, 900 - getW()));
                break;
            case LEFT:
                setX(Math.max(getX() - 10, 0));
                break;
            case DOWN:
                setY(Math.min(getY() + 10, 600 - getH()));
                break;
            case UP:
                setY(Math.max(getY() - 10, 0));
                break;
        }
    }

}
