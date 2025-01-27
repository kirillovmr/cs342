import java.util.ArrayList;
import java.util.Iterator;

public abstract class GenericList<T> implements CreateIterator<T>, Iterable<T> {

    // Encapsulated Node class
    public class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T value) {
            data = value;
            next = null;
        }
    }

    // Encapsulated GLIterator class
    public class GLIterator<T> implements Iterator<T> {

        // Keep track of the current element
        private Node<T> current;

        // Initialize iterator
        GLIterator(Node<T> head) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext())
                return null;

            T data = current.data;
            current = current.next;
            return data;
        }
    }

    // Members to manage the list
    private Node<T> head, tail;
    private int length;

    // Default initialization
    GenericList() {
        head = tail = null;
        length = 0;
    }

    // Returns iterator that starts from the head of the list
    public Iterator<T> createIterator() {
        return new GLIterator<T>(head);
    }

    // Implementing Iterable interface
    public Iterator<T> iterator() {
        return new GLIterator<T>(head);
    }

    // Prints the items of the list, one value per line
    public void print() {
        if (length == 0) {
            System.out.println("Empty List");
            return;
        }

        Node<T> node = getHead();
        while(node != null) {
            System.out.println(node.data);
            node = node.next;
        }
    }

    // Adds the value to the list
    protected abstract void add(T data);

    // Returns the first value of the list and deletes the node
    protected Node<T> delete() {
        // If list is empty
        if (getHead() == null)
            return null;

        // Back reference to deleted element
        Node<T> node = getHead();

        // Setting next element as head
        if (getHead() != null)
            setHead(head.next);

        // Deleting tail if needed
        if (getHead() == null)
            setTail(null);

        setLength(length - 1);
        return node;
    }

    // Returns a list as an array of values
    public ArrayList<T> dumpList() {
        ArrayList<T> list = new ArrayList<T>(length);

        Node<T> node = getHead();
        while(node != null) {
            list.add(node.data);
            node = node.next;
        }

        // Making the list empty
        setHead(null);
        setTail(null);
        setLength(0);

        return list;
    }

    public int getLength() { return length; }

    protected void setLength(int len) { length = len; }

    protected Node<T> getHead() { return head; }

    protected void setHead(Node<T> node) { head = node; }

    protected Node<T> getTail() { return tail; }

    protected void setTail(Node<T> node) { tail = node; }
}