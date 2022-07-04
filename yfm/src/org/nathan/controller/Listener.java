package org.nathan.controller;

import org.nathan.element.Element;
import org.nathan.manager.ElementManager;
import org.nathan.manager.ElementType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于监听用户操作
 * @author Shiroan
 * */
public class Listener implements KeyListener {
    private final ElementManager manager = ElementManager.getInstance();

    private final Set<Integer> set = new HashSet<>();
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Up 38 Down 40 Left 37 Right 39
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!set.contains(key)) {  // 第一次按下
            set.add(key);
            List<Element> players = manager.getElementByType(ElementType.PLAYER);
            players.forEach(player -> player.onKey(true, key));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        set.remove(key);
        List<Element> players = manager.getElementByType(ElementType.PLAYER);
        players.forEach(player -> player.onKey(false, key));
    }
}
