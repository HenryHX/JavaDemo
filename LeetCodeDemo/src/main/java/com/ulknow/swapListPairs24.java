package com.ulknow;

/**
 * Created by Administrator
 * 2019-11-01
 */

/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 *
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 *
 *
 * 示例:
 *
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 *
 */
public class swapListPairs24 {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        int index = 0;
        ListNode cur = head;
        ListNode next;
        ListNode pre = null;
        ListNode newHead = null;
        while ((next = cur.next) != null) {
            //偶数索引，表示需要它将和next node 交换
            if (index % 2 == 0) {
                //两两交换
                cur.next = next.next;
                next.next = cur;
                //如果是首节点，则不需要处理前置节点
                if (pre != null) {
                    pre.next = next;
                }
                //记录新链表的节点，只在index==0时才能获取
                if (index == 0) {
                    newHead = next;
                }
            } else {
                //奇数索引，需要记录该节点为pre，为了在下一个偶数节点交换完成后，连接到新的偶数节点
                pre = cur;
                //奇数索引，不交换节点，直接获取下一个偶数节点
                cur = next;
            }
            index++;
        }


        return newHead;
    }

    /**
     * 增加一个虚拟头结点，减少判断，可与swapPairs进行比较
     * @param head
     * @return
     */
    public ListNode swapPairs2(ListNode head) {
        //增加一个虚拟头结点
        ListNode tmp = new ListNode(0);
        tmp.next = head;

        ListNode pre = tmp;

        while (pre.next != null && pre.next.next != null) {
            ListNode node1 = pre.next;
            ListNode node2 = pre.next.next;
            //交换node1和node2
            pre.next = node2;
            node1.next = node2.next;
            node2.next = node1;

            pre = node1;
        }

        return tmp.next;
    }

    /**
     * 递归
     * @param head
     * @return
     */
    public ListNode swapPairs3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode next = head.next;

        head.next = swapPairs3(next.next);
        next.next = head;

        return next;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
