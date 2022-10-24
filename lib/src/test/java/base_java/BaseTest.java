package base_java;

import org.junit.Test;

import java.util.Arrays;

public class BaseTest {

    /**
     * 按位取反的效果
     */
    @Test
    public void one() {
        System.out.println("0 取反" + (~0));//-1
        System.out.println("1 取反" + (~1));//-2
        System.out.println("2 取反" + (~2));//-3
        System.out.println("3 取反" + (~3));//-4
        System.out.println("4 取反" + (~4));//-5
        System.out.println("5 取反" + (~5));//-6
        System.out.println("6 取反" + (~6));//-7
        System.out.println("7 取反" + (~7));//-8
    }

    /**
     * 二分查找的效果
     */
    @Test
    public void binarySearchTest() {

        //int index = ContainerHelpers.binarySearch(new int[]{1, 2, 3, 4}, 4, 2);
        //  System.out.println("找到的位置是:" + index);

        int[] a1 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        //int[] insertValues = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        //int[] insertValues = new int[]{8, 7, 6, 5, 4, 3, 2, 1};
        int[] insertValues = new int[]{7, 8, 5, 6, 3, 4, 1, 2};
        int size = 0;
        for (int i = 0; i < 8; i++) {

            int inertValue = insertValues[i];

            int low = ContainerHelpers.binarySearch(a1, size, inertValue);
            if (low < 0) {
                System.out.println("未找到" + inertValue);
                low = ~low;
                System.out.println("将" + inertValue + "插入到位置" + low);

                if (low < size) {
                    System.arraycopy(a1, low, a1, low + 1, size - low);
                }

                a1[low] = inertValue;
                System.out.println("插入后的数组" + Arrays.toString(a1));
                size++;
            } else {
                System.out.println("找到了" + inertValue + ",它在第" + low);
            }
            System.out.println("===========");
        }

    }
}
