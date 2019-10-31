package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-24
 */

/**
 * 给定一个整数数组 nums，将该数组升序排列。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[5,2,3,1]
 * 输出：[1,2,3,5]
 * <p>
 * 示例 2：
 * <p>
 * 输入：[5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 * <p>
 * 提示：
 * <p>
 * 1 <= A.length <= 10000
 * -50000 <= A[i] <= 50000
 */
public class SortArray912 {
    /**
     * 冒泡排序
     *
     * @param nums
     * @return
     */
    public static int[] bubbleSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        int numsLength = nums.length;
        for (int i = 0; i < numsLength - 1; i++) {
            boolean finished = true;
            for (int j = 0; j < numsLength - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;

                    finished = false;
                }
            }

            if (finished) {
                break;
            }
        }

        return nums;
    }

    /**
     * 插入排序
     *
     * @param nums
     * @return
     */
    public static int[] insertSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        int numsLength = nums.length;
        for (int i = 1; i < numsLength; i++) {
            int tmp = nums[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (nums[j] > tmp) {
                    nums[j + 1] = nums[j];
                } else {
                    //j+1的位置是空的
                    break;
                }
            }
            nums[j + 1] = tmp;
        }

        return nums;
    }

    /**
     * 快速排序
     * 理解快排的重点也是理解递推公式，quickSortImpl() 分区函数。
     *
     * @param nums
     * @return
     */
    public static int[] quickSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        quickSortImpl(nums, 0, nums.length - 1);

        return nums;
    }

    /**
     * 分区，找到pivot将数组分成三部分
     * 遍历startIndex~endIndex数据，将小于 pivot 的放到左边，将大于 pivot 的放到右边，将 pivot 放到中间
     * @param nums
     * @param startIndex
     * @param endIndex
     */
    private static void quickSortImpl(int[] nums, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        //选取最后一个元素当做基点
        int pivot = nums[endIndex];
        int leftIndex = startIndex;
        //应该从最后一个，不可以设置成倒数第二个
        int rightIndex = endIndex;

        while (leftIndex != rightIndex) {
            //先从左往右边找，直到找到比pivot值大的数
            while (nums[leftIndex] <= pivot && leftIndex < rightIndex) {
                leftIndex++;
            }

            //再从右边开始往左找，直到找到比pivot值小的数
            while (nums[rightIndex] >= pivot && leftIndex < rightIndex) {
                rightIndex--;
            }

            // 上面的循环结束表示找到了位置或者(leftIndex>=rightIndex)了，交换两个数在数组中的位置
            if (leftIndex < rightIndex) {
                //交换数据
                int tmp = nums[leftIndex];
                nums[leftIndex] = nums[rightIndex];
                nums[rightIndex] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        nums[endIndex] = nums[leftIndex];
        nums[leftIndex] = pivot;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        quickSortImpl(nums, startIndex, leftIndex - 1);
        quickSortImpl(nums, leftIndex + 1, endIndex);
    }

    /**
     * 归并排序
     * 理解归并排序的重点是理解递推公式和 merge() 合并函数
     *
     * @param nums
     * @return
     */
    public static int[] mergeSort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        mergeSortImpl(nums, 0, nums.length - 1);
        return nums;
    }

    /**
     * 递归排序子序列，每次递归完成时可以保证当前子序列有序，供前一次递归使用
     * @param nums
     * @param startIndex
     * @param endIndex
     */
    public static void mergeSortImpl(int[] nums, int startIndex, int endIndex) {
        // 当子序列中只有一个元素时结束递归
        if (startIndex < endIndex) {
            int midIndex = (startIndex + endIndex) / 2;
            //对左侧子序列进行递归排序
            mergeSortImpl(nums, startIndex, midIndex);
            //对右侧子序列进行递归排序
            mergeSortImpl(nums, midIndex + 1, endIndex);
            //合并
            merge(nums, startIndex, midIndex, endIndex);
        }
    }

    /**
     * 两路归并算法，两个排好序的子序列合并为一个子序列
     *
     * @param nums
     * @param startIndex
     * @param midIndex
     * @param endIndex
     */
    private static void merge(int[] nums, int startIndex, int midIndex, int endIndex) {
        int[] tmp = new int[endIndex - startIndex + 1];
        // p1、p2是左右两个子序列的检测指针，k是存放指针
        int p1 = startIndex, p2 = midIndex + 1, k = 0;
        while (p1 <= midIndex && p2 <= endIndex) {
            if (nums[p1] < nums[p2]) {
                tmp[k++] = nums[p1++];
            } else {
                tmp[k++] = nums[p2++];
            }
        }

        // 如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while (p1 <= midIndex) {
            tmp[k++] = nums[p1++];
        }

        // 如果第二个序列未检测完，直接将后面所有元素加到合并的序列中
        while (p2 <= endIndex) {
            tmp[k++] = nums[p2++];
        }

        // 复制回原数组
        for (int i = 0; i < tmp.length; i++) {
            nums[startIndex + i] = tmp[i];
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5864, -12333, 4151, -6239, -10306, 10866, -7013, 13195, -8855, 1150, -560, 3227, 10387, -2329, 5169, -19527, 2689, 597, 4255, -13697, 12495, 19719, 15995, 8991, -12859, 5601, 8195, 3943, 16216, -19677, 15590, 10316, -4255, 45, -6508, -5416, 4993, 15278, -6015, -18843, -8400, -6994, 3608, -7695, -14698, -2684, 8753, 18019, 3266, -10860, -14354, 8708, 19037, -16188, 4932, 1403, -10571, 18368, -9786, 13410, 1686, -17352, -13827, 6647};
        long start = System.nanoTime();
//        int[] res = SortArray912.bubbleSort(nums);
        int[] res = SortArray912.mergeSort(nums);
//        int[] res = SortArray912.quickSort(nums);

        long end = System.nanoTime();

        System.out.println(res);
        System.out.println((end - start) / 1000);
    }
}
