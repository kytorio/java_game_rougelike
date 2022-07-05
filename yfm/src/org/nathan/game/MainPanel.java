package org.nathan.game;

import org.nathan.element.Element;
import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;

import javax.swing.*;
import java.awt.*;

// 元素显示 界面刷新（多线程）
public class MainPanel extends JPanel implements Runnable {
    // 管理器
    private ElementManager manager;

    public MainPanel() {
        init();
    }

    protected void init() {
        manager = ElementManager.getInstance();
    }

    /**
     * paint 进行绘画
     * 绘画有固定顺序
     * 因此有覆盖问题
     *
     * 只执行一次
     * 实时刷新需要多线程
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

    @Override
    public void run() {
        while (true)
        {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
