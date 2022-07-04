package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * 游戏的主线程 <br/>
 * 用于控制游戏加载, 游戏关卡, 游戏运行时自动化,
 * 游戏判定, 游戏地图切换, 资源释放, 资源读取等
 */
public class GameThread implements Runnable {
    private final ElementManager em;
    private JPanel gamePanel = null;
    public GameThread(JPanel panel) {
        this.em = ElementManager.getManager();
        this.gamePanel = panel;
    }

    @Override
    public void run() {
        gameLoad();

        gameRun();

        gameOver();
    }

    /**
     * 游戏加载
     */
    private void gameLoad() {
        load();
    }

    /**
     * 游戏进行时
     */
    private void gameRun() {
        boolean flag = true;
        while (flag) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            for (GameElement ele: GameElement.values()) {
                List<ElementObj> list = all.get(ele);
                for (int i = 0; i < list.size(); i++) {
                    ElementObj obj = list.get(i);
                    if (obj.getCurrentStatus().equals(ElementObj.Status.DEAD)) {
                        obj.die();
                        em.addElement(obj, GameElement.DYING);
                        list.remove(i);
                        i--;
                        continue;
                    }
                    obj.model(this.gamePanel.getWidth(), this.gamePanel.getHeight());
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 游戏阶段性结束
     */
    private void gameOver() {

    }

    private void load() {
        ImageIcon icon = new ImageIcon("image/player1_up.png");
        ElementObj player = new Player(100, 100, 50, 50, icon);
        em.getElementsByKey(GameElement.PLAYER).add(player);
    }
}
