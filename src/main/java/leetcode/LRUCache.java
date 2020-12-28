package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private int capacity;
    private Map<Integer, Node<Integer, Integer>> map;
    private DoublyLinkedList doublyLinkedList;

    LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.doublyLinkedList = new DoublyLinkedList();
    }

    public int get(int k) {
        if (!map.containsKey(k)) {
            return -1;
        }
        int v = map.get(k).getVal();
        put(k, v);
        return v;
    }

    //如果k存在于Map, 会将k对应的node置于队列头，并更新value
    public void put(int k, int v) {
        if (map.containsKey(k)) {
            doublyLinkedList.remove(map.get(k));
        } else {
            if (map.size() == capacity) {
                //需要移除老的元素
                Node key = doublyLinkedList.removeLast();
                map.remove(key.getKey());
            }
        }
        Node<Integer, Integer> newNode = new Node<>(k, v);
        doublyLinkedList.addFirst(newNode);
        map.put(k, newNode);
    }
}

class DoublyLinkedList {

    private Node head;
    private Node tail;

    public void addFirst(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            Node cur = head;
            head = node;
            cur.setPrev(node);
            node.setNext(cur);
        }
    }

    //移除尾节点，返回key
    public Node removeLast() {
        if (tail == null) {
            throw new IllegalArgumentException("No tail element.");
        }
        Node res = tail;
        if (tail.getPrev() == null) {
            head = tail = null;
        } else {
            Node cur = tail.getPrev();
            cur.setNext(null);
            tail.setPrev(null);
            tail = cur;
        }
        return res;
    }

    //删除指定的node， 如果存在则删除， 不存在不删除
    public void remove(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node should not be null.");
        }
        if (node.getPrev() == null && node.getNext() == null) {
            return;
        } else if (node.getPrev() != null && node.getNext() == null) { //尾节点
            Node cur = node.getPrev();
            cur.setNext(null);
            tail = cur;
        } else if (node.getPrev() == null && node.getNext() != null) { //头节点
            Node cur = node.getNext();
            cur.setPrev(null);
            head = cur;
        } else { //中间的节点
            Node prev = node.getPrev();
            Node next = node.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }
    }
}

class Node<K, V> {

    private Node prev;
    private Node next;
    private K key;
    private V val;

    public Node(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Node{" + "key=" + key + ", val=" + val + '}';
    }
}
