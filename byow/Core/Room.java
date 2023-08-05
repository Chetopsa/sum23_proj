package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    boolean connected = false;
    //room size length and width constraints
    protected int max;
    protected int min;
    Random randy;
    protected int x; //bottom left
    protected int y; //bottom left
    protected int width;
    protected int height;
    protected int[] center = new int[2];
    protected int[] bl = new int[2]; // bottom left (x,y)
    protected int[] tl = new int[2]; // top left
    protected int[] tr = new int[2]; // top right
    protected int[] br = new int[2]; // bottom right


    public Room(Random randy, int max, int min) {
        this.randy = randy;
        this.max = max;
        this.min = min;
    }
    public Room(Random randy){
        this.randy = randy;
    }
    public int[] getBl(){
        return bl;
    }
    public int[] getTl(){
        return tl;
    }
    public int[] getTr(){
        return tr;
    }
    public int[] getBr(){
        return br;
    }
    public int[] getCenter(){
        return center;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }




    public boolean makeRoom(TETile[][] map) {
        //fill in values
        x = randy.nextInt(Engine.WIDTH); //bottom left
        y = randy.nextInt(Engine.HEIGHT); //bottom left
        width = (int) randy.nextInt(max - min) + min;
        height = (int) randy.nextInt(max - min) + min;
        bl[0] = x; bl[1] = y; // bottom left
        tl[0] = x; tl[1] = y + height; // top left
        tr[0] = x + width; tr[1] = y + height; // top right
        br[0] = x + width; br[1] = y; // bottom right
        center[0] = (x + width) / 2; center[1] = (y + height) / 2; //gcenter coord

        //get random coord
        boolean valid = true;
        if(!checkInside(WorldGen.utils.getRoomList())){
            valid = false;
        }
        if(!checkCollision(map)){
            valid = false;
        }

        System.out.println("cool");
        // ~-~-~-~-~-~-~-~-~- DRAW -- ROOM -~-~-~-~-~-~-~-~-~-~-~
        if (valid) {
            drawRoom(map);
            return true; //success
        }
        return false; //collision detected
    }
    public void drawRoom(TETile[][] map){
        for (int k = bl[0]; k < br[0]; k++) {
            map[k][bl[1]] = Tileset.WATER;
        }
        //draw top
        for (int k = tl[0]; k < tr[0] + 1; k++) {
            map[k][tl[1]] = Tileset.WATER;
        }
        //draw left side
        for (int k = bl[1]; k < tl[1]; k++) {
            map[bl[0]][k] = Tileset.WATER;
        }
        //draw right side
        for (int k = br[1]; k < tr[1]; k++) {
            map[br[0]][k] = Tileset.WATER;
        }
    }
    public boolean checkCollision(TETile[][] map){
        System.out.println(tr[1]+"<--tr\n\n\n");
        if (tl[1] >= Engine.HEIGHT) {
            return false;
        }
        if (tr[0] >= Engine.WIDTH) {
            return false;
        }
        // test bottom
        for (int k = bl[0]; k < br[0]; k++) {
            if (tr[0] >= Engine.WIDTH) {
                return false;
            }
            System.out.println(k + "<-----FAIL BOTTOM TEST");

            if (!TETile.tileEquals(map[k][bl[1]], Tileset.NOTHING)) {
                return false;
            }
        }
        //test top
        for (int k = tl[0]; k < tr[0]; k++) {
            if (tl[1] >= Engine.HEIGHT || tr[0] >= Engine.WIDTH) {
                return false;
            }
            System.out.println(tr[1] + " <--FAILT TOP TEST");
            if (!TETile.tileEquals(map[k][tr[1]], Tileset.NOTHING)) {
                return false;
            }
        }
        //test left side
        for (int k = bl[1]; k < tl[1]; k++) {
            if (k >= Engine.HEIGHT) {
               return false;
            }
            System.out.println(k + "<-----FAIL LEFT TEST");
            if (!TETile.tileEquals(map[tl[0]][k], Tileset.NOTHING)) {
                return false;
            }
        }
        System.out.println(tr[1] + "      "+ br[1]+"\n\n\n\n\n\n\n");
        //test right side
        for (int k = br[1]; k < tr[1]; k++) {
            if (tr[1] >= Engine.HEIGHT || tr[0] >= Engine.WIDTH) {
               return false;
            }
            System.out.println(k + "<-----FAIL RIGHT TEST");
            if (!TETile.tileEquals(map[tr[0]][k], Tileset.NOTHING)) {
                return false;

            }
        }
        return true;
    }
    public boolean checkInside(ArrayList<Room> rooms){

        if(rooms.size() == 0){return true;}
        for(Room room : rooms) {
            //check bottom left corner
            if (room.getBl()[0] <= bl[0] && room.getBr()[0] >= bl[0] && room.getBl()[1] <= bl[1] && room.getTl()[1] >= bl[1]) {
                System.out.println("mhmmmmmmm\n\n\n\n\n\n\n\n\n\n ah \n\n\n\n");
                return false;
            }
            //check top right corner
            if (room.getBl()[0] <= tr[0] && room.getBr()[0] >= tr[0] && room.getBl()[1] <= tr[1] && room.getTl()[1] >= tr[1]) {
                return false;
            }
        }
        return true;
    }
    public boolean generateHallway(ArrayList<Room> rooms, TETile[][] map ){
        Room Hallway = new Room(randy);
        width = randy.nextInt(4 - 2) + 2;
        center[0] = (x + width) / 2; center[1] = (y + height) / 2; //center coord
        for(Room room: rooms) {
            boolean connected = false;
            x = room.getTl()[0];
            y = room.getTl()[1];

            //find other room go left
            for(int k = x; k > 0; k--) {
                if (!TETile.tileEquals(map[x][k], Tileset.NOTHING)) {
                }
            }

        }
        return true;
    }


    @Override
    public String toString() {
        String str = "(" +x+", " +y + ")";
        return str;
    }
}
