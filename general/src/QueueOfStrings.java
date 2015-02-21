public class QueueOfStrings 
{
    Node first, last;
    
    private class Node
    {
        String item;
        Node next;
    }
    
    public QueueOfStrings() 
    {
        first = null;
        last = null;
    }
    
    public void enqueue(String item)
    {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }
    
    public String dequeue()
    {
        String item = first.item;
        first.item = null;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }
    
    boolean isEmpty()
    {
        return first == null;
    }
}
