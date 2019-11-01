package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-31
 */

import java.util.ArrayDeque;

/**
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 *
 * 返回滑动窗口中的最大值。
 *
 *
 *
 * 示例:
 *
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 *
 *   滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 *
 *
 * 提示：
 *
 * 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。
 *
 */
public class MaxSlidingWindow239 {

    //使用双端队列保存滑动窗口范围内最大值的索引，保证最大值在队首
    //
    //时间复杂度：O(N)，每个元素被处理两次- 其索引被添加到双向队列中和被双向队列删除。
    //空间复杂度：O(N)，输出数组使用了 O(N−k+1)空间，双向队列使用了 O(k)。
    private ArrayDeque<Integer> deque = new ArrayDeque<Integer>();

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];

        //避免出现滑动窗口中只包含一个元素和数组为空的情景
        if (k == 1 || n < 1) {
            return nums;
        }

        int i = 0;
        while (i < k - 1) {
            while (deque.size() > 0 && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
            i++;
        }

        while (i < n) {
            //先查看队首元素是否满足要求, 不在滑动窗口内，移除该下标
            if (deque.peekFirst() == i - k) {
                deque.pollFirst();
            }

            //从队尾遍历队列中的数据，如果待加入的数据比原来的要大，则移除小的数据
            while (deque.size() > 0 && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);

            //保存当前窗口最大值，也就是队首元素
            res[i - k + 1] = nums[deque.peekFirst()];

            i++;
        }

        return res;
    }

    public static void main(String[] args) {
        MaxSlidingWindow239 maxSlidingWindow239 = new MaxSlidingWindow239();
//        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] nums = new int[]{7, 2, 4};
        int[] ret = maxSlidingWindow239.maxSlidingWindow(nums, 2);

        for (int i : ret) {
            System.out.println(i);
        }
    }
}
