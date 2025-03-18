package deque;

public class ArrayDeque <T> {
    /* initializes items and size */
    private T[] items;
    private int size;
    private int start;
    private int nextFirst;
    private int nextLast;
    /** Creates an empty ArrayDeque */
    public ArrayDeque(){
        int initialize_size = 1000;
        items = (T[]) new Object[initialize_size];
        size=0;
        start = initialize_size /2;
        nextFirst = start-1;
        nextLast = start+1;

    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return (size==0);
    }
    /** Returns the last item  */
    public T getLast(){
        if (size==0){
            return null;
        }
        return items[nextLast-1];
    }

    /** Adds a last item to the ArrayDeque */
    public void addLast(T x){
        if (size==0){
            items[start]= x;
        }
        else {
            items[nextLast]= x;
            nextLast = nextLast+1;
        }
        size= size+1;
    }

    /** Adds a first item to the beginning of the ArrayDeque */
    public void addFirst(T x){
        if (size==0){
            items[start]=x;
        }
        else {
            items[nextFirst]=x;
            nextFirst = nextFirst -1;
        }
        size = size+1;
    }

    /** Returns the first item of the ArrayDeque */
    public T getFirst(){
        if (size==0){
            return null;
        }
        return items[nextFirst+1];
    }

    /** Remove last item from the ArrayDeque */
    public T removeLast(){
        if (size==0){
            return null;
        }
        T last_item = getLast();
        items[nextLast-1] = null;
        nextLast = nextLast-1;
        size = size-1;
        return last_item;
    }
    public T removeFirst(){
        if (size==0){
            return null;
        }
        T first_item = getFirst();
        items[nextFirst+1] = null;
        nextFirst = nextFirst+1;
        size = size-1;
        return first_item;
    }

}
