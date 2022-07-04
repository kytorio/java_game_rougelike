package com.tedu.element;

import java.awt.*;

public class Role extends ElementObj {

    private final int SPEED = 2;
    private Direction moveDir;
    private Direction faceDir;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(),
                null);
    }

    public Direction getMoveDir() {
        return moveDir;
    }

    public void setMoveDir(int moveDir) {
        this.moveDir.setDir(moveDir);
    }

    public Direction getFaceDir() {
        return faceDir;
    }
}
