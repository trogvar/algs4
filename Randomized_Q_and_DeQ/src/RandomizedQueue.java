import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure.
 */

/**
 * @author d.plechystyy
 * 
 */
public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] values;
    private int current = 0;

    @SuppressWarnings("unchecked")
    public RandomizedQueue()
    {
        values = (Item[]) new Object[1];
    }

    public boolean isEmpty()
    {
        return current == 0;
    }

    public int size()
    {
        return current;
    }

    public void enqueue(Item item)
    {
        if (item == null)
            throw new NullPointerException("Trying to enqueue() a null item");

        if (current == values.length)
            resize(values.length * 2);
        values[current++] = item;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize)
    {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < values.length && i < newSize; i++)
            copy[i] = values[i];
        values = copy;
    }

    private void swap(int from, int to)
    {
        Item ref = values[from];
        values[from] = values[to];
        values[to] = ref;
    }

    /**
     * returns a random item & remove it from array
     * 
     * @return Item
     */
    public Item dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException("trying to dequeue() from an empty deque");

        // pick a random index in the array and swap it with the last position
        // we do it because we will later return this random item from the last
        // index in array
        swap(current - 1, StdRandom.uniform(0, current));

        --current;
        Item item = values[current];
        values[current] = null;
        if (current > 0 && current == values.length / 4)
            resize(values.length / 2);

        return item;
    }

    /**
     * returns a random item without removing it from array
     * 
     * @return Item
     */
    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException("trying to sample() from an empty deque");

        return values[StdRandom.uniform(0, current)];
    }

    private void print()
    {
        System.out.print("Randomized queue contents: ");
        for (int i = 0; i < values.length; i++)
            System.out.print(values[i] + " ");
        System.out.println();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.print("Iteration 0 over RQ: ");
        for (Integer x : rq) {
            System.out.print(x + " ");
        }
        System.out.println();

        rq.enqueue(15);
        rq.enqueue(30);
        rq.enqueue(45);
        rq.enqueue(60);
        rq.enqueue(90);
        rq.print();

        System.out.print("Iteration 1 over RQ: ");
        for (Integer x : rq) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.print("Iteration 2 over RQ: ");
        for (Integer x : rq) {
            System.out.print(x + " ");
        }
        System.out.println();

        System.out.print("Iteration 3 over RQ: ");
        for (Integer x : rq) {
            System.out.print(x + " ");
        }
        System.out.println();

        rq.dequeue();
        rq.print();
        rq.dequeue();
        rq.print();
        rq.dequeue();
        rq.print();
        rq.dequeue();
        rq.print();
    }

    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int[] indices;
        private int position;

        public RandomizedQueueIterator()
        {
            position = 0;
            indices = new int[current];
            for (int i = 0; i < current; i++)
                indices[i] = i;

            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext()
        {
            return position < indices.length;
        }

        @Override
        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException("Attempted to get next() element in empty iterator");

            return values[indices[position++]];
        }

        public void remove()
        {
            throw new UnsupportedOperationException("tried to remove() in the RandomizedQueue iterator");
        }

    }
}
