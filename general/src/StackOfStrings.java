public class StackOfStrings
{
    private Node first = null;
    
    public StackOfStrings()
    {

    }

    public void push(String item)
    {
        Node n = new Node();
        n.item = item;
        n.next = first;
        first = n;

    }

    public String pop()
    {
        if (first == null) return null;
        String item = first.item;
        first = first.next;
        return item;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    private class Node
    {
        String item;
        Node next;
    }

    public static void main(String[] args)
    {
        StackOfStrings sos = new StackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(sos.pop());
            else
                sos.push(s);
        }
    }
}
