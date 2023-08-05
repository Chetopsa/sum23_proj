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
        mapGen(113574441, map, utils);

        ter.renderFrame(map);

    }

    public static void mapGen(long seed, TETile[][] map, RoomUtils utils){
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
        for(int i = 0; i < roomAttempts; i++) {
            //System.out.println("count -- " + i);
            Room room = new Room(randy, 22, 10);
            if(room.makeRoom(map)){
                utils.addRoom(room);
            }
        }
        //create hallways
        for(Room room: utils.getRoomList()) {
            room.generateHallway(utils.getRoomList(), map, utils);
        }
        System.out.println(utils);

    }

}
