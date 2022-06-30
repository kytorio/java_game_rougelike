package org.nathan.game;

import org.nathan.show.GameFrame;

public class Run {
    public static void main(String[] args) {
        GameFrame f = new GameFrame();

        f.setPanel(new MainPanel());
        f.start();
    }
}
