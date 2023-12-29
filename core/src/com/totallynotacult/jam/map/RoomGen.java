package com.totallynotacult.jam.map;

import com.totallynotacult.jam.DungeonScreen;

import java.util.ArrayList;

public class RoomGen {

    private final Room[][] sceneGrid = new Room[5][5];
    private int[] startRoom = new int[2];

    public RoomGen(int roomCount) {
        for (int i = 0; i < 5; i++)
            for (int k = 0; k < 5; k++)
                sceneGrid[i][k] = new Room(0, new int[]{}, DungeonScreen.currentTimeLine);

        int[] currentRoom = new int[]{2, 2}; //Current room being generated

        int previousRoomExit = 0;

        //Generate possible adjacent rooms
        for (int i = 0; i < roomCount; i++) {

            var list = adjRooms(currentRoom);
            if (list.isEmpty()) break;
            int nextRoomRandom = (int) (Math.random() * list.size() - 1);

            System.out.print(list.get(nextRoomRandom)[1] + " ");
            System.out.print(list.get(nextRoomRandom)[0] + " :");
            System.out.println(list.get(nextRoomRandom)[2]);

            if (i == 0) { //Starting room
                sceneGrid[currentRoom[0]][currentRoom[1]] = new Room(1, new int[]{list.get(nextRoomRandom)[2]},DungeonScreen.currentTimeLine);
                startRoom = currentRoom;
            } else if (i == roomCount - 1) //Ending room
                sceneGrid[currentRoom[0]][currentRoom[1]] = new Room(3, new int[]{previousRoomExit},DungeonScreen.currentTimeLine);
            else
                sceneGrid[currentRoom[0]][currentRoom[1]] = new Room(2, new int[]{list.get(nextRoomRandom)[2], previousRoomExit},DungeonScreen.currentTimeLine); //Everything else

            currentRoom = new int[]{list.get(nextRoomRandom)[0], list.get(nextRoomRandom)[1]};
            previousRoomExit = (list.get(nextRoomRandom)[2] + 2) % 4;

        }
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++)
                System.out.print(sceneGrid[i][k].getRoomType());
            System.out.println();
        }

    }

    public int[] getStartRoom() {
        return startRoom;
    }

    public Room[][] getLevelMatrix() {
        return sceneGrid;
    }

    private boolean isRoomSpaceValid(int x, int y) {
        if (x < 0 || x >= sceneGrid.length || y < 0 || y >= sceneGrid[0].length) return false;
        return (sceneGrid[x][y].getRoomType() == 0);
    }

    private ArrayList<int[]> adjRooms(int[] cr) {
        var list = new ArrayList<int[]>();
        //Right
        if (isRoomSpaceValid(cr[0], cr[1] + 1))
            list.add(new int[]{cr[0], cr[1] + 1, 0});
        //Top
        if (isRoomSpaceValid(cr[0] - 1, cr[1]))
            list.add(new int[]{cr[0] - 1, cr[1], 1});
        //Left
        if (isRoomSpaceValid(cr[0], cr[1] - 1))
            list.add(new int[]{cr[0], cr[1] - 1, 2});
        //Bottom
        if (isRoomSpaceValid(cr[0] + 1, cr[1]))
            list.add(new int[]{cr[0] + 1, cr[1], 3});


        return list;
    }
}
