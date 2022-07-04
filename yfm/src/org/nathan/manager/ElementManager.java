package org.nathan.manager;

import org.nathan.element.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @说明 元素管理器，储存元素
 * @author Shiroan
 *
*/
public class ElementManager {
    private ElementManager() {
        init();
    }

    static private ElementManager instance;
    // 锁
    static public synchronized ElementManager getInstance() {
        if (instance == null) {
            instance = new ElementManager();
        }
        return instance;
    }

    private Map<ElementType, List<Element>> elements;

    public Map<ElementType, List<Element>> getElements() {
        return elements;
    }

    // 添加元素, 通常由加载器调用
    public void addElements(ElementType type, Element ...elements) {
        for (Element element : elements) {
            this.elements.get(type).add(element);
        }
    }

    public List<Element> getElementByType(ElementType type) {
        return elements.get(type);
    }

    protected void init() {
        elements = new HashMap<>();
        for (ElementType type : ElementType.values()) {
            elements.put(type, new ArrayList<>());
        }
    }
}
