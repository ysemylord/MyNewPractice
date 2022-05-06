package com;

import java.util.ArrayList;
import java.util.List;

public class ArrayTest {
    public static void main(String[] args) {



        int[][] blocks = {{2, 3, 3}, {4, 5, 2}, {3, 4, 2}, {0, 1, 4}, {1, 3, 3}, {7, 8, 2}, {6, 7, 2}, {8, 9, 2}};
        int limit = 7;

      /*  int[][] blocks = {{0,5,2},{4,6,5},{7,8,2}};
        int limit = 4;*/
        int count = 0;

        while (limit > 0) {
            List<int[]> canPickList = new ArrayList<>();
            for (int i = 0; i < blocks.length; i++) {

                if (blocks[i][0] == -1) {
                    continue;
                }
                boolean canPick = canPick(blocks, i);
                if (canPick) {
                    canPickList.add(blocks[i]);
                }
            }
            int removeIndex = miniTime(canPickList);



            limit = limit - canPickList.get(removeIndex)[2];
            if (limit >= 0) {
                count++;
                canPickList.get(removeIndex)[0] = -1;
            }
        }


    }

    private static boolean canPick(int[][] blocks, int i) {
        int[] target = blocks[i];
        for (int index = i + 1; index < blocks.length; index++) {
            int[] nowBlock = blocks[index];
            if (nowBlock[0] <= target[1] && nowBlock[1] >= target[1]) {
                return false;
            }
        }
        return true;
    }

    private static int miniTime(List<int[]> canPickList) {
        int res = 0;
        for (int i = 1; i < canPickList.size(); i++) {
            if (canPickList.get(i)[2] < canPickList.get(res)[2]) {
                res = i;
            }
        }
        return res;
    }
}
