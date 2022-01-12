
package sprintbot.battlecode2022.util;

import battlecode.common.*;

// Data such as turn number, turns alive, local environment etc

public class Cache {
    public static RobotController controller;
    public static Team OUR_TEAM;
    public static Team OPPONENT_TEAM;


    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;

    // the end of data is marked with -1
    public static int[] opponent_archon_compressed_locations;
    public static int[] metal_compressed_locations;
    public static int[] opponent_soldier_compressed_locations;

    public static int age = 0;

    public static void init(RobotController controller) {
        Cache.controller = controller;
        OUR_TEAM = controller.getTeam();
        OPPONENT_TEAM = OUR_TEAM.opponent();
        MAP_WIDTH = controller.getMapWidth();
        MAP_HEIGHT = controller.getMapHeight();
    }

    public static void update() {
        age += 1;
    }

}