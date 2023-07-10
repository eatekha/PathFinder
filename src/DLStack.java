public class DLStack<T> implements DLStackADT<T>{

    private DoubleLinkedNode<T> top;
    private int numItems;

    public DLStack(){
        top = null;
        numItems = 0;
    }

    /**
     *
     * @param dataItem: takes data item and pushes in to top of stack
     *
     */
    public void push(T dataItem){
        numItems ++;
        DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T> (dataItem);

        // If stack is empty
        if(top == null) {
            top = newNode;
            newNode.setNext(null);
            newNode.setPrevious(null);

        }
        //Adding to a stack w at least 1 element
        else {
            top.setNext(newNode);
            newNode.setPrevious(top);
            //for next instance
            top = newNode;
        }
    }

    /**
     *
     * @return front, which is the former top of the stack
     * @throws EmptyStackException: if stack is empty
     * decreases count and reassigns top
     */
    public T pop() throws EmptyStackException{
        DoubleLinkedNode<T> front = top;
        if (isEmpty()) throw new EmptyStackException("DLStack");

        top = top.getPrevious();
        //top is now redefined
        numItems--;
        return front.getElement();
    }

    /**
     *
     * @param k: position of element to be popped
     * @return deleted node
     * @throws InvalidItemException
     */
    public T pop(int k) throws InvalidItemException{
        DoubleLinkedNode<T> first = top;

        if (k > size() || k<= 0) throw new InvalidItemException ("DLStack");
        // Returns node that is getting popped
        for (int i = 1; i < k; i++) {
            first = first.getPrevious();
        }

        DoubleLinkedNode<T> nodeToDelete = first;
        numItems--;
        DoubleLinkedNode<T> current, predecessor;
        current = top;
        predecessor = null;

        //getting the node we need current
        if (current == nodeToDelete){
            top = top.getPrevious();
            return nodeToDelete.getElement();

        }
        while ((current != null) && (current != nodeToDelete)) {
            predecessor = current;
            current = current.getPrevious();
        }

        // after this current is equal to the node we need to delete
            if (predecessor != null)
                predecessor.setPrevious(current.getPrevious());
            else top = top.getNext();

            if (current != null && current.getPrevious() != null){
                current.getPrevious().setNext(predecessor);
            }
        return nodeToDelete.getElement();
    }

    public T peek() throws EmptyStackException{
        if (isEmpty()) throw new EmptyStackException("DLStack");
        return top.getElement();

    }
    public int size(){
        return numItems;
    }

    public boolean isEmpty(){
        return numItems == 0;
    }

    public DoubleLinkedNode<T> getTop(){
        return top;
    }

    public String toString(){
        DoubleLinkedNode<T> start = top;
        String returnString = "[";

        while (start!= null){
            returnString += start.getElement() + " ";
            start = start.getPrevious();
        }
        return returnString + "]";
    }

    public static void main(String[] args) {
        DLStack<Integer> stack = new DLStack<Integer>();

        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }
}
