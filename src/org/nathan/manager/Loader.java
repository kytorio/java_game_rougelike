package org.nathan.manager;

import org.nathan.element.Direction;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static Map<Direction, ImageIcon> players;

    static {
        players = new HashMap<>();
        players.put(Direction.LEFT, new ImageIcon("image/tank/play1/player1_left.png"));
        players.put(Direction.RIGHT, new ImageIcon("image/tank/play1/player1_right.png"));
        players.put(Direction.UP, new ImageIcon("image/tank/play1/player1_up.png"));
        players.put(Direction.DOWN, new ImageIcon("image/tank/play1/player1_down.png"));
    }

}
