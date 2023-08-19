package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

public class RoomUtils {
    public ArrayList<Room> roomList = new ArrayList<Room>();
    public ArrayList<Room> hallwayList = new ArrayList<Room>();
    public ArrayList<Room> allObjects = new ArrayList<Room>();
    public TETile[][] map;
    public RoomUtils(TETile[][] map){
        this.map = map;
    }
    public ArrayList<Room> getRoomList(){
        return roomList;
    }
    public ArrayList<Room> getHallwayList(){
        return hallwayList;
    }
    public void addRoom(Room room){
        roomList.add(room);
    }
    public void addHallway(Room room){
        hallwayList.add(room);
    }
    public void combineRoomsHallways(){
        allObjects.addAll(roomList);
        allObjects.addAll(hallwayList);
    }
    public void drawAll(){
        for(int r = 0; r < Engine.WIDTH; r++){
            for(int c = 0; c < Engine.HEIGHT; c++){
                map[r][c] = Tileset.NOTHING;
            }
        }
        for(Room room: roomList){
            room.drawRoom(map, Tileset.WALL);
        }
        for(Room hallway: hallwayList){
            hallway.drawRoom(map, Tileset.TREE);
        }
    }
    public void removeCollisions(){
        ArrayList<Room> toRemove = new ArrayList<Room>();
        for (Room room1 : hallwayList) {
            for (Room room2 : hallwayList) {
                if (room1 != room2 && room1.overlapsWith(room2)) {
                    System.out.println("yozo");
                    toRemove.add(room1);
                }
            }
        }
        for (Room room: toRemove){
            allObjects.remove(room);
        }
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("rooms: ");
        for (Room room : roomList) {
            str.append(room.toString());
            str.append(", ");
        }
        str.append("\thallways: ");
        for (Room room : hallwayList) {
            str.append(room.toString());
            str.append(", ");
        }

        return str.toString();
    }
    public String allString(){
        StringBuilder str = new StringBuilder();
        str.append("All:");
        for (Room room : allObjects) {
            str.append(room.toString());
            str.append(", ");
        }


        return str.toString();
    }
}
