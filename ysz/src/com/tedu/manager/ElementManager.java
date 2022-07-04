package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 用于存储元素, 提供获取数据的方法
 */
public class ElementManager {
    private Map<GameElement, List<ElementObj>> gameElements;

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    public void addElement(ElementObj obj, GameElement type) {
        gameElements.get(type).add(obj);
    }

    public List<ElementObj> getElementsByKey(GameElement type) {
        return gameElements.get(type);
    }

    // 构造函数私有化
    private ElementManager() {
        init();
    }

    // 用于被子类继承、重写
    public void init() {
        gameElements = new HashMap<>();
        for (GameElement ele: GameElement.values()) {
            gameElements.put(ele, new ArrayList<>());
        }
    }

    private static ElementManager EM = null;

    // 线程安全
    public static synchronized ElementManager getManager() {
        if (EM == null) {
            EM = new ElementManager();
        }

        return EM;
    }

    // Java 允许全局初始化的代码
//    static {
//        EM = new ElementManager();
//    }

}
