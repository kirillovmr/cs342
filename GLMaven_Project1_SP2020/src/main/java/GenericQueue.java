public class GenericQueue<T> extends GenericList<T> {

    public GenericQueue(T value) {
        super();
        add(value);
    }

    @Override
    protected void add(T data) {
        Node<T> node = new Node<T>(data);

        // Setting head if list is empty
        if (getHead() == null)
            setHead(node);

        // Appending the node to the end
        if (tail != null)
            tail.next = node;
        tail = node;

        // Updating length
        setLength(length + 1);
    }

    public void enqueue(T data) {
        add(data);
    }

    public T dequeue() {
        return delete().data;
    }
}
