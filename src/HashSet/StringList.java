package HashSet;

public class StringList {

    Node head;

    public void add(String value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public boolean contains(String value) {
        Node current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean remove(String value) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.value.equals(value)) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public void clear() {
        head = null;
    }
}
