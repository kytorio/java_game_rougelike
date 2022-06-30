package org.nathan.manager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static Map<String, ImageIcon> images;

    static {
        images = new HashMap<>();
        images.put("left", new ImageIcon("image/tank/play1/player1_left.png"));
        images.put("right", new ImageIcon("image/tank/play1/player1_right.png"));
        images.put("up", new ImageIcon("image/tank/play1/player1_up.png"));
        images.put("down", new ImageIcon("image/tank/play1/player1_down.png"));
    }

}
