import java.util.ArrayList;
import java.util.Iterator;

// Iterator interface
interface CreateIterator {
    abstract Iterator createIterator();
}

public abstract class GenericList<T> implements CreateIterator {

    // Encapsulated Node class
    public class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T value) {
            data = value;
            next = null;
        }
    }

    // Encapsulated Iterator class
    public class GLIterator<T> implements Iterator<T> {

        // Keep track of the current element
        Node<T> current;

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

    protected Node<T> head, tail;
    protected int length;

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
            tail = null;

        setLength(length - 1);

        return node;
    }

    public ArrayList<T> dumpList() {
        ArrayList<T> list = new ArrayList<T>(length);

        Node<T> node = getHead();
        while(node != null) {
            list.add(node.data);
            node = node.next;
        }

        return list;
    }

    public int getLength() {
        return length;
    }

    protected void setLength(int len) {
        length = len;
    }

    public Node<T> getHead() {
        return head;
    }

    protected void setHead(Node<T> node) {
        node.next = head;
        head = node;
    }
}