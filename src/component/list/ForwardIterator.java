package component.list;

import java.util.Iterator;

/**
 * Forward iterator, the most basic one
 *
 * @param <T>
 */
public class ForwardIterator<T> implements Iterator<T> {
    /**
     * Pointer to the parent arrayList
     */
    private final ArrayList<T> _arrayList;

    /**
     * Integer of the index position
     */
    private int _index = 0;

    /**
     * Generate Iterator from parent arrayList
     *
     * @param arrayList parent list
     */
    public ForwardIterator( ArrayList<T> arrayList ){
        this._arrayList = arrayList;
    }

    /**
     * Check if there one or more item next to the current
     *
     * @return true if there is one or more item next to the current
     */
    @Override
    public boolean hasNext() {
        return _index < _arrayList.size();
    }

    /**
     * Get the next item if it exist
     *
     * @return next item
     */
    @Override
    public T next() {
        if( hasNext() )
            return _arrayList.get(_index++);
        else
            throw new NullPointerException();
    }

    /**
     * Remove item from the array list
     */
    @Override
    public void remove() {
        _arrayList.remove(_index-1);
    }
}
