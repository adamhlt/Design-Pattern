package component.list;

import java.util.*;
import java.util.function.Predicate;

/**
 * ArrayList
 *
 * @param <T>
 */
public class ArrayList<T> implements Iterable<T> {
    /**
     * Node pointer to the first element of the list if it exist
     */
    private Node<T> _first;

    /**
     * Node pointer to the last element of the list if it exist
     */
    private Node<T> _last;

    /**
     * Integer of the size of the list
     */
    private int _size;

    /**
     * Generate an empty arrayList
     */
    public ArrayList() { _size = 0; }

    /**
     * Get size of the list
     * @return An integer of the size of the list
     */
    public int size() { return _size; }

    /**
     * Get the first item in the list if it exist
     *
     * @return the first item in the list
     */
    public T getFirst() { return _first._item; }

    /**
     * Get the last item in the list if it exist
     *
     * @return the last item in the list
     */
    public T getLast() { return _last._item; }

    /**
     * Add an item at the end of the list
     *
     * @param item to be add in the list
     */
    public void add( T item ) {
        if( _first == null ) {
            _first = new Node<>( item );
            _last = _first;
        } else {
            _last._next = new Node<>( item );
            _last._next._previous = _last;
            _last = _last._next;
        }
        _size++;
    }

    /**
     * Set an item at the specific position of the list
     *
     * @param item to be set
     * @param i the index of the position ]0;size()[
     */
    public void set( T item , int i ){
        if( i < 0 || i >= _size )
            throw new NullPointerException("try to set "+i+" in array of " + _size + " element(s)");
        else if( i == 0 )
            _first._item = item;
        else if( i == _size - 1 )
            _last._item = item;
        else {
            Node<T> node = _first;
            for(; i > 0; i--)
                node = node._next;
            node._item = item;
        }
    }

    /**
     * Remove an item at the specific position of the list
     *
     * @param i the index of the position ]0;size()[
     */
    public void remove( int i ) {
        if( i < 0 || i >= _size )
            throw new NullPointerException("try to remove "+i+" in array of " + _size + " element(s)");
        else if( i == 0 ) {
            _first = _first._next;
        } else {
            Node<T> node = getNode( i );
            node._previous._next = node._next;
        }
        _size--;
    }

    /**
     * Add an item at the front of the list
     *
     * @param item to be add in the list
     */
    public void addFront( T item ) {
        if( size() == 0 )
            add( item );
        else {
            Node<T> node = new Node<>( item );
            node._next = _first;
            _first._previous = node;
            _first = node;
        }
    }

    /**
     * Remove item if predicate if true
     *
     * @param filter use to check all item
     */
    public void removeIf( Predicate<T> filter) {
        Objects.requireNonNull(filter);
        final Iterator<T> each = iterator();
        while (each.hasNext())
            if (filter.test(each.next()))
                each.remove();
    }

    /**
     * Get item at specific index
     *
     * @param i the index of the position ]0;size()[
     * @return item that is find
     */
    public T get( int i ){
        return getNode( i )._item;
    }

    /**
     * Get node at specific index
     *
     * @param i the index of the position ]0;size()[
     * @return node that is find
     */
    private Node<T> getNode( int i ){
        if( i < 0 || i >= _size )
            throw new NullPointerException("try to get "+i+" in array of " + _size + " element(s)");
        else if( i == 0 )
            return _first;
        else if( i == _size - 1 )
            return _last;
        else {
            Node<T> node = _first;
            for(; i > 0; i--)
                node = node._next;
            return node;
        }
    }

    /**
     * Get the default iterator of the list
     *
     * @return Default iterator of the list
     */
    @Override
    public Iterator<T> iterator() {
        return new ForwardIterator<>( this );
    }

    /**
     * Convert this list to an array
     *
     * @return An array of all the list's items
     */
    public Object[] toArray() {
        Object[] result = new Object[_size];
        int i = 0;
        for ( Node<T> x = _first; x != null; x = x._next)
            result[i++] = x._item;
        return result;
    }

    /**
     * Sort the list using specific comparator
     *
     * @param c the comparator that will be use to sort all items
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> c) {
        Object[] a = toArray();
        Arrays.sort(a, (Comparator<Object>) c);
        int i = 0;
        for (Object e : a) {
            set((T)e,i++);
        }
    }

    /**
     * Private Node class use to store item , next and previous node
     *
     * @param <T>
     */
    private static class Node<T> {
        /**
         * item the is store in the node
         */
        private T _item;

        /**
         * Pointer to the previous node
         */
        private Node<T> _previous;

        /**
         * Pointer to the next node
         */
        private Node<T> _next;

        /**
         * Generate Node using item
         *
         * @param item that will be store in the bode
         */
        Node( T item ) { _item = item; }
    }
}
