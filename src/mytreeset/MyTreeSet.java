package mytreeset;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public class MyTreeSet<E> extends AbstractSet<E> implements NavigableSet<E> {
    private static class Node<E> {
        E value;
        Node<E> left, right;

        Node(E value) {
            this.value = value;
        }
    }

    private Node<E> root;
    private int size;
    private final Comparator<? super E> comparator;
    private boolean removed;

    public MyTreeSet() {
        this(null);
    }

    public MyTreeSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private int compare(E a, E b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super E>) a).compareTo(b);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        E e = (E) o;
        Node<E> cur = root;
        while (cur != null) {
            int c = compare(e, cur.value);
            if (c == 0) {
                return true;
            }
            cur = c < 0 ? cur.left : cur.right;
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        if (root == null) {
            compare(e, e);
            root = new Node<>(e);
            size++;
            return true;
        }
        Node<E> cur = root;
        while (true) {
            int c = compare(e, cur.value);
            if (c == 0) {
                return false;
            }
            if (c < 0) {
                if (cur.left == null) {
                    cur.left = new Node<>(e);
                    break;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    cur.right = new Node<>(e);
                    break;
                }
                cur = cur.right;
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        removed = false;
        root = remove(root, (E) o);
        if (removed) {
            size--;
        }
        return removed;
    }

    private Node<E> remove(Node<E> node, E e) {
        if (node == null) {
            return null;
        }
        int c = compare(e, node.value);
        if (c < 0) {
            node.left = remove(node.left, e);
        } else if (c > 0) {
            node.right = remove(node.right, e);
        } else {
            removed = true;
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node<E> min = node.right;
            while (min.left != null) {
                min = min.left;
            }
            node.value = min.value;
            node.right = remove(node.right, min.value);
        }
        return node;
    }

    @Override
    public E first() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node<E> cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.value;
    }

    @Override
    public E last() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node<E> cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.value;
    }

    @Override
    public E pollFirst() {
        if (root == null) {
            return null;
        }
        E value = first();
        remove(value);
        return value;
    }

    @Override
    public E pollLast() {
        if (root == null) {
            return null;
        }
        E value = last();
        remove(value);
        return value;
    }

    @Override
    public E lower(E e) { // строго меньше
        Node<E> cur = root;
        E result = null;
        while (cur != null) {
            if (compare(cur.value, e) < 0) {
                result = cur.value;
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return result;
    }

    @Override
    public E floor(E e) { // меньше или равно
        Node<E> cur = root;
        E result = null;
        while (cur != null) {
            if (compare(cur.value, e) <= 0) {
                result = cur.value;
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return result;
    }

    @Override
    public E ceiling(E e) { // больше или равно
        Node<E> cur = root;
        E result = null;
        while (cur != null) {
            if (compare(cur.value, e) >= 0) {
                result = cur.value;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return result;
    }

    @Override
    public E higher(E e) { // строго больше
        Node<E> cur = root;
        E result = null;
        while (cur != null) {
            if (compare(cur.value, e) > 0) {
                result = cur.value;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return result;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public NavigableSet<E> headSet(E to, boolean inclusive) {
        MyTreeSet<E> result = new MyTreeSet<>(comparator);
        for (E e : this) {
            int c = compare(e, to);
            if (c < 0 || (c == 0 && inclusive)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public NavigableSet<E> tailSet(E from, boolean inclusive) {
        MyTreeSet<E> result = new MyTreeSet<>(comparator);
        for (E e : this) {
            int c = compare(e, from);
            if (c > 0 || (c == 0 && inclusive)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public NavigableSet<E> subSet(E from, boolean fromInclusive, E to, boolean toInclusive) {
        return tailSet(from, fromInclusive).headSet(to, toInclusive);
    }

    @Override
    public SortedSet<E> subSet(E from, E to) {
        return subSet(from, true, to, false);
    }

    @Override
    public SortedSet<E> headSet(E to) {
        return headSet(to, false);
    }

    @Override
    public SortedSet<E> tailSet(E from) {
        return tailSet(from, true);
    }


    @Override
    public NavigableSet<E> descendingSet() {
        return new DescendingSet();
    }

    private class DescendingSet extends AbstractSet<E> implements NavigableSet<E> {

        @Override
        public int size() {
            return MyTreeSet.this.size();
        }

        @Override
        public boolean contains(Object o) {
            return MyTreeSet.this.contains(o);
        }

        @Override
        public boolean add(E e) {
            return MyTreeSet.this.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return MyTreeSet.this.remove(o);
        }

        @Override
        public Iterator<E> iterator() {
            return MyTreeSet.this.descendingIterator();
        }

        @Override
        public Iterator<E> descendingIterator() {
            return MyTreeSet.this.iterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return MyTreeSet.this;
        }

        @Override
        public Comparator<? super E> comparator() {
            return (a, b) -> MyTreeSet.this.compare(b, a);
        }

        @Override
        public E first() {
            return MyTreeSet.this.last();
        }

        @Override
        public E last() {
            return MyTreeSet.this.first();
        }

        @Override
        public E pollFirst() {
            return MyTreeSet.this.pollLast();
        }

        @Override
        public E pollLast() {
            return MyTreeSet.this.pollFirst();
        }

        @Override
        public E lower(E e) {
            return MyTreeSet.this.higher(e);
        }

        @Override
        public E floor(E e) {
            return MyTreeSet.this.ceiling(e);
        }

        @Override
        public E ceiling(E e) {
            return MyTreeSet.this.floor(e);
        }

        @Override
        public E higher(E e) {
            return MyTreeSet.this.lower(e);
        }

        @Override
        public NavigableSet<E> subSet(E from, boolean fromInclusive, E to, boolean toInclusive) {
            return MyTreeSet.this.subSet(to, toInclusive, from, fromInclusive).descendingSet();
        }

        @Override
        public NavigableSet<E> headSet(E to, boolean inclusive) {
            return MyTreeSet.this.tailSet(to, inclusive).descendingSet();
        }

        @Override
        public NavigableSet<E> tailSet(E from, boolean inclusive) {
            return MyTreeSet.this.headSet(from, inclusive).descendingSet();
        }

        @Override
        public SortedSet<E> subSet(E from, E to) {
            return subSet(from, true, to, false);
        }

        @Override
        public SortedSet<E> headSet(E to) {
            return headSet(to, false);
        }

        @Override
        public SortedSet<E> tailSet(E from) {
            return tailSet(from, true);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator(false);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new TreeIterator(true);
    }

    private class TreeIterator implements Iterator<E> {
        private final boolean descending;
        private E next;
        private boolean hasNext;

        TreeIterator(boolean descending) {
            this.descending = descending;
            if (root != null) {
                hasNext = true;
                next = descending ? last() : first();
            }
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public E next() {
            if (!hasNext) {
                throw new NoSuchElementException();
            }
            E result = next;
            next = descending ? lower(result) : higher(result);
            hasNext = next != null;
            return result;
        }
    }
}