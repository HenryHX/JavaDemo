package com.ulknow;

/**
 * Created by Administrator
 * 2019-11-04
 */

/**
 * Your Skiplist object will be instantiated and called as such:
 * Skiplist obj = new Skiplist();
 * boolean param_1 = obj.search(target);
 * obj.add(num);
 * boolean param_3 = obj.erase(num);
 *
 * bool search(int target) : 返回target是否存在于跳表中。
 * void add(int num): 插入一个元素到跳表。
 * bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
 * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
 */
public class Skiplist1206 {
    public Skiplist1206() {

    }

    public boolean search(int target) {

        return false;
    }

    public void add(int num) {

    }

    public boolean erase(int num) {

        return false;
    }

    /**
     * 双向列表
     */
    class ListNode {
        private int val;
        private ListNode prev;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
