package leetcode.skiplist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Skiplist {

    private List<HeadIndex<Integer>> headIndexList;
    private Random random;

    public Skiplist() {
        headIndexList = new ArrayList<>();
        HeadIndex<Integer> h0HeadIndex = new HeadIndex<>(Integer.MIN_VALUE, 0);
        headIndexList.add(h0HeadIndex);
        random = new Random();
    }

    public boolean search(int target) {
        Node<Integer> cur = headIndexList.get(headIndexList.size() - 1);
        int headLevel = headIndexList.size();
        while (cur.getRight() != null || cur.getDown() != null) {
            if (headLevel < 1) {
                throw new IllegalStateException("Head level must more than 1");
            } else if (headLevel == 1) {
                while (cur.getRight() != null && cur.getRight().val < target) {
                    cur = cur.getRight();
                }
                if (cur.getRight() != null && cur.getRight().val == target) {
                    return true;
                } else {
                    return false;
                }
            } else {
                while (cur.getRight() != null && cur.getRight().val < target) {
                    cur = cur.getRight();
                }
                if (cur.getRight() != null && cur.getRight().val == target) {
                    return true;
                } else {
                    cur = cur.getDown();
                    headLevel--;
                }
            }

        }
        return false;
    }

    public void add(int num) {
        Node<Integer> cur = headIndexList.get(0);
        while ((cur.getRight() != null && cur.getRight().getVal() < num)) {
            cur = cur.right;
        }
        //找到需要加入Node的地方
        Node addNode = new Node<>(num);
        if (cur.getRight() == null) {
            cur.right = addNode;
        } else {
            Node right = cur.getRight();
            cur.setRight(addNode);
            addNode.setRight(right);
        }
        cur = addNode;
        //先走竖线
        int nodeIndexCount = 1;
        //随机化是否扩展层数
        while (addLevel(random, nodeIndexCount)) {
            Node addLevelNode = new Node<>(num);
            addLevelNode.down = cur;
            cur = addLevelNode;
            //判断当层的headIndex是否存在, 再走横线
            if (headIndexList.size() <= nodeIndexCount) {
                HeadIndex<Integer> curHeadIndex = headIndexList.get(headIndexList.size() - 1);
                HeadIndex<Integer> addHeadIndex = new HeadIndex<>(Integer.MIN_VALUE,
                        nodeIndexCount);
                headIndexList.add(addHeadIndex);
                addHeadIndex.setDown(curHeadIndex);
            }
            Node<Integer> levelNodeCur = headIndexList.get(nodeIndexCount);
            while ((levelNodeCur.getRight() != null
                    && levelNodeCur.getRight().getVal() < cur.val)) {
                levelNodeCur = levelNodeCur.getRight();
            }
            if (levelNodeCur.getRight() == null) {
                levelNodeCur.setRight(cur);
            } else {
                Node right = levelNodeCur.getRight();
                levelNodeCur.setRight(cur);
                cur.setRight(right);
            }
            nodeIndexCount++;
        }
    }

    public boolean erase(int num) {
        Node<Integer> cur = headIndexList.get(headIndexList.size() - 1);
        int headLevel = headIndexList.size();
        List<Node<Integer>> needErasePrevNodes = new LinkedList<>();
        while (cur.getRight() != null || cur.getDown() != null) {
            if (headLevel < 1) {
                throw new IllegalStateException("Head level must more than 1");
            } else if (headLevel == 1) {
                while (cur.getRight() != null && cur.getRight().val < num) {
                    cur = cur.getRight();
                }
                if (cur.getRight() != null && cur.getRight().val == num) {
                    needErasePrevNodes.add(cur);
                }
                break;
            } else {
                while (cur.getRight() != null && cur.getRight().val < num) {
                    cur = cur.getRight();
                }
                if (cur.getRight() != null && cur.getRight().val == num) {
                    needErasePrevNodes.add(cur);
                }
                cur = cur.getDown();
                headLevel--;
            }
        }
        //最后清理needErasePrevNode
        if (needErasePrevNodes.size() <= 0) {
            return false;
        }
        for (int i = 0; i < needErasePrevNodes.size(); i++) {
            Node<Integer> prevNode = needErasePrevNodes.get(i);
            Node<Integer> curNode = prevNode.getRight();
            Node<Integer> nextNode = prevNode.getRight().getRight();
            prevNode.setRight(nextNode);
            curNode.setRight(null);
        }
        return true;
    }

    public boolean addLevel(Random random, int indexNodeCount) {
        return 0 == random.nextInt(2 << indexNodeCount);
    }

    public void printSkiplist() {
        for (int i = headIndexList.size() - 1; i >= 0; i--) {
            System.out.print("h" + i + ": ");
            Node cur = headIndexList.get(i);
            while (cur.right != null) {
                cur = cur.right;
                System.out.print(cur.val + " ");
            }
            System.out.println();
        }
    }
}

class Node<T> {

    T val;
    Node<T> right;
    Node<T> down;

    public Node(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getDown() {
        return down;
    }

    public void setDown(Node<T> down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return "Node{" + "val=" + val + '}';
    }
}

class HeadIndex<T> extends Node<T> {

    int level;

    public HeadIndex(T val, int level) {
        super(val);
        this.level = level;
    }

    @Override
    public String toString() {
        return "HeadIndex{" + "val=" + val + ", level=" + level + '}';
    }
}