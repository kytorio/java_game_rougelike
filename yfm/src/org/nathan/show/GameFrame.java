package org.nathan.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
* @说明 Frame
* @author Shioan
* @分析 1. 面板绑定到窗体
 *     2. 绑定监听
 *     3. 启动主线程
 *     4. 显示窗体
* */
public class GameFrame extends JFrame {
    private static final int w = 900;
    private static final int h = 600;
    private JPanel panel;
    private KeyListener keyListener;
    private MouseMotionListener mouseMotionListener;
    private MouseListener mouseListener;
    private Thread thread;

    public GameFrame() {
        init();
    }

    protected void init() {
        setSize(w, h);
        setTitle("Saw News");
        setDefaultCloseOperation(EXIT_ON_CLOSE); //退出时关闭
        setLocationRelativeTo(null);
    }

    // 初始化绑定和监听
    public void start() {
        if (panel != null) {
            add(panel);
        }
        if (keyListener != null) {
            addKeyListener(keyListener);
        }
        if (thread != null) {
            thread.start();
        }
        setVisible(true);
        if (panel instanceof Runnable) {
            new Thread((Runnable) panel).start();
        }
    }


    // 布局
    public void addButton() {

    }

    // set注入
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
