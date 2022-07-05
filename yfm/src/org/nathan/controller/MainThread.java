package org.nathan.controller;

import org.nathan.element.Bullet;
import org.nathan.element.Element;
import org.nathan.element.MapItem;
import org.nathan.element.Point;
import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;
import org.nathan.manager.Loader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @说明 游戏主线程，控制加载、关卡、运行时自动化、地图切换资源释放和重新读取
 * @author Shiroan
 * */
public class MainThread extends Thread{
    private final ElementManager manager;

    private int level = 1;
    private int point = 0;

    public MainThread() {
        this.manager = ElementManager.getInstance();
    }

    public void load() {
        Loader.mapLoad(level).forEach(el -> manager.addElements(ElementType.MAP, el));

        Loader.loadLevel(level, manager);
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

            if (level > 2) {
                System.exit(0);
            }

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

    private long time = 0;
    /**
     * @说明 游戏进行时
     * @任务 1. 自动化玩家移动，碰撞，死亡
     *      2. 元素添加
     *      3. 暂停等
     * */
    private void gameRuntime() {
        while (true) {  // For extending
            elementsUpdate();
            collideDetect();
            time++;  // Unique Time Control
            if (manager.getElementByType(ElementType.ENEMY).isEmpty()) {
                break;
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void elementsUpdate() {
        for (List<Element> elementList : manager.getElements().values()) {
            for (int i = elementList.size() - 1; i >= 0; i--) {
                Element el = elementList.get(i);
                if (el.isAlive()) {
                    el.onTick(time);
                } else {
                    el.onDeath();
                    elementList.remove(i);
                }
            }
        }
    }

    // 碰撞检测
    private void collideDetect() {
        List<Element> enemies = manager.getElementByType(ElementType.ENEMY);
        List<Element> bullets = manager.getElementByType(ElementType.BULLET);
        List<Element> mapItem = manager.getElementByType(ElementType.MAP);
        Element player = manager.getElementByType(ElementType.PLAYER).get(0);

        for (Element enemy : enemies) {
            for (Element bullet : bullets) {
                if (enemy.collide(bullet)) {
                    if (bullet instanceof Bullet) {
                        enemy.setAlive(false);
                        point += 10;
                    } else {
                        enemy.setAlive(false);
                    }
                    bullet.setAlive(false);
                    break;
                }
            }
        }
        List<MapItem> items =  mapItem.stream()
                .filter(e -> e instanceof MapItem)
                .map(e -> (MapItem) e)
                .filter(e -> e.getType() == MapItem.Type.BRICK || e.getType() == MapItem.Type.IRON)
                .collect(Collectors.toList());

        for (MapItem item : items) {
            if (item.collide(player)) {
                player.rollback();
            }
            for (Element enemy : enemies) {
                if (item.collide(enemy)) {
                    enemy.rollback();
                }
            }
            for (Element bullet : bullets) {
                if (item.collide(bullet)) {
                    bullet.setAlive(false);
                }
            }
        }
    }

    private void switchLevel() {
        manager.clearAll();
        level++;
        Point point1 = new Point(point);
        manager.addElements(ElementType.DYING, point1);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        manager.clearElement(ElementType.DYING);
    }
}
