package org.nathan.manager;

import org.nathan.element.Direction;
import org.nathan.element.Element;
import org.nathan.element.MapItem;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Loader {
    public static Map<Direction, ImageIcon> players;
    public static Map<MapItem.Type, ImageIcon> blocks;

    static {
        players = new HashMap<>();
        players.put(Direction.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        players.put(Direction.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));
        players.put(Direction.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        players.put(Direction.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));

        blocks = new HashMap<>();
        blocks.put(MapItem.Type.BASE, new ImageIcon("image/wall/base.png"));
        blocks.put(MapItem.Type.BRICK, new ImageIcon("image/wall/brick.png"));
        blocks.put(MapItem.Type.GRASS, new ImageIcon("image/wall/grass.png"));
        blocks.put(MapItem.Type.IRON, new ImageIcon("image/wall/iron.png"));
        blocks.put(MapItem.Type.RIVER, new ImageIcon("image/wall/river.png"));
    }

    private static final Properties properties = new Properties();

    public static List<Element> mapLoad(int mapID) {
        String path = "org/nathan/text/" + mapID + ".map";
        ClassLoader classLoader = Loader.class.getClassLoader();
        InputStream mapFile= classLoader.getResourceAsStream(path);
        if (mapFile == null) {
            System.err.println("Map load failed.");
        }

        List<Element> map = new ArrayList<>();

        try {
            properties.load(mapFile);
            Enumeration<?> names = properties.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                String[] items = properties.getProperty(key).split(";");
                for (String item : items) {
                    Element mapItem = new MapItem().build(String.format("%s,%s", key, item));
                    map.add(mapItem);
                }
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        mapLoad(1);
    }
}
