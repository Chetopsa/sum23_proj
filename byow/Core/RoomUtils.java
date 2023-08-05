package byow.Core;

import byow.TileEngine.TETile;

import java.util.ArrayList;

public class RoomUtils {
    public ArrayList<Room> roomList = new ArrayList<Room>();
    public ArrayList<Room> hallwayList = new ArrayList<Room>();
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
}
