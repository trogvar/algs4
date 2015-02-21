import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    Stack<Item> bag;
    private int size = 0;
    
    public Bag() 
    {
        bag = new Stack<Item>();
    }
    
    public void add(Item item)
    {
        bag.push(item);
        size++;
    }
    
    public int size() 
    {
        return size;
    }
    
    public Iterator<Item> iterator()
    {
        return bag.iterator();
    }
}
