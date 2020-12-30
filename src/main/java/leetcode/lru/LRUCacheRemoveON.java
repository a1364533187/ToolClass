package leetcode.lru;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//remove O(N)
public class LRUCacheRemoveON {

    private int capacity;
    private Map<Integer, Integer> map;
    private LinkedList<Integer> linkedList;

    LRUCacheRemoveON(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.linkedList = new LinkedList<>();
    }

    public int get(int k) {
        if (!map.containsKey(k)) {
            return -1;
        }
        int v = map.get(k);
        put(k, v);
        return v;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public LinkedList<Integer> getLinkedList() {
        return linkedList;
    }

    //会将<k, v>置为队列头
    public void put(int k, int v) {
        if (map.containsKey(k)) {
            linkedList.removeLastOccurrence(k);
            map.remove(k);
        }
        if (map.size() < capacity) {
            linkedList.addFirst(k);
        } else {
            int key = linkedList.removeLast();
            map.remove(key);
            linkedList.addFirst(k);
        }
        map.put(k, v);
    }

}