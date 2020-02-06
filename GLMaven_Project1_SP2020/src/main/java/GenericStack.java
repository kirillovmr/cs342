public class GenericStack<T> extends GenericList<T> {

    public GenericStack(T value) {
        add(value);
    }

    @Override
    protected void add(T data) {
        Node<T> node = new Node(data);

        // Appending node
        node.next = getHead();
        setHead(node);

        // Setting tail if list was empty
        if (tail == null)
            tail = node;

        // Updating length
        setLength(length + 1);
    }

    public void push(T data) {
        add(data);
    }

    public T pop() {
        return delete().data;
    }
}
