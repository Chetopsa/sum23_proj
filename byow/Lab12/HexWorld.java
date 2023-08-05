package byow.Lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void main(String[] args) {
        int WIDTH = 60;
        int HEIGHT = 50;
        int cur = 0;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int j = 0;
        for(int i = 2; i < WIDTH - 6; i+=4){
            makeHex(i, j, cur, world );
            j+=3;
            if(cur == 5){cur = 0;} else{cur++;}
        }
        ter.renderFrame(world);
    }
    public static void makeHex(int x, int y, int cur, TETile[][] world){
        TETile[] tiles = {Tileset.FLOWER, Tileset.FLOOR, Tileset.WATER, Tileset.MOUNTAIN, Tileset.GRASS, Tileset.SAND};
        int size = 6;

//        for(int i = x; i < x+3; i++){
//            for(int j = y; j < y+4; j++){
//                if(i )
//            }
        //}
        world[x+1][y] = tiles[cur];
        world[x+2][y] = tiles[cur];
        world[x][y+1] = tiles[cur];
        world[x+1][y+1] = tiles[cur];
        world[x+2][y+1] = tiles[cur];
        world[x+3][y+1] = tiles[cur];
        world[x+1][y+2] = tiles[cur];
        world[x+2][y+2] = tiles[cur];
        System.out.println("yo");

    }

}
