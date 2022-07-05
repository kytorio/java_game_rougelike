package org.nathan.element;

import java.awt.*;

public class Point extends Element{
    private final int point;

    public Point(int point) {
        setX(0);
        setY(0);
        setW(1350);
        setH(900);
        this.point = point;
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("微软雅黑", Font.BOLD, 30));
        g.drawString(String.format("您的得分：%d", point), 500, 420);
    }
}
