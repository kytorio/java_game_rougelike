package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @说明 游戏的主要面板
 * @功能说明 主要进行元素的显示、刷新
 */
public class GameMainJPanel extends JPanel implements Runnable {
    // 联动管理器
    private ElementManager em;

    public GameMainJPanel() {
        init();
    }

    public void init() {
        em = ElementManager.getManager();
    }

    public void load() {
        // 将游戏的各个元素从这里导入
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for (GameElement element: GameElement.values()) {
            List<ElementObj> list = all.get(element);
            for (ElementObj obj : list) {
                obj.showElement(g);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
