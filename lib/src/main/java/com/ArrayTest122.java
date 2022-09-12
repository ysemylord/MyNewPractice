package com;

import java.util.ArrayList;
import java.util.List;

public class ArrayTest122 {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        
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
