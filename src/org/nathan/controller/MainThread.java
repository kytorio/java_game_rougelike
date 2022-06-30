package org.nathan.controller;

import org.nathan.element.Element;
import org.nathan.element.Player;
import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;

import javax.swing.*;
import java.util.List;

/**
 * @说明 游戏主线程，控制加载、关卡、运行时自动化、地图切换资源释放和重新读取
 * @author Shiroan
 * */
public class MainThread extends Thread{
    private final ElementManager manager;

    public MainThread() {
        this.manager = ElementManager.getInstance();
    }

    public void load() {
        ImageIcon icon = new ImageIcon("image/tank/play1/player1_down.png");
        Element el1 = new Player(100, 100, 50, 50, icon);
        manager.addElements(ElementType.PLAYER, el1);
    }

    @Override
    public void run() {
        while (true) {
            // Before Start (Loading, Progress)
            gameLoad();
            // Runtime
            gameRuntime();
            // Level End (Release Resources)
            switchLevel();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gameLoad() {
        load();
    }

    /**
     * @说明 游戏进行时
     * @任务 1. 自动化玩家移动，碰撞，死亡
     *      2. 元素添加
     *      3. 暂停等
     * */
    private void gameRuntime() {
        while (true) {  // For extending
            for (List<Element> elementList : manager.getElements().values()) {
                elementList.forEach(Element::onTick);
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchLevel() {

    }
}
