package org.nathan.element;

import java.awt.*;

/**
 * @说明 子弹, 由Player调用和创建
 * */
public class Bullet extends Element{
    private int attack = 1;  // 攻击力
    private int speed = 15;
    private Direction direction;
    // ...Type?

    @Override
    public Element build(String data) {
        String[] items = data.split(",");
        for (String item : items) {
            String[] pair = item.split(":");
            switch (pair[0].trim()) {
                case "x":
                    this.setX(Integer.parseInt(pair[1].trim()));
                    break;
                case "y":
                    this.setY(Integer.parseInt(pair[1].trim()));
                    break;
                case "direction":
                    direction = Direction.valueOf(pair[1].trim());
                    break;
            }
        }
        setW(10);
        setH(10);
        return this;
    }

    public Bullet() {}

    public boolean inside() {
        return getX() >= 0 && getX() <= 500 && getY() >= 0 && getY() <= 600;
    }

    @Override
    protected void onMove() {
        super.onMove();

        if (!inside()) {
            setAlive(false);
        }

        if (!isAlive()) {
            return;
        }

        switch (direction) {
            case UP:
                setY(getY() - speed);
                break;
            case DOWN:
                setY(getY() + speed);
                break;
            case LEFT:
                setX(getX() - speed);
                break;
            case RIGHT:
                setX(getX() + speed);
                break;
        }
    }

    public int getAttack() {
        return attack;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(getX(), getY(), getW(), getH());
    }
}
