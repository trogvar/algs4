import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private Node first = null;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node newnode = new Node();
        newnode.item = item;
        newnode.next = first;
        first = newnode;
    }

    public Item pop() {
        Item result = first.item;
        first = first.next;
        return result;
    }
    
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() { /* not supported */ }
        public Item next() 
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
