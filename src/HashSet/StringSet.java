package HashSet;

public class StringSet {
    private StringList[] table;
    private int size;
    private int capacity;

    public StringSet() {
        capacity = 4;
        table = new StringList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new StringList();
        }
        size = 0;
    }

    private int getIndex(String element) {
        int hash = FnvHash.hash(element);
        return Math.abs(hash) % capacity;
    }

    public void put(String element) {
        if (contains(element)) {
            return;
        }

        if (size >= capacity) {
            resize();
        }

        int index = getIndex(element);
        table[index].add(element);
        size++;
    }

    public boolean contains(String element) {
        int index = getIndex(element);
        return table[index].contains(element);
    }

    public boolean remove(String element) {
        int index = getIndex(element);
        if (table[index].remove(element)) {
            size--;
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i].clear();
        }
        size = 0;
    }

    private void resize() {
        int oldCapacity = capacity;
        StringList[] oldTable = table;

        capacity = oldCapacity * 2;
        table = new StringList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new StringList();
        }
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            Node current = oldTable[i].head;
            while (current != null) {
                put(current.value);
                current = current.next;
            }
        }
    }
}

