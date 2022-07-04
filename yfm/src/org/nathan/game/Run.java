package org.nathan.game;

import org.nathan.controller.Listener;
import org.nathan.controller.MainThread;
import org.nathan.show.GameFrame;

public class Run {
    public static void main(String[] args) {
        GameFrame f = new GameFrame();

        Listener listener = new Listener();

        f.setPanel(new MainPanel());
        f.setKeyListener(listener);
        f.setThread(new MainThread());
        f.start();
    }
}
