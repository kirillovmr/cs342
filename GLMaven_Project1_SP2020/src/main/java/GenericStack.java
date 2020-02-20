public class GenericStack<T> extends GenericList<T> {

    public GenericStack(T value) {
        super();
        add(value);
    }

    @Override
    protected void add(T data) {
        Node<T> node = new Node(data);

        // Appending node
        node.next = getHead();
        setHead(node);

        // Setting tail if list was empty
        if (getTail() == null)
            setTail(node);

        // Updating length
        setLength(getLength() + 1);
    }

    public void push(T data) {
        add(data);
    }

    public T pop() {
        Node<T> temp = delete();
        return temp == null ? null : temp.data;
    }
}
