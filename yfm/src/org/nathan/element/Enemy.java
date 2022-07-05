package org.nathan.element;

import org.nathan.manager.Loader;

import java.awt.*;

public class Enemy extends Element {
    // 当前方向, 默认为up
    private Direction direction;
    private final int speed = 10;

    private int hp = 1;

    @Override
    public Element build(String data) {
        String[] coordinate = data.split(",");
        setX(Integer.parseInt(coordinate[0]));
        setY(Integer.parseInt(coordinate[1]));
        direction = Direction.valueOf(coordinate[2]);
        setH(50);
        setW(50);
        return this;
    }

    @Override
    public void render(Graphics g) {
        if (getIcon() != null)
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

    @Override
    protected void onMove() {
        super.onMove();
        if (getX() > 1350 - getW() || getY() > 900 - getH() || getX() < 0 || getY() < 0)
            rollback();

        switch (direction) {
            case RIGHT:
                setX(getX() + speed);
                break;
            case LEFT:
                setX(getX() - speed);
                break;
            case DOWN:
                setY(getY() + speed);
                break;
            case UP:
                setY(getY() - speed);
                break;
        }
    }

    @Override
    protected void onChangeIcon() {
        super.onChangeIcon();
        setIcon(Loader.enemies.get(direction));
    }

    @Override
    public void rollback() {
        super.rollback();
        switch (direction) {
            case RIGHT:
                setX(Math.min(getX() - speed, 1350 - getW()));
                direction = Direction.LEFT;
                break;
            case LEFT:
                setX(Math.max(getX() + speed, 0));
                direction = Direction.RIGHT;
                break;
            case DOWN:
                setY(Math.min(getY() - speed, 900 - getH()));
                direction = Direction.UP;
                break;
            case UP:
                setY(Math.max(getY() + speed, 0));
                direction = Direction.DOWN;
                break;
        }
    }
}
