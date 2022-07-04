package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();

        GameMainJPanel jp = new GameMainJPanel();
        gameFrame.setJPanel(jp);

        GameListener listener = new GameListener();
        gameFrame.setKeyListener(listener);

        GameThread gameMainThread = new GameThread(jp);
        gameFrame.setThread(new Thread(gameMainThread));

        gameFrame.start();
    }
}
