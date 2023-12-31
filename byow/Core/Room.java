package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    protected boolean isHallway = false;
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
    int hy;
    int hx;


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
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setBl(int x, int y){
        this.bl[0] = x;
        this.bl[1] = y;
    }
    public void setTl(int x, int y){
        this.tl[0] = x;
        this.tl[1] = y;
    }
    public void setTr(int x, int y){
        this.tr[0] = x;
        this.tr[1] = y;
    }
    public void setBr(int x, int y){
        this.br[0] = x;
        this.br[1] = y;
    }




    public boolean makeRoom(TETile[][] map) {
        //fill in values
        x = randy.nextInt(Engine.WIDTH); //bottom left
        y = randy.nextInt(Engine.HEIGHT); //bottom left
        width = randy.nextInt(max - min) + min;
        height = randy.nextInt(max - min) + min;
        bl[0] = x; bl[1] = y; // bottom left
        tl[0] = x; tl[1] = y + height; // top left
        tr[0] = x + width; tr[1] = y + height; // top right
        br[0] = x + width; br[1] = y; // bottom right
        center[0] = x + (width / 2); center[1] = y + (height / 2); //center coord

        //get random coord
        boolean valid = true;
//        if(!checkInside(WorldGen.utils.getRoomList())){
//            valid = false;
//        }
        for(Room room : WorldGen.utils.getRoomList()){
            if (overlapsWith(room)){
                valid = false;
            }
        }
        if(!checkCollision(map)){
            valid = false;
        }

        //System.out.println("cool");
        // ~-~-~-~-~-~-~-~-~- DRAW -- ROOM -~-~-~-~-~-~-~-~-~-~-~
        if (valid) {

            drawRoom(map, Tileset.WALL, Tileset.FLOOR);
            return true; //success
        }
        return false; //collision detected
    }
    public void drawRoom(TETile[][] map, TETile tile, TETile inside){
        TETile hallway= Tileset.FLOOR;
        int cur = 0;
        for (int k = bl[0]; k < br[0]; k++) {
            map[k][bl[1]] = tile;
        }
        //draw top
        for (int k = tl[0]; k < tr[0] + 1; k++) {
            map[k][tl[1]] = tile;
        }
        //draw left side
        for (int k = bl[1]; k < tl[1]; k++) {
            map[bl[0]][k] = tile;
        }
        //draw right side
        for (int k = br[1]; k < tr[1]; k++) {
            map[br[0]][k] = tile;
        }
        //draw inside
        if(!isHallway) {
            for (int r = bl[0] + 1; r < br[0]; r++) {
                for (int c = bl[1] + 1; c < tl[1]; c++) {
                    map[r][c] = inside;
                }
            }
        }else{
            for (int r = bl[0] + 1; r < br[0]; r++) {
                for (int c = bl[1] + 1; c < tl[1]; c++) {
                    map[r][c] = hallway;
                }
            }
        }
    }
    public boolean checkCollision(TETile[][] map){
        //System.out.println(tr[1]+"<--tr\n\n\n");
        if (tl[1] >= Engine.HEIGHT) {
            return false;
        }
        if (tr[0] >= Engine.WIDTH) {
            return false;
        }
//        // test bottom
//        for (int k = bl[0]; k < br[0]; k++) {
//            if (tr[0] >= Engine.WIDTH) {
//                return false;
//            }
//            //System.out.println(k + "<-----FAIL BOTTOM TEST");
//
//            if (!TETile.tileEquals(map[k][bl[1]], Tileset.NOTHING)) {
//                return false;
//            }
//        }
//        //test top
//        for (int k = tl[0]; k < tr[0]; k++) {
//            if (tl[1] >= Engine.HEIGHT || tr[0] >= Engine.WIDTH) {
//                return false;
//            }
//            //System.out.println(tr[1] + " <--FAILT TOP TEST");
//            if (!TETile.tileEquals(map[k][tr[1]], Tileset.NOTHING)) {
//                return false;
//            }
//        }
//        //test left side
//        for (int k = bl[1]; k < tl[1]; k++) {
//            if (k >= Engine.HEIGHT) {
//               return false;
//            }
//            //System.out.println(k + "<-----FAIL LEFT TEST");
//            if (!TETile.tileEquals(map[tl[0]][k], Tileset.NOTHING)) {
//                return false;
//            }
//        }
//        System.out.println(tr[1] + "      "+ br[1]+"\n\n\n\n\n\n\n");
//        //test right side
//        for (int k = br[1]; k < tr[1]; k++) {
//            if (tr[1] >= Engine.HEIGHT || tr[0] >= Engine.WIDTH) {
//               return false;
//            }
//            //System.out.println(k + "<-----FAIL RIGHT TEST");
//            if (!TETile.tileEquals(map[tr[0]][k], Tileset.NOTHING)) {
//                return false;
//
//            }
//        }
        return true;
    }
    public boolean checkInside(Room room){
            //check bottom left corner
            if (room.getBl()[0] <= bl[0] && room.getBr()[0] >= bl[0] && room.getBl()[1] <= bl[1] && room.getTl()[1] >= bl[1]) {
                return false;
            }
            //check top right corner
            if (room.getBl()[0] <= tr[0] && room.getBr()[0] >= tr[0] && room.getBl()[1] <= tr[1] && room.getTl()[1] >= tr[1]) {
                return false;
            }
        return true;
    }

    public boolean overlapsWith(Room other) {

        // Check for horizontal overlap
        boolean horizontalOverlap = !(this.tr[0] + 1 < other.tl[0] || this.tl[0] > other.tr[0] + 1);

        // Check for vertical overlap with a minimum distance of 1 coordinate
        boolean verticalOverlap = !(this.bl[1] > other.tl[1] + 1 || this.tl[1] + 1 < other.bl[1]);
        // If both exist, the rooms overlap
        return horizontalOverlap && verticalOverlap;
    }
    public boolean hallwayOverlaps(Room other) {

        // Check for horizontal overlap
        boolean horizontalOverlap = (this.tr[0] >= other.tl[0] && this.tl[0] <= other.tr[0]);

        // Check for vertical overlap with a minimum distance of 0 coordinate
        boolean verticalOverlap = this.tl[1] >= other.bl[1] && this.bl[1] <= other.tl[1];
        // If both exist, the rooms overlap
        return horizontalOverlap && verticalOverlap;
    }
    public Room newHallway(){
        Room hallway = new Room(randy);
        hallway.isHallway = true;
        hallway.setWidth(randy.nextInt(5 - 2) + 2);
        center[0] = x+ (width / 2); center[1] = y + (height / 2); //center coord
        System.out.print("center x: "+center[0]+ "  center y: "+center[1]);
        hallway.hy = center[1] + randy.nextInt(2) - 1; //hallway y coord
        System.out.print("hallway y: "+hy+ "  hallway_x: ");
        hallway.hx = center[0] + randy.nextInt(2) - 1; //hallway x coord
        return hallway;
    }
    public boolean generateHallway(ArrayList<Room> rooms, TETile[][] map, RoomUtils utils){
        boolean clearPath = false;
        int length = 0;
        //find other room go left
        Room hallway = newHallway();
        System.out.println("\n"+hallway.hy+"\t"+ hallway.hx+"\n");
        for(int k = x-1; k > 0; k--) {
            System.out.print(k+ "<--\n");
            if (!TETile.tileEquals(map[k][hallway.hy + hallway.getWidth()/2], Tileset.NOTHING) || !TETile.tileEquals(map[k][hallway.hy - hallway.getWidth()/2], Tileset.NOTHING)) {
                if(length <=1){
                    break;
                }
                System.out.println("\nwidth: "+ hallway.getWidth()/2);
                hallway.setBl(k + 1, hallway.hy - hallway.getWidth()/2);
                hallway.setTl(k + 1, hallway.hy + hallway.getWidth()/2);
                hallway.setTr(x - 1, hallway.hy + hallway.getWidth()/2);
                hallway.setBr(x - 1, hallway.hy - hallway.getWidth()/2);
                hallway.x = hallway.bl[0];
                hallway.y = hallway.bl[1];
                hallway.drawRoom(map, Tileset.WALL, Tileset.FLOOR);
                utils.addHallway(hallway);
                clearPath = true;

                break;
            }
            length++;
        }
        length = 0;
        //find room bottom
        hallway = newHallway();
        for(int k = y-2; k > 0; k--) {
            if (!TETile.tileEquals(map[hallway.hx + hallway.getWidth()/2][k], Tileset.NOTHING) || !TETile.tileEquals(map[hallway.hx - hallway.getWidth()/2][k], Tileset.NOTHING)){
                if(length <= 1){
                    break;
                }
                hallway.setBl(hallway.hx - hallway.getWidth()/2, k + 1);
                hallway.setTl(hallway.hx - hallway.getWidth()/2, y - 1);
                hallway.setTr(hallway.hx + hallway.getWidth()/2, y - 1);
                hallway.setBr(hallway.hx + hallway.getWidth()/2, k + 1);
                hallway.x = hallway.bl[0];
                hallway.y = hallway.bl[1];
                hallway.drawRoom(map, Tileset.WALL, Tileset.FLOOR);
                utils.addHallway(hallway);
                clearPath = true;

                break;
            }
            length ++;
        }
        length = 0;
//        //find other room go right
        hallway = newHallway();
        for(int k = br[0]+1; k < Engine.WIDTH; k++) {
            if (!TETile.tileEquals(map[k][hallway.hy + hallway.getWidth()/2], Tileset.NOTHING) || !TETile.tileEquals(map[k][hallway.hy - hallway.getWidth()/2], Tileset.NOTHING)) {
                if(length == 0){
                    break;
                }
                hallway.setBl(br[0] + 1, hallway.hy - hallway.getWidth()/2 );
                hallway.setTl(br[0] + 1, hallway.hy + hallway.getWidth()/2);
                hallway.setTr(k - 1, hallway.hy + hallway.getWidth()/2);
                hallway.setBr(k - 1, hallway.hy - hallway.getWidth()/2);
                hallway.x = hallway.bl[0];
                hallway.y = hallway.bl[1];
                hallway.drawRoom(map, Tileset.WALL, Tileset.FLOOR);
                utils.addHallway(hallway);
                clearPath = true;
                break;
            }
            length++;
        }
        length = 0;
       //find room going up
        hallway = newHallway();
        for(int k = tl[1] + 1; k < Engine.HEIGHT; k++) {
            if (!TETile.tileEquals(map[hallway.hx + hallway.getWidth()/2][k], Tileset.NOTHING) || !TETile.tileEquals(map[hallway.hx - hallway.getWidth()/2][k], Tileset.NOTHING)) {
                if(length <= 1){
                    break;
                }
                hallway.setBl(hallway.hx - hallway.getWidth()/2, tl[1] + 1);
                hallway.setTl(hallway.hx - hallway.getWidth()/2, k - 1);
                hallway.setTr(hallway.hx + hallway.getWidth()/2, k - 1);
                hallway.setBr(hallway.hx + hallway.getWidth()/2, tl[1] + 1);
                hallway.x = hallway.bl[0];
                hallway.y = hallway.bl[1];
                hallway.drawRoom(map, Tileset.WALL, Tileset.FLOOR);
                utils.addHallway(hallway);
                clearPath = true;
                break;
            }
            length ++;
        }
    return clearPath;
    }




    @Override
    public String toString() {
        String str = " {(" + x +", " + y + "), ";
        str += " (" + tl[0] +", " + tl[1] + "), ";
        str += " (" + tr[0] +", " + tr[1] + "), ";
        str += " (" + br[0] +", " + br[1] + ")}";

        return str;
    }
}
