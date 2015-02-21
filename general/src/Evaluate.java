/**
 * Use Dijkstra's algorithm to evaluate arithmetic expression
 * ( - ignore
 * value - push on the operands stack
 * operation - push on the operations stack
 * ) - pop the last two operands and 1 last operation, perform it on the operands. push the result back into operands stack
 * @author Trogvar
 *
 */
public class Evaluate {

    public static void main(String[] args) {
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            if      (s.equals("("));
            else if  (s.equals("+")) 
                ops.push(s);
            else if  (s.equals("-")) 
                ops.push(s);
            else if  (s.equals("*")) 
                ops.push(s);
            else if  (s.equals("/")) 
                ops.push(s);
            else if  (s.equals(")")) {
                String op = ops.pop();
                switch (op) {
                case "+":
                    vals.push(vals.pop()+vals.pop());
                    break;
                case "-":
                    vals.push(vals.pop()-vals.pop());
                    break;
                case "*":
                    vals.push(vals.pop()*vals.pop());
                    break;
                case "/":
                    vals.push(vals.pop()/vals.pop());
                default:
                    break;
                }
            }
            else if(s.equals("^")) break;
            else vals.push(Double.parseDouble(s));
        }
        StdOut.println(vals.pop());
    }
}
