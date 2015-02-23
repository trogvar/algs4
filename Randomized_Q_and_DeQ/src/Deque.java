import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck").
 * A generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure.
 */

/**
 * @author d.plechystyy
 * 
 */
public class Deque<Item> implements Iterable<Item>
{
    private class Node
    {
        Item item;
        Node prev;
        Node next;
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque()
    {
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public void addFirst(Item item)
    {
        if (item == null)
            throw new NullPointerException("addFirst() called with a null argument");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (size > 0) {
            first.next = oldFirst;
            oldFirst.prev = first;
        } else
            last = first;

        size++;
    }

    public void addLast(Item item)
    {
        if (item == null)
            throw new NullPointerException("addLast() called with a null argument");

        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (size > 0) {
            last.prev = oldLast;
            oldLast.next = last;
        } else
            first = last;

        size++;
    }

    public Item removeFirst()
    {
        if (size == 0)
            throw new NoSuchElementException("removeFirst() called for empty deque");

        Item ret = first.item;
        first.item = null;
        if (first.next != null) {
            first.next.prev = null;
            first = first.next;
        } else
            first = null;
        size--;
        if (size <= 1)
            last = first;

        return ret;
    }

    public Item removeLast()
    {
        if (size == 0)
            throw new NoSuchElementException("removeLast() called for empty deque");

        Item ret = last.item;
        last.item = null;
        if (last.prev != null) {
            last.prev.next = null;
            last = last.prev;
        } else
            last = null;
        size--;
        if (size <= 1)
            first = last;

        return ret;
    }

    private void print()
    {
        System.out.print("Deque contents: ");
        for (Item i : this) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Deque<Integer> deq = new Deque<Integer>();
        deq.addFirst(15);
        deq.addLast(25);
        deq.addLast(35);
        deq.print();
        deq.removeLast();
        deq.print();
        deq.removeLast();
        deq.print();
        deq.removeFirst();
        deq.print();
        deq.addFirst(77);
        deq.addLast(88);
        deq.addFirst(99);
        deq.addLast(111);
        deq.print();
        System.out.println(deq.size());

    }

    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (current == null)
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
