package org.nathan.element;

import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;
import org.nathan.manager.Loader;

import java.awt.*;

/**
 * @说明 玩家类
 * @问题 1. 图片需要读取到内存中
 *      2. 图片使用什么数据结构进行存储
 *      3. 什么时候修改图片
 * */
public class Player extends Element{
    private static final long interval = 5;
    private int keyDown = 0;

    // 当前方向, 默认为up
    private Direction direction = Direction.UP;

    private final int speed = 10;

    private boolean shooting = false;

    // Image Collection

    public Player() {
        setIcon(Loader.players.get(direction));
    }

    @Override
    public Element build(String data) {
        String[] coordinate = data.split(",");
        setX(Integer.parseInt(coordinate[0]));
        setY(Integer.parseInt(coordinate[1]));
        setH(50);
        setW(50);
        return this;
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

    private long lastShoot = 0;

    /**
     * 需要发射位置、方向、子弹类型？
     * */
    @Override
    protected void onShoot(long time) {
        super.onShoot(time);
        if (shooting && time - lastShoot >= interval) {
            lastShoot = time;
            // Bullet Generate Format  {x: ..., y: ..., direction: "..."}
            Element bullet = new Bullet().build(getBulletString());
            // Load to Collection.
            ElementManager.getInstance().addElements(ElementType.BULLET, bullet);
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
                setX(Math.min(getX() + speed, 1350 - getW()));
                break;
            case LEFT:
                setX(Math.max(getX() - speed, 0));
                break;
            case DOWN:
                setY(Math.min(getY() + speed, 900 - getH()));
                break;
            case UP:
                setY(Math.max(getY() - speed, 0));
                break;
        }
    }

    public String getBulletString() {
        int x = getX(), y = getY();
        switch (direction) {
            case DOWN:
                y += 50;
            case UP:
                x += 20;
                break;
            case RIGHT:
                x += 50;
            case LEFT:
                y += 20;
                break;
        }

        return String.format("x: %d, y: %d, direction: %s", x, y, direction);
    }

    @Override
    public void rollback() {
        super.rollback();
        switch (direction) {
            case RIGHT:
                setX(Math.min(getX() - speed, 1350 - getW()));
                break;
            case LEFT:
                setX(Math.max(getX() + speed, 0));
                break;
            case DOWN:
                setY(Math.min(getY() - speed, 900 - getH()));
                break;
            case UP:
                setY(Math.max(getY() + speed, 0));
                break;
        }
    }
}
