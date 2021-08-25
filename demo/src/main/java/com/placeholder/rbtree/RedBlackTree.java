package com.placeholder.rbtree;

import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> implements Map<K, V> {
    private RedBlackTreeNode<K, V> root;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO: 2020/4/12
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO: 2020/4/12
        return false;
    }

    @Override
    public V get(Object key) {
        // TODO: 2020/4/12
        return null;
    }


    @Override
    public V put(K key, V value) {
        RedBlackTreeNode<K, V> addNode = insert(key, value);
        if (addNode != null) {
            fixAfterInsert(addNode);
        }
        // TODO: 2020/4/12
        return null;
    }

    private void fixAfterInsert(RedBlackTreeNode<K, V> node) {
        if (node == null) {
            return;
        }

        RedBlackTreeNode<K, V> p;
        while ((p = node.getParent()) != null && p.isRed()) {
            if (p.getParent().getLeft() == p) {
                // 父节点为左节点
                RedBlackTreeNode<K, V> sib = p.getParent().getRight();
                if (sib != null && sib.isRed()) {
                    sib.setRed(false);
                    p.setRed(false);
                    p.getParent().setRed(true);
                    node = p.getParent();
                } else {
                    if (p.getRight() == node) {
                        node = p;
                        leftRotate(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    rightRotate(p.getParent().getParent());
                    break;
                }
            } else {
                // 对称
                RedBlackTreeNode<K, V> sib = p.getParent().getLeft();
                if (sib != null && sib.isRed()) {
                    sib.setRed(false);
                    p.setRed(false);
                    p.getParent().setRed(true);
                    node = p.getParent();
                } else {
                    if (p.getLeft() == node) {
                        node = p;
                        rightRotate(node);
                    }
                    node.getParent().setRed(false);
                    node.getParent().getParent().setRed(true);
                    leftRotate(node.getParent().getParent());
                    break;
                }
            }
        }
        root.setRed(false);
    }

    private void leftRotate(RedBlackTreeNode<K, V> node) {
        if (node != null) {
            RedBlackTreeNode<K, V> r = node.getRight();
            node.setRight(r.getLeft());
            if (node.getRight() != null) {
                node.getRight().setParent(node);
            }

            if (node.getParent() == null) {
                root = r;
            } else if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(r);
            } else {
                node.getParent().setRight(r);
            }
            r.setLeft(node);
            r.setParent(node.getParent());
            node.setParent(r);
        }
    }

    private void rightRotate(RedBlackTreeNode<K, V> node) {
        if (node != null) {
            RedBlackTreeNode<K, V> l = node.getLeft();
            node.setLeft(l.getRight());
            if (node.getLeft() != null) {
                node.getLeft().setParent(node);
            }

            if (node.getParent() == null) {
                root = l;
            } else if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(l);
            } else {
                node.getParent().setRight(l);
            }
            l.setRight(node);
            l.setParent(node.getParent());
            node.setParent(l);
        }
    }

    private RedBlackTreeNode<K, V> insert(K key, V value) {
        if (root == null) {
            size++;
            root = new RedBlackTreeNode<>(key, value, null);
            return root;
        }
        RedBlackTreeNode<K, V> p;
        RedBlackTreeNode<K, V> c = root;

        do {
            int cmp = key.compareTo(c.getKey());
            p = c;
            if (cmp == 0) {
                c.setValue(value);
                return null;
            } else if (cmp > 0) {
                c = c.getRight();
            } else {
                c = c.getRight();
            }
        } while (c != null);

        RedBlackTreeNode<K, V> addNode = new RedBlackTreeNode<>(key, value, p);
        int cmp = key.compareTo(p.getKey());
        addNode.setParent(p);
        if (cmp > 0) {
            p.setRight(addNode);
        } else {
            p.setLeft(addNode);
        }
        size++;
        return addNode;
    }


    private RedBlackTreeNode<K, V> find(K key) {
        RedBlackTreeNode<K, V> n = root;
        while (n != null) {
            int cmp = key.compareTo(n.getKey());
            if (cmp == 0) {
                break;
            } else if (cmp > 0) {
                n = n.getRight();
            } else {
                n = n.getLeft();
            }
        }
        return n;
    }

    @Override
    public V remove(Object key) {
        K k = (K) key;
        if (k == null) {
            return null;
        }
        RedBlackTreeNode<K, V> toDelete = find(k);
        if (toDelete == null) {
            return null;
        }

        if (toDelete.getRight() != null) {
            // 如果有后继节点，则与后继节点交换位置，
            RedBlackTreeNode<K, V> s = toDelete.getRight();
            while (s.getLeft() != null) {
                s = s.getLeft();
            }
            toDelete.setKey(s.getKey());
            toDelete.setValue(s.getValue());
            toDelete = s;
        }

        if (toDelete.getLeft() == null && toDelete.getRight() == null) {
            fixAfterDelete(toDelete);
        } else {
//        1、子节点有红色，可以交换位置 TODO:
            RedBlackTreeNode<K, V> c = toDelete.getLeft() != null ? toDelete.getLeft() : toDelete.getRight();
            toDelete.setKey(c.getKey());
            toDelete.setValue(c.getValue());
            toDelete = c;
        }

        // 删除节点
        if (toDelete.getParent() != null) {
            if (toDelete.getParent().getLeft() == toDelete) {
                toDelete.getParent().setLeft(null);
            } else {
                toDelete.getParent().setRight(null);
            }
        }
        toDelete.setParent(null);

        size--;
        if (root != null) {
            root.setRed(false);
        }

        return null;
    }

    private void fixAfterDelete(RedBlackTreeNode<K, V> node) {
        // 如果当前是黑色，删除了就会违法【黑高相同】规则，需要借一个
        // 1、子节点有红色，可以交换位置 TODO:
        // 2、兄弟节点借一个
        // 3、父节点借一个
        while (!node.isRed() && node.getParent() != null) {
            RedBlackTreeNode<K, V> p = node.getParent();
            if (p.getLeft() == node) {
                RedBlackTreeNode<K, V> sib = p.getRight();
                if (sib.isRed()) {
                    sib.setRed(false);
                    p.setRed(true);
                    leftRotate(p);
                    sib = p.getRight();
                }

                if (!isRed(sib.getLeft()) && !isRed(sib.getRight())) {   // 兄弟节点为2-node
                    sib.setRed(true);
                    // node节点为被删节点或者被子节点借过去的节点，所以不变红
//                    node.setRed(true);

                    if (p.isRed()) {
                        // 如果父节点为红色，则直接借一个。结束循环，黑色，说明父节点是2-node，需要继续循环
                        p.setRed(false);
                        break;
                    } else {
                        p.setRed(false);
                        node = p; //继续循环
                    }
                } else {  // 兄弟节点为3/4-node
                    if (!isRed(sib.getRight())) { // 右节点非红，把右节点变红
                        sib.getLeft().setRed(false);
                        sib.setRed(true);
                        rightRotate(sib);
                        sib = p.getRight();
                    }

                    sib.setRed(p.isRed());
                    p.setRed(false);
                    sib.getRight().setRed(false);
                    leftRotate(p);
                    break;
                }

            } else {
                // 对称

                RedBlackTreeNode<K, V> sib = p.getLeft();
                if (sib.isRed()) {
                    sib.setRed(false);
                    p.setRed(true);
                    rightRotate(p);
                    sib = p.getLeft();
                }

                if (!isRed(sib.getLeft()) && !isRed(sib.getRight())) {   // 兄弟节点为2-node
                    sib.setRed(true);
                    // node节点为被删节点或者被子节点借过去的节点，所以不变红
//                    node.setRed(true);

                    if (p.isRed()) {
                        // 如果父节点为红色，则直接借一个。结束循环，黑色，说明父节点是2-node，需要继续循环
                        p.setRed(false);
                        break;
                    } else {
                        node = p; //继续循环
                    }
                } else {  // 兄弟节点为3/4-node
                    if (!isRed(sib.getLeft())) { // 右节点非红，把右节点变红
                        sib.getRight().setRed(false);
                        sib.setRed(true);
                        leftRotate(sib);
                        sib = p.getLeft();
                    }

                    sib.setRed(p.isRed());
                    p.setRed(false);
                    sib.getLeft().setRed(false);
                    rightRotate(p);
                    break;
                }
            }
        }
    }

    private boolean isRed(RedBlackTreeNode<K, V> node) {
        return node != null && node.isRed();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        // TODO: 2020/4/12
    }

    @Override
    public void clear() {
        // TODO: 2020/4/12
    }

    @Override
    public Set<K> keySet() {
        // TODO: 2020/4/12
        return null;
    }

    @Override
    public Collection<V> values() {
        // TODO: 2020/4/12
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        // TODO: 2020/4/12
        return null;
    }


