package com.ulknow;

import java.util.HashSet;

/**
 * Created by Administrator
 * 2019-10-29
 */

public class ListHasCycle141 {
    /**
     * 哈希表
     * 我们遍历所有结点并在哈希表中存储每个结点的引用（或内存地址）。
     * 如果当前结点为空结点 null（即已检测到链表尾部的下一个结点），那么我们已经遍历完整个链表，并且该链表不是环形链表。
     * 如果当前结点的引用已经存在于哈希表中，那么返回 true（即该链表为环形链表）。
     *
     * 时间复杂度：O(n)，对于含有 n 个元素的链表，我们访问每个元素最多一次。添加一个结点到哈希表中只需要花费 O(1) 的时间。
     * 空间复杂度：O(n)，空间取决于添加到哈希表中的元素数目，最多可以添加 n 个元素。
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode nextNode = head;
        HashSet<ListNode> tmp = new HashSet<>();
        tmp.add(head);
        while ((nextNode = nextNode.next) != null) {
            if (tmp.contains(nextNode)) {
                return true;
            }
            tmp.add(nextNode);
        }

        return false;
    }

    /**
     * 双指针
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode fast = head;
        ListNode slow = head;


        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
