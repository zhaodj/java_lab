package com.zhaodj.foo.algorithm;

/**
 * Created by zhaodaojun on 2016/12/24.
 * 判断单链表是否存在环
 */
public class LinkedListChecker {

    private LinkedListChecker next;

    public LinkedListChecker getNext() {
        return next;
    }

    public void setNext(LinkedListChecker next) {
        this.next = next;
    }

    public static boolean checkRing(LinkedListChecker list){
        LinkedListChecker fast = list, slow = list;
        while(fast != null && fast.getNext() != null){
            fast = fast.getNext().getNext();
            slow = slow.getNext();
            if(slow == fast){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        LinkedListChecker head = new LinkedListChecker();
        LinkedListChecker node1 = new LinkedListChecker();
        LinkedListChecker node2 = new LinkedListChecker();
        LinkedListChecker node3 = new LinkedListChecker();
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node1);
        System.out.println(checkRing(head));
    }
}
