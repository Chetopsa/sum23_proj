package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class WorldGen {
    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;
    public static RoomUtils utils;


    public static void main(String[] args) {
        System.out.println("start\n");
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] map = new TETile[WIDTH][HEIGHT];
        utils = new RoomUtils(map);
        mapGen(114574454, map, utils);
        ter.renderFrame(map);
    }

    public static void mapGen(long seed, TETile[][] map, RoomUtils utils) {
        int avLength = 7;
        int avHeight = 8;
        Random randy = new Random(seed);
        //set blank
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                map[x][y] = Tileset.NOTHING;
            }
        }
        //create rooms
        int roomAttempts = 60;
        boolean valid;
        for (int i = 0; i < roomAttempts; i++) {
            //System.out.println("count -- " + i);
            Room room = new Room(randy, 22, 10);
            if (room.makeRoom(map)) {
                utils.addRoom(room);
            }
        }
        //create hallways
        for (Room room : utils.getRoomList()) {
            room.generateHallway(utils.getRoomList(), map, utils);
        }
        utils.removeCollisions();
        utils.drawAll();
        //create doorways

        makeDoorways(map);
        //System.out.println(utils.allString());

    }


    public static void makeDoorways(TETile[][] map) {
        utils.combineRoomsHallways(); //make one array of all rooms and hallways
        for (Room room1 : utils.allObjects) {
            for (Room room2 : utils.allObjects) {
                if (room1 != room2 && room1.overlapsWith(room2)) {
                    //do right side
                    int[][] rightDoor = rightDoorway(room1, room2);
                    if (rightDoor != null) {
                        determineDoorwayPosition(rightDoor, map);
                    }
                    // do left side
                    int[][] leftDoor = leftDoorway(room1, room2);
                    if (leftDoor != null) {
                        determineDoorwayPosition(leftDoor, map);
                    }
                    //do top
                    int[][] topDoor = topDoorway(room1, room2);
                    if (topDoor != null) {
                        determineDoorwayPosition(topDoor, map);
                    }
                    //do bottom
                    int[][] bottomDoor = bottomDoorway(room1, room2);
                    if (bottomDoor != null) {
                        determineDoorwayPosition(bottomDoor, map);
                    }
                }
            }
        }
    }

    public static int[][] rightDoorway(Room room1, Room room2){
        //check right side of room 2
        if((room1.getTr()[0] + 1 == room2.getBl()[0]) && (room1.getTr()[1] >= room2.getTl()[1] && room1.getBr()[1] <= room2.getTl()[1]) ||
                (room1.getTr()[1] <= room2.getTl()[1] && room1.getBr()[1] >= room2.getTl()[1])){
            int bottom = Math.max(room1.getBr()[1], room2.getBl()[1]) + 1;
            int top = Math.min(room1.getTr()[1], room2.getTl()[1]) - 1;
            return new int[][]{{room1.getTr()[0], bottom}, {room2.getTl()[0], top}};
        }
        return null;
    }
    public static int[][] leftDoorway(Room room1, Room room2){
        //check left side of room 2
        if((room1.getTl()[0] - 1 == room2.getBr()[0]) && (room1.getTl()[1] >= room2.getTr()[1] && room1.getBl()[1] <= room2.getTr()[1]) ||
                (room1.getTl()[1] <= room2.getTr()[1] && room1.getBl()[1] >= room2.getTr()[1])){
            int bottom = Math.max(room1.getBr()[1], room2.getBl()[1]) + 1;
            int top = Math.min(room1.getTr()[1], room2.getTl()[1]) - 1;
            return new int[][]{{room2.getTr()[0], bottom}, {room1.getTl()[0], top}};
        }
        return null;
    }
    public static int[][] topDoorway(Room room1, Room room2) {
        //check top side of room 1
        if(room1.getTr()[1] + 1 == room2.getBl()[1] && (room1.getTl()[0] >= room2.getBl()[0] && room1.getTr()[0] <= room2.getBl()[0])||
                (room1.getTl()[0] <= room2.getBl()[0] && room1.getTr()[0] >= room2.getBl()[0])){
            int left = Math.max(room1.getBl()[0], room2.getBl()[0]) + 1;
            int right = Math.min(room1.getTr()[0], room2.getTr()[0]) - 1;
            return new int[][]{{left, room1.getTr()[1]}, {right, room2.getBl()[1]}};
        }
        return null;
    }

    public static int[][] bottomDoorway(Room room1, Room room2) {
        //check bottom room 1
        if(room1.getBr()[1] - 1 == room2.getTl()[1] && (room1.getBl()[0] >= room2.getBl()[0] && room1.getBr()[0] <= room2.getBl()[0])||
                (room1.getTl()[0] <= room2.getBl()[0] && room1.getTr()[0] >= room2.getBl()[0])){
            int left = Math.max(room1.getBl()[0], room2.getBl()[0]) + 1;
            int right = Math.min(room1.getTr()[0], room2.getTr()[0]) - 1;
            return new int[][]{{left, room2.getTr()[1]}, {right, room1.getBl()[1]}};
        }
        else return null; // No adjacent walls found
    }


    public static void determineDoorwayPosition(int[][] commonWall, TETile[][] map) {
        // Determine the position for the doorway within the common wall
        System.out.print("\ngets here\n");
        int startX = commonWall[0][0];
        int startY = commonWall[0][1];
        int endX = commonWall[1][0];
        int endY = commonWall[1][1];

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                map[x][y] = Tileset.UNLOCKED_DOOR;
            }
        }
    }



}
