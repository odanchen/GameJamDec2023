package com.totallynotacult.jam.map;
import java.util.ArrayList;

public class RoomGen {

        private int[][] sceneGrid = new int[5][5];

        public RoomGen(int roomCount) {

            int[] currentRoom = new int[2]; //Current room being generated
            currentRoom[0] = 2; currentRoom[1] = 2; //Current room is center

            //Generate possible adjacent rooms

            for (int i = 0; i < roomCount; i++) {
                var list = adjRooms(currentRoom);
                int[] room = new int[2];
                room = list.get( (int) (Math.random()*list.size()));
                sceneGrid[room[0]][room[1]] = (i+1);
                currentRoom = room;
            }


        }
        public int[][] getLevelMatrix() {
            return sceneGrid;
        }
        private boolean isRoomSpaceValid(int x,int y) {
            if (x < 0 || x >= sceneGrid.length || y < 0 || y >= sceneGrid[0].length) return false;
            return (sceneGrid[x][y] == 0);
        }
        private ArrayList<int[]> adjRooms(int[] cr) {
            var list = new ArrayList<int[]>();
            //Top
            if (isRoomSpaceValid(cr[0],cr[1]-1))
                list.add(new int[]{cr[0], cr[1]-1});
            //Bottom
            if (isRoomSpaceValid(cr[0],cr[1]+1))
                list.add(new int[]{cr[0], cr[1]+1});
            //Left
            if (isRoomSpaceValid(cr[0]-1,cr[1]))
                list.add(new int[]{cr[0]-1, cr[1]});
            //Right
            if (isRoomSpaceValid(cr[0]+1,cr[1]))
                list.add(new int[]{cr[0]+1, cr[1]});

            return list;
        }
}
