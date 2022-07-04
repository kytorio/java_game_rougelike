package com.tedu.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @说明 游戏窗体 关闭, 显示, 最大最小化
 * @功能说明 需要嵌入面板
 * @窗体说明 使用 swing awt 记录用户上次使用的软件大小
 */
public class GameFrame extends JFrame {
    // 窗体大小
    public static int GameX = 900;
    public static int GameY = 600;
    // 正在显示的面板
    private JPanel jPanel = null;
    // 键盘监听
    private KeyListener keyListener = null;
    // 鼠标监听
    private MouseMotionListener mouseMotionListener = null;
    private MouseListener mouseListener = null;
    // 游戏主线程
    private Thread thread = null;

    public GameFrame() {
        init();
    }

    public void init() {
        // 设置窗体大小
        this.setSize(GameX, GameY);
        // 设置窗体标题
        this.setTitle("Game");
        // 设置窗体位置: 屏幕居中
        this.setLocationRelativeTo(null);
        // 设置默认退出方式: 退出并关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 启动方法
     */
    public void start() {
        if (jPanel != null) {
            this.add(jPanel);
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
        }
        if (thread != null) {
            thread.start();
        }
        // 显示界面
        this.setVisible(true);

        // 启动渲染线程
        if (this.jPanel instanceof Runnable) {
            new Thread((Runnable) this.jPanel).start();
        }
    }

    // 窗体布局
    public void addButton() {
        // 用于添加控件, 设置布局
        // this.setLayout(...)
    }

    public void setJPanel(JPanel jPanel) {
        this.jPanel = jPanel;
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
