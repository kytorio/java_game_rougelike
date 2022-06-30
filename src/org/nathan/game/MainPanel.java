package org.nathan.game;

import org.nathan.element.Element;
import org.nathan.element.Player;
import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;

import javax.swing.*;
import java.awt.*;

// 元素显示 界面刷新（多线程）
public class MainPanel extends JPanel {
    // 管理器
    private ElementManager manager;

    public MainPanel() {
        init();

        // For Test
        load();
    }

    public void load() {
        ImageIcon icon = new ImageIcon("image/tank/play1/player1_down.png");
        Element el1 = new Player(100, 100, 50, 50, icon);
        Element el2 = new Player(100, 100, 100, 100, icon);
        manager.addElements(ElementType.PLAYER, el1);
        manager.addElements(ElementType.MAP, el2);
    }

    protected void init() {
        manager = ElementManager.getInstance();
    }

    /**
     * paint 进行绘画
     * 绘画有固定顺序
     * 因此有覆盖问题
     * */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (ElementType type : ElementType.values()) {
            java.util.List<Element> elements = manager.getElementByType(type);
            if (elements != null) {
                elements.forEach(el -> el.render(g));
            }
        }

    }
}
