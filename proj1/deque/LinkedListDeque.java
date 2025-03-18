package deque;

/* Item type should be generic: type T */
public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node next;
        public Node prev;
        public Node (T i, Node n, Node p){
            item = i;
            next = n;
            prev = p;
        }
    }
    /** initializes both items and size */
    private int size;
    /* the first and last item, if it exists, would be at sentinel.next (circular sentinel) */
    private Node sentinel;

    /** Creates an empty Deque. */
    public LinkedListDeque(){
        sentinel = new Node(null, null, null);
        size = 0;
    }
    public LinkedListDeque(T x){
        sentinel = new Node(null, null, null);
        sentinel.next = new Node (x, null, sentinel.prev);
        size = 1;
    }
    /**checks to see if the Deque is empty, e.g., size==0 */
    public boolean isEmpty(){
        return (size==0);
    }
    /** outputs the size of the deque **/
    public int size(){
        return size;
    }
    /** helper function to get the first item of the Deque */
    private Node getFirst(){
        return sentinel.next;
    }
    /**helper function to get the last item of the Deque */
    private Node getLast(){
        return sentinel.prev;
    }
    /** Add to the front of the Deque */
    public void addFirst(T item){
        //first node: .prev is sentinel, .next is null //
        sentinel.next = new Node(item, null, sentinel);
        if (size==0){
            sentinel.prev = sentinel.next;
        }
        size = size +1;
    }
    /** Adds to the end of the Deque */
    public void addLast(T item){
        // ** last item's next is sentinel, previous is sentinel.prev (e.g., the previous last item)*/
        if (size==0){
            sentinel.prev = new Node (item, sentinel, sentinel);
            sentinel.next = sentinel.prev;
        }
        else {
            sentinel.prev = new Node(item, sentinel, sentinel.prev);
            sentinel.prev.prev.next = sentinel.prev;
        }
        size = size+1;
    }
    /** Removes the first item of the deque */
    public T removeFirst(){
        // empty deque //
        if (sentinel.next==null){
            return null;
        }
        // if deque has 1+ nodes //
        T first_item = getFirst().item;
        if (sentinel.next.next!=null){
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
        }
        //if deque only has 1 node//
        else {
            sentinel.next=null;
            sentinel.prev=null;
        }
        size = size-1;
        return first_item;
    }

    /** removes the last item of the deque */
    public T removeLast(){
        // there is a last item
        if (sentinel.prev==null){
            return null;
        }
        //only 1 node in the deque //
        T last_item = getLast().item;
        if (sentinel.prev.prev==sentinel){
            sentinel.prev = null;
            sentinel.next = null;
        }
        // the deque is empty //
        else {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.prev.next = sentinel;
        }
        size = size-1;
        return last_item;
    }

    //** get the item at given index of the deque */
    public T get(int index){
        Node p = sentinel;
        /* advance p to the ith of the list */
        while (index>0){
            p = p.next;
            index = index -1;
        }
        return p.next.item;
    }
    //** get the item at given index of the deque recursively*/
    public T getRecursive(int index){
        if (index>0) {
            return null
        }
        Node p = sentinel;
        return getRecursive(p.next, index).item;
        }
    // ** helper function for getRecursive */
    public Node getRecursive(Node current, int index){
        if (index==0){
            return null;
        }
        return getRecursive(current.next, index-1);
    }

    //** prints the Deque */
    public void printDeque() {
        Node p = sentinel;
        while (p.next!=sentinel){
            T print_item = p.next.item;
            System.out.print(print_item + " ");
            p = p.next;
        }
        // prints another new line
        System.out.println();
    }

}
