package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-31
 */

import java.util.Stack;

/**
 * 使用栈实现队列的下列操作：
 *
 *     push(x) -- 将一个元素放入队列的尾部。
 *     pop() -- 从队列首部移除元素。
 *     peek() -- 返回队列首部的元素。
 *     empty() -- 返回队列是否为空。
 *
 * 示例:
 *
 * MyQueue queue = new MyQueue();
 *
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 *
 * 说明:
 *
 *     你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 *     你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 *     假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
 */
public class MyQueue232 {

    private Stack s1 = new Stack<Integer>();
    private Stack s2 = new Stack<Integer>();
    private int top;

    /** Initialize your data structure here. */
    public MyQueue232() {

    }

    /** Push element x to the back of queue. */
    // 时间复杂度：O(1)
    // 向栈压入元素的时间复杂度为O(1
    //
    // 空间复杂度：O(n)
    // 需要额外的内存来存储队列元素
    public void push(int x) {
        if (s1.empty()) {
            top = x;
        }
        s1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    // 时间复杂度： 摊还复杂度 O(1)，最坏情况下的时间复杂度 O(n)
    // 在最坏情况下，s2 为空，算法需要从 s1 中弹出 n 个元素，然后再把这 n 个元素压入 s2，在这里n代表队列的大小。
    // 这个过程产生了 2n 步操作，时间复杂度为 O(n)。
    // 但当 s2 非空时，算法就只有 O(1) 的时间复杂度。
    //
    // 空间复杂度 ：O(1)
    public int pop() {
        if (s2.empty()) {
            while (!s1.empty()) {
                s2.push(s1.pop());
            }
        }
        return (int) s2.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (s2.empty()) {
            return top;
        }
        return (int) s2.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return s1.empty() && s2.empty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
