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
    public void addRoom(Room room){
        roomList.add(room);
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (Room room : roomList) {
            str.append(", { ");
            str.append(room.toString());
            str.append(" }");

        }
        return str.toString();
    }
}
