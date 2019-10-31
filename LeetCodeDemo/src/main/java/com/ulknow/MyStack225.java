package com.ulknow;

/**
 * Created by Administrator
 * 2019-10-31
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用队列实现栈的下列操作：
 *
 *     push(x) -- 元素 x 入栈
 *     pop() -- 移除栈顶元素
 *     top() -- 获取栈顶元素
 *     empty() -- 返回栈是否为空
 *
 * 注意:
 *
 *     你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
 *     你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 *     你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 *
 *
 *
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */

public class MyStack225 {
    private Queue<Integer> q1 = new LinkedList<Integer>();
    private Queue<Integer> q2 = new LinkedList<Integer>();
    private int top;

    /** Initialize your data structure here. */
    public MyStack225() {

    }

    /** Push element x onto stack. */
    // 队列是通过链表来实现的，入队（add）操作的时间复杂度为 O(1)。
    public void push(int x) {
        q1.offer(x);
        top = x;
    }

    /** Removes the element on top of the stack and returns that element. */
    // 算法让 q1 中的 n 个元素出队，让 n−1 个元素从 q2 入队，在这里 n 是栈的大小。这个过程总共产生了 2n−1 次操作，时间复杂度为 O(n)。
    public int pop() {
        while (q1.size() > 1) {
            top = q1.poll();
            q2.offer(top);
        }

        Integer ret = q1.poll();

        Queue<Integer> tmp;
        tmp = q1;
        q1 = q2;
        q2 = tmp;

        return  ret;
    }

    /** Get the top element. */
    public int top() {
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return q1.isEmpty();
    }
}
