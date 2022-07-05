package org.nathan.manager;

import org.nathan.element.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Loader {
    public static Map<Direction, ImageIcon> players;
    public static Map<MapItem.Type, ImageIcon> blocks;
    public static Map<Direction, ImageIcon> enemies;

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

        enemies = new HashMap<>();
        enemies.put(Direction.LEFT, new ImageIcon("image/tank/bot/bot_left.png"));
        enemies.put(Direction.RIGHT, new ImageIcon("image/tank/bot/bot_right.png"));
        enemies.put(Direction.UP, new ImageIcon("image/tank/bot/bot_up.png"));
        enemies.put(Direction.DOWN, new ImageIcon("image/tank/bot/bot_down.png"));
    }

    public static List<Element> mapLoad(int mapID) {
        String path = "org/nathan/text/" + mapID + ".map";
        ClassLoader classLoader = Loader.class.getClassLoader();
        InputStream mapFile= classLoader.getResourceAsStream(path);
        if (mapFile == null) {
            System.err.println("Map load failed.");
        }

        List<Element> map = new ArrayList<>();

        try {
            Properties properties = new Properties();
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

    public static void loadLevel(int id, ElementManager manager) {
        String path = "org/nathan/text/level" + id + ".lev";
        Properties level = new Properties();
        InputStream stream = Loader.class.getClassLoader().getResourceAsStream(path);
        try {
            level.load(stream);
            //load player
            String playerStr = level.getProperty("PLAYER");
            Element player = new Player().build(playerStr);
            manager.addElements(ElementType.PLAYER, player);

            // load enemies
            String[] enemyStr = level.getProperty("ENEMY").split(";");
            Arrays.stream(enemyStr)
                    .map(s -> new Enemy().build(s))
                    .forEach(e -> manager.addElements(ElementType.ENEMY, e));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "org/nathan/text/level" + 1 + ".lev";
        Properties enemy1 = new Properties();
        InputStream stream = Loader.class.getClassLoader().getResourceAsStream(path);
        enemy1.load(stream);
        enemy1.forEach((k, v) -> System.out.printf("%s=%s%n", k, v));
    }
}
