package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 监听类: 用于监听用户操作
 */
public class GameListener implements KeyListener {
    private final ElementManager em = ElementManager.getManager();

    // 通过 Set 避免重复调用游戏对象的 keyPressed(), keyReleased() 方法
    private final Set<Integer> pressedKeys = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //        w = 87
    // a = 65 s = 83  d = 68    WhiteSpace = 32
    //         up = 38
    // le = 37 dw = 40 rt = 39    Enter = 10
    @Override
    public void keyPressed(KeyEvent e) {
        // 记录按键
        int keyCode = e.getKeyCode();
        if (!this.pressedKeys.contains(keyCode)) {
            this.pressedKeys.add(keyCode);
            // 通过 ElementManager 获取游戏元素并调用 keyPressed() 进行处理
            List<ElementObj> player = em.getElementsByKey(GameElement.PLAYER);
            for (ElementObj obj : player) {
                obj.keyPressed(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ...
        int keyCode = e.getKeyCode();
        if (this.pressedKeys.contains(keyCode)) {
            this.pressedKeys.remove(keyCode);
            List<ElementObj> player = em.getElementsByKey(GameElement.PLAYER);
            for (ElementObj obj: player) {
                obj.keyReleased(e.getKeyCode());
            }
        }
    }
}
