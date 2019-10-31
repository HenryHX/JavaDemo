package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-28
 */

/**
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 */
public class ReverseList206 {

    /**
     * 迭代
     * 时间复杂度：O(n)，假设 n 是列表的长度，时间复杂度是 O(n)。
     * 空间复杂度：O(1)。
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        ListNode next;

        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }


    /**
     * 递归实现
     *
     * 不妨假设链表为1，2，3，4，5。按照递归，当执行
     * reverseList(5)的时候返回了5这个节点，reverseList(4)中的p就是5这个节点，
     * reverseList(4)接下来执行完之后，5->next = 4, 4->next = null。这时候返回了p这个节点，也就是链表5->4->null，
     * reverseList(3)，代码解析为4->next = 3,3->next = null，这个时候p就变成了，5->4->3->null, 
     * reverseList(2), 
     * reverseList(1)依次类推，p就是:5->4->3->2->1->null
     *
     *
     * 时间复杂度：O(n)，假设 n 是列表的长度，那么时间复杂度为 O(n)。
     * 空间复杂度：O(n)，由于使用递归，将会使用隐式栈空间。递归深度可能会达到 n 层。
     *
     * @param head
     * @return
     */
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;

        return p;
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}