/*============================ 校验树结构 =============================*/

    public void check() {
        checkParent();
        checkOrder();
        checkBlackHeight();
    }

    public void checkBlackHeight() {
        checkBlackHeight(Collections.singletonList(root));
    }

    private void checkBlackHeight(List<RedBlackTreeNode<K, V>> nodes) {
        boolean exists = getChildren(nodes.get(0)).get(0) != null;
        List<RedBlackTreeNode<K, V>> children = new ArrayList<>();
        for (RedBlackTreeNode<K, V> node : nodes) {
            List<RedBlackTreeNode<K, V>> c1 = getChildren(node);
            for (RedBlackTreeNode<K, V> c : c1) {
                if ((c == null) == exists) {
                    throw new RedBlackTreeException("checkBlackHeight");
                }
            }
            children.addAll(c1);
        }
        if (exists) {
            checkBlackHeight(children);
        }
    }

    private List<RedBlackTreeNode<K, V>> getChildren(RedBlackTreeNode<K, V> node) {
        ArrayList<RedBlackTreeNode<K, V>> children = new ArrayList<>();
        if (node.getLeft() != null && node.getLeft().isRed()) {
            children.add(node.getLeft().getLeft());
            children.add(node.getLeft().getRight());
        } else {
            children.add(node.getLeft());
        }

        if (node.getRight() != null && node.getRight().isRed()) {
            children.add(node.getRight().getLeft());
            children.add(node.getRight().getRight());
        } else {
            children.add(node.getRight());
        }
        return children;
    }


    public void checkOrder() {
        checkOrder(root);
    }

    private void checkOrder(RedBlackTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null && node.getLeft().getKey().compareTo(node.getKey()) > 0) {
            throw new RedBlackTreeException("checkOrder");
        }
        if (node.getRight() != null && node.getRight().getKey().compareTo(node.getKey()) < 0) {
            throw new RedBlackTreeException("checkOrder");
        }
        checkOrder(node.getLeft());
        checkOrder(node.getRight());
    }

    public void checkParent() {
        checkParent(root);
    }

    private void checkParent(RedBlackTreeNode<K, V> node) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null && node.getLeft().getParent() != node) {
            throw new RedBlackTreeException("checkParent");
        }
        if (node.getRight() != null && node.getRight().getParent() != node) {
            throw new RedBlackTreeException("checkParent");
        }
        checkParent(node.getLeft());
        checkParent(node.getRight());

    }


    public static void main(String[] args) {
        new RedBlackTree().buildTree();
    }

    public void buildTree() {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        tree.size = 21;

        RedBlackTreeNode<Integer, Integer> node8 = tree.root = new RedBlackTreeNode<>(8, null, false, null);
        RedBlackTreeNode<Integer, Integer> node4 = node8.left = new RedBlackTreeNode<>(4, null, false, node8);
        RedBlackTreeNode<Integer, Integer> node12 = node8.right = new RedBlackTreeNode<>(12, null, false, node8);
        RedBlackTreeNode<Integer, Integer> node2 = node4.left = new RedBlackTreeNode<>(2, null, false, node4);
        RedBlackTreeNode<Integer, Integer> node6 = node4.right = new RedBlackTreeNode<>(6, null, false, node4);
        RedBlackTreeNode<Integer, Integer> node1 = node2.left = new RedBlackTreeNode<>(1, null, false, node2);
        RedBlackTreeNode<Integer, Integer> node3 = node2.right = new RedBlackTreeNode<>(3, null, false, node2);
        RedBlackTreeNode<Integer, Integer>  node5 = node6.left = new RedBlackTreeNode<>(5, null, false, node6);
        RedBlackTreeNode<Integer, Integer>  node7 = node6.right = new RedBlackTreeNode<>(7, null, false, node6);

        RedBlackTreeNode<Integer, Integer>  node10 = node12.left = new RedBlackTreeNode<>(10, null, false, node12);
        RedBlackTreeNode<Integer, Integer>  node16 = node12.right = new RedBlackTreeNode<>(16, null, true, node12);

        RedBlackTreeNode<Integer, Integer>  node9 = node10.left = new RedBlackTreeNode<>(9, null, false, node10);
        RedBlackTreeNode<Integer, Integer> node11 = node10.right = new RedBlackTreeNode<>(11, null, false, node10);

        RedBlackTreeNode<Integer, Integer> node14 = node16.left = new RedBlackTreeNode<>(14, null, false, node16);
        RedBlackTreeNode<Integer, Integer> node20 = node16.right = new RedBlackTreeNode<>(20, null, false, node16);

        RedBlackTreeNode<Integer, Integer> node13 = node14.left = new RedBlackTreeNode<>(13, null, false, node14);
        RedBlackTreeNode<Integer, Integer> node15 = node14.right = new RedBlackTreeNode<>(15, null, false, node14);

        RedBlackTreeNode<Integer, Integer> node18 = node20.left = new RedBlackTreeNode<>(18, null, true, node20);
        RedBlackTreeNode<Integer, Integer> node21 = node20.right = new RedBlackTreeNode<>(21, null, false, node20);

        RedBlackTreeNode<Integer, Integer> node17 = node18.left = new RedBlackTreeNode<>(17, null, false, node18);
        RedBlackTreeNode<Integer, Integer> node19 = node18.right = new RedBlackTreeNode<>(19, null, false, node18);

//        tree.remove(10);
        tree.remove(14);
        tree.check();
    }

}

class RedBlackTreeException extends RuntimeException {
    public RedBlackTreeException(String message) {
        super(message);
    }
}

class RedBlackTreeNode<K, V> {
    K key;
    V value;
    boolean red = true;
    RedBlackTreeNode<K, V> left;
    RedBlackTreeNode<K, V> right;
    RedBlackTreeNode<K, V> parent;

    RedBlackTreeNode(K key, V value, boolean red, RedBlackTreeNode<K,V> parent) {
        this.key = key;
        this.value = value;
        this.red = red;
        this.parent = parent;
    }

    RedBlackTreeNode(K key, V value, RedBlackTreeNode<K,V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public RedBlackTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(RedBlackTreeNode<K, V> left) {
        this.left = left;
    }

    public RedBlackTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(RedBlackTreeNode<K, V> right) {
        this.right = right;
    }

    public RedBlackTreeNode<K, V> getParent() {
        return parent;
    }

    public void setParent(RedBlackTreeNode<K, V> parent) {
        this.parent = parent;
    }

}