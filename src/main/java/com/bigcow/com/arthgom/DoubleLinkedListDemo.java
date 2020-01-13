package com.bigcow.com.arthgom;

/**
 * Create by suzhiwu on 2019/02/04
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        DoubleLinkedList dllist = new DoubleLinkedList();
        dllist.addNodeTail(new Node(1));
        dllist.addNodeTail(new Node(2));
        printDoubleLinkedListCyclic(dllist.head);
        //循环打印

    }

    public static void printDoubleLinkedListCyclic(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur.next != null) {
            System.out.println(cur.getValue());
            cur = cur.next;
        }
        System.out.println("cur value: " + cur.getValue());
        while (cur.prev != null) {
            System.out.println("" + cur.getValue());
            cur = cur.prev;
        }
        System.out.println("cur value: " + cur.getValue());
        return;
    }

    public static void printDoubleLinkedList(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.getValue());
            cur = cur.next;
        }

        return;
    }
}

class Node {

    public Node prev;
    public Node next;
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class DoubleLinkedList {

    public Node head;

    public Node addNodeTail(Node node) {
        if (head == null) {
            head = node;
            return head;
        }
        Node cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = node;
        node.prev = cur;

        return head;
    }
}
