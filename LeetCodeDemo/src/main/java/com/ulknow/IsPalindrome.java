package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-16
 */

/**
 * 请判断一个链表是否为回文链表
 *
 * 输入: 1->2
 * 输出: false
 *
 * 输入: 1->2->2->1
 * 输出: true
 * 
 * 由于回文串最重要的就是对称，那么最重要的问题就是找到那个中心，用快指针fast每步两格走，
 * 当它到达链表末端的时候，慢指针slow刚好到达中心，慢指针在过来的这趟路上还做了一件事，它把走过的节点反向了，
 * 在中心点再开辟一个新的指针compare用于往回走，而慢指针继续向前，当慢指针扫完整个链表，就可以判断这是回文串，否则就提前退出，
 * 总的来说时间复杂度按慢指针遍历一遍来算是O(n),空间复杂度因为只开辟了3个额外的辅助，所以是o(1)
 */
public class IsPalindrome {
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode slow_prev = null;
        ListNode slow_next = null;

        //保存前半部分翻转之后，用于与后半部分进行比较的起始节点
        ListNode compare = null;

        //判断是否偶数个node点
        boolean isNodeEventCount = false;

        while (slow != null) {
            if (fast != null) {
                //奇数个节点数，fast会遍历到最后一个节点
                //偶数个节点数，fast会遍历到倒数第二个节点
                if (fast.next == null) {
                    isNodeEventCount = false;
                    fast = null;
                } else {
                    isNodeEventCount = true;
                    fast = fast.next.next;
                }
            }

            //翻转前半部分链表
            slow_next = slow.next;
            slow.next = slow_prev;
            slow_prev = slow;
            slow = slow_next;

            //fast已经到了最后的节点
            if (fast == null) {
                if (isNodeEventCount) {
                    compare = slow_prev;
                } else {
                    //如果是奇数个节点数，去掉中间的节点，再进行比较
                    compare = slow_prev.next;
                }

                break;
            }
        }

        while (slow != null  && compare != null) {
            if (slow.val != compare.val) {
                return false;
            }
            slow = slow.next;
            compare = compare.next;
        }

        return true;
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(2);
        ListNode node5 = new ListNode(1);

        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;

        System.out.println(isPalindrome(node1));
    }
}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
