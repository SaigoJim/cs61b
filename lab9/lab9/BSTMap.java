package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @Saigo Jim Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /** (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /** Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /** Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.equals(p.key)) {
            return p.value;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        }
        return getHelper(key, p.left);
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        }
        if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        }
        if (key.equals(p.key)) {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySet(root);
    }

    private Set<K> keySet(Node T) {
        Set<K> keyset = new HashSet<>();
        if (T == null) {
            return keyset;
        }
        keyset.add(T.key);
        keyset.addAll(keySet(T.left));
        keyset.addAll(keySet(T.right));
        return keyset;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V removed = get(key);
        if (removed != null) {
            root = removeNode(key, root);
        }
        return removed;
    }
    private Node removeNode(K key, Node T) {
        if (T == null) {
            return null;
        }
        if (key.compareTo(T.key) > 0) {
            T.right = removeNode(key, T.right);
        } else if (key.compareTo(T.key) < 0) {
            T.left = removeNode(key, T.left);
        } else if (key.equals(T.key)) {
            size -= 1;
            if (T.left == null) {
                T = T.right;
            } else if (T.right == null) {
                T = T.left;
            } else {
                T.left = subLeftLargest(T.left, T);
            }
        }
        return T;
    }
    private Node subLeftLargest(Node l, Node node) {
        if (l.right == null) {
            node.key = l.key;
            node.value = l.value;
            return l.left;
        }
        l.right = subLeftLargest(l.right, node);
        return l;
    }
    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V removed = get(key);
        if (removed != null && removed.equals(value)) {
            root = removeNode(key, root);
            return removed;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
